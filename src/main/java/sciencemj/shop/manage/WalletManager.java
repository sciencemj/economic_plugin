package sciencemj.shop.manage;

import org.bukkit.entity.Player;
import sciencemj.shop.Ecnm;

import java.util.HashMap;

public class WalletManager {
    public static HashMap<String, Double> wallet = new HashMap<>();

    public static void add(Player p, Double amount){
        if (wallet.containsKey(p.getName()))
            wallet.put(p.getName(), wallet.get(p.getName()) + amount);
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
        Ecnm.config.set("wallet", wallet);
    }

    public static void loadData(){
        for(String s: Ecnm.config.getConfigurationSection("wallet").getKeys(false)){
            wallet.put(s, Ecnm.config.getDouble("wallet" + "." + s));
        }
    }

}
