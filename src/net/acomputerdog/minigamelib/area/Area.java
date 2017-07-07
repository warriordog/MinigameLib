package net.acomputerdog.minigamelib.area;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface Area {
    default boolean isPlayerInGameArea(Player p) {
        return isLocInGameArea(p.getLocation());
    }

    default boolean isBlockInGameArea(Block b) {
        return isLocInGameArea(b.getLocation());
    }

    boolean isLocInGameArea(Location l);

    /**
     * Signals that this area should release any caches, like World or Location objects.
     */
    void invalidate();
}
