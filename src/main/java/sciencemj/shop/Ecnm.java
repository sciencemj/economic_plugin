package sciencemj.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import sciencemj.shop.manage.ShopManager;
import sciencemj.shop.manage.WalletManager;

import java.io.File;

public final class Ecnm extends JavaPlugin {
    public static Inventory inv; // 인벤토리 기본 모델 -> 플레이어마다 인스턴트 생성 -> 리스트로 관리
    public static FileConfiguration config;
    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginDescriptionFile file = this.getDescription();
        getLogger().info(file.getName() + "version:" + file.getVersion() + " loaded"); //print description
        //load config---------------------------------------------------------------------------
        saveConfig();
        config = this.getConfig();
        File sfile = new File(getDataFolder(), "config.yml");
        if (sfile.length() == 0){
            getLogger().info("콘피그 파일 비었음");
            config.options().copyDefaults(true);
        }else {
            getLogger().info("콘피그 파일 안 비었음");
            WalletManager.loadData(); //load wallet data
            ShopManager.loadData();
        }
        //--------------------------------------------------------------------------------------
        this.getServer().getPluginManager().registerEvents(new EcnmEventHandler(), this); //add event listener
        this.getCommand("money").setExecutor((CommandExecutor) new EcnmCommand());

        for (Player p : this.getServer().getOnlinePlayers()) {
            p.sendMessage("hi");
        }

        // =======================================================================================
        inv = Bukkit.createInventory(null, 9, "WALLET");
        itemFill(inv, createItem(Material.BLUE_STAINED_GLASS_PANE, ""), 1, 9); //fill inv 1 to 7
        inv.setItem(4, createItem(Material.GOLD_NUGGET, "잔액"));
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
        Inventory inven = Bukkit.createInventory(null, 9, "WALLET");
        itemFill(inven, createItem(Material.BLUE_STAINED_GLASS_PANE, " "), 1, 9);
        inven.setItem(0, createItem(Material.BUNDLE, "상점 만들기"));
        inven.setItem(4, createItem(Material.GOLD_NUGGET, "잔액"));
        inven.setItem(0, createItem(Material.PAPER, "상점 만들기"));
        return inven;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        WalletManager.saveData();
        ShopManager.saveData();
        saveConfig();
    }
}
