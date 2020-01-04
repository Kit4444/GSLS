package gsls.api.mc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsAPI {
	
	public static ItemStack defItem(Material mat, int avg, int subid, String dpname) {
		ItemStack item = new ItemStack(mat, avg, (short)subid);
		ItemMeta mitem = item.getItemMeta();
		mitem.setDisplayName(dpname);
		item.setItemMeta(mitem);
		return item;
	}

}
