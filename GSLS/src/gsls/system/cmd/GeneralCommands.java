package gsls.system.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gsls.api.mc.GetPlayerPing;
import gsls.api.mysql.lb.MySQL;
import gsls.system.LanguageHandler;
import gsls.system.Main;
import net.minecraft.server.v1_12_R1.MinecraftServer;

public class GeneralCommands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(cmd.getName().equalsIgnoreCase("servers")) {
				p.sendMessage("§7========[§aList Server§7]========");
				try {
					PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM serverstats");
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						if(rs.getBoolean("locked") == false) {
							p.sendMessage("§7SID: §a" + rs.getString("serverid") + "§7 | Server: §a" + rs.getString("server") + " §7| Players: §a" + rs.getInt("currentPlayers"));
						}
					}
				}catch (SQLException e) { }
			}else if(cmd.getName().equalsIgnoreCase("gc")) {
				Runtime runtime = Runtime.getRuntime();
				SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
				String stime = time.format(new Date());
				SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
				String sdate = date.format(new Date());
				StringBuilder sb = new StringBuilder("§7TPS from last 1m §a" );
				for(double tps : MinecraftServer.getServer().recentTps) {
					sb.append(format(tps));
					sb.append( "§7,§a " );
				}
				if(p.hasPermission("mlps.admin.checkServer")) {
					p.sendMessage("§7§m================§7[§cServerinfo§7]§m================");
					p.sendMessage("§7operating System: §9" + System.getProperty("os.name"));
					p.sendMessage("§7Architecture: §9" + System.getProperty("os.arch"));
					p.sendMessage("§7Java Vendor: §9" + System.getProperty("java.vendor"));
					p.sendMessage("§7Java Version: §9" + System.getProperty("java.version"));
					p.sendMessage("§7RAM: §a" + (runtime.totalMemory() - runtime.freeMemory()) / 1048576L + "§8MB §7/ §c" + runtime.totalMemory() / 1048576L + "§8MB §7(§e" + runtime.freeMemory() / 1048576L + "§8MB free§7)");
					p.sendMessage("§7CPU-Cores: §9" + runtime.availableProcessors() + " §7Cores");
					p.sendMessage("§7IP + Port: §9" + Bukkit.getIp() + "§7:§9" + Bukkit.getPort());
					p.sendMessage("§7Servername + ID: §9" + Bukkit.getServerName() + " §7/§9 " + Bukkit.getServerId());
					p.sendMessage("§7Date & Time: §9" + sdate + " §7/§9 " + stime);
					String str = sb.substring(0, sb.length() - 1);
					String str2 = str.substring(0, 28);
					p.sendMessage(str2);
				}else {
					LanguageHandler.noperm(p);
				}
			}else if(cmd.getName().equalsIgnoreCase("pinfo")) {
				if(args.length == 0) {
					p.sendMessage(LanguageHandler.prefix() + "§7Usage: /pinfo <userID>");
				}else if(args.length == 1) {
					PreparedStatement ps;
					try {
						ps = MySQL.getConnection().prepareStatement("SELECT * FROM playerigid WHERE id = ?");
						ps.setInt(1, Integer.valueOf(args[0]));
						ResultSet rs = ps.executeQuery();
						rs.next();
						SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
				        String stime = time.format(new Date());
				        String uuid = rs.getString("untrimmeduuid");
				        String prefix = rs.getString("prefix");
				        String rank = rs.getString("rank");
				        String firstjoin = rs.getString("firstJoin");
				        String name = rs.getString("Name");
				        boolean online = rs.getBoolean("iscurrentlyingame");
				        String currServer = rs.getString("ifingamewhichserver");
				        String moneten = rs.getString("money");
				        p.sendMessage("§7----------------[§aPlayerinfo§7]----------------");
				        p.sendMessage("§7Searched ID: §a" + args[0]);
				        p.sendMessage("§7Name: §a" + name);
				        p.sendMessage("§7UUID: §a" + uuid);
				        p.sendMessage("§7Custom Prefix: " + prefix + " §a" + args[0]);
				        p.sendMessage("§7Rank: §a" + rank);
				        p.sendMessage("§7First Join: §a" + firstjoin);
				        p.sendMessage("§7Money: §a" + moneten);
				        if(online == true) {
					        p.sendMessage("§7Online: §ayes");
					        p.sendMessage("§7Current Server: §a" + currServer);
				        }else {
					        p.sendMessage("§7Online: §cno");
				        }
				        p.sendMessage("§7Requested: §9" + stime);
					}catch (SQLException e) {
				        p.sendMessage("§7----------------[§aPlayerinfo§7]----------------");
						p.sendMessage(LanguageHandler.prefix() + "Following ID is not used: " + args[0]);
					}
				}else {
					p.sendMessage(LanguageHandler.prefix() + "§7Usage: /pinfo <userID>");
				}
			}else if(cmd.getName().equalsIgnoreCase("ping")) {
				if(args.length == 0) {
					p.sendMessage(LanguageHandler.prefix() + "§7Usage: /ping <me|Player|all>");
				}else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("me")) {
						p.sendMessage(LanguageHandler.prefix() + "§7Your ping is §a" + GetPlayerPing.getPing(p) + "§7ms");
					}else if(args[0].equalsIgnoreCase("all")) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							p.sendMessage(LanguageHandler.prefix() + "§7" + all.getDisplayName() + "§7's ping is §a" + GetPlayerPing.getPing(all) + "§7ms");
						}
					}else {
						Player p2 = Bukkit.getPlayerExact(args[0]);
						if(p2 == null) {
							p.sendMessage(LanguageHandler.prefix() + "§cThis player is not online! §7Player: " + args[0]);
						}else {
							p.sendMessage(LanguageHandler.prefix() + "§7" + p2.getDisplayName() + "§7's ping is §a" + GetPlayerPing.getPing(p2) + "§7ms");
						}
					}
				}else {
					p.sendMessage(LanguageHandler.prefix() + "§7Usage: /ping <me|Player|all>");
				}
			}else if(cmd.getName().equalsIgnoreCase("monitoring")) {
				if(args.length == 0) {
					p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
				}else if(args.length >= 1 && args.length <= 2) {
					if(p.hasPermission("mlps.setMonitoring")) {
						String server = args[0];
						if(server.equalsIgnoreCase("Lobby")) {
							String s = "Lobby";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Buildserver")) {
							String s = "Buildserver";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Towny")) {
							String s = "Towny";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("DevPM")) {
							String s = "Dev-PM-Server";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Gameslobby")) {
							String s = "Gameslobby";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Creative")) {
							String s = "Creative";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Survival")) {
							String s = "Survival";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Farmserver")) {
							String s = "Farmserver";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("SkyBlock")) {
							String s = "SkyBlock";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Event1")) {
							String s = "Event 1";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Event2")) {
							String s = "Event 2";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Monitoring for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("all")) {
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateMonitor("BungeeCord", true);
								updateMonitor("MasterBuilders 1", true);
								updateMonitor("MasterBuilders 2", true);
								updateMonitor("Lobby", true);
								updateMonitor("Gameslobby", true);
								updateMonitor("Buildserver", true);
								updateMonitor("BedWars 1", true);
								updateMonitor("BedWars 2", true);
								updateMonitor("GunGame 1", true);
								updateMonitor("GunGame 2", true);
								updateMonitor("Towny", true);
								updateMonitor("Dev-PM-Server", true);
								updateMonitor("Creative", true);
								updateMonitor("Survival", true);
								updateMonitor("Farmserver", true);
								updateMonitor("SkyBlock", true);
								updateMonitor("Event 1", true);
								updateMonitor("Event 2", true);
								p.sendMessage(LanguageHandler.prefix() + "§7The servers are currently getting monitored");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateMonitor("BungeeCord", false);
								updateMonitor("MasterBuilders 1", false);
								updateMonitor("MasterBuilders 2", false);
								updateMonitor("Lobby", false);
								updateMonitor("Gameslobby", false);
								updateMonitor("Buildserver", false);
								updateMonitor("BedWars 1", false);
								updateMonitor("BedWars 2", false);
								updateMonitor("GunGame 1", false);
								updateMonitor("GunGame 2", false);
								updateMonitor("Towny", false);
								updateMonitor("Dev-PM-Server", false);
								updateMonitor("Creative", false);
								updateMonitor("Survival", false);
								updateMonitor("Farmserver", false);
								updateMonitor("SkyBlock", false);
								updateMonitor("Event 1", false);
								updateMonitor("Event 2", false);
								p.sendMessage(LanguageHandler.prefix() + "§7The servers are currently not getting monitored");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else {
							p.sendMessage("§7Possible Servers: all, Lobby, Gameslobby, Buildserver, Towny, Creative, Survival, Farmserver, SkyBlock, DevPM, Event<1|2>");
						}
					}else {
						LanguageHandler.sendMSGReady(p, "noPermission");
					}
				}else {
					p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
				}
			}else if(cmd.getName().equalsIgnoreCase("lockdown")) {
				if(args.length == 0) {
					p.sendMessage(LanguageHandler.prefix() + "§7Usage: /lockdown <server> <true|false>");
				}else if(args.length >= 1 && args.length <= 2) {
					if(p.hasPermission("mlps.setLockdown")) {
						String server = args[0];
						if(server.equalsIgnoreCase("Lobby")) {
							String s = "Lobby";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Buildserver")) {
							String s = "Buildserver";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Towny")) {
							String s = "Towny";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("DevPM")) {
							String s = "Dev-PM-Server";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Gameslobby")) {
							String s = "Gameslobby";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Creative")) {
							String s = "Creative";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Survival")) {
							String s = "Survival";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Farmserver")) {
							String s = "Farmserver";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("SkyBlock")) {
							String s = "SkyBlock";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Event1")) {
							String s = "Event 1";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("Event2")) {
							String s = "Event 2";
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock(s, true);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' enabled.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock(s, false);
								p.sendMessage(LanguageHandler.prefix() + "§7Lockdown for Server '§a" + s + "§7' disabled.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /monitoring <server> <true|false>");
							}
						}else if(server.equalsIgnoreCase("all")) {
							if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
								updateLock("BungeeCord", true);
								updateLock("MasterBuilders 1", true);
								updateLock("MasterBuilders 2", true);
								updateLock("Lobby", true);
								updateLock("Gameslobby", true);
								updateLock("Buildserver", true);
								updateLock("BedWars 1", true);
								updateLock("BedWars 2", true);
								updateLock("GunGame 1", true);
								updateLock("GunGame 2", true);
								updateLock("Towny", true);
								updateLock("Dev-PM-Server", true);
								updateLock("Creative", true);
								updateLock("Survival", true);
								updateLock("Farmserver", true);
								updateLock("SkyBlock", true);
								updateLock("Event 1", true);
								updateLock("Event 2", true);
								p.sendMessage(LanguageHandler.prefix() + "§7All servers are now locked down.");
							}else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
								updateLock("BungeeCord", false);
								updateLock("MasterBuilders 1", false);
								updateLock("MasterBuilders 2", false);
								updateLock("Lobby", false);
								updateLock("Gameslobby", false);
								updateLock("Buildserver", false);
								updateLock("BedWars 1", false);
								updateLock("BedWars 2", false);
								updateLock("GunGame 1", false);
								updateLock("GunGame 2", false);
								updateLock("Towny", false);
								updateLock("Dev-PM-Server", false);
								updateLock("Creative", false);
								updateLock("Survival", false);
								updateLock("Farmserver", false);
								updateLock("SkyBlock", false);
								updateLock("Event 1", false);
								updateLock("Event 2", false);
								p.sendMessage(LanguageHandler.prefix() + "§7All servers are not locked down anymore.");
							}else {
								p.sendMessage(LanguageHandler.prefix() + "§7Usage: /lockdown <server> <true|false>");
							}
						}else {
							p.sendMessage("§7Possible Servers: all, Lobby, Gameslobby, Buildserver, Towny, Creative, Survival, Farmserver, SkyBlock, DevPM, Event<1|2>");
						}
					}else {
						LanguageHandler.sendMSGReady(p, "noPermission");
					}
				}else {
					p.sendMessage(LanguageHandler.prefix() + "§7Usage: /lockdown <server> <true|false>");
				}
			}
		}
		return true;
	}
	
	private String format(double tps) {
		return (( tps > 18.0 ) ? ChatColor.GREEN : ( tps > 16.0 ) ? ChatColor.YELLOW : ChatColor.RED ).toString() + (( tps > 20.0 ) ? "*" : "" ) + Math.min( Math.round(tps * 100.0) / 100.0, 20.0);
	}
	
	private void updateLock(String server, boolean boo) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE serverstats SET locked = ? WHERE server = ?");
			ps.setBoolean(1, boo);
			ps.setString(2, server);
			ps.executeUpdate();
		}catch (SQLException e) {
		}
	}
	
	private void updateMonitor(String server, boolean boo) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE serverstats SET monitoring = ? WHERE server = ?");
			ps.setBoolean(1, boo);
			ps.setString(2, server);
			ps.executeUpdate();
		}catch (SQLException e) {}
	}
}