package gsls.system;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.hotmail.steven.bconomy.account.AccountData;

import gsls.api.mysql.lpb.MySQL;
import gsls.system.cmd.AFKCMD;
import gsls.system.cmd.AntiExecuteCMD;
import gsls.system.cmd.CIDsetCMD;
import gsls.system.cmd.LogCMD;
import gsls.system.cmd.NickCMD;
import gsls.system.cmd.RecruitmentCMD;
import gsls.system.cmd.ServerInfoCMD;
import gsls.system.cmd.WarpSetCMD;
import gsls.system.listener.BlockClass;
import gsls.system.listener.BuildClass;
import gsls.system.listener.JoinEV;
import gsls.system.listener.ScoreboardCLS;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Main extends JavaPlugin{
	
	public static String prefix = "§aRedi§cCraft §7» ";
	public static String mysqlprefix = "§6MySQL §7» ";
	public static String consolesend = prefix + "§aPlease run the command on the game server.";
	public static String unavailable = prefix + "§cOoops. Thats currently unavailable.";
	public static Main instance;
	public static MySQL mysql;
	public static ArrayList<String> afk_list = new ArrayList<>();
	
	public static String noperm(String lackperm) {
		String noperm = prefix + "§cInsufficent Permissions!";
		return noperm + "\n§7You are lacking the following Permission-Note:§c " + lackperm;
	}
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(prefix + "§aPlugin will be activating...");
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		instance = this;
		registerEvent();
		registerCMD();
		registerMisc();
		File file = new File("plugins/GSLS/mysql.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		gsls.api.mysql.lb.MySQL.connect(cfg.getString("MySQL.IP"), cfg.getString("MySQL.Port"), cfg.getString("MySQL.Database"), cfg.getString("MySQL.user"), cfg.getString("MySQL.Pass"));
		mysql = new MySQL(cfg.getString("MySQL.IP"), Integer.valueOf(cfg.getString("MySQL.Port")), cfg.getString("MySQL.Database"), cfg.getString("MySQL.user"), cfg.getString("MySQL.Pass"));
		try {
			mysql.connect();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Bukkit.getConsoleSender().sendMessage(prefix + "§aPlugin is activated and running!");
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(prefix + "§cPlugin will be stopping...");
		gsls.api.mysql.lb.MySQL.disconnect();
		try {
			mysql.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Bukkit.getConsoleSender().sendMessage(prefix + "§cPlugin is stopped.");
	}
	
	public static void onCFG() throws IOException {
		File mysql = new File("plugins/GSLS/mysql.yml");
		
		File ordner = new File("plugins/GSLS");
		File chordner = new File("plugins/GSLS/Chatlogs");
		
		if(!ordner.exists()) {
			ordner.mkdirs();
			if(!chordner.exists()) {
				chordner.mkdirs();
			}
			if(!mysql.exists()) {
				mysql.createNewFile();
			}
		}else {
			ordner.mkdirs();
			if(!chordner.exists()) {
				chordner.mkdirs();
			}
			if(!mysql.exists()) {
				mysql.createNewFile();
			}
		}
		
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(mysql);
		cfg.addDefault("MySQL.user", "root");
		cfg.addDefault("MySQL.Pass", "PASS123!");
		cfg.addDefault("MySQL.Database", "insert database name here");
		cfg.addDefault("MySQL.IP", "localhost");
		cfg.addDefault("MySQL.Port", "3306");
		cfg.options().copyDefaults(true);
		cfg.save(mysql);
	}
	
	private void registerMisc() {
		ScoreboardCLS.startScheduler(0, 100, 20);
		serverRestarter(0, 20);
		writeDBStats(0, 100);
		updateUserDBIG(0, 100);
		try {
			onCFG();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void registerEvent() {
		PluginManager pl = Bukkit.getPluginManager();
		pl.registerEvents(new ScoreboardCLS(), this);
		pl.registerEvents(new BuildClass(), this);
		pl.registerEvents(new BlockClass(), this);
		pl.registerEvents(new AntiExecuteCMD(), this);
		pl.registerEvents(new JoinEV(), this);
	}
	
	private void registerCMD() {
		getCommand("build").setExecutor(new BuildClass());
		getCommand("login").setExecutor(new LogCMD());
		getCommand("logout").setExecutor(new LogCMD());
		getCommand("recruitment").setExecutor(new RecruitmentCMD());
		getCommand("setid").setExecutor(new CIDsetCMD());
		getCommand("setpf").setExecutor(new CIDsetCMD());
		getCommand("afk").setExecutor(new AFKCMD());
		getCommand("setwarp").setExecutor(new WarpSetCMD());
		getCommand("setnick").setExecutor(new NickCMD());
		getCommand("serverinfo").setExecutor(new ServerInfoCMD());
	}
	
	public static void serverRestarter(int delay, int period) {
		new BukkitRunnable() {
			@Override
			public void run() {
				SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
				String stime = time.format(new Date());
				if(stime.matches("22:00:00")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §9120 minutes.");
				}else if(stime.matches("22:30:00")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §990 minutes.");
				}else if(stime.matches("23:00:00")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §960 minutes.");
				}else if(stime.matches("23:30:00")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §930 minutes.");
				}else if(stime.matches("23:45:00")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §915 minutes.");
				}else if(stime.matches("23:55:00")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §95 minutes.");
				}else if(stime.matches("23:56:00")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §94 minutes.");
				}else if(stime.matches("23:57:00")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §93 minutes.");
				}else if(stime.matches("23:58:00")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §92 minutes.");
				}else if(stime.matches("23:59:00")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §9 minute.");
				}else if(stime.matches("23:59:30")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §c30 seconds.");
				}else if(stime.matches("23:59:45")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §c15 seconds.");
				}else if(stime.matches("23:59:50")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §c10 seconds.");
				}else if(stime.matches("23:59:55")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §c5 seconds.");
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.getWorld().save();
					}
				}else if(stime.matches("23:59:56")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §44 seconds.");
				}else if(stime.matches("23:59:57")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §43 seconds.");
				}else if(stime.matches("23:59:58")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §42 seconds.");
				}else if(stime.matches("23:59:59")) {
					Bukkit.broadcastMessage(Main.prefix + "§7The server is restarting in §41 second.");
				}else if(stime.matches("00:00:00")) {
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.kickPlayer("§aRedi§cCraft\n \n§7You have been kicked by: §aSystem\n§7Reason: §aServer restart\n§7Additional Message: §aRejoin in few seconds.");
					}
					Bukkit.shutdown();
				}
			}
		}.runTaskTimer(Main.instance, delay, period);
	}
	
	public static void writeDBStats(int delay, int period) {
		new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				PreparedStatement ps;
				try {
					if(gsls.api.mysql.lb.MySQL.isConnected()) {
						ps = gsls.api.mysql.lb.MySQL.getConnection().prepareStatement("SELECT * FROM serverstats WHERE server = ?");
						ps.setString(1, Bukkit.getName());
						ps.executeQuery();
						Runtime run = Runtime.getRuntime();
						long ram = (run.totalMemory() - run.freeMemory()) / 1048576L;
						String server = Bukkit.getServerName();
						String sid = Bukkit.getServerId();
						String sver = Bukkit.getBukkitVersion();
						String ssver = sver.substring(0, 6);
						int players = Bukkit.getOnlinePlayers().size();
						int playersmax = Bukkit.getMaxPlayers();
						Timestamp ts = new Timestamp(System.currentTimeMillis());
						long tss = ts.getTime();
						SimpleDateFormat time = new SimpleDateFormat("dd.MM.yy - HH:mm:ss");
						String date = time.format(new Date());
						StringBuilder sb = new StringBuilder("");
						for(double tps : MinecraftServer.getServer().recentTps) {
							sb.append(format(tps));
						}
						String str = sb.substring(0, sb.length() - 1);
						String str2;
						if(str.startsWith("*", 2)) {
							str2 = str.substring(3, 7);
						}else {
							str2 = str.substring(2, 6);
						}
						PreparedStatement pps = gsls.api.mysql.lb.MySQL.getConnection().prepareStatement("UPDATE serverstats SET tps = ?, ram = ?, serverid = ?, currentPlayers = ?, maxPlayers = ?, lastupdated = ?, serverversion = ?, lastUpdatedString = ? WHERE server = ?");
						pps.setString(9, server);
						pps.setString(1, str2);
						pps.setInt(2, (int)ram);
						pps.setString(3, sid);
						pps.setInt(4, players);
						pps.setInt(5, playersmax);
						pps.setLong(6, tss);
						pps.setString(7, ssver);
						pps.setString(8, date);
						pps.executeUpdate();
						Bukkit.getConsoleSender().sendMessage(Main.prefix + "§7DB-Stats updated!");
					}else {
						Bukkit.getConsoleSender().sendMessage(Main.prefix + "§cW A R N §7- §4DB NOT CONNECTED §7- §cW A R N");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskTimer(Main.instance, delay, period);
	}
	
	private static String format(double tps) {
		return String.valueOf((tps > 18.0 ? ChatColor.GREEN : (tps > 16.0 ? ChatColor.YELLOW : ChatColor.RED)).toString()) + (tps > 20.0 ? "*" : "") + Math.min((double)Math.round(tps * 100.0) / 100.0, 20.0);
	}
	
	public static void updateUserDBIG(int delay, int period) {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					try {
						String uuid = all.getUniqueId().toString().replaceAll("-", "");
						PermissionUser po = PermissionsEx.getUser(all);
							int moneten = AccountData.getAccountBalance(all.getUniqueId().toString(), "default");
							PreparedStatement ps = gsls.api.mysql.lb.MySQL.getConnection().prepareStatement("UPDATE playerigid SET rank = ?, money = ?, ifingamewhichserver = ?, iscurrentlyafk = ? WHERE uuid = ?");
							ps.setString(5, uuid);
							if (po.inGroup("Developer")) {
							    ps.setString(1, "Developer");
							}else if (po.inGroup("Projectmanager")) {
							    ps.setString(1, "Projectmanager");
							}else if (po.inGroup("CMan")) {
								ps.setString(1, "Community Manager");
							}else if (po.inGroup("AMan")) {
							    ps.setString(1, "Administrations Manager");
							}else if (po.inGroup("Admin")) {
							    ps.setString(1, "Administrator");
							}else if (po.inGroup("Support")) {
							    ps.setString(1, "Support Team");
							}else if (po.inGroup("Mod")) {
							    ps.setString(1, "Moderator");
							}else if (po.inGroup("Builder")) {
							    ps.setString(1, "Builder");
							}else if (po.inGroup("RediFMTeam")) {
							    ps.setString(1, "RediFM Team");
							}else if (po.inGroup("RLTM")) {
							    ps.setString(1, "Retired Legend Team Member");
							}else if (po.inGroup("RTM")) {
							    ps.setString(1, "Retired Team Member");
							}else if (po.inGroup("Beta")) {
							    ps.setString(1, "Beta");
							}else if (po.inGroup("Friend")) {
							    ps.setString(1, "Friend");
							}else {
							    ps.setString(1, "User");
							}
							ps.setInt(2, moneten);
							ps.setString(3, "Lobby");
							if(Main.afk_list.contains(all.getName())) {
								ps.setBoolean(4, true);
							}else {
								ps.setBoolean(4, false);
							}
							ps.executeUpdate();
					}catch (SQLException sql) {
						sql.printStackTrace();
					}
				}
			}
		}.runTaskTimer(Main.instance, delay, period);
	}

}
