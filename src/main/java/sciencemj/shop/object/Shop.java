package sciencemj.shop.object;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Shop {
    public String uuid;

    public boolean buying;
    public Inventory frontInv;
    public Inventory backInv;
    public Player owner;

    public Shop(String uuid, Inventory frontInv, Inventory backInv, Player owner, boolean buying){
        this.uuid = uuid;
        this.frontInv = frontInv;
        this.backInv = backInv;
        this.owner = owner;
        this.buying = buying;
    }
}
