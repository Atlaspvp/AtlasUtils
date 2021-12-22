package net.atlaspvp.atlasutils.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.*;
import net.atlaspvp.atlasutils.AtlasUtils;
import net.atlaspvp.atlasutils.features.FPS;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import  java.util.Set;

import java.util.UUID;

public class FPSAdapter {
    private FPS fps;
    private PacketAdapter adapter;

    public FPSAdapter(Plugin plugin, FPS fps) {
        this.fps = fps;

        this.adapter = new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.SPAWN_ENTITY) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                UUID uuid = event.getPlayer().getUniqueId();
                EntityType e = packet.getEntityTypeModifier().read(0);

                if (fps.tnt_status.get(uuid) && e == EntityType.PRIMED_TNT) {
                    event.setCancelled(true);
                } else if (fps.sand_status.get(uuid) && e == EntityType.FALLING_BLOCK) {
                    event.setCancelled(true);
                }
            }
        };
    }

    public PacketAdapter getAdapter() {
        return this.adapter;
    }
}
