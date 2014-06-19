package NomarTheHero;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TPAVoteTime implements Runnable {

	private String ign;
	private long startTime;
	private int id;
	private boolean cancelled = false;

	public TPAVoteTime(String ign, long startTime) {
		this.ign = ign;
		this.startTime = startTime;

	}

	@Override
	public void run() {

		// if cancelled do nuffin
		if (cancelled) {
			return;

		}

		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + ign + " unset worldedit.*");

		Player player = Bukkit.getServer().getPlayer(ign);

		if (player != null) {
			player.sendMessage(ChatColor.RED + "Your TPA time has ran out!");
			player.sendMessage(ChatColor.RED + "Vote again with /vote for more time!");

		}

		MyText.TPAvotes.remove(ign);

	}

	public String getIgn() {
		return ign;

	}

	public void setID(int id) {
		this.id = id;

	}

	public int getID() {
		return id;

	}

	public void cancel() {
		cancelled = true;
		MyText.TPAvotes.remove(ign);

	}

	public long getTicksLeft() {

		// 20 ticks in a second
		// 1000 millis in a second
		// therefore 20 ticks in 1000 millis
		// 1000/20 = 50
		// 50 millis per tick!

		return (System.currentTimeMillis() - startTime) / 50;

	}
}
