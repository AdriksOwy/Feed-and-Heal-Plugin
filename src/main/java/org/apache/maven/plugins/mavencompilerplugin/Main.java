package org.apache.maven.plugins.mavencompilerplugin;

import org.apache.maven.plugins.mavencompilerplugin.Commands.Feed;
import org.apache.maven.plugins.mavencompilerplugin.Commands.Heal;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().log(Level.INFO, "Turn on Plugin...");
        new Heal(this);
        new Feed(this);
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.INFO, "Turn off Plugin...");
    }
}