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

public class FPSAdapter extends  PacketAdapter {
    private final FPS fps;

    public FPSAdapter(Plugin p, FPS fps) {
        super(p, ListenerPriority.NORMAL,
                PacketType.Play.Server.SPAWN_ENTITY,
                PacketType.Play.Server.EXPLOSION
                );
        this.fps = fps;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        UUID uuid = event.getPlayer().getUniqueId();
        if (packet.getType() == PacketType.Play.Server.SPAWN_ENTITY) {
            EntityType e = packet.getEntityTypeModifier().read(0);
        if (fps.tnt_status.getOrDefault(uuid, false) && e == EntityType.PRIMED_TNT) {
                event.setCancelled(true);
            } else if (fps.sand_status.getOrDefault(uuid, false) && e == EntityType.FALLING_BLOCK) {
                event.setCancelled(true);
            }
        }
        else if (packet.getType() == PacketType.Play.Server.EXPLOSION) {
            if (fps.explosion_status.getOrDefault(uuid, false)) {
                event.setCancelled(true);
            }
        }
    }
}