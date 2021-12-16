package ir.jeykey.megacore.events;

import ir.jeykey.megacore.MegaPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {
        /**
         * Handling inventory click events
         * @param event InventoryClickEvent
         */
        @EventHandler
        protected void onGUIClickEvent(InventoryClickEvent event) {
                MegaPlugin.registeredGuis.forEach((name, gui) -> {
                        if (!event.getInventory().getName().equalsIgnoreCase(gui.getName()))
                                return;
                        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
                                return;

                        gui.getItemHandlers().forEach((itemStack, handler) -> {
                                if (itemStack.isSimilar(event.getCurrentItem())) {
                                        event.setCancelled(true);
                                        handler.handle((Player) event.getWhoClicked(), event.getCurrentItem(), event.getSlot(), event.getClick());
                                }
                        });
                });

        }
}
