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

public class NickCMD implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(args.length == 0) {
				if(p.hasPermission("mlps.changeNick.admin")) {
					p.sendMessage(Main.prefix + "§7Usage: /setnick <Player> <new Nick*|reset>");
					p.sendMessage("§7* -> max. 16 Characters!");
				}else if(p.hasPermission("mlps.changeNick")) {
					p.sendMessage(Main.prefix + "§7Usage: /setnick <new Nick*|reset>");
					p.sendMessage("§7* -> max. 16 Characters!");
				}else {
					p.sendMessage(Main.noperm("mlps.changeNick.admin"));
				}
			}else if(args.length >= 1) {
				if(p.hasPermission("mlps.changeNick.admin")) {
					Player p2 = Bukkit.getPlayerExact(args[0]);
					if(p2 == null) {
						p.sendMessage(Main.prefix + "§cThe given Player is not online. §7Player: §9" + args[0]);
					}else {
						String nick = args[1];
						if(nick.length() <= 16) {
							if(nick.equalsIgnoreCase("reset")) {
								try {
									PreparedStatement ps1 = MySQL.getConnection().prepareStatement("UPDATE playerigid SET nickname = ? WHERE uuid = ?");
									ps1.setString(1, "RESET");
									ps1.setString(2, p.getUniqueId().toString().replaceAll("-", ""));
									ps1.executeUpdate();
									p.sendMessage(Main.prefix + "§7User §8(§9" + p2.getName() + "§8)§7 nick's got resetted.");
								}catch (SQLException e) {
								}
							}else {
								try {
									PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT nickname FROM playerigid WHERE nickname = ?");
									ps.setString(1, nick);
									ResultSet rs = ps.executeQuery();
									rs.next();
									String nick1 = rs.getString("nickname");
									if(nick1.equalsIgnoreCase(nick)) {
										PreparedStatement ps1 = MySQL.getConnection().prepareStatement("UPDATE playerigid SET nickname = ? WHERE uuid = ?");
										ps1.setString(1, nick);
										ps1.setString(2, p.getUniqueId().toString().replaceAll("-", ""));
										ps1.executeUpdate();
										p.sendMessage(Main.prefix + "§cWarning! The given Nick is already in use. §7Nick: §9" + nick);
										p.sendMessage("§aInfo: As moderator, you can set it anyway. User §8(§9" + p2.getName() + "§8)§a has now the Nick!");
									}
								} catch (SQLException e) {
									try {
										PreparedStatement ps1 = MySQL.getConnection().prepareStatement("UPDATE playerigid SET nickname = ? WHERE uuid = ?");
										ps1.setString(1, nick);
										ps1.setString(2, p.getUniqueId().toString().replaceAll("-", ""));
										ps1.executeUpdate();
										p.sendMessage(Main.prefix + "§7The nick §8(§9" + nick + "§8)§7 is now in use from §9" + p2.getName());
									}catch (SQLException e1) {
									}
								}
							}
						}else {
							p.sendMessage(Main.prefix + "§cThe nickname is only allowed up to 16 Chars! §7Given: §9" + nick.length());
						}
					}
				}else if(p.hasPermission("mlps.changeNick")) {
					String nick = args[0];
					if(nick.length() <= 16) {
						if(nick.equalsIgnoreCase("reset")) {
							try {
								PreparedStatement ps1 = MySQL.getConnection().prepareStatement("UPDATE playerigid SET nickname = ? WHERE uuid = ?");
								ps1.setString(1, "RESET");
								ps1.setString(2, p.getUniqueId().toString().replaceAll("-", ""));
								ps1.executeUpdate();
								p.sendMessage(Main.prefix + "§7You resetted your nickname");
							}catch (SQLException e) {
							}
						}else {
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT nickname FROM playerigid WHERE nickname = ?");
								ps.setString(1, nick);
								ResultSet rs = ps.executeQuery();
								rs.next();
								String nick1 = rs.getString("nickname");
								if(nick1.equalsIgnoreCase(nick)) {
									p.sendMessage(Main.prefix + "§cThe nickname is already is use!");
								}
							}catch (SQLException e) {
								try {
									PreparedStatement ps1 = MySQL.getConnection().prepareStatement("UPDATE playerigid SET nickname = ? WHERE uuid = ?");
									ps1.setString(1, nick);
									ps1.setString(2, p.getUniqueId().toString().replaceAll("-", ""));
									ps1.executeUpdate();
									p.sendMessage(Main.prefix + "§7Your nick is now: §9" + nick);
								}catch (SQLException e1) {
								}
							}
						}
					}else {
						p.sendMessage(Main.prefix + "§cThe nickname is only allowed up to 16 Chars! §7Given: §9" + nick.length());
					}
				}
			}
		}
		return true;
	}

}
