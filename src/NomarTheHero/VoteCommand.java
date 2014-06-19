package NomarTheHero;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {

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
				plugin.getServer().getLogger().info("Could not give tempperm time to player " + ign);
				return true;

			}

			if (args[1].equalsIgnoreCase("wetime")) {

				// if player already has voted and is in the 30 min range
				if (MyText.WEvotes.containsKey(ign)) {

					// gets the existing VT var
					WEVoteTime existing = MyText.WEvotes.get(ign);

					// cancels the existing VT var so it doesn't run
					existing.cancel();

					// makes new VT
					WEVoteTime newVT = new WEVoteTime(ign, System.currentTimeMillis());

					// adds VT to list
					MyText.WEvotes.put(ign, newVT);

					// schedules the VT
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, newVT, 600L + existing.getTicksLeft());

					// sends confirmation
					player.sendMessage(ChatColor.GOLD + "30 minutes have been added onto your remaining time!");

					return true;
				}

				/*
				 * ANNOUNCE TO LE SERVER THAT SUCH AND SUCH HAS VOTED BLA BLA
				 * YEY
				 */

				// makes new WEVoteTime var
				WEVoteTime weTime = new WEVoteTime(ign, System.currentTimeMillis());

				// puts in in le list
				MyText.WEvotes.put(ign, weTime);

				// 36000 ticks in 30 minutes

				// makes le WEVoteTime var run in 30 minutes
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, weTime, 600L);

				// allows the player to use WE
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " set worldedit.*");

				return true;
			}

			else if (args[1].equalsIgnoreCase("tpatime")) {
				// if player already has voted and is in the 30 min range
				if (MyText.TPAvotes.containsKey(ign)) {

					// gets the existing VT var
					TPAVoteTime existing = MyText.TPAvotes.get(ign);

					// cancels the existing VT var so it doesn't run
					existing.cancel();

					// makes new VT
					TPAVoteTime newVT = new TPAVoteTime(ign, System.currentTimeMillis());

					// schedules the VT
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, newVT, 600L + existing.getTicksLeft());

					// adds VT to list
					MyText.TPAvotes.put(ign, newVT);

					return true;
				}

				/*
				 * ANNOUNCE TO LE SERVER THAT SUCH AND SUCH HAS VOTED BLA BLA
				 * YEY
				 */

				// makes new TPAVoteTime var
				TPAVoteTime weTime = new TPAVoteTime(ign, System.currentTimeMillis());

				//
				MyText.TPAvotes.put(ign, weTime);

				// 36000 ticks in 30 minutes

				// makes le TPAVoteTime var run in 30 minutes
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, weTime, 600L);

				// allows the player to use TPA
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " set worldedit.*");

				// sends confirmation
				player.sendMessage(ChatColor.GOLD + "30 minutes have been added onto your remaining time!");

				return true;

				// 1728000 =

			}

		}

		return true;

	}
}
