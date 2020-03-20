package gsls.api.mc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gsls.api.mysql.lb.MySQL;

public class GetPlayerAPI {
	
	public static int getPlayers(String server, String type) {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM serverstats WHERE server = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if(type.equalsIgnoreCase("currPlayer")) {
				i = rs.getInt("currentPlayers");
			}else if(type.equalsIgnoreCase("maxPlayer")) {
				i = rs.getInt("maxPlayers");
			}else {
				i = 0;
			}
		}catch (SQLException e) {
			i = 9999;
		}
		return i;
	}
}