package net.atlaspvp.atlasutils;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import net.atlaspvp.atlasutils.features.FPS;
import net.atlaspvp.atlasutils.listeners.FPSAdapter;
import net.atlaspvp.atlasutils.listeners.InventoryClickListener;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AtlasUtils extends JavaPlugin {
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        FPS fps = new FPS();
        FPSAdapter adapter = new FPSAdapter(this, fps);
        InventoryClickListener clickgui = new InventoryClickListener(fps);

        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(adapter);

        getServer().getPluginManager().registerEvents(clickgui, this);

        getCommand("fps").setExecutor(fps);
        getCommand("tnttoggle").setExecutor(fps);
        getCommand("sandtoggle").setExecutor(fps);
        getCommand("explosiontoggle").setExecutor(fps);
        getCommand("maxfps").setExecutor(fps);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
