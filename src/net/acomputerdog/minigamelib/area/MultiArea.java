package net.acomputerdog.minigamelib.area;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class MultiArea implements Area {
    private final Area[] areas;

    public MultiArea(Area[] areas) {
        this.areas = areas;
    }

    @Override
    public boolean isPlayerInGameArea(Player p) {
        for (Area area : areas) {
            if (area.isPlayerInGameArea(p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isBlockInGameArea(Block b) {
        for (Area area : areas) {
            if (area.isBlockInGameArea(b)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isLocInGameArea(Location l) {
        for (Area area : areas) {
            if (area.isLocInGameArea(l)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void invalidate() {
        for (Area area : areas) {
            area.invalidate();
        }
    }
}
