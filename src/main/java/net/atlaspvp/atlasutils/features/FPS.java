package net.atlaspvp.atlasutils.features;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class FPS implements CommandExecutor {
    public final HashMap<UUID, Boolean> tnt_status;
    public final HashMap<UUID, Boolean> sand_status;
    public final HashMap<UUID, Boolean> explosion_status;

    private static final ItemStack guifill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    public static final ItemStack tntitem = new ItemStack(Material.TNT);
    public static final ItemStack sanditem = new ItemStack(Material.SAND);
    public static final ItemStack exitem = new ItemStack(Material.GUNPOWDER);
    private static final String nc = "" + ChatColor.RESET + ChatColor.RED;

    private static final String on = "" + ChatColor.RESET + ChatColor.GREEN + "on" + ChatColor.RESET + ".";
    private static final String off = "" + ChatColor.RESET + ChatColor.RED+ "off" + ChatColor.RESET + ".";

    public FPS() {
        this.tnt_status = new HashMap<>();
        this.sand_status = new HashMap<>();
        this.explosion_status = new HashMap<>();

        this.initBlockMeta();
    }

    public static boolean ValidateInv(Inventory inv) {
        return (inv.contains(tntitem) && inv.contains(sanditem) && inv.contains(exitem));
    }

    private void initBlockMeta() {
        ItemMeta fm = this.guifill.getItemMeta();
        fm.setDisplayName(" ");
        this.guifill.setItemMeta(fm);

        ItemMeta tm = this.guifill.getItemMeta();
        fm.setDisplayName(nc + " TNT Visibility");
        this.guifill.setItemMeta(tm);

        ItemMeta sm = this.guifill.getItemMeta();
        fm.setDisplayName(nc + "Falling Block Visibility");
        this.guifill.setItemMeta(sm);

        ItemMeta em = this.guifill.getItemMeta();
        fm.setDisplayName(nc + "Explosion Visibility");
        this.guifill.setItemMeta(em);
    }

    private Inventory GetGui(Player p) {
        Inventory i = Bukkit.createInventory(p, 27, "FPS Settings");
        ItemStack[] f = new ItemStack[27];
        Arrays.fill(f, guifill);
        i.setContents(f);
        i.setItem(11, tntitem);
        i.setItem(13, sanditem);
        i.setItem(15, exitem);
        return i;
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
            explosion_status.put(uuid, false);
        }

        if (cmd.getName().equalsIgnoreCase("fps")) {
            player.openInventory(this.GetGui(player));
        }

        // tnt command (toggle tnt)
        else if (cmd.getName().equalsIgnoreCase("tnttoggle")) {
            Boolean ns = !tnt_status.get(uuid);
            tnt_status.replace(uuid, ns);
            author.sendMessage("§a[+] §7Turned TNT " + (ns ? off : on));
        }

        else if (cmd.getName().equalsIgnoreCase("maxfps")) {
            Boolean ns = !tnt_status.get(uuid);
            tnt_status.replace(uuid, ns);
            sand_status.replace(uuid, ns);
            explosion_status.replace(uuid, ns);
            author.sendMessage("§a[+] §7Turned ALL " + (ns ? off : on));
        }

        // sandtoggle command (toggle falling block)
        else if (cmd.getName().equalsIgnoreCase("sandtoggle")) {
            Boolean ns = !sand_status.get(uuid);
            sand_status.replace(uuid, ns);
            author.sendMessage("§a[+] §7Turned Falling Blocks " + (ns ? off : on));
        }

        // explosiontoggle command (toggle explosions)
        else if (cmd.getName().equalsIgnoreCase("explosiontoggle")) {
            Boolean ns = !explosion_status.get(uuid);
            explosion_status.replace(uuid, ns);
            author.sendMessage("§a[+] §7Turned Explosions " + (ns ? off : on));
        }

        return true;
    }
}
