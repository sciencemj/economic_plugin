package sciencemj.shop.object;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Shop {
    public Entity entity;
    public Inventory frontInv;
    public Inventory backInv;
    public Player owner;

    public Shop(Entity entity, Inventory frontInv, Inventory backInv, Player owner){
        this.entity = entity;
        this.frontInv = frontInv;
        this.backInv = backInv;
        this.owner = owner;
    }
}
