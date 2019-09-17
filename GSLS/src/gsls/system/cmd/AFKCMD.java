package gsls.system.cmd;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import gsls.api.mysql.lb.MySQL;
import gsls.system.Main;

public class AFKCMD implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(Main.afk_list.contains(p.getName())) {
				Main.afk_list.remove(p.getName());
				updateMySQLAFK(false, p);
				p.sendMessage(Main.prefix + "§cYou left the AFK-State.");
				Bukkit.broadcastMessage(Main.prefix + p.getDisplayName() + "§7 is no longer AFK.");
			}else {
				Main.afk_list.add(p.getName());
				updateMySQLAFK(true, p);
				p.sendMessage(Main.prefix + "§aYou joined the AFK-State.");
				Bukkit.broadcastMessage(Main.prefix + p.getDisplayName() + "§7 is now AFK.");
			}
		}
		return true;
	}
	
	@EventHandler
	public void onMoveCheck(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(Main.afk_list.contains(p.getName())) {
			Main.afk_list.remove(p.getName());
			updateMySQLAFK(false, p);
		}
	}
	
	public void updateMySQLAFK(boolean boo, Player p){
		PreparedStatement ps;
		try {
			ps = MySQL.getConnection().prepareStatement("UPDATE playerigid SET iscurrentlyafk = ? WHERE uuid = ?");
			ps.setBoolean(1, boo);
			ps.setString(2, p.getUniqueId().toString().replaceAll("-", ""));
			ps.executeUpdate();
		} catch (SQLException e) {
			p.sendMessage(Main.unavailable);
		}
	}

}
