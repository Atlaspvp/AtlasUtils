package net.atlaspvp.atlasutils;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.atlaspvp.atlasutils.features.FPS;
import net.atlaspvp.atlasutils.listeners.FPSAdapter;
import net.atlaspvp.atlasutils.listeners.InventoryClickListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

public final class AtlasUtils extends JavaPlugin {
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        Config.initConfig(this);

        FPS fps = new FPS(this.getConfig());
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

        getCommand("reload").setExecutor(new Config(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
