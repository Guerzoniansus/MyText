package NomarTheHero;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatWatcher implements Listener{
	
	
		private Map<String, String> playerChat = new HashMap<String, String>();
		private Map<String, Long> chatCooldown = new HashMap<String, Long>();

		private String noSpamStarter = ChatColor.BLUE + "[" + ChatColor.LIGHT_PURPLE + "NoSpam" + ChatColor.BLUE + "] " + ChatColor.GOLD;
		
		private boolean cancel = false;

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

			float iPercent = .5f;
			if (message.length() > 6 && caps > 0) {
				if ((float) caps / message.length() >= iPercent) {
					e.setMessage(message.toLowerCase());
					p.sendMessage(noSpamStarter + "Do not use too many caps!");
				}
			}
			
			for (Player player : Bukkit.getServer().getOnlinePlayers()){
				if (SoundsCommand.soundEnabled.contains(p.getName())){
					player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
				}
			}

		}
	
		
	

}
