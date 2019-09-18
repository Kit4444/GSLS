package gsls.system.cmd;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import gsls.system.Main;

public class WarpSetCMD implements CommandExecutor{
	
	File warp = new File("plugins/GSLS/warps.yml");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(args.length == 0) {
				p.sendMessage(Main.prefix + "§7Usage: /setwarp <Gamename*>");
				p.sendMessage("§7* -> Defaults: BedWars, GunGames");
			}else if(args.length == 1) {
				String game = args[0];
				if(p.hasPermission("mlps.setWarp.Gameslobby")) {
					try {
						saveCFG(p, game, warp);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					p.sendMessage(Main.noperm("mlps.setWarp.Gameslobby"));
				}
			}else {
				p.sendMessage(Main.prefix + "§7Usage: /setwarp <Gamename*>");
				p.sendMessage("§7* -> Defaults: BedWars, GunGames");
			}
		}
		return false;
	}
	
	private void saveCFG(Player p, String game, File file) throws IOException {
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		Location loc = p.getLocation();
		SimpleDateFormat time = new SimpleDateFormat("dd.MM.yy - HH:mm:ss");
	    String stime = time.format(new Date());
	    String admin = p.getDisplayName();
	    double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        double pitch = loc.getPitch();
        double yaw = loc.getYaw();
        String world = loc.getWorld().getName().toString();
        cfg.set("Warp." + game + ".X", x);
        cfg.set("Warp." + game + ".Y", y);
        cfg.set("Warp." + game + ".Z", z);
        cfg.set("Warp." + game + ".Pitch", pitch);
        cfg.set("Warp." + game + ".Yaw", yaw);
        cfg.set("Warp." + game + ".World", world);
        cfg.set("Warp." + game + ".Admin", admin);
        cfg.set("Warp." + game + ".Time", stime);
        cfg.save(file);
        if(game.equalsIgnoreCase("bedwars")) {
        	p.sendMessage(Main.prefix + "§7Default-Warp setted. Game: BedWars");
        }else if(game.equalsIgnoreCase("gungames")) {
        	p.sendMessage(Main.prefix + "§7Default-Warp setted. Game: GunGames");
        }else {
        	p.sendMessage(Main.prefix + "Custom-Warp setted. Game: " + game);
        }
        
	}

}
