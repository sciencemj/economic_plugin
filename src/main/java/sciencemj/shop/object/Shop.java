package sciencemj.shop.object;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Shop {
    public static int entityId;
    public Inventory frontInv;
    public Inventory backInv;
    public Player owner;

    public Shop(int entityId, Inventory frontInv, Inventory backInv, Player owner){
        this.entityId = entityId;
        this.frontInv = frontInv;
        this.backInv = backInv;
        this.owner = owner;
    }

    public static boolean equals_id(int entityId_){
        if (entityId == entityId_)
            return true;
        else
            return false;
    }
}
