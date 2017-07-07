package net.acomputerdog.minigamelib;

import net.acomputerdog.minigamelib.area.Area;
import net.acomputerdog.minigamelib.engine.MinigameEventHandler;
import net.acomputerdog.plugindb.DBSettings;
import net.acomputerdog.plugindb.PluginDB;
import net.acomputerdog.plugindb.db.Database;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public abstract class MinigamePlugin extends JavaPlugin {
    private FileConfiguration minigameConfig;

    // Plugin database
    private PluginDB pluginDB;

    // event handler
    private MinigameEventHandler eventHandler;

    @Override
    public void onEnable() {
        // read config for this minigame
        loadMinigameConfig();

        // load database
        if (minigameConfig.getBoolean("database.enabled")) {
            pluginDB = loadDatabase();
        }

        // initialize handler
        eventHandler = new MinigameEventHandler(this);
    }

    @Override
    public void onDisable() {
        if (pluginDB != null) {
            pluginDB.disconnect();
        }

    }

    private PluginDB loadDatabase() {
        DBSettings settings = new DBSettings();
        if (minigameConfig.getBoolean("database.autoload")) {
            settings.setAutoBuildDB(true);
            settings.setSemiSyncEnabled(true);
            settings.setDatabasePath(minigameConfig.getString("database.name"));
        }
        PluginDB db = new PluginDB(this);
        db.load(settings);
        db.connect();
        return db;
    }

    private void loadMinigameConfig() {
        // load config
        minigameConfig = new YamlConfiguration();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("/minigame/config.yml"))) {
            minigameConfig.load(reader);
        } catch (IOException e) {
            getLogger().severe("IO error loading minigame profile.  Make sure you include it in the jar!");
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            getLogger().severe("Configuration error in minigame profile.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a custom Area that defines the game area
     *
     * @return return an Area object that will encase the game area
     */
    public Area getCustomArea() {
        return null;
    }

    public PluginDB getPluginDB() {
        return pluginDB;
    }

    public Database getDB() {
        return pluginDB.getDatabase();
    }
}
