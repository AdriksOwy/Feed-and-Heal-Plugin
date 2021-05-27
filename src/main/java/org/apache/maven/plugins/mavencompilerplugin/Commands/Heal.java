package org.apache.maven.plugins.mavencompilerplugin.Commands;

import org.apache.maven.plugins.mavencompilerplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Heal implements CommandExecutor {

    Main plugin;

    public Heal(Main main) {
        plugin = main;
        main.getCommand("heal").setExecutor(this);
    }

    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        int cooldownTime = 10;
        if (cooldowns.containsKey(sender.getName())) {
            long secondsLeft = ((cooldowns.get(sender.getName()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
            if (secondsLeft > 0) {
                sender.sendMessage("§cCo tak szybko!? Zanim uzyjesz jeszcze raz komendy, odczekaj §6" + secondsLeft + " §csekund!");
                return true;
            }
        }
        cooldowns.put(sender.getName(), System.currentTimeMillis());

        if (sender.isOp()) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    eventHealth(player);

                    player.sendMessage("§8>> §aUleczyles/as sie!");
                }
            } else if (args.length == 1) {
                Player objective = Bukkit.getPlayerExact(args[0]);

                if (objective != null) {
                    eventHealth(objective);
                    if (objective == sender) {
                        sender.sendMessage("§8>> §aUleczyles/as sie!");
                    } else {
                        objective.sendMessage("§8>> §aZostales/as uleczony przez gracza §6" + sender.getName() + "§a!");
                        sender.sendMessage("§8>> §aUleczyles gracza §6" + objective.getDisplayName() + "§a!");
                    }
                } else {
                    sender.sendMessage("§cTego gracza nie ma na serwerze!");
                }
            } else {
                sender.sendMessage("§cUzycie: §8/heal [nick]");
            }
        } else {
            sender.sendMessage("§cNie posiadasz uprawnien do uzycia tej komendy!");
        }
        return false;
    }

    public void eventHealth(Player player) {
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(0);
    }
}