package NomarTheHero;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor{
		
	private MyText plugin;
	
	public VoteCommand(MyText instance) {
		plugin = instance;
		}
	
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            if (command.getName().equalsIgnoreCase("tempperm")) {

                    final String ign = args[0];

                    final Player player = plugin.getServer().getPlayer(ign);

                    if (player == null) {
                            plugin.getServer().getLogger().info("Could not give WorldEdit time to player " + ign);
                            return true;

                    }
                    
                    if (args[1].equalsIgnoreCase("wetime")){
                    	Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " set worldedit.*");

                        MyText.players.add(ign);

                        /*
                         * ANNOUNCE TO LE SERVER THAT SUCH AND SUCH HAS VOTED BLA BLA YEY
                         */

                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                                @Override
                                public void run() {

                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " unset worldedit.*");

                                        MyText.players.remove(ign);

                                        Player player = Bukkit.getServer().getPlayer(ign);

                                        if (player != null) {
                                                player.sendMessage(ChatColor.RED + "Your 30 minutes of WorldEdit time has ran out!");
                                                player.sendMessage(ChatColor.RED + "Vote again with /vote for more time!");
                                        }
                                }
                        }

                        // note: 20 game ticks in 1 second, that number below = how many
                        // ticks the player should be able to use WE for.

                                        // 30 minutes * 60 seconds = 1800 seconds
                                        // 1800 seconds * 20 ticks = 36000 game ticks
                                        // check the math if you want.
                                        // set the 600L to 36000L when ready to deploy, as the 600L
                                        // is just for testing

                                        , 36000L);

                        return true;
                    }
                    
                    else if (args[1].equalsIgnoreCase("tpatime")){
                    	 Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " set essentials.tpa");
                         Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " set essentials.tpahere");

                         MyText.players.add(ign);

                         /*
                          * ANNOUNCE TO LE SERVER THAT SUCH AND SUCH HAS VOTED BLA BLA YEY
                          */

                         plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                                 @Override
                                 public void run() {

                                         Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " unset essentials.tpa");
                                         Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " unset essentials.tpahere");

                                         MyText.players.remove(ign);

                                         Player player = Bukkit.getServer().getPlayer(ign);

                                         if (player != null) {
                                                 player.sendMessage(ChatColor.RED + "Your 30 minutes of /tpa time has ran out!");
                                                 player.sendMessage(ChatColor.RED + "Vote again with /vote for more time!");
                                         }
                                 }
                         }

                         // note: 20 game ticks in 1 second, that number below = how many
                         // ticks the player should be able to use WE for.

                                         // 30 minutes * 60 seconds = 1800 seconds
                                         // 1800 seconds * 20 ticks = 36000 game ticks
                                         // check the math if you want.
                                         // set the 600L to 36000L when ready to deploy, as the 600L
                                         // is just for testing

                                         , 1728000L);

                         return true;
                    	
                    }                  

            } 

		return true;  
            
           
    }

}
