package sciencemj.shop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopEventHandler implements Listener {
    @EventHandler
    public void onPlayerLeftClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && p.isSneaking()){
            p.sendMessage("clicked");
        }
    }
}
