package com.precipicegames.zeryl.survivalrun;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;



public class SurvivalRunPlayerListener implements Listener {
    private final SurvivalRun plugin;

    public SurvivalRunPlayerListener(SurvivalRun instance) {
        plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void noBlockBreak(BlockBreakEvent event) {
        /*
         * Set event to cancelled, unless mushroom (and mushroom blocks) or leaves
         */
        
        Material type = event.getBlock().getType();

        if(type == Material.LEAVES
                || type == Material.BROWN_MUSHROOM
                || type == Material.RED_MUSHROOM
                || type == Material.HUGE_MUSHROOM_1
                || type == Material.HUGE_MUSHROOM_2
                || event.getPlayer().isOp())
            return;
        else {
            event.setCancelled(true);
            event.getPlayer().sendMessage(event.getBlock().toString());
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void deathAnnounce(PlayerDeathEvent event) {
        if(event.getEntity() instanceof Player) {
            Player dead = (Player) event.getEntity();
            
            event.setDeathMessage(dead.getDisplayName() + " has fallen dead");
            /*EntityDamageEvent lastDamage = dead.getLastDamageCause();

            DamageCause dc = lastDamage.getCause();
            switch (dc) {
                case BLOCK_EXPLOSION:
                    event.setDeathMessage(event.getDeathMessage() + " an explosion!");
                    break;
            }
                    
            event.setDeathMessage(event.getDeathMessage() + lastDamage.getCause().toString());*/
            event.setDeathMessage(event.getDeathMessage() + "!  May the Odds be Ever in your Favor");
        }
    }   
}
