package org.apache.maven.plugins.mavencompilerplugin.Commands;

import org.apache.maven.plugins.mavencompilerplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Feed implements CommandExecutor {

    Main plugin;

    public Feed(Main main) {
        plugin = main;
        main.getCommand("feed").setExecutor(this);
    }

    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        int cooldownTime = 10;
        if(cooldowns.containsKey(sender.getName())) {
            long secondsLeft = ((cooldowns.get(sender.getName()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
            if(secondsLeft > 0) {
                sender.sendMessage("§cCo tak szybko!? Zanim uzyjesz jeszcze raz komendy, odczekaj §6" + secondsLeft + " §csekund!");
                return true;
            }
        }
        cooldowns.put(sender.getName(), System.currentTimeMillis());

        if(sender.isOp()) {
            if(args.length == 0) {
                if(sender instanceof Player) {
                    Player player = (Player) sender;

                    player.setFoodLevel(20);
                    player.sendMessage("§8>> §aTwój glód zaspokoil sie!");
                }
            } else if(args.length == 1) {
                Player objective = Bukkit.getPlayerExact(args[0]);

                if(objective != null) {
                    objective.setFoodLevel(20);
                    if(objective == sender) {
                        sender.sendMessage("§8>> §aTwój glód zaspokoil sie!");
                    } else {
                        objective.sendMessage("§8>> §6" + sender.getName() + " §azaspokoil twój glód!");

                        sender.sendMessage("§8>> §aZaspokoiles/as glód gracza §6" + objective.getDisplayName() + "§a!");
                    }
                } else {
                    sender.sendMessage("§cTego gracza nie ma na serwerze!");
                }
            } else {
                sender.sendMessage("§cUzycie: §8/feed [nick]");
            }
        } else {
            sender.sendMessage("§cNie posiadasz uprawnien do uzycia tej komendy!");
        }
        return false;
    }
}