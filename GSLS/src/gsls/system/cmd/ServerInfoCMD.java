package gsls.system.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gsls.api.mysql.lb.MySQL;
import gsls.system.Main;

public class ServerInfoCMD implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			String servername = Bukkit.getServerName();
			int currServer = Bukkit.getOnlinePlayers().size();
			int currFullServer = retPlayers("BungeeCord", "currentPlayers");
			int maxPlayers = retPlayers("BungeeCord", "maxPlayers");
			int afkKick = Bukkit.getIdleTimeout();
			String serverid = Bukkit.getServerId();
			p.sendMessage("§7§m================§7[§aServer§cInfo§7]§m================");
			p.sendMessage("§7Server information for: " + servername);
			p.sendMessage("§7Online players: (§a" + currServer + "§7) §9" + currFullServer + "§7 / §c" + maxPlayers);
			if(afkKick <= 0) {
				p.sendMessage("§7AFK kick: disabled");
			}else if(afkKick >= 1) {
				p.sendMessage("§7AFK kick: §a" + afkKick + " §7minutes");
			}
			if(serverid.equalsIgnoreCase("E")) {
				p.sendMessage("§7Event Server: " + retEvent(servername, "eventname").replace("&", "§"));
			}else {
				p.sendMessage("§7Event Server: false");
			}
			
		}
		return true;
	}
	
	private int retPlayers(String server, String col) {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM serverstats WHERE server = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			i = rs.getInt(col);
		}catch (SQLException e) {
		}
		return i;
	}
	
	private String retEvent(String server, String col) {
		String s = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM serverstats WHERE server = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			s = rs.getString(col);
		}catch (SQLException e) {
		}
		return s;
	}

}
