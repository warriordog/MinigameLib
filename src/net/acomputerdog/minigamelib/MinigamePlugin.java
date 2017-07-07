package net.acomputerdog.minigamelib;

import net.acomputerdog.minigamelib.area.Area;
import net.acomputerdog.minigamelib.area.AreaTypes;
import net.acomputerdog.minigamelib.engine.MinigameEventHandler;
import net.acomputerdog.minigamelib.engine.PlayerManager;
import net.acomputerdog.minigamelib.util.FileUtils;
import net.acomputerdog.minigamelib.util.RedirectableConfigurationSection;
import net.acomputerdog.plugindb.DBSettings;
import net.acomputerdog.plugindb.PluginDB;
import net.acomputerdog.plugindb.db.Database;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public abstract class MinigamePlugin extends JavaPlugin {
    private ConfigurationSection minigameConfig;

    // Plugin database
    private PluginDB pluginDB;

    // event handler
    private MinigameEventHandler eventHandler;

    // game area
    private Area gameArea;

    // player manager
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        // read config for this minigame
        loadMinigameConfig();

        // load database
        if (minigameConfig.getBoolean("database.enabled")) {
            pluginDB = loadDatabase();
        }

        // create game area
        gameArea = AreaTypes.createArea(minigameConfig, this);

        // create player manager
        playerManager = new PlayerManager(this);

        // initialize handler
        eventHandler = new MinigameEventHandler(this);
        eventHandler.register();
    }

    @Override
    public void onDisable() {
        if (pluginDB != null) {
            pluginDB.disconnect();
        }
        if (eventHandler != null) {
            eventHandler.unregister();
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
        YamlConfiguration mainCfg = new YamlConfiguration();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("/minigame/config.yml"))) {
            mainCfg.load(reader);
        } catch (IOException e) {
            getLogger().severe("IO error loading minigame profile.  Make sure you include it in the jar!");
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            getLogger().severe("Configuration error in minigame profile.");
            throw new RuntimeException(e);
        }

        // set up override
        if (mainCfg.getBoolean("redirect.enabled")) {
            ConfigurationSection alternate;

            if (mainCfg.getBoolean("redirect.custom_create")) {
                alternate = createCustomConfig();
            } else {
                File dir = getDataFolder();

                if (!dir.isDirectory() && !dir.mkdirs()) {
                    getLogger().warning("Unable to create data directory.");
                }

                File altFile = new File(dir, "minigame.yml");
                try {
                    FileUtils.copyFile(altFile, getResource("/defaults/minigame.yml"));
                    YamlConfiguration cfg = new YamlConfiguration();
                    cfg.load(altFile);
                    alternate = cfg;
                } catch (IOException | InvalidConfigurationException e) {
                    throw new RuntimeException("Exception reading redirect config", e);
                }
            }

            minigameConfig = new RedirectableConfigurationSection(mainCfg, alternate);
        }
    }

    /**
     * Create a custom Area that defines the game area.  May be called during onEnable()
     *
     * @return return an Area object that will encase the game area
     */
    public Area createCustomArea() {
        return null;
    }

    /**
     * Create a custom configuration to override the one in the jar.  Must be enabled in the jar config.
     * May be called during onEnable()
     *
     * @return return a ConfigurationSection with keys and values to match undefined ones in the internal config
     */
    public ConfigurationSection createCustomConfig() {
        return null;
    }

    public PluginDB getPluginDB() {
        return pluginDB;
    }

    public Database getDB() {
        return pluginDB.getDatabase();
    }

    public Area getGameArea() {
        return gameArea;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public MinigameEventHandler getEventHandler() {
        return eventHandler;
    }

    public ConfigurationSection getMinigameConfig() {
        return minigameConfig;
    }
}
