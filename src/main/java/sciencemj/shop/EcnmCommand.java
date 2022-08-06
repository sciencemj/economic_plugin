package sciencemj.shop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sciencemj.shop.manage.wM;

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
                        sender.sendMessage(p.getName() + "'s money: " + wM.get(p));
                    }else if (args.length == 3){
                        double amount = Double.parseDouble(args[2]);
                        switch (args[1]) {
                            case "set" -> {
                                wM.set(p, amount);
                                sender.sendMessage(p.getName() + "'s money is now: " + wM.get(p));
                            }
                            case "add" -> {
                                wM.add(p, amount);
                                wM.saveData();
                                sender.sendMessage(p.getName() + "'s money is now: " + wM.get(p));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
