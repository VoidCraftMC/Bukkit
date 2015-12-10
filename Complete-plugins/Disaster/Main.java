package me.fbmc.disaster;

import java.util.logging.Logger;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass
extends JavaPlugin
implements Listener {
    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;

    public void onEnable() {
        this.getLogger().info("Disaster has been Enabled.");
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
    }

    public void onDisable() {
        this.getLogger().info("CommandBox disabled");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("dis")) {
            if (args.length == 0) {
                sender.sendMessage((Object)ChatColor.YELLOW + "-----------------------------------\n" + (Object)ChatColor.AQUA + "Disaster v1.0 by B3D - Cameron\n/dis <explosion strength> - Explosion strength can be:\n" + (Object)ChatColor.GREEN + "Tiny | " + (Object)ChatColor.GREEN + "Small | " + (Object)ChatColor.AQUA + "Normal | " + (Object)ChatColor.YELLOW + "Big | " + (Object)ChatColor.GOLD + "Huge | " + (Object)ChatColor.RED + "Godzilla | " + (Object)ChatColor.DARK_RED + "BiomeBuster" + (Object)ChatColor.YELLOW + "\n-----------------------------------");
            } else if (args[0].equalsIgnoreCase("tiny")) {
                sender.sendMessage((Object)ChatColor.BLUE + "Explode> " + (Object)ChatColor.WHITE + "A" + (Object)ChatColor.GREEN + " Tiny" + (Object)ChatColor.WHITE + " explosion has occured.");
                Player target = player;
                float explosionPower = 2.0f;
                target.getWorld().createExplosion(target.getLocation(), explosionPower);
            } else if (args[0].equalsIgnoreCase("small")) {
                sender.sendMessage((Object)ChatColor.BLUE + "Explode> " + (Object)ChatColor.WHITE + "A" + (Object)ChatColor.GREEN + " Small" + (Object)ChatColor.WHITE + " explosion has occured.");
                Player target = player;
                float explosionPower = 3.0f;
                target.getWorld().createExplosion(target.getLocation(), explosionPower);
            } else if (args[0].equalsIgnoreCase("normal")) {
                sender.sendMessage((Object)ChatColor.BLUE + "Explode> " + (Object)ChatColor.WHITE + "A" + (Object)ChatColor.AQUA + " Normal" + (Object)ChatColor.WHITE + " explosion has occured.");
                Player target = player;
                float explosionPower = 4.0f;
                target.getWorld().createExplosion(target.getLocation(), explosionPower);
            } else if (args[0].equalsIgnoreCase("big")) {
                sender.sendMessage((Object)ChatColor.BLUE + "Explode> " + (Object)ChatColor.WHITE + "A" + (Object)ChatColor.YELLOW + " Big" + (Object)ChatColor.WHITE + " explosion has occured.");
                Player target = player;
                float explosionPower = 10.0f;
                target.getWorld().createExplosion(target.getLocation(), explosionPower);
            } else if (args[0].equalsIgnoreCase("huge")) {
                sender.sendMessage((Object)ChatColor.BLUE + "Explode> " + (Object)ChatColor.WHITE + "A" + (Object)ChatColor.GOLD + " Huge" + (Object)ChatColor.WHITE + " explosion has occured.");
                Player target = player;
                float explosionPower = 18.0f;
                target.getWorld().createExplosion(target.getLocation(), explosionPower);
            } else if (args[0].equalsIgnoreCase("godzilla")) {
                sender.sendMessage((Object)ChatColor.BLUE + "Explode> " + (Object)ChatColor.WHITE + "A" + (Object)ChatColor.RED + " Godzilla" + (Object)ChatColor.WHITE + " explosion has occured.");
                Player target = player;
                float explosionPower = 25.0f;
                target.getWorld().createExplosion(target.getLocation(), explosionPower);
            } else if (args[0].equalsIgnoreCase("biomebuster")) {
                sender.sendMessage((Object)ChatColor.BLUE + "Explode> " + (Object)ChatColor.WHITE + "A" + (Object)ChatColor.DARK_RED + " BiomeBuster" + (Object)ChatColor.WHITE + " explosion has occured." + (Object)ChatColor.RED + "WARNING: THIS SIZE EXPLOSION MAY LAG YOUR SERVER!");
                Player target = player;
                float explosionPower = 70.0f;
                target.getWorld().createExplosion(target.getLocation(), explosionPower);
            } else {
                sender.sendMessage((Object)ChatColor.RED + "Invalid argument " + args[0] + "\nType /dis for a list of valid arguments.");
            }
        }
        return false;
    }

    private int getRandom(int i, int j) {
        return 0;
    }
}
