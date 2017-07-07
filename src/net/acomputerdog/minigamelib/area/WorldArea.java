package net.acomputerdog.minigamelib.area;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * An Area that only checks the current world.
 *
 * This will start with just a String name, but if it finds the matching world instance then it will
 * hold a reference.
 *
 * If the world is unloaded, then this area must be invalidated.
 */
public class WorldArea extends SimpleArea {
    private final String worldName;
    private World world;

    public WorldArea(String worldName) {
        this.worldName = worldName;
    }

    public WorldArea(World world) {
        this(world.getName());
        this.world = world;
    }

    @Override
    public boolean isLocInGameArea(Location l) {
        // since this is likely to be called MANY times during the server tick,
        //   we can speed up a bit by not calling getWorld() multiple times.
        World lWorld = l.getWorld();

        if (world != null) {
            // can possibly use == for speedup, but safer this way
            return world.equals(lWorld);
        } else {
            if (worldName.equals(lWorld.getName())) {
                world = lWorld;
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void invalidate() {
        // call super to invalidate shared Location object
        super.invalidate();

        world = null;
    }
}
