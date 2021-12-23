package net.atlaspvp.atlasutils.listeners;

import net.atlaspvp.atlasutils.features.FPS;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class InventoryClickListener implements Listener {
    private final FPS fps;

    public InventoryClickListener(FPS fps) {
        this.fps = fps;
    }

    @EventHandler
    private void inventoryClickListener(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && FPS.ValidateInv(event.getClickedInventory())) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            UUID uuid = player.getUniqueId();
            Server server = Bukkit.getServer();

            if (event.getCurrentItem().equals(FPS.tntitem)) {
                server.dispatchCommand(player, "tnttoggle");
            }
            else if (event.getCurrentItem().equals(FPS.sanditem)) {
                server.dispatchCommand(player, "sandtoggle");
            }
            else if (event.getCurrentItem().equals(FPS.exitem)) {
                server.dispatchCommand(player, "explosiontoggle");
            }
        }
    }
}
