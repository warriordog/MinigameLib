package net.acomputerdog.minigamelib.engine;

import net.acomputerdog.minigamelib.MinigamePlugin;
import net.acomputerdog.minigamelib.event.PlayerEnterAreaEvent;
import net.acomputerdog.minigamelib.event.PlayerExitAreaEvent;
import net.acomputerdog.minigamelib.event.PlayerQuitMinigameEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MinigameEventHandler implements Listener {
    private final MinigamePlugin plugin;
    private final PlayerManager playerManager;

    public MinigameEventHandler(MinigamePlugin plugin) {
        this.plugin = plugin;
        this.playerManager = plugin.getPlayerManager();
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    private void checkPlayerInArea(Player p) {
        if (plugin.getGameArea().isPlayerInGameArea(p)) {
            if (!playerManager.isPlayerFlaggedInArea(p)) {
                plugin.getServer().getPluginManager().callEvent(new PlayerEnterAreaEvent(p));
            }
        } else {
            if (playerManager.isPlayerFlaggedInArea(p)) {
                plugin.getServer().getPluginManager().callEvent(new PlayerExitAreaEvent(p));
            }
        }
    }

    private void checkPlayerInArea(Player p, Location l) {
        if (plugin.getGameArea().isLocInGameArea(l)) {
            if (!playerManager.isPlayerFlaggedInArea(p)) {
                playerManager.onPlayerEnterArea(p);
                plugin.getServer().getPluginManager().callEvent(new PlayerEnterAreaEvent(p));
            }
        } else {
            if (playerManager.isPlayerFlaggedInArea(p)) {
                playerManager.onPlayerExitArea(p);
                plugin.getServer().getPluginManager().callEvent(new PlayerExitAreaEvent(p));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLogin(PlayerJoinEvent e) {
        checkPlayerInArea(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e) {
        checkPlayerInArea(e.getPlayer(), e.getTo());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        checkPlayerInArea(e.getPlayer(), e.getTo());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerExitArea(PlayerExitAreaEvent e) {
        playerManager.onPlayerExitArea(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuitMinigame(PlayerQuitMinigameEvent e) {
        playerManager.onPlayerQuitMinigame(e.getPlayer());
    }

    @EventHandler // can't stop a player from leaving, so don't ignore canceled
    public void onPlayerLogout(PlayerQuitEvent e) {
        plugin.getServer().getPluginManager().callEvent(new PlayerExitAreaEvent(e.getPlayer()));
        plugin.getServer().getPluginManager().callEvent(new PlayerQuitMinigameEvent(e.getPlayer()));
        playerManager.onPlayerQuitServer(e.getPlayer());
    }
}
