package net.acomputerdog.minigamelib.util;

import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A configuration wrapper that allows undefined entries to be redirected to another.
 * Entries to be redirected should be specified as "?" in the config
 */
public class RedirectableConfigurationSection implements ConfigurationSection {
    private final ConfigurationSection primary;
    private final ConfigurationSection alternate;

    public RedirectableConfigurationSection(ConfigurationSection primary, ConfigurationSection alternate) {
        this.primary = primary;
        this.alternate = alternate;
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return primary.getKeys(deep);
    }

    @Override
    public Map<String, Object> getValues(boolean deep) {
        Map<String, Object> values = primary.getValues(deep);
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getValue() instanceof String && "?".equals(entry.getValue())) {
                entry.setValue(alternate.get(entry.getKey()));
            }
        }
        return values;
    }

    @Override
    public boolean contains(String path) {
        return primary.contains(path);
    }

    @Override
    public boolean contains(String path, boolean ignoreDefault) {
        return primary.contains(path, ignoreDefault);
    }

    @Override
    public boolean isSet(String path) {
        return primary.isSet(path);
    }

    @Override
    public String getCurrentPath() {
        return primary.getCurrentPath();
    }

    @Override
    public String getName() {
        return primary.getName();
    }

    @Override
    public Configuration getRoot() {
        return primary.getRoot();
    }

    @Override
    public ConfigurationSection getParent() {
        return primary.getParent();
    }

    @Override
    public Object get(String path) {
        Object obj = primary.get(path);
        if (obj instanceof String && "?".equals(obj)) {
            obj = alternate.get(path);
        }
        return obj;
    }

    @Override
    public Object get(String path, Object def) {
        Object obj = this.get(path);
        if (obj == null) {
            obj = def;
        }
        return obj;
    }

    @Override
    public void set(String path, Object value) {
        primary.set(path, value);
    }

    @Override
    public ConfigurationSection createSection(String path) {
        return primary.createSection(path);
    }

    @Override
    public ConfigurationSection createSection(String path, Map<?, ?> map) {
        return primary.createSection(path, map);
    }

    @Override
    public String getString(String path) {
        String str = primary.getString(path);
        if ("?".equals(str)) {
            str = alternate.getString(path);
        }
        return str;
    }

    @Override
    public String getString(String path, String def) {
        String str = this.getString(path);
        if (str == null) {
            str = def;
        }
        return str;
    }

    @Override
    public boolean isString(String path) {
        Object obj = this.get(path);
        return obj instanceof String;
    }

    @Override
    public int getInt(String path) {
        Object obj = this.get(path);
        return (Integer)obj;
    }

    @Override
    public int getInt(String path, int def) {
        Object obj = this.get(path, def);
        return (Integer)obj;
    }

    @Override
    public boolean isInt(String path) {
        Object obj = this.get(path);
        return obj instanceof Integer;
    }

    @Override
    public boolean getBoolean(String path) {
        Object obj = this.get(path);
        return (Boolean)obj;
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        Object obj = this.get(path, def);
        return (Boolean)obj;
    }

    @Override
    public boolean isBoolean(String path) {
        Object obj = this.get(path);
        return obj instanceof Boolean;
    }

    @Override
    public double getDouble(String path) {
        Object obj = this.get(path);
        return (Double)obj;
    }

    @Override
    public double getDouble(String path, double def) {
        Object obj = this.get(path, def);
        return (Double)obj;
    }

    @Override
    public boolean isDouble(String path) {
        Object obj = this.get(path);
        return obj instanceof Double;
    }

    @Override
    public long getLong(String path) {
        Object obj = this.get(path);
        return (Long)obj;
    }

    @Override
    public long getLong(String path, long def) {
        Object obj = this.get(path, path);
        return (Long)obj;
    }

    @Override
    public boolean isLong(String path) {
        Object obj = this.get(path);
        return obj instanceof Long;
    }

    @Override
    public List<?> getList(String path) {
        Object obj = this.get(path);
        List lstPrim = (List<?>)obj;
        List lstAlt = alternate.getList(path);

        if (lstAlt != null && lstPrim != null && lstAlt.size() == lstPrim.size()) {
            for (int i = 0; i < lstPrim.size(); i++) {
                Object o = lstPrim.get(i);

                if (o instanceof String && "?".equals(o)) {
                    // generics cause problems here
                    lstPrim.set(i, lstAlt.get(i));
                }
            }
        }
        return lstPrim;
    }

    @Override
    public List<?> getList(String path, List<?> def) {
        List<?> lst = getList(path);
        if (lst == null) {
            lst = def;
        }
        return lst;
    }

    @Override
    public boolean isList(String path) {
        Object obj = this.get(path);
        return obj instanceof List;
    }

    @Override
    public List<String> getStringList(String path) {
        List<?> lst = getList(path);
        return (List<String>)lst;
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        List<?> lst = getList(path);
        return (List<Integer>)lst;
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        List<?> lst = getList(path);
        return (List<Boolean>)lst;
    }

    @Override
    public List<Double> getDoubleList(String path) {
        List<?> lst = getList(path);
        return (List<Double>)lst;
    }

    @Override
    public List<Float> getFloatList(String path) {
        List<?> lst = getList(path);
        return (List<Float>)lst;
    }

    @Override
    public List<Long> getLongList(String path) {
        List<?> lst = getList(path);
        return (List<Long>)lst;
    }

    @Override
    public List<Byte> getByteList(String path) {
        List<?> lst = getList(path);
        return (List<Byte>)lst;
    }

    @Override
    public List<Character> getCharacterList(String path) {
        List<?> lst = getList(path);
        return (List<Character>)lst;
    }

    @Override
    public List<Short> getShortList(String path) {
        List<?> lst = getList(path);
        return (List<Short>)lst;
    }

    // Don't even try to fix the generics
    @Override
    public List<Map<?, ?>> getMapList(String path) {
        List lstPrim = this.getList(path);
        List lstAlt = alternate.getMapList(path);
        for (int i = 0; i < lstPrim.size(); i++) {
            Map map = ((List<Map>)lstPrim).get(i);
            for (Map.Entry entry : (Set<Map.Entry>)map.entrySet()) {
                if (entry.getKey() instanceof String && "?".equals(entry.getKey())) {
                    entry.setValue(((List<Map>)lstAlt.get(i)).get((Integer)entry.getKey()));
                }
            }
        }

        return (List<Map<?, ?>>)lstPrim;
    }

    @Override
    public Vector getVector(String path) {
        Object obj = this.get(path);
        return (Vector)obj;
    }

    @Override
    public Vector getVector(String path, Vector def) {
        Object obj = this.get(path, def);
        return (Vector)obj;
    }

    @Override
    public boolean isVector(String path) {
        Object obj = this.get(path);
        return obj instanceof Vector;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String path) {
        Object obj = get(path);
        return (OfflinePlayer)obj;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
        Object obj = this.get(path, def);
        return (OfflinePlayer)obj;
    }

    @Override
    public boolean isOfflinePlayer(String path) {
        Object obj = this.get(path);
        return obj instanceof OfflinePlayer;
    }

    @Override
    public ItemStack getItemStack(String path) {
        Object obj = this.get(path);
        return (ItemStack)obj;
    }

    @Override
    public ItemStack getItemStack(String path, ItemStack def) {
        Object obj = this.get(path, def);
        return (ItemStack) obj;
    }

    @Override
    public boolean isItemStack(String path) {
        Object obj = this.get(path);
        return obj instanceof ItemStack;
    }

    @Override
    public Color getColor(String path) {
        Object obj = this.get(path);
        return (Color)obj;
    }

    @Override
    public Color getColor(String path, Color def) {
        Object obj = this.get(path, def);
        return (Color)obj;
    }

    @Override
    public boolean isColor(String path) {
        Object obj = this.get(path);
        return obj instanceof Color;
    }

    @Override
    public ConfigurationSection getConfigurationSection(String path) {
        Object obj = this.get(path);
        return (ConfigurationSection)obj;
    }

    @Override
    public boolean isConfigurationSection(String path) {
        Object obj = this.get(path);
        return obj instanceof ConfigurationSection;
    }

    @Override
    public ConfigurationSection getDefaultSection() {
        return primary.getDefaultSection();
    }

    @Override
    public void addDefault(String path, Object value) {
        primary.addDefault(path, value);
    }
}
