package ir.jeykey.megacore.events;

import ir.jeykey.megacore.gui.MegaPaginatedGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    @EventHandler
    public void handleQuit(PlayerQuitEvent e) {
        MegaPaginatedGui.getPagination().remove(e.getPlayer());
    }
}
