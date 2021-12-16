package ir.jeykey.megacore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.*;

public class Common {
        public static String colorize(String string) {
                return ChatColor.translateAlternateColorCodes('&', string);
        }

        public static String[] colorize(String... strings) {
                String[] translatedStrings = new String[strings.length];
                for (int i = 0; i < strings.length; i++) translatedStrings[i] = colorize(strings[i]);
                return translatedStrings;
        }

        public static List<String> colorize(List<String> strings) {
                List<String> translatedStrings = new ArrayList<String>();
                for (String string: strings) translatedStrings.add(colorize(string));
                return translatedStrings;
        }


        public static void log(String... messages) {
                Bukkit.getServer().getConsoleSender().sendMessage(colorize(messages));
        }

        public static void logPrefixed(String prefix, String ... messages) {
                for(String message : messages) log(prefix + message);
        }

        public static String repeat(String string, int amount) {
                return repeat(string, amount, "");
        }

        public static String repeat(String string, int amount, String separator) {
                return String.join(separator, Collections.nCopies(amount, string));
        }

        public static void send(CommandSender sender, String string) {
                send(sender, string, null);
        }

        public static void send(CommandSender sender, String string, String prefix) {
                string = colorize(string);

                if (prefix != null) string = prefix + string;

                sender.sendMessage(string);
        }

        public static String getBeautifiedDt() {
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                return formatter.format(date);
        }

        /**
         * This method is used to create ItemStack with custom name and meta
         * @param material Material Item/Block
         * @param name Custom name
         * @param lore Custom lore
         * @return Built ItemStack
         */
        public static ItemStack createItem(final Material material, final String name, final String... lore) {
                final ItemStack item = new ItemStack(material, 1);
                final ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(Common.colorize(name));

                meta.setLore(Arrays.asList(Common.colorize(lore)));

                item.setItemMeta(meta);

                return item;
        }



}
