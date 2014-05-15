package com.goreacraft.plugins.goreabuffs;

import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {
	
    
     
	static Integer taskid;
	public static JavaPlugin plugin;
	final int potiontime=100;
   
      
      public final Logger logger = Logger.getLogger("Minecraft");
	  static YamlConfiguration ConfigData;
	  static File ConfigFile;
      
      public void onDisable()
      {
    	  Bukkit.getScheduler().cancelTasks(plugin);
      this.logger.info("Gorea Buffs is now disabled.");
      }
      
      public void onEnable()
      {
    	  PluginDescriptionFile pdfFile = this.getDescription();
    	  plugin = (JavaPlugin) this.getServer().getPluginManager().getPlugin(pdfFile.getName());
    	  this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " has been enabled! " + pdfFile.getWebsite());
      	getConfig().options().copyDefaults(true);
      	getConfig().options().header("If you need help with this plugin you can contact goreacraft on teamspeak ip: goreacraft.com\n Website http://www.goreacraft.com");
      	saveConfig();
      	ConfigFile = new File(getDataFolder(), "config.yml");
      	ConfigData = YamlConfiguration.loadConfiguration(ConfigFile);
      	
      this.logger.info("Gorea Buffs is now enabled.");
      }
      
      public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
      {
      Player p = (Player)sender;
      if (sender instanceof Player){
    	      
	      if (commandLabel.equalsIgnoreCase("gbuff") && p.isOp())
	      	{
	    	  
	    	  if( args.length==1)
	    	  {
	    		  
				  if ( args[0].equals("start"))	{			  
					 
						p.sendMessage("Canceling the last task: " +  taskid + " before starting a new one, also trying to cancel others if any left.");						  				  
				  		Bukkit.getScheduler().cancelTasks(plugin);
				    	 				    	  
				    		 runner();
				    	 
				    	 taskid = new BukkitRunnable() {			
							@Override
							public void run() {	
										runner();
							}				    		  
				    	  }.runTaskTimer(plugin, getConfig().getLong("Run again in"), getConfig().getLong("Time to wait between tasks")).getTaskId();
				    	
			      
				  }
				  if ( args[0].equals("reload"))
			    	 {
					
					 Bukkit.getScheduler().cancelTasks(plugin);
					  plugin.reloadConfig();	
					  p.sendMessage("Configs reloaded!");
			    	 }
				  
			    	 
				  if ( args[0].equals("info"))
			    	 {
					  
					  p.sendMessage(" Dimension you are in: " + p.getLocation().getWorld().getName());
					  p.sendMessage(" You are in the same dimension as in configs: " + p.getLocation().getWorld().getName().equals(getConfig().get("Dimension")));
					  if (getConfig().getBoolean("Speed")) {p.sendMessage("Speed buff enabled. Running every " + getConfig().getLong("Run again in") + " for " + getConfig().getInt("Speed time") + " ticks and power of " + getConfig().getInt("Speed power"));}
					  else p.sendMessage("Speed buff disabled");
					  
					  if (getConfig().getBoolean("Jump")) {p.sendMessage("Jump buff enabled. Running every " + getConfig().getLong("Run again in") + " for " + getConfig().getInt("Jump time") + " ticks and power of " + getConfig().getInt("Jump power"));}
					  else p.sendMessage("Jump buff disabled");
					  p.sendMessage(" The schedule is set for every: " + getConfig().getInt("Run again in"));
					  
					  
			    	 }
				  if ( args[0].equals("stop"))
			    	 {
					  
					  p.sendMessage("Canceling all plugin tasks.");
					  Bukkit.getScheduler().cancelTasks(plugin);
					  
			    	 }
				  if ( args[0].equals("help") || args[0].equals("?"))
			    	 {
					  
					  help(p);
					  
			    	 }
	    	  	    	  
	    		  
	    	  }
	    	  
	    	  if( args.length==2)
	    	  {
	    		  if ( args[0].equals("check"))	
	    		  { 
	    			  
	    			  if(findPlayerByString(args[1]) != null)
	    			  {
	    				 int pots = findPlayerByString(args[1]).getActivePotionEffects().size();
	    				// for( PotionEffect aaa: pots.)
	    				// {
	    					 p.sendMessage("Potion effects active on this player: " + pots);
	    				// }
	    			  } else p.sendMessage("There is no player with this name");
	    			  
	    		  }
	    		  
	    		  if ( args[0].equals("clean"))	
	    		  { 
	    			  
	    			  if(findPlayerByString(args[1]) != null)
	    			  {
	    				 Player target = findPlayerByString(args[1]);
	    				//  findPlayerByString(args[1]).
	    				 Collection<PotionEffect> pots = target.getActivePotionEffects();
	    				 for ( PotionEffect aaa: pots)
	    				 {
	    					 target.removePotionEffect(aaa.getType());
	    				 }
	    				 int potss = target.getActivePotionEffects().size();
	    					 p.sendMessage("Player " + args[1] + " has been cleaned. Now has: " + potss + " potion effects active on him." );
	    					 
	    					 
	    				
	    			  } else p.sendMessage("There is no player with this name");
	    			  
	    		  }
	    		  
	    	  }
	   }
      
      
        }
	return false;
      }
      
      private void help(Player player) {
    	 player.sendMessage( ChatColor.YELLOW + "......................................................." + ChatColor.GOLD + " Plugin made by: "+ ChatColor.YELLOW + ".......................................................");
      	player.sendMessage( ChatColor.YELLOW + "     o   \\ o /  _ o              \\ /               o_   \\ o /   o");
      	player.sendMessage( ChatColor.YELLOW + "    /|\\     |      /\\   __o        |        o__    /\\      |     /|\\");
      	player.sendMessage( ChatColor.YELLOW + "    / \\   / \\    | \\  /) |       /o\\       |  (\\   / |    / \\   / \\");
      	player.sendMessage( ChatColor.YELLOW + "......................................................." + ChatColor.GOLD + ChatColor.BOLD + " GoreaCraft  "+ ChatColor.YELLOW + ".......................................................");
      	
      	player.sendMessage("");
      	player.sendMessage( ChatColor.YELLOW + "/gbuff start: " + ChatColor.RESET +  " Starts the whole thing");
      	
      	player.sendMessage( ChatColor.YELLOW + "/gbuff stop :" + ChatColor.RESET + " Stops the current tasks");
      	
      	player.sendMessage( ChatColor.YELLOW + "/gbuff info" + ChatColor.RESET + " Some nice info that you might need.");
      	player.sendMessage( ChatColor.YELLOW + "/gbuff reload" + ChatColor.RESET + " Reloads the config file");
      	player.sendMessage( ChatColor.YELLOW + "/gbuff check <player>" + ChatColor.RESET + " Checks number of potion effects that players has active on him");
      	player.sendMessage( ChatColor.YELLOW + "/gbuff clean <player>" + ChatColor.RESET + " Cleans all potion effects on that player.");
      //	player.sendMessage( ChatColor.YELLOW + "/gbuff setworld:" + ChatColor.RESET + " Sets the new world in file and reloads the config too");
     // 	player.sendMessage( ChatColor.YELLOW + "/gbuf setspeed/setspeedtime/setspeedpower  :" + ChatColor.RESET + "variables for speed, reloads the config too");
      //	player.sendMessage( ChatColor.YELLOW + "/gbuf setjump/setjumptime/setsjumppower  :" + ChatColor.RESET + "variables for jump, reloads the config too");
      //	player.sendMessage( ChatColor.YELLOW + "/gbuf setscheduletime  :" + ChatColor.RESET + "sets the time for the buffs to reaply, reloads the config too");
		
	}

	public void runner() {
    	  
    
    	  Player[] players = Bukkit.getServer().getOnlinePlayers();
			for ( Player playera: players)
			{
				if (playera.getLocation().getWorld().getName().equals(getConfig().get("Dimension")))
				{
					Collection<PotionEffect> pots = playera.getActivePotionEffects();
   				 for ( PotionEffect aaa: pots)
   				 {
   					playera.removePotionEffect(aaa.getType());
   				 }
   				 
					if (getConfig().getBoolean("Speed")) playera.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, getConfig().getInt("Speed time"),getConfig().getInt("Speed power")));
					if (getConfig().getBoolean("Jump")) playera.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, getConfig().getInt("Jump time"),getConfig().getInt("Jump power")));
					

					//}
					//if(getConfig().getBoolean("Cuctom")) playera.addPotionEffects(effects)
				}
			}
      }
	private Player findPlayerByString(String name) 
	{
		for ( Player player : Bukkit.getServer().getOnlinePlayers())
		{
			if(player.getName().equals(name)) 
			{
				return player;
			}
		}
		
		return null;
	}  
}
