package sciencemj.shop.manage;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sciencemj.shop.Ecnm;
import sciencemj.shop.object.Shop;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ShopManager {
    //public static ArrayList<Shop> shops = new ArrayList<>();
    //public static ArrayList<Integer> shopIds = new ArrayList<>();
    public static HashMap<String, Shop> shops = new HashMap<>();
    public static HashMap<ItemStack, Double> prices = new HashMap<>();

    public static void saveData(){
        FileConfiguration config = Ecnm.config;
        ArrayList<String> uuids = new ArrayList<>();
        ArrayList<ItemStack> stacks = new ArrayList<>();
        ArrayList<Double> price = new ArrayList<>();
        config.set("shop", null);
        config.set("prices", null);
        config.set("prices_cost", null);
        for(Shop shop : shops.values()){
            String uuid = shop.uuid; // uuid 아래 디렉으로 나머지 인수들 넣기
            uuids.add(uuid);
            config.set("shop" + "." + uuid + ".frontInv", shop.frontInv.getContents()); // config.get("path) 로 구할 수 있음
            config.set("shop" + "." + uuid + ".backInv", shop.backInv.getContents());
            config.set("shop" + "." + uuid + ".owner", shop.owner);
            config.set("shop." + uuid + ".buying", shop.buying);
        }
        for(ItemStack s : prices.keySet()){
            stacks.add(s);
            price.add(prices.get(s));
        }
        config.set("prices", stacks);
        config.set("prices_cost", price);
        config.set("shop.uuids", uuids);
    }
    public static void loadData(){
        FileConfiguration config = Ecnm.config;
        prices.clear();
        List<String> uuids = config.getStringList("shop.uuids");
        ArrayList<ItemStack> s = (ArrayList) config.get("prices");
        List<Double> price = config.getDoubleList("prices_cost");
        for (int i = 0; i < price.size(); i++) {
            prices.put(s.get(i), price.get(i));
        }
        for(String id : uuids){
            Shop shop = new Shop(id, stacksToInv((ArrayList) config.get("shop." + id + ".frontInv"), "상점"),
                    stacksToInv((ArrayList) config.get("shop." + id + ".backInv"), "상점 설정"),
                    (Player) config.getOfflinePlayer("shop" + "." + id + ".owner"), config.getBoolean("shop."+id+".buying"));
            shops.put(id, shop);
        }
    }

    public static Inventory stacksToInv(ArrayList<ItemStack> itemStacks, String name){
        Inventory inventory = Bukkit.createInventory(null, itemStacks.size(), name);
        for(int i = 0;i < itemStacks.size();i++){
            inventory.setItem(i, itemStacks.get(i));
        }
        return inventory;
    }
}
