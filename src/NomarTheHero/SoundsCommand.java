package NomarTheHero;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SoundsCommand implements CommandExecutor{
	
	public static Set<String> soundEnabled = new HashSet<String>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 1){
			
			Player p = (Player)sender;
			
			if (args[0].equalsIgnoreCase("on")){
				if (soundEnabled.contains(p.getName())){
					p.sendMessage("Chat sounds are already enabled!");
				}
				else{
					soundEnabled.add(p.getName());
					p.sendMessage("Enabled chat sounds!");
				}
			}
			else if (args[0].equalsIgnoreCase("off")){
				if (!soundEnabled.contains(p.getName())){
					p.sendMessage("Chat sounds are already disabled!");
				}
				else{
					soundEnabled.remove(p.getName());
					p.sendMessage("Disabled chat sounds!");
				}
			}
		}
		
		
		
		
		return false;
	}

}


