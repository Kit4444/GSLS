package gsls.system.listener;

import java.io.File;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import gsls.api.mc.ItemsAPI;
import gsls.system.Main;

public class JoinEV implements Listener {
	
	File file = new File("plugins/GSLS/warps.yml");

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		Location loc = p.getLocation();
		try {
			ScoreboardCLS.setScoreboard(p);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		p.setGameMode(GameMode.SURVIVAL);
		p.getInventory().clear();
		p.getInventory().setItem(4, ItemsAPI.defItem(Material.COMPASS, 1, 0, "§9Navigator"));
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
	
	@EventHandler
	public void onAction(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Navigator")) {
				
			}
		}
	}
}