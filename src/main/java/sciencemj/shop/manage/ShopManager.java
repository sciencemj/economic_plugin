package sciencemj.shop.manage;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sciencemj.shop.Ecnm;
import sciencemj.shop.object.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ShopManager {
    //public static ArrayList<Shop> shops = new ArrayList<>();
    //public static ArrayList<Integer> shopIds = new ArrayList<>();
    public static HashMap<Integer, Shop> shops = new HashMap<>();

    public static void saveData(){
        FileConfiguration config = Ecnm.config;
        ArrayList<Integer> uuids = new ArrayList<>();

        for(Shop shop : shops.values()){
            int uuid = shop.entityId; // uuid 아래 디렉으로 나머지 인수들 넣기
            uuids.add(uuid);
            config.addDefault("shop" + "." + uuid + ".frontInv", shop.frontInv.getContents()); // config.get("path) 로 구할 수 있음
            config.addDefault("shop" + "." + uuid + ".backInv", shop.backInv.getContents());
            config.addDefault("shop" + "." + uuid + ".owner", shop.owner);
        }
        config.addDefault("shop.uuids", uuids);
    }
    public static void loadData(){
        FileConfiguration config = Ecnm.config;
        List<Integer> uuids = config.getIntegerList("shop.uuids");
        for(int id : uuids){
            Shop shop = new Shop(id, stacksToInv((ItemStack[]) config.get("shop." + id + ".frontInv")),
                    stacksToInv((ItemStack[]) config.get("shop." + id + ".backInv")),
                    (Player) config.getOfflinePlayer("shop" + "." + id + ".owner"));
            shops.put(id, shop);
        }
    }

    public static Inventory stacksToInv(ItemStack[] itemStacks){
        Inventory inventory = Bukkit.createInventory(null, itemStacks.length);
        inventory.setContents(itemStacks);
        return inventory;
    }
}
