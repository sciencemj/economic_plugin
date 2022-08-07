package sciencemj.shop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sciencemj.shop.manage.ShopManager;
import sciencemj.shop.manage.WalletManager;
import sciencemj.shop.object.Shop;

public class EcnmCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equals("money")){
            if (sender instanceof Player){
                if(args.length < 2){
                    sender.sendMessage("this command require at least 2 args");
                }else {
                    Player p = Bukkit.getPlayer(args[0]);
                    if(args.length == 2){
                        sender.sendMessage(p.getName() + "'s money: " + WalletManager.get(p));
                    }else if (args.length == 3){
                        double amount = Double.parseDouble(args[2]);
                        switch (args[1]) {
                            case "set" -> {
                                WalletManager.set(p, amount);
                                sender.sendMessage(p.getName() + "'s money is now: " + WalletManager.get(p));
                            }
                            case "add" -> {
                                WalletManager.add(p, amount);
                                sender.sendMessage(p.getName() + "'s money is now: " + WalletManager.get(p));
                            }
                        }
                    }
                }
            }
        }else if (cmd.getName().equals("store")){
            if ( (sender instanceof Player)){
                if(args.length == 2){
                    if(args[0].equals("set")){
                        ItemStack item = new ItemStack(((Player) sender).getInventory().getItemInMainHand().getType(),
                                ((Player) sender).getInventory().getItemInMainHand().getAmount());
                        ShopManager.prices.put(item, Double.parseDouble(args[1]));
                        sender.sendMessage(ShopManager.prices.toString());
                    } else if (args[0].equals("create")) {
                        Player p = (Player) sender;
                        EcnmEventHandler.createStore(p, Boolean.parseBoolean(args[1]));
                    }
                }else if (args.length == 3){
                    if(args[0].equals("set")){
                        ItemStack item = new ItemStack(Material.getMaterial(args[1]), 1);
                        ShopManager.prices.put(item, Double.parseDouble(args[2]));
                        sender.sendMessage(ShopManager.prices.toString());
                    }
                }
            }
        }
        return true;
    }
}
