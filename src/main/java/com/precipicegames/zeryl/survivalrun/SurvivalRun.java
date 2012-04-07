package com.precipicegames.zeryl.survivalrun;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.entity.Player;

/**
 *
 * @author Zeryl
 */
public class SurvivalRun extends JavaPlugin {

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();

        PluginDescriptionFile pdf = this.getDescription();
        System.out.println(pdf.getName() + " is now enabled.");
    }

    public void onDisable() {
        PluginDescriptionFile pdf = this.getDescription();
        System.out.println(pdf.getName() + " is now disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {


        if (cmd.getName().equalsIgnoreCase("dnp")) {
            if (sender instanceof Player) {
            }
        }


        return true;
    }
}