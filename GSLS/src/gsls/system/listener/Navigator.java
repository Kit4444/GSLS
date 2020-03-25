package gsls.system.listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import gsls.api.mc.ItemsAPI;
import gsls.api.mysql.lb.MySQL;
import gsls.system.LanguageHandler;
import gsls.system.Main;

public class Navigator implements Listener{
	
	File warp = new File("plugins/GSLS/warps.yml");
	
	public static void setNavi(Player p) {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§aGames§cSelector");
		inv.setItem(0, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(1, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(2, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(3, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(4, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(5, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(6, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(7, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(8, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(9, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(10, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(12, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(14, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(16, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(17, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(18, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(19, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(20, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(21, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(23, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(24, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(25, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		inv.setItem(26, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			public void run() {
				inv.setItem(4, ItemsAPI.defItem(Material.BARRIER, 1, 0, "§aServer§cSwitcher"));
				inv.setItem(11, ItemsAPI.defItem(Material.BED, 1, 0, "§cBed§fWars"));
				inv.setItem(13, ItemsAPI.defItem(Material.PURPUR_BLOCK, 1, 0, "§9Master§bBuilders"));
				inv.setItem(15, ItemsAPI.defItem(Material.WOOD_SWORD, 1, 0, "§aGunGames"));
				inv.setItem(22, ItemsAPI.defItem(Material.EXP_BOTTLE, 1, 0, "§6Spawn"));
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
			}
		}, 5);
		p.openInventory(inv);
		p.updateInventory();
	}
	
	@EventHandler
	public void onClickInv(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(warp);
		if(e.getInventory().getName().equals("§aGames§cSelector")) {
			e.setCancelled(true);
			if(e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
				e.setCancelled(true);
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cBed§fWars")){
				e.setCancelled(true);
				tpPlayer(p, cfg, "bedwars", "§cBed§fWars");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Master§bBuilders")){
				e.setCancelled(true);
				tpPlayer(p, cfg, "masterbuilders", "§9Master§bBuilders");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aGunGames")){
				e.setCancelled(true);
				tpPlayer(p, cfg, "gungames", "§aGunGames");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Spawn")) {
				e.setCancelled(true);
				tpPlayer(p, cfg, "spawn", "§6Spawn");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aServer§cSwitcher")) {
				e.setCancelled(true);
				mainMenu(p);
				p.sendMessage(LanguageHandler.prefix() + "§7You opened the Serverselector.");
			}else {
				e.setCancelled(false);
			}
		}
	}
	
	public static void mainMenu(final Player p) {
		Inventory smallInv = Bukkit.createInventory(null, 9*3, "§aServer§cNavigator");
		smallInv.setItem(0, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(1, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(3, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(4, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(5, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(7, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(8, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(9, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(11, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(12, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(14, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(15, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(17, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(19, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(21, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(22, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(23, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    smallInv.setItem(25, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
	    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				try {
					if(lockdown("SkyBlock") == true) {
						smallInv.setItem(2, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 4, "§fSky§2Block §7- §clocked"));
					}else {
						if(isOnline("SkyBlock") == true) {
							smallInv.setItem(2, ItemsAPI.onlineItem(Material.GRASS, 1, 0, "§fSky§2Block", getPlayers("SkyBlock")));
						}else {
							smallInv.setItem(2, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 14, "§fSky§2Block §7- §coffline"));
						}
					}
					if(lockdown("Farmserver") == true) {
						smallInv.setItem(10, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 4, "§dFarmserver §7- §clocked"));
					}else {
						if(isOnline("Farmserver") == true) {
							smallInv.setItem(10, ItemsAPI.onlineItem(Material.GOLD_AXE, 1, 0, "§dFarmserver", getPlayers("Farmserver")));
						}else {
							smallInv.setItem(10, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 14, "§dFarmserver §7- §coffline"));
						}
					}
					if(lockdown("Towny") == true) {
						smallInv.setItem(16, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 4, "§cTowny §7- §clocked"));
					}else {
						if(isOnline("Towny") == true) {
							smallInv.setItem(16, ItemsAPI.l2onItem(Material.BRICK, 1, 0, "§cTowny", "§cALPHA", "§cOnly Staff!", getPlayers("Towny")));
						}else {
							smallInv.setItem(16, ItemsAPI.l2Item(Material.STAINED_GLASS_PANE, 1, 14, "§cTowny §7- §coffline", "§cALPHA", "§cOnly Staff!"));
						}
					}
					if(p.hasPermission("mlps.isBuilder")) {
						if(lockdown("Buildserver") == true) {
							smallInv.setItem(26, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 4, "§9Buildserver §7- §clocked"));
						}else {
							if(isOnline("Buildserver") == true) {
								smallInv.setItem(26, ItemsAPI.onlineItem(Material.WOOD_AXE, 1, 0, "§9Buildserver", getPlayers("Buildserver")));
							}else {
								smallInv.setItem(26, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 14, "§9Buildserver §7- §coffline"));
							}
						}
					}else {
						smallInv.setItem(26, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, ""));
					}
					if(lockdown("Gameslobby") == true) {
						smallInv.setItem(24, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 4, "§6Gamesserver §7- §clocked"));
					}else {
						if(isOnline("Gameslobby") == true) {
							smallInv.setItem(24, ItemsAPI.onlineItem(Material.IRON_SWORD, 1, 0, "§6Gamesserver", getPlayers("Gameslobby")));
						}else {
							smallInv.setItem(24, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 14, "§6Gamesserver §7- §coffline"));
						}
					}
					if(lockdown("Creative") == true) {
						smallInv.setItem(6, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 4, "§6Creative §7- §clocked"));
					}else {
						if(isOnline("Creative") == true) {
							smallInv.setItem(6, ItemsAPI.onlineItem(Material.DIAMOND_PICKAXE, 1, 0, "§6Creative", getPlayers("Creative")));
						}else {
							smallInv.setItem(6, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 14, "§6Creative §7- §coffline"));
						}
					}
					if(lockdown("Survival") == true) {
						smallInv.setItem(20, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 4, "§3Survival §7- §clocked"));
					}else {
						if(isOnline("Survival") == true) {
							smallInv.setItem(20, ItemsAPI.onlineItem(Material.DIAMOND_AXE, 1, 0, "§3Survival", getPlayers("Survival")));
						}else {
							smallInv.setItem(20, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 14, "§3Survival §7- §coffline"));
						}
					}
					if(p.hasPermission("mlps.isDEVPM") || p.hasPermission("mlps.canjoindevpm")) {
						if(isOnline("Dev-PM-Server")) {
							smallInv.setItem(8, ItemsAPI.onlineItem(Material.BOOK_AND_QUILL, 1, 0, "§dDEV§6PM §7- Server", getPlayers("Dev-PM-Server")));
						}else {
							smallInv.setItem(8, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 14, "§dDEV§6PM §7- Server §7- §coffline"));
						}
					}else {
						smallInv.setItem(8, ItemsAPI.defItem(Material.STAINED_GLASS_PANE, 1, 15, "§2"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				smallInv.setItem(18, ItemsAPI.defItem(Material.BARRIER, 1, 0, "§cclose"));
	            smallInv.setItem(13, ItemsAPI.defItem(Material.NETHER_STAR, 1, 0, "§aGames§cSelector"));
	            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
			}
		}, 5);
	    p.openInventory(smallInv);
	    p.updateInventory();
	}
	
	private static Main plugin;
	public Navigator(Main m) {
		plugin = m;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		String off = " §7- offline";
		String lock = "§7- §clocked";
		String offmsg = LanguageHandler.prefix() + LanguageHandler.returnStringReady(p, "Navigator.offmessage");
		String lockmsg = LanguageHandler.prefix() + LanguageHandler.returnStringReady(p, "Navigator.lockmessage");
		if(e.getInventory().getName().equalsIgnoreCase("§aServer§cNavigator")) {
			if(e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
				e.setCancelled(true);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cclose")) {
				e.setCancelled(true);
				p.closeInventory();
				p.sendMessage(LanguageHandler.prefix() + "§cYou closed the Server - Navigator.");
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLobby")) {
				e.setCancelled(true);
				p.sendMessage(LanguageHandler.prefix() + "§7Connecting to server§a Lobby");
				p.closeInventory();
				sendPlayer(p, "lobby");
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLobby" + off)) {
				e.setCancelled(true);
				p.sendMessage(offmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLobby" + lock)) {
				e.setCancelled(true);
				p.sendMessage(lockmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Gamesserver")) {
				e.setCancelled(true);
				p.sendMessage(LanguageHandler.prefix() + "§7Connecting to server§a Gameslobby");
				p.closeInventory();
				sendPlayer(p, "gameslobby");
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Gamesserver" + off)) {
				e.setCancelled(true);
				p.sendMessage(offmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Gamesserver" + lock)) {
				e.setCancelled(true);
				p.sendMessage(lockmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Creative")) {
				e.setCancelled(true);
				p.sendMessage(LanguageHandler.prefix() + "§7Connecting to server§a Creative");
				p.closeInventory();
				sendPlayer(p, "creative");
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Creative" + off)) {
				e.setCancelled(true);
				p.sendMessage(offmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Creative" + lock)) {
				e.setCancelled(true);
				p.sendMessage(lockmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Survival")) {
				e.setCancelled(true);
				p.sendMessage(LanguageHandler.prefix() + "§7Connecting to server§a Survival");
				p.closeInventory();
				sendPlayer(p, "survival");
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Survival" + off)) {
				e.setCancelled(true);
				p.sendMessage(offmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Survival" + lock)) {
				e.setCancelled(true);
				p.sendMessage(lockmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cTowny")) {
				e.setCancelled(true);
				p.sendMessage(LanguageHandler.prefix() + "§7Connecting to server§a Towny");
				p.closeInventory();
				sendPlayer(p, "towny");
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cTowny" + off)) {
				e.setCancelled(true);
				p.sendMessage(offmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cTowny" + lock)) {
				e.setCancelled(true);
				p.sendMessage(lockmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fSky§2Block")) {
				e.setCancelled(true);
				p.sendMessage(LanguageHandler.prefix() + "§7Connecting to server§a SkyBlock");
				p.closeInventory();
				sendPlayer(p, "skyblock");
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fSky§2Block" + off)) {
				e.setCancelled(true);
				p.sendMessage(offmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fSky§2Block" + lock)) {
				e.setCancelled(true);
				p.sendMessage(lockmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§dFarmserver")) {
				e.setCancelled(true);
				p.sendMessage(LanguageHandler.prefix() + "§7Connecting to server§a Farmserver");
				p.closeInventory();
				sendPlayer(p, "farm");
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§dFarmserver" + off)) {
				e.setCancelled(true);
				p.sendMessage(offmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§dFarmserver" + lock)) {
				e.setCancelled(true);
				p.sendMessage(lockmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Buildserver")) {
				e.setCancelled(true);
				p.sendMessage(LanguageHandler.prefix() + "§7Connecting to server§a Buildserver");
				p.closeInventory();
				sendPlayer(p, "bauserver");
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Buildserver" + off)) {
				e.setCancelled(true);
				p.sendMessage(offmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Buildserver" + lock)) {
				e.setCancelled(true);
				p.sendMessage(lockmsg);
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aGames§cSelector")) {
				e.setCancelled(true);
				setNavi(p);
			}
		}
	}
	
	private void tpPlayer(Player p, YamlConfiguration cfg, String gamemode, String gm) {
		Location loc = p.getLocation();
		loc.setX(cfg.getDouble("Warp." + gamemode + ".X"));
		loc.setY(cfg.getDouble("Warp." + gamemode + ".Y"));
		loc.setZ(cfg.getDouble("Warp." + gamemode + ".Z"));
		loc.setYaw((float)cfg.getDouble("Warp." + gamemode + ".Yaw"));
		loc.setPitch((float)cfg.getDouble("Warp." + gamemode + ".Pitch"));
		loc.setWorld(Bukkit.getWorld(cfg.getString("Warp." + gamemode + ".World")));
		p.teleport(loc);
		p.sendMessage(Main.prefix + "You has been teleported to warp §6" + gm);
	}
	
	private static boolean isOnline(String server) throws SQLException {
		PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM serverstats WHERE server = ?");
		ps.setString(1, server);
		ResultSet rs = ps.executeQuery();
		rs.next();
		boolean online = rs.getBoolean("online");
		return online;
	}
	
	private static int getPlayers(String server) throws SQLException {
		PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM serverstats WHERE server = ?");
		ps.setString(1, server);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int playeronline = rs.getInt("currentPlayers");
		return playeronline;
	}
	
	private static boolean lockdown(String server) throws SQLException {
		PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM serverstats WHERE server = ?");
		ps.setString(1, server);
		ResultSet rs = ps.executeQuery();
		rs.next();
		boolean boo = rs.getBoolean("locked");
		return boo;
	}
	
	private static void sendPlayer(Player p, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		}catch (IOException e) {
		}
		p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
	}

}