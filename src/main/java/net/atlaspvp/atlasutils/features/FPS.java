package net.atlaspvp.atlasutils.features;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class FPS implements CommandExecutor {
    public HashMap<UUID, Boolean> tnt_status;
    public HashMap<UUID, Boolean> sand_status;

    public FPS() {
        this.tnt_status = new HashMap<>();
        this.sand_status = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender author, Command cmd, String label, String[] args) {
        if (!(author instanceof Player)) {
            author.sendMessage("Command can only be used by players!");
            return true;
        }

        Player player = ((Player) author);
        UUID uuid = player.getUniqueId();

        //add player to hash maps if not already added
        if (!tnt_status.containsKey(uuid)) {
            tnt_status.put(uuid, false);
            sand_status.put(uuid, false);
        }

        // fps command (toggle both on condition of first)
        if (cmd.getName().equalsIgnoreCase("fps")) {
            Boolean ns = !tnt_status.get(uuid);
            tnt_status.replace(uuid, ns);
            sand_status.replace(uuid, ns);
            author.sendMessage("§a[+] §7Turned TNT and Falling Blocks " + (ns ? "off" : "on"));
        }

        // tnt command (toggle tnt)
        else if (cmd.getName().equalsIgnoreCase("tnttoggle")) {
            Boolean ns = !tnt_status.get(uuid);
            tnt_status.replace(uuid, ns);
            author.sendMessage("§a[+] §7Turned TNT " + (ns ? "off" : "on"));
        }

        // sandtoggle command (toggle falling block)
        else if (cmd.getName().equalsIgnoreCase("sandtoggle")) {
            Boolean ns = !sand_status.get(uuid);
            sand_status.replace(uuid, ns);
            author.sendMessage("§a[+] §7Turned Falling Blocks " + (ns ? "off" : "on"));
        }

        return true;
    }
}
