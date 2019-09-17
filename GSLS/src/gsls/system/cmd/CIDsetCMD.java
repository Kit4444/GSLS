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

public class CIDsetCMD implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(cmd.getName().equalsIgnoreCase("setpf")) {
				if(args.length == 0) {
					p.sendMessage(Main.prefix + "§7Usage: /setpf <Player> <newPF*>");
					p.sendMessage("* -> max length: 12 Chars incl. Colorcodes!");
				}else if(args.length >= 1) {
					if(p.hasPermission("mlps.setPF")) {
						Player p2 = Bukkit.getPlayerExact(args[0]);
						if(p2 == null) {
							p.sendMessage(Main.prefix + "§cThe given Player is not online. §7Player: §9" + args[0]);
						}else {
							String uuid = p2.getUniqueId().toString().replaceAll("-", "");
							String prefix = args[1].replaceAll("&", "§");
							if(prefix.length() <= 12) {
								try {
									PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE playerigid SET prefix = ? WHERE uuid = ?");
									ps.setString(1, prefix);
									ps.setString(2, uuid);
									ps.executeUpdate();
									p.sendMessage(Main.prefix + "§7User updated: " + p2.getDisplayName() + "§7, new Prefix:§f " + prefix);
								}catch (SQLException e) {
									
								}
							}else {
								p.sendMessage(Main.prefix + "§cThe prefix is only allowed up to 12 chars! §7Given: §9" + prefix.length());
							}
						}
					}else {
						p.sendMessage(Main.noperm);
					}
				}
			}else if(cmd.getName().equalsIgnoreCase("setid")) {
				if(args.length == 0) {
					p.sendMessage(Main.prefix + "§7Usage: /setid <Player> <newID*>");
					p.sendMessage("* -> ID needs to be unique! Range: 1 - 750000");
				}else if(args.length >= 1) {
					if(p.hasPermission("mlps.setID")) {
						Player p2 = Bukkit.getPlayerExact(args[0]);
						if(p2 == null) {
							p.sendMessage(Main.prefix + "§cThe given Player is not online. §7Player: §9" + args[0]);
						}else {
							String uuid = p2.getUniqueId().toString().replaceAll("-", "");
							String iid = args[1];
							int id = Integer.valueOf(iid);
							if(id >= 1 || id <= 750000) {
								try {
									PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT id FROM playerigid WHERE id = ?");
									ps.setInt(1, id);
									ResultSet rs = ps.executeQuery();
									rs.next();
									int mid = rs.getInt("id");
									if(mid == id) {
										p.sendMessage(Main.prefix + "§cThis ID is already given. §7ID: §9" + id);
									}
								}catch (SQLException e) {
									try {
										PreparedStatement pss = MySQL.getConnection().prepareStatement("UPDATE playerigid SET id = ? WHERE uuid = ?");
										pss.setInt(1, id);
										pss.setString(2, uuid);
										pss.executeUpdate();
										p.sendMessage(Main.prefix + "§7User updated: " + p2.getDisplayName() + "§7, new ID: §9" + id);
									}catch (SQLException ee) {
										ee.printStackTrace();
									}
								}
							}else {
								p.sendMessage(Main.prefix + "§cWarning, the ID must be between 1 - 750000 !");
							}
						}
					}else {
						p.sendMessage(Main.noperm);
					}
				}
			}
		}
		return false;
	}

}
