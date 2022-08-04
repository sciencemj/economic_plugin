package sciencemj.shop;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Shop extends JavaPlugin {
    public List<Player> wallet = new ArrayList<Player>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginDescriptionFile file = this.getDescription();

        System.out.println(file.getName() + "version:" + file.getVersion() + " loaded");

        this.getServer().getPluginManager().registerEvents(new ShopEventHandler(), this);

        for(Player p : this.getServer().getOnlinePlayers()){
            p.sendMessage("hi");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
