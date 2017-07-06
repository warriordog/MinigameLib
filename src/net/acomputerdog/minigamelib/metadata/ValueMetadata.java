package net.acomputerdog.minigamelib.metadata;

import net.acomputerdog.minigamelib.MinigamePlugin;
import org.bukkit.metadata.MetadataValue;

public class ValueMetadata implements MetadataValue {
    private final MinigamePlugin plugin;

    private Object obj;

    public ValueMetadata(MinigamePlugin plugin, Object value) {
        this.plugin = plugin;
        this.obj = value;
    }

    public void setValue(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object value() {
        return obj;
    }

    @Override
    public int asInt() {
        return Integer.parseInt(obj.toString());
    }

    @Override
    public float asFloat() {
        return Float.parseFloat(obj.toString());
    }

    @Override
    public double asDouble() {
        return Double.parseDouble(obj.toString());
    }

    @Override
    public long asLong() {
        return Long.parseLong(obj.toString());
    }

    @Override
    public short asShort() {
        return Short.parseShort(obj.toString());
    }

    @Override
    public byte asByte() {
        return Byte.parseByte(obj.toString());
    }

    @Override
    public boolean asBoolean() {
        return "true".equals(obj.toString());
    }

    @Override
    public String asString() {
        return obj.toString();
    }

    @Override
    public MinigamePlugin getOwningPlugin() {
        return plugin;
    }

    @Override
    public void invalidate() {
        obj = null;
    }

    public boolean isValid() {
        return obj != null;
    }
}
