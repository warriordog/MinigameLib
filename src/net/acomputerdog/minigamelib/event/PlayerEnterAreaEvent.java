package net.acomputerdog.minigamelib.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called when a player enters the minigame area
 */
public class PlayerEnterAreaEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    public PlayerEnterAreaEvent(Player who) {
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
