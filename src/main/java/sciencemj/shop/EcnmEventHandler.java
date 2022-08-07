package sciencemj.shop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sciencemj.shop.manage.ShopManager;
import sciencemj.shop.manage.WalletManager;
import sciencemj.shop.object.Shop;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EcnmEventHandler implements Listener {

    public static HashMap<Player, Inventory> playerInv = new HashMap<>();
    @EventHandler
    public void onPlayerLeftClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && p.getInventory().getItemInMainHand().getType().equals(Material.CLOCK)){
            //open wallet inventory
            if(!playerInv.containsKey(p))
                playerInv.put(p, Ecnm.newInv());
            if (WalletManager.get(p) == null)
                WalletManager.set(p, 0D);
            playerInv.get(p).setItem(4, Ecnm.createItem(Material.GOLD_NUGGET,
                    ChatColor.YELLOW + "잔액: " + WalletManager.get(p).intValue()));
            p.openInventory(playerInv.get(p));
        }
    }

    @EventHandler
    public void onInventoryClicked(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack item = e.getCurrentItem();
        if(playerInv.containsValue(inv) && e.getCurrentItem() != null){
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals("상점 만들기")){
                createStore(p, false);
                p.closeInventory();
            }
            e.setCancelled(true);
        }else{
            for (Shop shop : ShopManager.shops.values()){
                if (shop.frontInv.equals(inv)){ //상점이면
                    for(ItemStack stack : ShopManager.prices.keySet()){
                        if (item != null) {
                            if (stack.getType().equals(item.getType()) && item.getAmount() == stack.getAmount()) {
                                if (!shop.buying) {
                                    if (e.isLeftClick()) {
                                        if (WalletManager.wallet.get(p.getName()) >= ShopManager.prices.get(stack)) {
                                            p.getInventory().addItem(stack);
                                            WalletManager.add(p, -ShopManager.prices.get(stack));
                                        } else {
                                            p.sendMessage(ChatColor.RED + "잔액이 부족합니다");
                                            p.closeInventory();
                                        }
                                    } else if (e.isRightClick()) {
                                        if (WalletManager.wallet.get(p.getName()) >= ShopManager.prices.get(stack)/stack.getAmount() * 64) {
                                            p.getInventory().addItem(new ItemStack(stack.getType(), 64));
                                            WalletManager.add(p, -ShopManager.prices.get(stack)/stack.getAmount() * 64);
                                        } else {
                                            p.sendMessage(ChatColor.RED + "잔액이 부족합니다");
                                            p.closeInventory();
                                        }
                                    }
                                }else {
                                    if (e.isLeftClick()) {
                                        if (p.getInventory().containsAtLeast(stack, 1)) {
                                            p.getInventory().removeItem(new ItemStack(stack.getType(), stack.getAmount()));
                                            p.updateInventory();
                                            WalletManager.add(p, ShopManager.prices.get(stack));
                                        } else {
                                            p.sendMessage(ChatColor.RED + "상품이 부족합니다");
                                            p.closeInventory();
                                        }
                                    } else if (e.isRightClick()) {
                                        if (p.getInventory().containsAtLeast(new ItemStack(stack.getType(), 1), 64)) {
                                            p.getInventory().removeItem(new ItemStack(stack.getType(), 64));
                                            p.updateInventory();
                                            WalletManager.add(p, ShopManager.prices.get(stack)/stack.getAmount() * 64);
                                        } else {
                                            p.sendMessage(ChatColor.RED + "상품이 부족합니다");
                                            p.closeInventory();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e){
        Inventory inv = e.getInventory();
        for (Shop s : ShopManager.shops.values()){
            if(s.backInv.equals(inv)){
                s.frontInv.setContents(s.backInv.getContents());
                for (int i = 0; i < s.frontInv.getContents().length;i++){
                    if (s.frontInv.getContents()[i] != null) {
                        ItemStack stack = s.frontInv.getContents()[i];
                        for (ItemStack stack1 : ShopManager.prices.keySet()) {
                            if ((stack1.getType().equals(stack.getType())) && (stack1.getAmount() == stack.getAmount())) {
                                e.getPlayer().sendMessage("found same item");
                                ItemMeta meta = stack.getItemMeta();
                                List<String> lore = new ArrayList<>();
                                if (s.buying){
                                    lore.add("판매: " + ShopManager.prices.get(stack1));
                                }else {
                                    lore.add("구매: " + ShopManager.prices.get(stack1));
                                }
                                meta.setLore(lore);
                                stack.setItemMeta(meta);
                                s.frontInv.setItem(i, stack);
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerInteractOnEntity(PlayerInteractEntityEvent e){
        Entity entity = e.getRightClicked();
        Player p = e.getPlayer();
        if (ShopManager.shops.containsKey(entity.getUniqueId().toString())){
            Shop shop = ShopManager.shops.get(entity.getUniqueId().toString());
            if (p.isSneaking() && shop.owner.equals(p)){
                p.openInventory(shop.backInv);
            }else {
                p.openInventory(shop.frontInv);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(WalletManager.get(p) == null){
            WalletManager.set(p, 0D);
            playerInv.put(p, Ecnm.newInv());
        }
    }

    @EventHandler
    public void onEntityDead(EntityDeathEvent e){
        if (ShopManager.shops.containsKey(e.getEntity().getUniqueId().toString()))
            ShopManager.shops.remove(e.getEntity().getUniqueId().toString());
    }

    public static void createStore(Player p, boolean buying){
        Inventory frontInv = Bukkit.createInventory(null, 27, "상점");
        Inventory backInv = createBackInv();
        Entity merchant = p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
        merchant.setInvulnerable(false);
        merchant.setCustomNameVisible(true);
        if (buying) {
            merchant.setCustomName("구매 상점");
        }else {
            merchant.setCustomName("판매 상점");
        }
        ((LivingEntity) merchant).setAI(false);
        Shop shop = new Shop(merchant.getUniqueId().toString(), frontInv, backInv, p, buying);
        ShopManager.shops.put(merchant.getUniqueId().toString(), shop);
    }
    public static Inventory createBackInv(){
        Inventory inv = Bukkit.createInventory(null, 27, "상점 설정");
        return inv;
    }

    public static void updateStore(){
        for (Shop s : ShopManager.shops.values()){
            s.frontInv.setContents(s.backInv.getContents());
            for (int i = 0; i < s.frontInv.getContents().length;i++) {
                if (s.frontInv.getContents()[i] != null) {
                    ItemStack stack = s.frontInv.getContents()[i];
                    for (ItemStack stack1 : ShopManager.prices.keySet()) {
                        if ((stack1.getType().equals(stack.getType())) && (stack1.getAmount() == stack.getAmount())) {
                            ItemMeta meta = stack.getItemMeta();
                            List<String> lore = new ArrayList<>();
                            if (s.buying) {
                                lore.add("판매: " + ShopManager.prices.get(stack1));
                            } else {
                                lore.add("구매: " + ShopManager.prices.get(stack1));
                            }
                            meta.setLore(lore);
                            stack.setItemMeta(meta);
                            s.frontInv.setItem(i, stack);
                        }
                    }
                }
            }
        }
    }
}
