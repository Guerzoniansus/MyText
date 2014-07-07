package NomarTheHero;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class JoinEvent implements Listener{
	
	private MonkeyPlugin plugin;

	public JoinEvent(MonkeyPlugin instance) {
		plugin = instance;
	}
	
	private String gold = ChatColor.GOLD.toString();
	private String boldWhite = ChatColor.WHITE.toString() + ChatColor.BOLD.toString();
	private String grey = ChatColor.GRAY.toString();
	private String boldGreen = ChatColor.GREEN.toString() + ChatColor.BOLD.toString();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		
		for (Player player : Bukkit.getServer().getOnlinePlayers()){
			player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
		}
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
				if (plugin.getConfig().getString("server").equals("creative"))
					joinMessageCreative(p);
				else if (plugin.getConfig().getString("server").equals("survival"))
					joinMessageSurvival(p);
			}
		}, 40L);
	}
	
	private void joinMessageCreative(Player p) {

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

	private void joinMessageSurvival(Player p) {
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
