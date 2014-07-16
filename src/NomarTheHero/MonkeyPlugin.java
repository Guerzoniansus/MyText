package NomarTheHero;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class MonkeyPlugin extends JavaPlugin implements Listener {

	// http://pastebin.com/nGW57tnm

	private BarMessages bMessages = new BarMessages();

	public static HashMap<String, WEVoteTime> WEvotes = new HashMap<String, WEVoteTime>();

	public static HashMap<String, TPAVoteTime> TPAvotes = new HashMap<String, TPAVoteTime>();

	private String prefix = ChatColor.GOLD + "---------------------------------------------";
	private String red = ChatColor.RED.toString();

	public void onEnable() {
		getServer().getPluginManager().registerEvents(new ChatWatcher(), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
		getCommand("tempperm").setExecutor(new VoteCommand(this));
		getCommand("sounds").setExecutor(new SoundsCommand());
		this.saveDefaultConfig();

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			SoundsCommand.soundEnabled.add(player.getName());

		}

		BukkitScheduler barBroadcaster = Bukkit.getServer().getScheduler();
		barBroadcaster.scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {

				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (getConfig().getString("server").equals("creative"))
						BarAPI.setMessage(p, bMessages.randomStringCreative(), 10);

					if (getConfig().getString("server").equals("survival"))
						BarAPI.setMessage(p, bMessages.randomStringSurvival(), 10);

					p.playSound(p.getLocation(), Sound.NOTE_PLING, 5, 1);

				}

				// 6000 ticks = 5 minutes
			}
		}, 0L, 6000);

		getPlayers();

	}

	public void onDisable() {
		for (String pName : WEvotes.keySet()) {

			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + pName + " unset worldedit.*");

		}

		for (String pName : TPAvotes.keySet()) {

			storeTPAPlayer(pName, TPAvotes.get(pName).getTicksLeft());

			//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + pName + " unset essentials.tpa");
			//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + pName + " unset essentials.tpahere");

		}

		this.saveConfig();

	}

	public void storeTPAPlayer(String name, long timeLeft) {

		// Map<String, Long> map = new HashMap<String, Long>();

		// map.put(name, timeLeft);

		getConfig().set("tpavote." + name + ".time", timeLeft);

	}

	public void getPlayers() {

		// getConfig().get

		// for (Map<?, ?> map : getConfig().get) {

		Set<String> peoples = getConfig().getConfigurationSection("tpavote").getKeys(false);

		for (String people : peoples) {

			long timeLeft = getConfig().getLong("tpavote." + people + ".time");

			TPAVoteTime TV = new TPAVoteTime(people, System.currentTimeMillis(), timeLeft);

			TPAvotes.put(people, TV);

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, TV, timeLeft);

		}

		// }

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (p.hasPermission("MyText.use") || p.isOp()) {
			if (args.length == 0) {
				switch (cmd.getName().toLowerCase()) {
				case "ranks":
					p.sendMessage(prefix);
					p.sendMessage(ChatColor.RED + " Info about all ranks can be found here:");
					p.sendMessage(ChatColor.RED + " http://monkeygamesmc.com/help");
					p.sendMessage(prefix);
					return true;
				case "shop":
					p.sendMessage(prefix);
					p.sendMessage(red + " Vip: €7,99 - Vip+: €14,99 - Hero: €24,99");
					p.sendMessage(red + " To see donator ranks and benefits go here:");
					p.sendMessage(red + " http://monkeygamesmc.com/shop");
					p.sendMessage(prefix);
					return true;
				case "website":
					p.sendMessage(prefix);
					p.sendMessage(red + " Website:");
					p.sendMessage(red + " http://MonkeyGamesMC.com");
					p.sendMessage(prefix);
					return true;
				case "faq":
					p.sendMessage(prefix);
					p.sendMessage(red + " Frequently Asked Questions:");
					p.sendMessage(red + " http://MonkeyGamesMC.com/help");
					p.sendMessage(prefix);
					return true;
				case "contest":
					p.sendMessage(prefix);
					p.sendMessage(red + " Info about build contest:");
					p.sendMessage(red + " http://monkeygamesmc.com/forum/m/20531573/viewthread/11143786/-read-on-building-contests");
					p.sendMessage(prefix);
					return true;
				case "member":
					p.sendMessage(prefix);
					p.sendMessage(red + " To get Member visit this page:");
					p.sendMessage(red + " http://MonkeyGamesMC.com/Member");
					p.sendMessage(prefix);
				}
			}
		} else {
			p.sendMessage(ChatColor.RED + "You do not have permission to use that command.");

		}

		return false;
	}

}
