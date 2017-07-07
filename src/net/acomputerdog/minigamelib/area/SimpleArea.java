package net.acomputerdog.minigamelib.area;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Superclass for areas that just look at raw location.
 *
 * This uses a shared Location object to speed up lookups from Players or Blocks,
 * but this object will hold a reference to the last World that was checked.
 * Make sure to call invalidate() if a world is unloaded.
 */
public abstract class SimpleArea implements Area {
    private final Location temp = new Location(null, 0, 0, 0);

    @Override
    public boolean isPlayerInGameArea(Player p) {
        return isLocInGameArea(p.getLocation(temp));
    }

    @Override
    public boolean isBlockInGameArea(Block b) {
        return isLocInGameArea(b.getLocation(temp));
    }

    @Override
    public void invalidate() {
        // don't hold world reference
        temp.setWorld(null);
    }
}
