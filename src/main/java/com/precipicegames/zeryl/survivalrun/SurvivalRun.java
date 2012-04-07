package com.precipicegames.zeryl.survivalrun;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 *
 * @author Zeryl
 */
public class SurvivalRun extends JavaPlugin {    
    private final SurvivalRunPlayerListener survivalrunplayerlistener = new SurvivalRunPlayerListener(this);

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        
        pm.registerEvents(survivalrunplayerlistener, this);

        PluginDescriptionFile pdf = this.getDescription();
        System.out.println(pdf.getName() + " is now enabled.");
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pdf = this.getDescription();
        System.out.println(pdf.getName() + " is now disabled.");
    }
}