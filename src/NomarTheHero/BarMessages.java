package NomarTheHero;

import java.util.Random;

import org.bukkit.ChatColor;

public class BarMessages {
	
	public String green = ChatColor.BOLD + "" + ChatColor.DARK_GREEN + "";
	public String gold = ChatColor.BOLD + "" + ChatColor.GOLD + "";
	
	public String[] messages = {
			green + "The Survival Server IP:" + gold + "Life.MonkeyGamesMC.com",
			gold + "Donating" + green + "keeps the server alive! Type" + gold + "/shop!",
			green + "To turn chat sounds off type" + gold + "/sounds off",
			green + "Don't forget to" + gold + "/vote" + green + "for WorldEdit!",
			green + "Website: " + gold + "http://MonkeyGamesMC.com"
	};
	
	public String randomString(){
		
		Random rand = new Random();
		int x = rand.nextInt(messages.length);		
		
		return messages[x];
	}
	
	
	

}
