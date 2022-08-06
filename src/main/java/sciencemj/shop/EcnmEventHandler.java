package sciencemj.shop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import sciencemj.shop.manage.wM;
import sciencemj.shop.object.Shop;

import java.util.ArrayList;
import java.util.HashMap;


public class EcnmEventHandler implements Listener {

    public static HashMap<Player, Inventory> playerInv = new HashMap<>();
    public static ArrayList<Shop> shops = new ArrayList<>();
    @EventHandler
    public void onPlayerLeftClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && p.isSneaking()){
            //open wallet inventory
            if(!playerInv.containsKey(p))
                playerInv.put(p, Ecnm.newInv());
            if (wM.get(p) == null)
                wM.set(p, 0D);
            playerInv.get(p).setItem(4, Ecnm.createItem(Material.GOLD_NUGGET,
                    ChatColor.YELLOW + "잔액: " + wM.get(p).intValue()));
            p.openInventory(playerInv.get(p));
        }
    }

    @EventHandler
    public void onInventoryClicked(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(playerInv.containsValue(e.getInventory())){
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals("상점 만들기")){
                Inventory frontInv = Bukkit.createInventory(null, 9, "STORE");
                Inventory backInv = Bukkit.createInventory(null, 27, "BACK_STORE");
                Entity merchant = p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
                Shop shop = new Shop(merchant, frontInv, backInv, p);
                shops.add(shop);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(wM.get(p) == null){
            wM.set(p, 0D);
            playerInv.put(p, Ecnm.newInv());
        }
    }
}
