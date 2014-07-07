package NomarTheHero;

import java.util.Random;

import org.bukkit.ChatColor;

public class BarMessages {
	
	
	public String green = ChatColor.BOLD + "" + ChatColor.DARK_GREEN + "";
	public String gold = ChatColor.BOLD + "" + ChatColor.GOLD + "";
	
	public String[] messagesCreative = {
			green + "The Survival Server IP: " + gold + "Life.MonkeyGamesMC.com",
			gold + "Donating " + green + "keeps the server alive! Type" + gold + "/shop!",
			green + "To turn chat sounds off type " + gold + "/sounds off",
			green + "Don't forget to " + gold + "/vote " + green + "for WorldEdit!",
			green + "Website:  " + gold + "http://MonkeyGamesMC.com"
	};
	
	public String[] messagesSurvival = {
			green + "The Creative Server IP: " + gold + "Build.MonkeyGamesMC.com",
			gold + "Donating " + green + "keeps the server alive! Type" + gold + "/shop!",
			green + "To turn chat sounds off type " + gold + "/sounds off",
			green + "Don't forget to " + gold + "/vote " + green + "for /tpa and $75!",
			green + "Website:  " + gold + "http://MonkeyGamesMC.com"
	};
	
	
	public String randomStringCreative(){
		
		Random rand = new Random();
		int x = rand.nextInt(messagesCreative.length);		
		
		return messagesCreative[x];
	}
	
	
	public String randomStringSurvival(){
		Random rand = new Random();
		int x = rand.nextInt(messagesSurvival.length);		
		
		return messagesSurvival[x];
		
	}
	
	
	

}
