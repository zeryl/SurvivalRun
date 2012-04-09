package com.precipicegames.zeryl.survivalrun;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Zeryl
 */
public class SurvivalRun extends JavaPlugin {

    private final SurvivalRunPlayerListener survivalrunplayerlistener = new SurvivalRunPlayerListener(this);
    private FileConfiguration config;
    private boolean sent;
    private int number = 0;
    public HashSet<String> deaths = new HashSet<String>();
    private String deathnames = "";

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(survivalrunplayerlistener, this);

        PluginDescriptionFile pdf = this.getDescription();
        System.out.println(pdf.getName() + " is now enabled.");
        config = getConfig();
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pdf = this.getDescription();
        System.out.println(pdf.getName() + " is now disabled.");
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("settube") && (sender.getName().equalsIgnoreCase("zeryl"))) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                boolean worked = addSpawn(player);
                if(worked)
                    player.sendMessage("Spawn added at: " + player.getLocation().toString());
                else
                    player.sendMessage("Spawn couldn't be added at: " + player.getLocation().toString());
            }
            return true;
        }
        
        if (cmd.getName().equalsIgnoreCase("sendplayers")) {
            boolean force = false;
            if ((sender instanceof Player)) {
                if(args.length > 0)
                    force = true;
                Player player = (Player) sender;
                if(player.isOp() && (!sent || force)) {
                    sent = true;
                    spawnPlayers(player.getWorld());
                }
                else {
                    player.sendMessage("Sorry, already sent them, reload or ask Zeryl how to bypass!");
                }
                return true;
            }
        }
        
        if (cmd.getName().equalsIgnoreCase("deaths")) {
            if(deaths.isEmpty()) {
                sender.sendMessage("Noone has died yet.");
                return true;
            }
            Iterator<String> iterator = deaths.iterator();
            while (iterator.hasNext()) {
                if(deathnames.length() == 0)
                    deathnames = iterator.next().toString();
                else
                    deathnames = deathnames + ", " + iterator.next().toString();
            }
            
            sender.sendMessage("Deaths: " + deathnames);
            return true;
        }
        
        /*if (cmd.getName().equalsIgnoreCase("testme")) {
            if (sender instanceof Player) {
                ItemStack is = new ItemStack(Material.DIAMOND);
                is.setAmount(1);
                dropChest((Player) sender, is);
            }
        }*/
        return true;
    }

    private boolean addSpawn(Player player) {
        int i = getMaxSpawn();
        
        if(i == 30)
            return false;
        
        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();
        
        String section = "spawns.spawn" + i;
        
        config.createSection(section);
        config.getConfigurationSection(section).set("x", x);
        config.getConfigurationSection(section).set("y", y);
        config.getConfigurationSection(section).set("z", z);

        return true;
    }

    private int getMaxSpawn() {
        for (int i = 0; i <= 30; i++) {
            String section = "spawns.spawn" + i;
            if (!config.isConfigurationSection(section)) {
                return i;
            }
        }
        return 0;
    }
    
    private void spawnPlayers(World world) {
        List<Player> players = world.getPlayers();
        int max = getMaxSpawn();
        
        for (Iterator it = players.iterator(); it.hasNext();) {
            Player thisplayer = (Player) it.next();
            
            if(!thisplayer.isOp()) {
                ConfigurationSection cs = config.getConfigurationSection("spawns.spawn" + number);
                Location loc = new Location(world, cs.getInt("x"), cs.getInt("y"), cs.getInt("z"));
                thisplayer.teleport(loc);
                number++;
                if(number > max - 1)
                    number = 0;
            }
            
            thisplayer.setHealth(20);
            thisplayer.setSaturation(1);
        }
    }
    
    //Implement "dropping" a chest at a user
    private void dropChest(Player player, ItemStack item) {
        int x = (int) player.getLocation().getX();
        int y = (int) player.getLocation().getY();
        int z = (int) player.getLocation().getZ();
        
        for(int i = player.getWorld().getMaxHeight(); i >= y; i--) {
            BlockState blockState = player.getWorld().getBlockAt(x, i, z).getState();

            blockState.setType(Material.CHEST);
            blockState.setType(Material.AIR);
        }

        Block block = player.getWorld().getBlockAt(x, y, z);
        block.setType(Material.CHEST);
        Chest chest = (Chest) block.getState();
        
        Inventory inventory = chest.getInventory();
        inventory.setItem(0, item);        
    }
}