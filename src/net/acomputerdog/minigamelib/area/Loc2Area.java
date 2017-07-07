package net.acomputerdog.minigamelib.area;

import org.bukkit.Location;

import java.util.List;

public class Loc2Area extends WorldArea {
    private final int xLoc1, zLoc1, xLoc2, zLoc2;

    public Loc2Area(String worldName, List<Integer> loc1, List<Integer> loc2) {
        this(worldName, loc1.get(0), loc1.get(1), loc2.get(0), loc2.get(1));
    }

    public Loc2Area(Location loc1, Location loc2) {
        super(loc1.getWorld());
        this.xLoc1 = loc1.getBlockX();
        this.zLoc1 = loc1.getBlockZ();
        this.xLoc2 = loc2.getBlockX();
        this.zLoc2 = loc2.getBlockZ();
    }

    public Loc2Area(String worldName, int xLoc1, int zLoc1, int xLoc2, int zLoc2) {
        super(worldName);
        this.xLoc1 = xLoc1;
        this.zLoc1 = zLoc1;
        this.xLoc2 = xLoc2;
        this.zLoc2 = zLoc2;
    }

    @Override
    public boolean isLocInGameArea(Location l) {
        if (!super.isLocInGameArea(l)) {
            return false;
        }

        int lX = l.getBlockX();
        if (lX < xLoc1 || lX > xLoc2) {
            return false;
        }

        int lZ = l.getBlockZ();
        if (lZ < zLoc1 || lZ > zLoc2) {
            return false;
        }

        return true;
    }
}
