package ir.jeykey.megacore.events;

import ir.jeykey.megacore.MegaPlugin;
import ir.jeykey.megacore.gui.HandleEvent;
import ir.jeykey.megacore.gui.MegaGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class InventoryClick implements Listener {
        /**
         * Handling inventory click events
         * @param event InventoryClickEvent
         */
        @EventHandler
        protected void onGUIClickEvent(InventoryClickEvent event) {
                outer:
                for (Map.Entry<Player, MegaGui> entry: MegaPlugin.getRegisteredGuis().entrySet()) {
                        Player player = entry.getKey();
                        MegaGui gui = entry.getValue();

                        if (!MegaPlugin.getRegisteredGuis().containsKey(player))
                                break;
                        if (!event.getInventory().getName().equalsIgnoreCase(gui.getName()))
                                break;
                        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
                                break;

                        for (Map.Entry<ItemStack, HandleEvent> itemHandler: gui.getItemHandlers().entrySet()) {
                                if (itemHandler.getKey().isSimilar(event.getCurrentItem())) {
                                        event.setCancelled(true);
                                        itemHandler.getValue().handle((Player) event.getWhoClicked(), event.getCurrentItem(), event.getSlot(), event.getClick());
                                        break outer;
                                }
                        }
                }
        }
}
