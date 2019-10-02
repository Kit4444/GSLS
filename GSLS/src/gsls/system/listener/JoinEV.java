package gsls.system.listener;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import gsls.system.Main;

public class JoinEV implements Listener {
	
	File file = new File("plugins/GSLS/warps.yml");

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		Location loc = p.getLocation();
		loc.setX(cfg.getDouble("Warp.spawn.X"));
		loc.setY(cfg.getDouble("Warp.spawn.Y"));
		loc.setZ(cfg.getDouble("Warp.spawn.Z"));
		loc.setYaw((float) cfg.getDouble("Warp.spawn.Yaw"));
		loc.setPitch((float) cfg.getDouble("Warp.spawn.Pitch"));
		loc.setWorld(Bukkit.getWorld(cfg.getString("Warp.spawn.World")));
		if(p.hasPlayedBefore()) {
			p.teleport(loc);
		}else {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					p.teleport(loc);
					p.sendTitle("§aWelcome", "§7on the Gameslobby-Server");
				}
			}, 10);
		}
	}
	
	
}
