package sciencemj.shop.manage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import sciencemj.shop.Ecnm;
import sciencemj.shop.manage.DataManager;

import java.util.HashMap;
import java.util.UUID;

public class wM {
    public static HashMap<String, Double> wallet = new HashMap<>();

    public static void add(Player p, Double amount){
        if (wallet.containsKey(p))
            wallet.put(p.getName(), wallet.get(p) + amount);
        else
            wallet.put(p.getName(), amount);
    }

    public static void set(Player p, Double amount){
        wallet.put(p.getName(), amount);
    }

    public static Double get(Player p){
        if(wallet.containsKey(p.getName()))
            return wallet.get(p.getName());
        return null;
    }

    public static void saveData(){
        Ecnm.config.addDefault("wallet", wallet);
    }

    public static void loadData(){
        for(String s: Ecnm.config.getConfigurationSection("wallet").getKeys(false)){
            wallet.put(s, Ecnm.config.getDouble("wallet" + "." + s));
        }
    }

}
