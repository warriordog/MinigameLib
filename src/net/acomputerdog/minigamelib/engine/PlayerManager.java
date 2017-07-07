package net.acomputerdog.minigamelib.engine;

import net.acomputerdog.minigamelib.MinigamePlugin;
import net.acomputerdog.minigamelib.metadata.FlagMetadata;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class PlayerManager {
    public static final String METADATA_KEY_PLAYER_IN_AREA = "MinigameLib.player_in_area";

    private final MinigamePlugin plugin;

    public PlayerManager(MinigamePlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isPlayerFlaggedInArea(Player p) {
        if (!p.hasMetadata(METADATA_KEY_PLAYER_IN_AREA)) {
            return false;
        }

        List<MetadataValue> metas = p.getMetadata(METADATA_KEY_PLAYER_IN_AREA);
        for (MetadataValue meta : metas) {
            if (meta instanceof FlagMetadata && plugin.equals(meta.getOwningPlugin())) {
                return meta.asBoolean();
            }
        }

        // if we get here, then the key was used on this player but not by this plugin
        return false;
    }

    public void onPlayerEnterArea(Player p) {
        // add player flag
        p.setMetadata(METADATA_KEY_PLAYER_IN_AREA, new FlagMetadata(plugin, true));
    }

    public void onPlayerExitArea(Player p) {
        // remove player flag
        p.removeMetadata(METADATA_KEY_PLAYER_IN_AREA, plugin);
    }
}
