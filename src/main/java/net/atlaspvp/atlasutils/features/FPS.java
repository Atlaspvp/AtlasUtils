package net.atlaspvp.atlasutils.features;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class FPS implements CommandExecutor {
    public final HashMap<UUID, Boolean> tnt_status;
    public final HashMap<UUID, Boolean> sand_status;
    public final HashMap<UUID, Boolean> explosion_status;

    public static final ItemStack tntitem = new ItemStack(Material.TNT);
    public static final ItemStack sanditem = new ItemStack(Material.SAND);
    public static final ItemStack exitem = new ItemStack(Material.GUNPOWDER);
    private static final String nc = "" + ChatColor.RESET + ChatColor.RED;

    private static final String on = ChatColor.GREEN + "on";
    private static final String off = ChatColor.RED+ "off";

    private final Configuration cfg;
    private static final String msgpath = "fps.message";
    private static final String itempath = "fps.menu-bg";

    private static Inventory GUI;

    public FPS(Configuration cfg) {
        this.tnt_status = new HashMap<>();
        this.sand_status = new HashMap<>();
        this.explosion_status = new HashMap<>();

        this.cfg = cfg;

        this.initBlockMeta();
        this.GUI = GetGui();
    }

    private ItemStack getGuiItem() {
        try {
            ItemStack ri =new ItemStack(Material.valueOf(cfg.getString(itempath).toUpperCase()));
            ItemMeta rm = ri.getItemMeta();
            rm.setDisplayName(" ");
            ri.setItemMeta(rm);
            return ri;
        } catch(Exception e) {
            return new ItemStack(Material.BARRIER);
        }
    }

    private String getMSG(String w, Boolean s) {
        String r = cfg.getString(msgpath);
        r = r.replace('&', '§');
        r = r.replace("%option%", w);
        r = r.replace("%status%", (s ? off : on));
        return r;
    }

    public boolean ValidateInv(Inventory inv) {
        return this.GUI == inv;
    }

    private void initBlockMeta() {
        ItemMeta tm = this.tntitem.getItemMeta();
        tm.setDisplayName(nc + "TNT Visibility");
        this.tntitem.setItemMeta(tm);

        ItemMeta sm = this.sanditem.getItemMeta();
        sm.setDisplayName(nc + "Falling Block Visibility");
        this.sanditem.setItemMeta(sm);

        ItemMeta em = this.exitem.getItemMeta();
        em.setDisplayName(nc + "Explosion Visibility");
        this.exitem.setItemMeta(em);
    }

    private Inventory GetGui() {
        Inventory i = Bukkit.createInventory(null, 27, "FPS Settings");
        ItemStack[] f = new ItemStack[27];
        Arrays.fill(f, getGuiItem());
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
            player.openInventory(this.GUI);
        }

        // tnt command (toggle tnt)
        else if (cmd.getName().equalsIgnoreCase("tnttoggle")) {
            Boolean ns = !tnt_status.get(uuid);
            tnt_status.replace(uuid, ns);
            author.sendMessage(getMSG("TNT", ns));
        }

        else if (cmd.getName().equalsIgnoreCase("maxfps")) {
            Boolean ns = !tnt_status.get(uuid);
            tnt_status.replace(uuid, ns);
            sand_status.replace(uuid, ns);
            explosion_status.replace(uuid, ns);
            author.sendMessage(getMSG("ALL", ns));
        }

        // sandtoggle command (toggle falling block)
        else if (cmd.getName().equalsIgnoreCase("sandtoggle")) {
            Boolean ns = !sand_status.get(uuid);
            sand_status.replace(uuid, ns);
            author.sendMessage(getMSG("Falling Blocks", ns));
        }

        // explosiontoggle command (toggle explosions)
        else if (cmd.getName().equalsIgnoreCase("explosiontoggle")) {
            Boolean ns = !explosion_status.get(uuid);
            explosion_status.replace(uuid, ns);
            author.sendMessage(getMSG("Explosions", ns));
        }

        return true;
    }
}
