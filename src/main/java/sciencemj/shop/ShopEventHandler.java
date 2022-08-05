package sciencemj.shop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;

public class ShopEventHandler implements Listener {

    public static HashMap<Player, Inventory> playerInv = new HashMap<>();
    @EventHandler
    public void onPlayerLeftClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && p.isSneaking()){
            //open wallet inventory
            if(!playerInv.containsKey(p))
                playerInv.put(p, Shop.newInv());
            if (wM.get(p) == null)
                wM.set(p, 0D);
            playerInv.get(p).setItem(4, Shop.createItem(Material.GOLD_NUGGET,
                    ChatColor.YELLOW + "잔액: " + wM.get(p).intValue()));
            p.openInventory(playerInv.get(p));
        }
    }

    @EventHandler
    public void onInventoryClicked(InventoryClickEvent e){
        if(playerInv.containsValue(e.getInventory())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(wM.get(p) == null){
            wM.set(p, 0D);
            playerInv.put(p, Shop.newInv());
        }
    }
}
