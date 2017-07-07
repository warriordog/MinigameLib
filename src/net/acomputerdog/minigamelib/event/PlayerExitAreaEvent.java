package net.acomputerdog.minigamelib.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called when a player leaves the minigame area
 */
public class PlayerExitAreaEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    public PlayerExitAreaEvent(Player who) {
        super(who);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
