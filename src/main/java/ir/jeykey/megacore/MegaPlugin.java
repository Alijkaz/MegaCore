package ir.jeykey.megacore;

import ir.jeykey.megacore.config.ConfigManager;
import ir.jeykey.megacore.events.InventoryClick;
import ir.jeykey.megacore.events.PlayerQuit;
import ir.jeykey.megacore.gui.MegaGui;
import ir.jeykey.megacore.utils.BungeeChannelApi;
import ir.jeykey.megacore.utils.Common;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;

public abstract class MegaPlugin extends JavaPlugin {
        private static Optional<MegaPlugin> instance;
        @Getter private static final HashMap<Player, MegaGui> registeredGuis = new HashMap<>();
        @Getter public static ConfigManager configManager;
        @Getter public static BungeeChannelApi bungeeApi;
        /**
         * Plugin prefix - Main plugin prefix (includes color codes) that will be shown before plugin messages
         */
        @Getter @Setter public static String prefix = "";

        @Override
        public void onEnable() {
                // For calculating
                long start = System.currentTimeMillis();

                // Assigning instance
                instance = Optional.of(this);

                // Setting up config manager
                configManager = new ConfigManager(this);

                // Setting up bungee channel API
                bungeeApi = BungeeChannelApi.of(this);

                onPluginEnable();

                // Disabling the plugin if it's disabled in the onPluinEnable
                if (!isEnabled()) return;

                // Registering core events
                register(new InventoryClick());
                register(new PlayerQuit());

                // Finished loading plugin millis
                long end = System.currentTimeMillis();

                // Calculating plugin load time in milliseconds
                long time = end - start;

                // Logging MegaReports has been activated
                Common.log(
                        Common.repeat("&a&m=", 18, "&2"),
                        "&a&l" + getDescription().getName() + " &aActivated",
                        "&a&lVersion: &2" + getDescription().getVersion(),
                        "&a&lTook: &2" + time+ " ms",
                        Common.repeat("&a&m=", 18, "&2")
                );

        }

        @Override
        public void onDisable() {
                onPluginDisable();

                // Logging MegaReports has been deactivated
                Common.log(
                        Common.repeat("&c&m=", 12, "&4"),
                        "&c&l" + getDescription().getName() + " &cDeactivated",
                        Common.repeat("&c&m=", 12, "&4")
                );
        }

        public void register(String name, CommandExecutor executor) {
                getCommand(name).setExecutor(executor);
        }

        public void register(Listener listener) {
                getServer().getPluginManager().registerEvents(listener, getInstance());
        }

        public abstract void onPluginEnable();

        public abstract void onPluginDisable();

        public static void disablePlugin(String... messages) {
                Common.logPrefixed(messages);
                Bukkit.getPluginManager().disablePlugin(getInstance());
        }

        public static MegaPlugin getInstance() {
                return instance.orElseThrow(IllegalStateException::new);
        }

}
