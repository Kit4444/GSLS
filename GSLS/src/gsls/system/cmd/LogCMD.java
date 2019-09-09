package gsls.system.cmd;

import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gsls.system.Main;

public class LogCMD implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(cmd.getName().equalsIgnoreCase("login")) {
				if(p.hasPermission("mlps.canBan")) {
					HashMap<String, Object> log = new HashMap<>();
					log.put("Name", p.getUniqueId().toString().replaceAll("-", ""));
					try {
						Main.mysql.update("loginsys", "loggedin", true, log);
						p.sendMessage(Main.prefix + "§7Successfully logged in.");
					}catch (SQLException e) {
						p.sendMessage(Main.unavailable);
						e.printStackTrace();
					}
				}else {
					p.sendMessage(Main.noperm);
				}
			}else if(cmd.getName().equalsIgnoreCase("logout")) {
				if(p.hasPermission("mlps.canBan")) {
					HashMap<String, Object> log = new HashMap<>();
					log.put("Name", p.getUniqueId().toString().replaceAll("-", ""));
					try {
						Main.mysql.update("loginsys", "loggedin", false, log);
						p.sendMessage(Main.prefix + "§7Successfully logged out.");
					}catch (SQLException e) {
						p.sendMessage(Main.unavailable);
						e.printStackTrace();
					}
				}else {
					p.sendMessage(Main.noperm);
				}
			}
		}
		return true;
	}

}
