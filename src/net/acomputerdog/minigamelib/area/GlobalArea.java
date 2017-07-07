package net.acomputerdog.minigamelib.area;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class GlobalArea implements Area {
    @Override
    public boolean isPlayerInGameArea(Player p) {
        return true;
    }

    @Override
    public boolean isBlockInGameArea(Block b) {
        return true;
    }

    @Override
    public boolean isLocInGameArea(Location l) {
        return true;
    }

    @Override
    public void invalidate() {

    }
}
