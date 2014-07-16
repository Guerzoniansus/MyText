package NomarTheHero;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {

	private MonkeyPlugin plugin;

	public VoteCommand(MonkeyPlugin instance) {
		plugin = instance;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length != 2) {
			return true;
		}

		final String ign = args[0];

		final Player player = Bukkit.getServer().getPlayer(ign);

		if (player == null) {
			Bukkit.getServer().getLogger().info("Could not give tempperm time to player " + ign);
			return true;

		}

		String subcmd = args[1].toLowerCase();

		if (subcmd.equals("wetime")) {

			// if player already has voted and is in the 30 min range
			if (MonkeyPlugin.WEvotes.containsKey(ign)) {

				// gets the existing VT var
				WEVoteTime existing = MonkeyPlugin.WEvotes.get(ign);

				// cancels the existing VT var so it doesn't run
				existing.cancel();

				// makes new VT
				WEVoteTime newVT = new WEVoteTime(ign, System.currentTimeMillis(), 36000L + existing.getTicksLeft());

				// removes old VT
				MonkeyPlugin.WEvotes.remove(ign);

				// adds VT to list
				MonkeyPlugin.WEvotes.put(ign, newVT);

				// schedules the VT
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, newVT, 36000L + existing.getTicksLeft());

				// sends confirmation
				player.sendMessage(ChatColor.GOLD + "30 minutes have been added onto your remaining WorldEdit time!");

				return true;
			}

			/*
			 * ANNOUNCE TO LE SERVER THAT SUCH AND SUCH HAS VOTED BLA BLA YEY
			 * P.S. make sure to change the 600L to whatever time you want (in
			 * server ticks)
			 */

			// makes new WEVoteTime var
			WEVoteTime weTime = new WEVoteTime(ign, System.currentTimeMillis(), 36000L);

			// puts in in le list
			MonkeyPlugin.WEvotes.put(ign, weTime);

			// 36000 ticks in 30 minutes

			// makes le WEVoteTime var run in 30 minutes
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, weTime, 36000L);

			// allows the player to use WE
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " set worldedit.*");

			return true;

		} else if (subcmd.equals("tpatime")) {
			// if player already has voted and is in the 30 min range
			if (MonkeyPlugin.TPAvotes.containsKey(ign)) {

				// gets the existing VT var
				TPAVoteTime existing = MonkeyPlugin.TPAvotes.get(ign);

				// cancels the existing VT var so it doesn't run
				existing.cancel();

				// makes new VT
				TPAVoteTime newVT = new TPAVoteTime(ign, System.currentTimeMillis(), 1728000L + existing.getTicksLeft());

				// removes old VT
				MonkeyPlugin.TPAvotes.remove(ign);

				// adds VT to list
				MonkeyPlugin.TPAvotes.put(ign, newVT);

				// schedules the VT
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, newVT, 1728000L + existing.getTicksLeft());

				// sends confirmation
				player.sendMessage(ChatColor.GOLD + "24 hours have been added onto your remaining TPA time!");

				return true;
			}

			/*
			 * ANNOUNCE TO LE SERVER THAT SUCH AND SUCH HAS VOTED BLA BLA YEY
			 * P.S. make sure to change the 600L to whatever time you want (in
			 * server ticks)
			 */

			// makes new TPAVoteTime var
			TPAVoteTime weTime = new TPAVoteTime(ign, System.currentTimeMillis(), 1728000L);

			// puts in in le list
			MonkeyPlugin.TPAvotes.put(ign, weTime);

			// 36000 ticks in 30 minutes

			// makes le TPAVoteTime var run in 30 minutes
			// add 8000 to end
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, weTime, 1728000L);

			// allows the player to use TPA
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " set essentials.tpa");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " set essentials.tpahere");

			return true;

		} else if (subcmd.equals("votestat")) {

			String tpa = ChatColor.GOLD + "TPA voting stats: " + ChatColor.YELLOW;
			String we = ChatColor.GOLD + "WE voting stats: " + ChatColor.YELLOW;

			if (MonkeyPlugin.WEvotes.containsKey(ign)) {

				WEVoteTime wev = MonkeyPlugin.WEvotes.get(ign);

				we += ("WE time left: (" + wev.getTicksLeft() + "ticks) (" + (wev.getTicksLeft() / 1200.0) + "minutes)");

			} else {

				we += "Has not voted.";

			}

			if (MonkeyPlugin.TPAvotes.containsKey(ign)) {

				TPAVoteTime wev = MonkeyPlugin.TPAvotes.get(ign);

				tpa += ("TPA time left: (" + wev.getTicksLeft() + " ticks) (" + (wev.getTicksLeft() / 1200.0) + " minutes)");

			} else {

				tpa += "Has not voted.";

			}

			sender.sendMessage(tpa);
			sender.sendMessage(we);

		}

		return true;
	}
}
