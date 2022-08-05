package sciencemj.shop;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class wM {
    public static HashMap<Player, Double> wallet = new HashMap<>();

    public static void add(Player p, Double amount){
        if (wallet.containsKey(p))
            wallet.put(p, wallet.get(p) + amount);
        else
            wallet.put(p, amount);
    }

    public static void set(Player p, Double amount){
        wallet.put(p, amount);
    }

    public static Double get(Player p){
        return wallet.get(p);
    }

}
