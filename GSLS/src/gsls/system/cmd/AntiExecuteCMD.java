package gsls.system.cmd;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import gsls.system.Main;

public class AntiExecuteCMD implements Listener {
	
	@EventHandler
	public void onChat(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		if(msg.equalsIgnoreCase("/execute") || msg.equalsIgnoreCase("/minecraft:execute")) {
			e.setCancelled(true);
			p.sendMessage(Main.prefix + "§cAre you kidding me? Don't do that ever again!");
		}
	}

}
