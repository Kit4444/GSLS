package gsls.api.mc;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

public class TabAPI {
	
	public static void sendTablist(Player p, String header, String footer) {
		IChatBaseComponent head = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent foot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter tablist = new PacketPlayOutPlayerListHeaderFooter();
		try {
			Field headerField = tablist.getClass().getDeclaredField("a");
			headerField.setAccessible(true);
			headerField.set(tablist, head);
			Field footerField = tablist.getClass().getDeclaredField("b");
			footerField.setAccessible(true);
			footerField.set(tablist, foot);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			(((CraftPlayer)p).getHandle()).playerConnection.sendPacket(tablist);
		}
	}

}
