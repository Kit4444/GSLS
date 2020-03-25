package gsls.api.mc;

import java.util.ArrayList;

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
	
	public static ItemStack onlineItem(Material mat, int avg, int subid, String dpname, int online) {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack item = new ItemStack(mat, avg, (short)subid);
		ItemMeta mitem = item.getItemMeta();
		lore.add("§aOnline§7: " + online);
		mitem.setLore(lore);
		mitem.setDisplayName(dpname);
		item.setItemMeta(mitem);
		return item;
	}
	
	public static ItemStack l2Item(Material mat, int avg, int subid, String dpname, String lore1, String lore2) {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack item = new ItemStack(mat, avg, (short)subid);
		ItemMeta mitem = item.getItemMeta();
		lore.add(lore1);
		lore.add(lore2);
		mitem.setLore(lore);
		mitem.setDisplayName(dpname);
		item.setItemMeta(mitem);
		return item;
	}
	
	public static ItemStack l2onItem(Material mat, int avg, int subid, String dpname, String lore2, String lore3, int online) {
		ArrayList<String> lore = new ArrayList<String>();
	    ItemStack item = new ItemStack(mat, avg, (short)subid);
	    ItemMeta mitem = item.getItemMeta();
	    lore.add("§aOnline§7: " + online);
	    lore.add(lore2);
	    lore.add(lore3);
	    mitem.setLore(lore);
	    mitem.setDisplayName(dpname);
	    item.setItemMeta(mitem);
	    return item;
	}
}