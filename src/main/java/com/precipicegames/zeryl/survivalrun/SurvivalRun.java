package com.precipicegames.zeryl.survivalrun;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.Lever;
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
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("toggle")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                toggleLever(-1574, 66, -641, player.getWorld());
            }
        }
        return true;
    }
    
    public boolean toggleLever(int x, int y, int z, World world) {        
        Block block = world.getBlockAt(x, y, z);
        BlockState bs = block.getState();
        if(bs.getBlock().getType() == Material.LEVER) {
            Lever lever = (Lever) bs.getData();
            lever.setPowered(!lever.isPowered());
            bs.setData(lever);
            return bs.update();
        }
        
        return false;
    }
}