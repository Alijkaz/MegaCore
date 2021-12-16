package ir.jeykey.megacore.config;

import ir.jeykey.megacore.MegaPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public class ConfigManager {
    private final MegaPlugin megaPlugin;

    public ConfigManager(MegaPlugin javaPlugin) {
        this.megaPlugin = javaPlugin;
    }

    public boolean register(Configurable configurable) {
        try {
            configurable.getClass().getDeclaredConstructor(MegaPlugin.class).newInstance(megaPlugin).setup();
            return true;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException|NoSuchMethodException ignored) {
            return false;
        }
    }
}
