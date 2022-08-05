package sciencemj.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Shop extends JavaPlugin {
    public static Inventory inv; // 인벤토리 기본 모델 -> 플레이어마다 인스턴트 생성 -> 리스트로 관리
    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginDescriptionFile file = this.getDescription();
        System.out.println(file.getName() + "version:" + file.getVersion() + " loaded"); //print description
        this.getServer().getPluginManager().registerEvents(new ShopEventHandler(), this); //add event listener
        this.getCommand("money").setExecutor((CommandExecutor) new ShopCommand());

        for (Player p : this.getServer().getOnlinePlayers()) {
            p.sendMessage("hi");
        }
        // =======================================================================================
        inv = Bukkit.createInventory(null, 7, "WALLET");
        itemFill(inv, createItem(Material.BLUE_STAINED_GLASS, ""), 1, 7); //fill inv 1 to 7
        inv.setItem(3, createItem(Material.GOLD_NUGGET, "잔액"));
        // =======================================================================================
    }

    public static ItemStack createItem(Material material, @NotNull String name) {
        ItemStack itemStack = new ItemStack(material, 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static void itemFill(Inventory inventory, ItemStack item, int countStart, int countEnd) { //[1,2,3,4,5,6,7...]
        if (countEnd == countStart - 1) {
            return;
        }
        inventory.setItem(countEnd - 1, item);
        itemFill(inventory, item, countStart, countEnd - 1);
    }

    public static Inventory newInv(){
        Inventory inven = Bukkit.createInventory(null, 7, "WALLET");
        itemFill(inven, createItem(Material.BLUE_STAINED_GLASS, ""), 1, 7);
        inven.setItem(3, createItem(Material.GOLD_NUGGET, "잔액"));

        return inven;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
