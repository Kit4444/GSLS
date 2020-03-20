package gsls.system.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gsls.api.mysql.lb.MySQL;
import gsls.system.LanguageHandler;
import gsls.system.Main;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

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
						if(logStat(p) == true) {
							LanguageHandler.sendMSGReady(p, "cmd.login.already");
						}else {
							Main.mysql.update("loginsys", "loggedin", true, log);
							LanguageHandler.sendMSGReady(p, "cmd.login.success");
						}
					}catch (SQLException e) {
					}
				}else {
					LanguageHandler.noperm(p);
				}
			}else if(cmd.getName().equalsIgnoreCase("logout")) {
				if(p.hasPermission("mlps.canBan")) {
					HashMap<String, Object> log = new HashMap<>();
					log.put("Name", p.getUniqueId().toString().replaceAll("-", ""));
					try {
						if(!logStat(p) == true) {
							LanguageHandler.sendMSGReady(p, "cmd.logout.already");
						}else {
							Main.mysql.update("loginsys", "loggedin", false, log);
							LanguageHandler.sendMSGReady(p, "cmd.logout.success");
						}
					}catch (SQLException e) {
					}
				}else {
					LanguageHandler.noperm(p);	
				}
			}else if(cmd.getName().equalsIgnoreCase("tg")) {
				PermissionUser po = PermissionsEx.getUser(p);
				if(po.inGroup("Patron") || po.inGroup("Beta") || po.inGroup("Mod") || po.inGroup("Support") || po.inGroup("Builder") || po.inGroup("VIPpls") || po.inGroup("NitroBooster") || po.inGroup("Friend") || po.inGroup("Premiumpls")) {
					HashMap<String, Object> log = new HashMap<>();
					log.put("Name", p.getUniqueId().toString().replaceAll("-", ""));
					try {
						if(logStat(p) == true) {
							Main.mysql.update("loginsys", "loggedin", false, log);
							LanguageHandler.sendMSGReady(p, "cmd.tg.hide");
						}else {
							Main.mysql.update("loginsys", "loggedin", true, log);
							LanguageHandler.sendMSGReady(p, "cmd.tg.visible");
						}
					}catch (SQLException e) {
						LanguageHandler.unavailable(p);
					}
				}else {
					LanguageHandler.noperm(p);
				}
			}
		}
		return true;
	}
	
	private boolean logStat(Player p) throws SQLException {
		PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM loginsys WHERE Name = ?");
		ps.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
		ResultSet rs = ps.executeQuery();
		rs.next();
		boolean boo = rs.getBoolean("loggedin");
		return boo;
	}
}