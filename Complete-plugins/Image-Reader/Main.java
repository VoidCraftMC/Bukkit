package vc.reader;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

  /** ©2016 Cameron Clark 
  Some code is derived from bobacododl's image message api**/

public class Main extends JavaPlugin implements Listener {
	
	public void onEnable(){
		
		getDataFolder().mkdirs();
        Bukkit.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
		
	}
	
	
	@SuppressWarnings("static-access")
	public String toString() {
    File file = new File(getDataFolder() + File.separator + "creepermap.png");
    BufferedImage imageToSend = null;
	try {
		imageToSend = ImageIO.read(file);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String[] lines = new ImageMessage(imageToSend, 8, ImageChar.MEDIUM_SHADE.getChar()).getLines();
    String[] array = lines;
    String joined = String.join("\n", array);
	return joined;
	}
	
	@SuppressWarnings("static-access")
	public String[] toList(File file) {
    //File file = new File(getDataFolder() + File.separator + "creepermap.png");
    BufferedImage imageToSend = null;
	try {
		imageToSend = ImageIO.read(file);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
    String[] lines = new ImageMessage(imageToSend, 8, ImageChar.MEDIUM_SHADE.getChar()).getLines();
	return lines;
	}
	
	@SuppressWarnings("static-access")
	public String[] urlToList(URL url, Player p) {
    //File file = new File(getDataFolder() + File.separator + "creepermap.png");
    BufferedImage imageToSend = null;
	try {
		imageToSend = ImageIO.read(url);
	} catch (IOException e) {
		p.sendMessage("\u00a7eThe URL \u00a7a" + url + " \u00a7e is not a picture!");
	} 
	if (imageToSend.getWidth() != imageToSend.getHeight()){
	p.sendMessage("\u00a7eThe image at \u00a7a" + url + " \u00a7eis not square!");
	p.sendMessage("\u00a7eAttempting to resize...");
	long time = System.currentTimeMillis();
	imageToSend = createThumbnail(imageToSend);
	time = System.currentTimeMillis() - time;
	p.sendMessage("\u00a7eThe image was resized in \u00a7a" + time + "ms");
	}
    String[] lines = new ImageMessage(imageToSend, 8, ImageChar.MEDIUM_SHADE.getChar()).getLines();
	return lines;
	}
	
	public static BufferedImage createThumbnail(BufferedImage image) {
		
		BufferedImage scaledImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
				
				Graphics2D graphics2D = scaledImage.createGraphics();
				
				graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				graphics2D.drawImage(image, 0, 0, 800, 800, null);
				graphics2D.dispose();
				return scaledImage;
	    
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("image")) { // If the player typed /basic then do the following...
			player.sendMessage(toString());
			ItemStack i = new ItemStack(Material.STONE,1);
			ItemMeta im = i.getItemMeta();
			File file = new File(getDataFolder() + File.separator + "creepermap.png");
			im.setLore(Arrays.asList(toList(file)));
			i.setItemMeta(im);
			player.getInventory().setItemInHand(i);
			
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("getimage")){
		  if (player.hasPermission("imagereader.use") || player.isOp()){
			if (args.length < 2 || args.length > 3){
				player.sendMessage("\u00a7eProper usage is \u00a7a/getimage <\u00a73item type\u00a7a>\n <\u00a73picture \u00a7a|\u00a73 URL \u00a7a|\u00a73 head:[player]\u00a7a> [\u00a73item name\u00a7a]\u00a7e");
			}
			if (args.length == 2 || args.length == 3){
				//Get the image of a player's head
				if (args[1].contains("head:")){
					URL url = null;
					String nameunf = args[1];
					String namef[] = nameunf.split(":");
					if (namef.length == 2){
					try {
						url = new URL("http://www.minotar.net/avatar/" + namef[1] + "/80.png");
					} catch (MalformedURLException e) {
						player.sendMessage("\u00a7aAn error within the image server occured. Please inform the developer!");
					}
					Material type = Material.getMaterial(args[0].toUpperCase());
					
					if (type == null){
					
						player.sendMessage("\u00a7eItem '" + "\u00a7a" + args[0] + "\u00a7e' does not exist!");
					}
				
					else {
					
						ItemStack i = new ItemStack(type,1);
						ItemMeta im = i.getItemMeta();
						im.setLore(Arrays.asList(urlToList(url, player)));
						if (args.length == 3){
							String name = args[2].replaceAll("&", "\u00a7");
							im.setDisplayName(name);
						}
						i.setItemMeta(im);
						player.getInventory().setItemInHand(i);
					}
				
					
					}
					else {
						player.sendMessage("\u00a7ePlease specify a player. Example: \u00a7a/getimage stone head:NoSkillzAllHax");
					}
				}
				
				else {
					//Get an image from a URL
					if (args[1].contains("http://") || args[1].contains("https://")){
						URL url = null;
						try {
							url = new URL(args[1]);
						} catch (MalformedURLException e) {
							player.sendMessage("\u00a7eThe url \u00a7a" + args[1] + " \u00a7eis invalid!");;
						}
					
						Material type = Material.getMaterial(args[0].toUpperCase());
					
						if (type == null){
						
							player.sendMessage("\u00a7eItem '" + "\u00a7a" + args[0] + "\u00a7e' does not exist!");
						}
					
						else {
						
							ItemStack i = new ItemStack(type,1);
							ItemMeta im = i.getItemMeta();
							im.setLore(Arrays.asList(urlToList(url, player)));
							if (args.length == 3){
								String name = args[2].replaceAll("&", "\u00a7");
								im.setDisplayName(name);
							}
							i.setItemMeta(im);
							player.getInventory().setItemInHand(i);
						}
					
					}
					//Get image from plugin folder
					else {
					
						File file = new File(getDataFolder() + File.separator + args[1]);
					
						if (!file.exists()){
						
							player.sendMessage("\u00a7eFile " + "\u00a7a" + args[1] + " \u00a7enot found!");
						
						}
						else {
						
							Material type = Material.getMaterial(args[0].toUpperCase());
						
							if (type == null){
							
								player.sendMessage("\u00a7eItem '" + "\u00a7a" + args[0] + "\u00a7e' does not exist!");
							}
						
							else {
							
								ItemStack i = new ItemStack(type,1);
								ItemMeta im = i.getItemMeta();
								im.setLore(Arrays.asList(toList(file)));
								if (args.length == 3){
									String name = args[2].replaceAll("&", "\u00a7");
									im.setDisplayName(name);
								}
								i.setItemMeta(im);
								player.getInventory().setItemInHand(i);
							}
						}
					}
				
				
			
			
			
				}
			
			}
			return true;
		}
		  else {
			  player.sendMessage("\u00a74You are not allowed to access this command!");
		  }
		  
		}
	        
		return false; 
	}
    
}
