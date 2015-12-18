package rubies;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin {
	
	private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;
	
	public void onEnable(){
		saveDefaultConfig();
		 if (!setupEconomy() ) {
	            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
	            getServer().getPluginManager().disablePlugin(this);
	            return;
	        }
	        setupPermissions();
	        setupChat();
	    }

	    private boolean setupEconomy() {
	        if (getServer().getPluginManager().getPlugin("Vault") == null) {
	            return false;
	        }
	        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	            return false;
	        }
	        econ = rsp.getProvider();
	        return econ != null;
	    }

	    private boolean setupChat() {
	        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
	        chat = rsp.getProvider();
	        return chat != null;
	    }

	    private boolean setupPermissions() {
	        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
	        perms = rsp.getProvider();
	        return perms != null;
	    }
	    
	    public static boolean isNumeric(String str)  
	    {  
	      try  
	      {  
	        double d = Double.parseDouble(str);  
	      }  
	      catch(NumberFormatException nfe)  
	      {  
	        return false;  
	      }  
	      return true;  
	    }
	    
	    public static Object getRubies(Player p){
	    	int x = (int) econ.getBalance(p);
	    	if (p == null){
	    		return null;
	    	}
	    	return x;
	    }
	    
	    @SuppressWarnings("deprecation")
		public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
	        if(!(sender instanceof Player)) {
	            log.info("You must be a player!");
	            return true;
	        }

	        Player player = (Player) sender;

	        if(command.getLabel().equalsIgnoreCase("rubies")) {
	            
	        	if (args.length < 1){
	        	int x = (int) econ.getBalance(player.getName());
	            sender.sendMessage(this.getConfig().getString("rubies-format").replaceAll("<balance>", "" + x).replaceAll("&", "\u00a7"));
	        	}
	        if (args.length >= 1){
	        	if (args[0].equalsIgnoreCase("reload")){
	        		reloadConfig();
	        		player.sendMessage(ChatColor.YELLOW + "Rubies config " + ChatColor.WHITE + "reloaded!");
	        	}
	        	if (args[0].equalsIgnoreCase("config")){
	        		//if (args.length == 1){
	        		player.sendMessage(ChatColor.YELLOW + "Rubies config: ");
	        		player.sendMessage(ChatColor.GREEN + "\nrubies-format: " + ChatColor.GOLD + this.getConfig().getString("rubies-format"));
	        		player.sendMessage(ChatColor.GREEN + "rubies-see-format: " + ChatColor.GOLD + this.getConfig().getString("rubies-see-format"));
	        		player.sendMessage(ChatColor.GREEN + "give-rubies-permission: " + ChatColor.GOLD + this.getConfig().getString("give-rubies-permission"));
	        		player.sendMessage(ChatColor.GREEN + "rubies-recived-format: " + ChatColor.GOLD + this.getConfig().getString("rubies-recived-format"));
	        		player.sendMessage(ChatColor.GREEN + "rubies-error-message: " + ChatColor.GOLD + this.getConfig().getString("rubies-error-message"));
	        		player.sendMessage(ChatColor.GREEN + "give-rubies-permission: " + ChatColor.GOLD + this.getConfig().getString("give-rubies-permission"));
	        		player.sendMessage(ChatColor.GREEN + "no-give-ruby-perms-message: " + ChatColor.GOLD + this.getConfig().getString("no-give-ruby-perms-message"));
	        		player.sendMessage(ChatColor.GREEN + "take-rubies-permission: " + ChatColor.GOLD + this.getConfig().getString("take-rubies-permission"));
	        		player.sendMessage(ChatColor.GREEN + "rubies-taken-format: " + ChatColor.GOLD + this.getConfig().getString("rubies-taken-format"));
	        		player.sendMessage(ChatColor.GREEN + "no-take-ruby-perms-message: " + ChatColor.GOLD + this.getConfig().getString("no-take-ruby-perms-message"));
	        		//player.sendMessage(ChatColor.WHITE + "\nUse " + ChatColor.YELLOW + "/rubies config <config section> <new value>" + ChatColor.WHITE + " to edit a config value");
	        		//}
	        		/* if (args.length == 2){
	        			player.sendMessage("\nProper command use is " + ChatColor.YELLOW + "/rubies config <config section> <new value>");
	        		}
	        		if (args.length == 3){
	        			if (this.getConfig().getString(args[2]) != null){
	        			this.getConfig().set(args[2], args[3]);
	        			player.sendMessage(ChatColor.GREEN + args[2] + ChatColor.WHITE + " set to " + ChatColor.GOLD + args[3]);
	        			reloadConfig();
	        			}
	        			if (this.getConfig().getString(args[2]) == null) {
	        				player.sendMessage(ChatColor.RED + "Error: " + ChatColor.WHITE + " Config section " + ChatColor.YELLOW + "'" + args[2] + "'" + ChatColor.WHITE + " does not exist!");
	        				player.sendMessage("Type " + ChatColor.YELLOW + "/rubies config " + ChatColor.WHITE + "to see avalible options.");
	        			}
	        		}*/
	        	}
	            
	        	
	        	if (args[0].equalsIgnoreCase("see")){
	        		if (args.length == 2){
	        		if (player.getServer().getPlayer(args[1]) != null){
	        			
	        		player.sendMessage(this.getConfig().getString("rubies-see-format").replaceAll("<player>", args[1]).replaceAll("<balance>", "" + econ.getBalance(args[1])).replaceAll("&", "\u00a7"));
	        		}
	        		else { player.sendMessage("Player " + ChatColor.YELLOW + "'" + args[1] + "'" + ChatColor.WHITE + " not found");}
	        		}
	        		if (args.length != 2 ) {
	        			player.sendMessage("Proper Command use is " + ChatColor.YELLOW + "/rubies see <player>");
	        		}
	        	}
	        	
	        	
	        	if (args[0].equalsIgnoreCase("give")){
	        		if (player.hasPermission(this.getConfig().getString("give-rubies-permission")) || player.isOp()){
	        			
	        			if (args.length == 3){
	        			if (player.getServer().getPlayer(args[1]) != null){
	        				if (isNumeric(args[2])){
	        				double amount = Double.parseDouble(args[2]);
	        				EconomyResponse r = econ.depositPlayer(args[1], amount);//Try to give
	        				//econ.depositPlayer(args[1], amount);//Try to give
	        				if(r.transactionSuccess()) {//Rubies given
	        					int x = (int) econ.getBalance(args[1]);
	        					player.sendMessage(this.getConfig().getString("rubies-recived-format").replaceAll("<given>", "" + r.amount).replaceAll("<balance>", "" + x).replaceAll("<player>", args[1]).replaceAll("&", "\u00a7"));
	                
	                
	        				} 
	        				
	        				else {//An error has occurred
	        					sender.sendMessage(this.getConfig().getString("rubies-error-message").replaceAll("<error>", r.errorMessage));
	        				}
	        				}
	        				else { player.sendMessage(ChatColor.RED + "Error: " + ChatColor.YELLOW + "'" + args[2] + "'" + ChatColor.WHITE + " must be a number!");}
	        			}
	        			else { player.sendMessage("Player " + ChatColor.YELLOW + "'" + args[1] + "'" + ChatColor.WHITE + " not found");}
	        			}
	        			if (args.length != 3) {
		        			player.sendMessage("Proper Command use is " + ChatColor.YELLOW + "/rubies give <player> <amount>");
		        		}
	        			
	        		
	        			if (!player.hasPermission(this.getConfig().getString("give-rubies-permission")) || !player.isOp()) { //Player does not have permission
	        				player.sendMessage(this.getConfig().getString("no-give-ruby-perms-message").replaceAll("<balance>", "" + econ.getBalance(player.getName())).replaceAll("&", "\u00a7"));
	        			}
	        		}
	        	}
	        	if (args[0].equalsIgnoreCase("take")){
	        		if (player.hasPermission(this.getConfig().getString("take-rubies-permission")) || player.isOp()){
	        			if (args.length == 3){
	        				if (player.getServer().getPlayer(args[1]) != null){
	        					if (isNumeric(args[2])){
	        				double amount = Double.parseDouble(args[2]);
	        				EconomyResponse r = econ.withdrawPlayer(args[1], amount);//Try to take
	        				//econ.withdrawPlayer(args[1], amount);//Try to take
	        				if(r.transactionSuccess()) {//Rubies taken
	        					player.sendMessage(this.getConfig().getString("rubies-taken-format").replaceAll("<player>", args[1]).replaceAll("<taken>", "" + r.amount).replaceAll("<balance>", "" + econ.getBalance(player.getName())).replaceAll("&", "\u00a7"));
	                
	                
	        				} 
	        			    
	        				else {//An error has occurred
	        					sender.sendMessage(this.getConfig().getString("rubies-error-message").replaceAll("<error>", r.errorMessage));
	        				}
	        				}
	        				else { player.sendMessage(ChatColor.RED + "Error: " + ChatColor.YELLOW + "'" + args[2] + "'" + ChatColor.WHITE + " must be a number!");}
	        			}
	        				else { player.sendMessage("Player " + ChatColor.YELLOW + "'" + args[1] + "'" + ChatColor.WHITE + " not found");}
	        			}
	        			if (args.length != 3) {
		        			player.sendMessage("Proper Command use is " + ChatColor.YELLOW + "/rubies take <player> <amount>");
		        		}
	        		}
	        		if (!player.hasPermission(this.getConfig().getString("take-rubies-permission")) || !player.isOp()) {//Player does not have permission
	        			player.sendMessage(this.getConfig().getString("no-take-ruby-perms-message").replaceAll("<balance>", "" + econ.getBalance(player.getName())).replaceAll("&", "\u00a7"));
	        		}
	        	}
	        	if (!args[0].equalsIgnoreCase("reload") && !args[0].equalsIgnoreCase("see") && !args[0].equalsIgnoreCase("give") && !args[0].equalsIgnoreCase("take") && !args[0].equalsIgnoreCase("config")) {
	        		player.sendMessage("Rubies Commands: " + ChatColor.YELLOW + "\n/rubies " + ChatColor.GRAY + ": See how many rubies you have.");
	        		player.sendMessage(ChatColor.YELLOW + "/rubies see <player> " + ChatColor.GRAY + ": See how many rubies a player has.");
	        		player.sendMessage(ChatColor.YELLOW + "/rubies give <player> <amount> " + ChatColor.GRAY + ": Gives rubies to a player.");
	        		player.sendMessage(ChatColor.YELLOW + "/rubies take <player> <amount> " + ChatColor.GRAY + ": Take rubies from a player.");
	        		player.sendMessage(ChatColor.YELLOW + "/rubies config " + ChatColor.GRAY + ": Rubies config options.");
	        		player.sendMessage(ChatColor.YELLOW + "/rubies reload " + ChatColor.GRAY + ": Reloads the rubies config file.");
	        	}
	        	}
	            return true;
	        }
	            return false;
	        }
	    }
	
