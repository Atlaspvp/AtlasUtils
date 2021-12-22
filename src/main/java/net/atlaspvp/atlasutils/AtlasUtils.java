package net.atlaspvp.atlasutils;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import net.atlaspvp.atlasutils.features.FPS;
import net.atlaspvp.atlasutils.listeners.FPSAdapter;
import org.bukkit.plugin.java.JavaPlugin;

public final class AtlasUtils extends JavaPlugin {
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        FPS fps = new FPS();
        FPSAdapter adapter = new FPSAdapter(this, fps);

        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(adapter.getAdapter());

        getCommand("fps").setExecutor(fps);
        getCommand("tnttoggle").setExecutor(fps);
        getCommand("sandtoggle").setExecutor(fps);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
