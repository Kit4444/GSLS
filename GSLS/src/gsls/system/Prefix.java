package gsls.system;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gsls.api.mysql.lb.MySQL;

public class Prefix {

	public static String returnPrefix(String type) {
		String s = null;
		if(MySQL.isConnected()) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM prefixig WHERE typ = ?");
				if(type.equalsIgnoreCase("prefix")) {
					ps.setString(1, type.toLowerCase());
					ResultSet rs = ps.executeQuery();
					rs.next();
					s = rs.getString("prefix");
				}else if(type.equalsIgnoreCase("msg")) {
					ps.setString(1, type.toLowerCase());
					ResultSet rs = ps.executeQuery();
					rs.next();
					s = rs.getString("prefix");
				}else {
					s = "§cISTD #4511";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			if(type.equalsIgnoreCase("prefix")) {
				s = "§aRedi§cCraft §7» ";
			}else if(type.equalsIgnoreCase("msg")) {
				s = "§9MSG §7» ";
			}else {
				s = "§cISTD #4511";
			}
		}
		return s.replace("&", "§");
	}
}
