package ir.jeykey.megacore.config;

import ir.jeykey.megacore.MegaPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager<T extends Configurable> {
    private final MegaPlugin megaPlugin;
    private List<T> configs = new ArrayList<>();

    public ConfigManager(MegaPlugin javaPlugin) {
        this.megaPlugin = javaPlugin;
    }

    public boolean register(Class<T> clazz) {
        try {
            T config = clazz.getDeclaredConstructor(MegaPlugin.class).newInstance(megaPlugin);
            configs.add(config);
            config.setup();
            return true;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException|NoSuchMethodException ignored) {
            return false;
        }
    }

    public void reloadAll() {
        for (T t: configs) {
            t.setup();
        }
    }
}
