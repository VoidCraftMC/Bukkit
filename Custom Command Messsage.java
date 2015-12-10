package me.fbmc.ccm2;

import java.util.Set;
import java.util.logging.Logger;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass
extends JavaPlugin
implements Listener {
    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;

    public void onEnable() {
        this.getLogger().info("CCM has been Enabled.");
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.loadConfigFile();
        if (!this.setupEconomy()) {
            log.severe(String.format("no Vault dependency found!", this.getDescription().getName()));
            return;
        }
    }

    private void loadConfigFile() {
        this.saveDefaultConfig();
        if (this.getConfig().getConfigurationSection("commands") == null) {
            this.getConfig().createSection("commands");
            this.getConfig().set("commands.example", (Object)"&4This is an example command");
            this.saveConfig();
        }
    }

    public boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = (Economy)rsp.getProvider();
        if (econ != null) {
            return true;
        }
        return false;
    }

    public void onDisable() {
        this.getLogger().info("CCM disabled");
    }

    @EventHandler
    public void onPreprocess(PlayerCommandPreprocessEvent e) {
        ConfigurationSection s = this.getConfig().getConfigurationSection("commands");
        Player player = e.getPlayer();
        String balance = String.valueOf(econ.getBalance((OfflinePlayer)player));
        for (String command : s.getKeys(false)) {
            if (!e.getMessage().equalsIgnoreCase("/" + command)) continue;
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)this.getConfig().getString("commands." + command).replace("<player>", player.getName()).replace("<money>", balance)));
            e.setCancelled(true);
        }
        if (!s.getKeys(false).contains(e.getMessage().replace("/", ""))) {
            e.setCancelled(false);
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("ccm")) {
            if (args.length == 0) {
                sender.sendMessage((Object)ChatColor.BLUE + "CCM> " + (Object)ChatColor.WHITE + "Custom Command Message v2.0 by B3D - Cameron. Type /ccm help for help");
            }
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage((Object)ChatColor.BLUE + "CCM> " + (Object)ChatColor.WHITE + "Commands are set in config. Current commands: " + (Object)this.getConfig().getConfigurationSection("commands"));
            }
        }
        return false;
    }
}
