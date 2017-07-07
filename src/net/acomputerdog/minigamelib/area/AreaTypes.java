package net.acomputerdog.minigamelib.area;

import net.acomputerdog.minigamelib.MinigamePlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public enum AreaTypes {
    GLOBAL((cfg, plugin) -> new GlobalArea()),
    WORLD((cfg, plugin) -> new WorldArea(cfg.getString("area.world_name"))),
    FLAT((cfg, plugin) -> new Loc2Area(cfg.getString("area.world_name"), cfg.getIntegerList("area.corner_1"), cfg.getIntegerList("area.corner_2"))),
    AREA((cfg, plugin) -> new Loc3Area(cfg.getString("area.world_name"), cfg.getIntegerList("area.corner_1"), cfg.getIntegerList("area.corner_2"))),
    F_AREA((cfg, plugin) -> new Loc3AreaF(cfg.getString("area.world_name"), cfg.getDoubleList("area.corner_1"), cfg.getDoubleList("area.corner_2"))),
    FLATS(AreaTypes::createAreasOfFlats),
    AREAS(AreaTypes::createAreasOfAreas),
    F_AREAS(AreaTypes::createAreasOfFAreas),
    CUSTOM((cfg, plugin) -> plugin.getCustomArea());
    ;

    private final BiFunction<ConfigurationSection, MinigamePlugin, Area> producer;

    AreaTypes(BiFunction<ConfigurationSection, MinigamePlugin, Area> producer) {
        this.producer = producer;
    }

    public Area createInstance(ConfigurationSection configuration, MinigamePlugin plugin) {
        return producer.apply(configuration, plugin);
    }

    private static Area createAreas(ConfigurationSection cfg, MinigamePlugin plugin, AreaTypes type) {
        List<Area> areas = new ArrayList<>();

        ConfigurationSection section = cfg.getConfigurationSection("area.areas");
        for (String areaName : section.getKeys(false)) {
            areas.add(type.createInstance(section.getConfigurationSection("area.areas." + areaName), plugin));
        }

        return new MultiArea(areas.toArray(new Area[areas.size()]));
    }

    private static Area createAreasOfFlats(ConfigurationSection cfg, MinigamePlugin plugin) {
        return createAreas(cfg, plugin, FLAT);
    }

    private static Area createAreasOfAreas(ConfigurationSection cfg, MinigamePlugin plugin) {
        return createAreas(cfg, plugin, AREA);
    }

    private static Area createAreasOfFAreas(ConfigurationSection cfg, MinigamePlugin plugin) {
        return createAreas(cfg, plugin, F_AREA);
    }

    public static Area createArea(ConfigurationSection cfg, MinigamePlugin plugin) {
        String typeName = cfg.getString("area.type");
        AreaTypes type = valueOf(typeName);
        return type.createInstance(cfg, plugin);
    }
}
