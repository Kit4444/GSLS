package gsls.api.mc;

import java.lang.reflect.Field;

import org.bukkit.entity.Player;

public class GetPlayerPing {

	public static int getPing(Player p) {
		int i = 0;
		try {
			Object pplayer = p.getClass().getMethod("getHandle").invoke(p);
			Field f = pplayer.getClass().getDeclaredField("ping");
			f.setAccessible(true);
			i = f.getInt(pplayer);
		}catch (Exception e) {
			i = 9999;
		}
		return i;
	}
}