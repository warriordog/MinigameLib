package net.acomputerdog.minigamelib.metadata;

import net.acomputerdog.minigamelib.MinigamePlugin;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class FlagMetadata implements MetadataValue {

    private final MinigamePlugin plugin;

    private boolean flag;

    public FlagMetadata(MinigamePlugin plugin, boolean flag) {
        this.plugin = plugin;
        this.flag = flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public Object value() {
        return flag;
    }

    @Override
    public int asInt() {
        return flag ? 1 : 0;
    }

    @Override
    public float asFloat() {
        return flag ? 1f : 0f;
    }

    @Override
    public double asDouble() {
        return flag ? 1.0 : 0.0;
    }

    @Override
    public long asLong() {
        return flag ? 1 : 0;
    }

    @Override
    public short asShort() {
        return (short)(flag ? 1 : 0);
    }

    @Override
    public byte asByte() {
        return (byte)(flag ? 1 : 0);
    }

    @Override
    public boolean asBoolean() {
        return flag;
    }

    @Override
    public String asString() {
        return flag ? "true" : "false";
    }

    @Override
    public Plugin getOwningPlugin() {
        return plugin;
    }

    @Override
    public void invalidate() {
        //nothing to do
    }
}
