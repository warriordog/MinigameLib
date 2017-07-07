package net.acomputerdog.minigamelib.area;

import org.bukkit.Location;

import java.util.List;

public class Loc3AreaF extends WorldArea {
    private final double xLoc1, yLoc1, zLoc1, xLoc2, yLoc2, zLoc2;

    public Loc3AreaF(String worldName, List<Double> loc1, List<Double> loc2) {
        this(worldName, loc1.get(0), loc1.get(1), loc1.get(2), loc2.get(0), loc2.get(1), loc2.get(2));
    }

    public Loc3AreaF(Location loc1, Location loc2) {
        super(loc1.getWorld());
        this.xLoc1 = loc1.getX();
        this.yLoc1 = loc1.getY();
        this.zLoc1 = loc1.getZ();
        this.xLoc2 = loc2.getX();
        this.yLoc2 = loc2.getY();
        this.zLoc2 = loc2.getZ();
    }

    public Loc3AreaF(String worldName, double xLoc1, double yLoc1, double zLoc1, double xLoc2, double yLoc2, double zLoc2) {
        super(worldName);
        this.xLoc1 = xLoc1;
        this.yLoc1 = yLoc1;
        this.zLoc1 = zLoc1;
        this.xLoc2 = xLoc2;
        this.yLoc2 = yLoc2;
        this.zLoc2 = zLoc2;
    }

    @Override
    public boolean isLocInGameArea(Location l) {
        if (!super.isLocInGameArea(l)) {
            return false;
        }

        double lX = l.getX();
        if (lX < xLoc1 || lX > xLoc2) {
            return false;
        }

        double lY = l.getY();
        if (lY < yLoc1 || lY > yLoc2) {
            return false;
        }

        double lZ = l.getZ();
        if (lZ < zLoc1 || lZ > zLoc2) {
            return false;
        }

        return true;
    }
}
