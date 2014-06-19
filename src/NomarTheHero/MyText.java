package NomarTheHero;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class MyText extends JavaPlugin implements Listener {

	// String = IGN

	public static HashMap<String, WEVoteTime> WEvotes = new HashMap<String, WEVoteTime>();

	public static HashMap<String, TPAVoteTime> TPAvotes = new HashMap<String, TPAVoteTime>();

	// public static ArrayList<String> votingPlayersWE = new
	// ArrayList<String>();

	// public static ArrayList<String> votingPlayersTPA = new
	// ArrayList<String>();

	public boolean cancel = false;

	public String prefix = ChatColor.GOLD + "---------------------------------------------";

	// colors
	private String red = ChatColor.RED.toString();
	private String gold = ChatColor.GOLD.toString();
	private String boldWhite = ChatColor.WHITE.toString() + ChatColor.BOLD.toString();
	private String grey = ChatColor.GRAY.toString();
	String boldGreen = ChatColor.GREEN.toString() + ChatColor.BOLD.toString();

	public String noSpamStarter = ChatColor.BLUE + "[" + ChatColor.LIGHT_PURPLE + "NoSpam" + ChatColor.BLUE + "] " + ChatColor.GOLD;

	public Map<String, String> playerChat = new HashMap<String, String>();
	public Map<String, Long> chatCooldown = new HashMap<String, Long>();

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
		getCommand("tempperm").setExecutor(new VoteCommand(this));
	}

	public void onDisable() {
		for (String pName : WEvotes.keySet()) {
			Player player = Bukkit.getPlayer(pName);

			if (player == null) {
				continue;

			}

			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + pName + " unset worldedit.*");

		}

		for (String pName : TPAvotes.keySet()) {
			Player player = Bukkit.getPlayer(pName);

			if (player == null) {
				continue;

			}

			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + pName + " unset essentials.tpa");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "perm player " + pName + " unset essentials.tpahere");

		}

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				if (getConfig().getString("server").equals("creative"))
					joinMessageCreative(p);
				else if (getConfig().getString("server").equals("survival"))
					joinMessageSurvival(p);
			}
		}, 40L);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onChat(AsyncPlayerChatEvent e) {

		String message = e.getMessage();
		Player p = e.getPlayer();

		long time = System.currentTimeMillis();

		if (chatCooldown.containsKey(p.getName())) {

			int waitTime = 1000;

			final int onlinePlayers = Bukkit.getOnlinePlayers().length;

			if (onlinePlayers > 15 && onlinePlayers <= 25) {
				waitTime = 1500;

			} else if (onlinePlayers > 25) {
				waitTime = 2000;

			}

			if (time - chatCooldown.get(p.getName()) < waitTime) {
				p.sendMessage(noSpamStarter + "Please wait at least " + ChatColor.AQUA + waitTime / 1000.0 + ChatColor.GOLD + " second between messages!");
				e.setCancelled(true);
				cancel = true;
				return;

			} else
				chatCooldown.put(p.getName(), time);

		} else
			chatCooldown.put(p.getName(), time);

		if (playerChat.containsKey(p.getName()) && cancel == false) {
			if (playerChat.get(p.getName()).equals(message)) {
				p.sendMessage(noSpamStarter + "Please do not say the same message twice!");
				e.setCancelled(true);
				return;
			} else
				playerChat.put(p.getName(), message);
		} else if (cancel == true) {
			cancel = false;
			return;
		} else
			playerChat.put(p.getName(), message);

		int caps = 0;
		for (int i = 0; i < message.length(); i++) {
			if (Character.isWhitespace(message.charAt(i)))
				continue;
			if (Character.isUpperCase(message.charAt(i))) {
				caps++;
			}
		}

		/*
		 * int iPercent = 70; if (message.length() > 6 && caps > 0){ if (caps /
		 * message.length() * 100 >= iPercent){
		 * e.setMessage(message.toLowerCase()); p.sendMessage(starter +
		 * "Do not use too much caps!"); } }
		 */

		float iPercent = .5f;
		if (message.length() > 6 && caps > 0) {
			if ((float) caps / message.length() >= iPercent) {
				e.setMessage(message.toLowerCase());
				p.sendMessage(noSpamStarter + "Do not use too many caps!");
			}
		}

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

	public void joinMessageCreative(Player p) {

		String prefix = gold + "*" + grey + "-" + gold + "*" + grey + "-" + gold + "*" + grey + "-" + gold + "*" + grey + "-" + gold + "*" + grey + "-" + gold + "*" + grey + "-" + gold + "*" + grey + "-";
		p.sendMessage(prefix + prefix + prefix + gold + "*");
		p.sendMessage("");
		p.sendMessage(boldWhite + " Welcome to MonkeyCraft Creative!");
		p.sendMessage(boldWhite + " Survival IP: Life.MonkeyGamesMC.com");
		p.sendMessage("");
		p.sendMessage(boldGreen + " To start building type /plotme auto");
		p.sendMessage(boldGreen + " Donate: /shop");
		p.sendMessage(boldGreen + " Member: /member");
		p.sendMessage(boldGreen + " Info, ranks, minigames etc: /faq");
		p.sendMessage(boldGreen + " Vote for 30 minutes WorldEdit: /vote");
		p.sendMessage("");
		p.sendMessage(boldGreen + " To stay updated visit the homepage regulary!");
		p.sendMessage("");
		p.sendMessage(prefix + prefix + prefix + gold + "*");
	}

	public void joinMessageSurvival(Player p) {
		String prefix = gold + "*" + grey + "-" + gold + "*" + grey + "-" + gold + "*" + grey + "-" + gold + "*" + grey + "-" + gold + "*" + grey + "-" + gold + "*" + grey + "-" + gold + "*" + grey + "-";
		p.sendMessage(prefix + prefix + prefix + gold + "*");
		p.sendMessage("");
		p.sendMessage(boldWhite + " Welcome to MonkeyCraft Survival!");
		p.sendMessage(boldWhite + " Creative IP: Build.MonkeyGamesMC.com");
		p.sendMessage("");
		p.sendMessage(boldGreen + " Donate: /shop");
		p.sendMessage(boldGreen + " Member: /member");
		p.sendMessage(boldGreen + " Info, ranks, minigames etc: /faq");
		p.sendMessage("");
		p.sendMessage(boldGreen + " To stay updated visit the homepage regulary!");
		p.sendMessage("");
		p.sendMessage(prefix + prefix + prefix + gold + "*");
	}

}
