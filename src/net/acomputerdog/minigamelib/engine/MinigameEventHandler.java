package net.acomputerdog.minigamelib.engine;

import net.acomputerdog.minigamelib.MinigamePlugin;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class MinigameEventHandler implements Listener {
    private final MinigamePlugin plugin;

    public MinigameEventHandler(MinigamePlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }
}
