package ir.jeykey.megacore.events;

import ir.jeykey.megacore.MegaPlugin;
import ir.jeykey.megacore.gui.HandleEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InventoryClick implements Listener {
        /**
         * Handling inventory click events
         * @param event InventoryClickEvent
         */
        @EventHandler
        protected void onGUIClickEvent(InventoryClickEvent event) {
                MegaPlugin.getRegisteredGuis().forEach((player, gui) -> {
                        if (!MegaPlugin.getRegisteredGuis().containsKey(player))
                                return;
                        if (!event.getInventory().getName().equalsIgnoreCase(gui.getName()))
                                return;
                        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
                                return;

                        // We clone the map so we don't face illegal modifications / concurrent modifications error
                        HashMap<ItemStack, HandleEvent> itemHandlersClone = new HashMap<>(gui.getItemHandlers());

                        itemHandlersClone.forEach((itemStack, handler) -> {
                                if (itemStack.isSimilar(event.getCurrentItem())) {
                                        event.setCancelled(true);
                                        handler.handle((Player) event.getWhoClicked(), event.getCurrentItem(), event.getSlot(), event.getClick());
                                }
                        });
                });

        }
}
