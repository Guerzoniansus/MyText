package NomarTheHero;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener{
	
	public void onLeave(PlayerQuitEvent e){
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
		}
		
	}
	
	

}
