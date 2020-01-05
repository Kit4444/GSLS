package gsls.system.listener;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.hotmail.steven.bconomy.account.AccountData;

import gsls.api.mc.ServerPingAPI;
import gsls.api.mc.TabAPI;
import gsls.api.mysql.lb.MySQL;
import gsls.system.Main;
import gsls.system.Prefix;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ScoreboardCLS implements Listener{
	
	static ServerPingAPI proxy = new ServerPingAPI("0.0.0.0", 25565);
	
	@SuppressWarnings("deprecation")
	public static void setScoreboard(Player p) throws SQLException {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = sb.registerNewObjective("aaa", "bbb");
		PermissionUser po = PermissionsEx.getUser(p);
		int moneten = AccountData.getAccountBalance(p.getUniqueId().toString(), "default");
		int puser = proxy.getOnline();
		int pusermax = proxy.getMax();
		
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		if(puser < 20) {
			o.setDisplayName(Prefix.returnPrefix("prefix") + "§a" + puser + " §7/§9 " + pusermax);
		}else if(puser < 40) {
			o.setDisplayName(Prefix.returnPrefix("prefix") + "§e" + puser + " §7/§9 " + pusermax);
		}else if(puser < 60) {
			o.setDisplayName(Prefix.returnPrefix("prefix") + "§6" + puser + " §7/§9 " + pusermax);
		}else if(puser < 80) {
			o.setDisplayName(Prefix.returnPrefix("prefix") + "§c" + puser + " §7/§9 " + pusermax);
		}else if(puser > 80) {
			o.setDisplayName(Prefix.returnPrefix("prefix") + "§4" + puser + " §7/§9 " + pusermax);
		}
		if(!nick(p).equals("RESET")) {
			o.getScore("§7Nick:").setScore(13);
			o.getScore("§9  " + nick(p)).setScore(12);
			o.getScore("§0").setScore(11);
		}
    	if(p.hasPermission("mlps.canBan")) {
    		HashMap<String, Object> hml = new HashMap<>();
        	hml.put("Name", p.getUniqueId().toString().replace("-", ""));
        	ResultSet rss = Main.mysql.select("loginsys", hml);
        	rss.next();
        	o.getScore("§7Logged in:").setScore(10);
    		if(rss.getBoolean("loggedin")) {
        		o.getScore("§9  true").setScore(9);
        	}else {
        		o.getScore("§9  false").setScore(9);
        	}
    		o.getScore(" ").setScore(8);
    	}
		o.getScore("§7Money:").setScore(7);
		o.getScore("  §9" + moneten).setScore(6);
		o.getScore("§b").setScore(5);
		o.getScore("§7Rank:").setScore(4);
		if (po.inGroup("Developer")) {
			o.getScore("  §dDeveloper").setScore(3);
		}else if (po.inGroup("Projectmanager")) {
			o.getScore("  §6Projectmanager").setScore(3);
		}else if (po.inGroup("CMan")) {
			o.getScore("  §2Community Manager").setScore(3);
		}else if (po.inGroup("AMan")) {
			o.getScore("  §4Administrations Manager").setScore(3);
		}else if (po.inGroup("Admin")) {
			o.getScore("  §cAdministrator").setScore(3);
		}else if (po.inGroup("Support")) {
			o.getScore("  §9Support").setScore(3);
		}else if (po.inGroup("Mod")) {
			o.getScore("  §aModerator").setScore(3);
		}else if (po.inGroup("Builder")) {
			o.getScore("  §bBuilder").setScore(3);
		}else if (po.inGroup("RediFMTeam")) {
			o.getScore("  §2Redi§6FM §7Team").setScore(3);
		}else if (po.inGroup("RLTM")) {
			o.getScore("  §3Retired Legend Team Member").setScore(3);
		}else if (po.inGroup("RTM")) {
			o.getScore("  §3Retired Team Member").setScore(3);
		}else if(po.inGroup("Partner")) {
			o.getScore("  §aPartner").setScore(3);
		}else if (po.inGroup("Beta")) {
			o.getScore("  §5Beta-Tester").setScore(3);
		}else if(po.inGroup("Patron")) {
			o.getScore("  §ePatron").setScore(3);
		}else if(po.inGroup("NitroBooster")) {
			o.getScore("  §5Nitro Booster").setScore(3);
		}else if(po.inGroup("VIPpls")) {
			o.getScore("  §eVIP§2+").setScore(3);
		}else if(po.inGroup("VIP")) {
			o.getScore("  §eVIP").setScore(3);
		}else if(po.inGroup("Premiumpls")) {
			o.getScore("  §ePremium§2+").setScore(3);
		}else if(po.inGroup("Premium")) {
			o.getScore("  §ePremium").setScore(3);
		}else if (po.inGroup("Friend")) {
			o.getScore("  §3Friend").setScore(3);
		}else {
			o.getScore("  §7User").setScore(3);
		}
	    o.getScore("§8").setScore(2);
	    o.getScore("§7ID:").setScore(1);
	    o.getScore("  §7" + igpre(p) + " §9" + igid(p)).setScore(0);
	    
	    p.setScoreboard(sb);
	    
	    Team dev = getTeam(sb, "00000", "§dDev §7|§d "); //light-purple
	    Team pm = getTeam(sb, "00010", "§6PM §7|§6 "); //gold
	    Team cman = getTeam(sb, "00020", "§2CM §7|§2 "); //dark-green
	    Team aman = getTeam(sb, "00030", "§4AM §7|§4 "); //dark-red
	    Team admin = getTeam(sb, "00040", "§cA §7|§c "); //red
	    Team mod = getTeam(sb, "00050", "§aMod §7|§a "); //green
	    Team support = getTeam(sb, "00060", "§9S §7|§9 "); //blue
	    Team builder = getTeam(sb, "00070", "§bB §7|§b "); //aqua
	    Team rft = getTeam(sb, "00080", "§aR§6FM§7T |§a "); //multi
	    Team rltm = getTeam(sb, "00090", "§3RLTM §7|§5 "); //dark-purple
	    Team rtm = getTeam(sb, "00100", "§3RTM §7|§5 "); //dark-purple
	    Team partner = getTeam(sb, "00110", "§aP §7|§a "); //green
	    Team beta = getTeam(sb, "00120", "§5Beta §7|§c "); //dark-blue & red
	    Team patron = getTeam(sb, "00121", "§ePatron §7| "); //dark-aqua
	    Team nitrobooster = getTeam(sb, "00125", "§5NB §7| "); //dark-blue
	    Team freund = getTeam(sb, "00130", "§3Friend §7| "); //dark-aqua
	    Team vippl = getTeam(sb, "00140", "§eVIP§2+ §7| "); //yellow
	    Team vip = getTeam(sb, "00150", "§eVIP §7| "); //yellow
	    Team prempl = getTeam(sb, "00160", "§ePrem§2+ §7| "); //yellow
	    Team prem = getTeam(sb, "00170", "§ePremium §7| "); //yellow
	    Team spieler = getTeam(sb, "00180", "§7Player §7| "); //gray
	    Team tafk = getTeam(sb, "00190", "§9AFK §7-§c ");
	    Team afk = getTeam(sb, "00200", "§9AFK §7-");
	    for(Player all : Bukkit.getOnlinePlayers()) {
	    	PermissionUser pp = PermissionsEx.getUser(all);
	    	HashMap<String, Object> hm = new HashMap<>();
	    	hm.put("Name", all.getUniqueId().toString().replace("-", ""));
	    	ResultSet rs = Main.mysql.select("loginsys", hm);
	    	rs.next();
	    	if(pp.inGroup("Developer")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				if(nick(all).equals("RESET")) {
	    					tafk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					tafk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7-§c " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
	    			}else {
	    				if(nick(all).equals("RESET")) {
	    					all.setDisplayName("§dDeveloper §7|§d " + all.getName());
		    				dev.addPlayer(all);
		    				all.setPlayerListName("§dDev §7|§d " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§dDeveloper §7|§d " + all.getCustomName());
		    				dev.addPlayer(all);
		    				all.setPlayerListName("§dDev §7|§d " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
	    				
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				if(nick(all).equals("RESET")) {
	    					afk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					afk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
	    			}else {
	    				if(nick(all).equals("RESET")) {
	    					all.setDisplayName("§7Player | " + all.getName());
		    				all.setPlayerListName("§7Player | " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§7Player | " + all.getCustomName());
		    				all.setPlayerListName("§7Player | " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}
	    			}
	    		}
	    	}else if(pp.inGroup("Projectmanager")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				if(nick(all).equals("RESET")) {
	    					tafk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					tafk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7-§c " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
	    			}else {
	    				if(nick(all).equals("RESET")) {
	    					all.setDisplayName("§6Projectmanager §7|§6 " + all.getName());
		    				all.setPlayerListName("§6PM §7|§6 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				pm.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§6Projectmanager §7|§6 " + all.getCustomName());
		    				all.setPlayerListName("§6PM §7|§6 " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				pm.addPlayer(all);
	    				}
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				if(nick(all).equals("RESET")) {
	    					afk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					afk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
	    			}else {
	    				if(nick(all).equals("RESET")) {
	    					all.setDisplayName("§7Player | " + all.getName());
		    				all.setPlayerListName("§7Player | " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§7Player | " + all.getCustomName());
		    				all.setPlayerListName("§7Player | " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}
	    			}
	    		}
	    	}else if(pp.inGroup("CMan")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				if(nick(all).equals("RESET")) {
	    					tafk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					tafk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7-§c " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
	    			}else {
	    				if(nick(all).equals("RESET")) {
	    					all.setDisplayName("§2Community Manager §7|§2 " + all.getName());
		    				all.setPlayerListName("§2CM §7|§2 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				cman.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§2Community Manager §7|§2 " + all.getCustomName());
		    				all.setPlayerListName("§2CM §7|§2 " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				cman.addPlayer(all);
	    				}
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				if(nick(all).equals("RESET")) {
	    					afk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					afk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
	    			}else {
	    				if(nick(all).equals("RESET")) {
	    					all.setDisplayName("§7Player | " + all.getName());
		    				all.setPlayerListName("§7Player | " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§7Player | " + all.getCustomName());
		    				all.setPlayerListName("§7Player | " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}
	    			}
	    		}
	    	}else if(pp.inGroup("AMan")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				if(nick(all).equals("RESET")) {
	    					tafk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					tafk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7-§c " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
	    			}else {
	    				if(nick(all).equals("RESET")) {
	    					all.setDisplayName("§4Administration Manager §7|§4 " + all.getName());
		    				all.setPlayerListName("§4AM §7|§4 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				aman.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§4Administration Manager §7|§4 " + all.getCustomName());
		    				all.setPlayerListName("§4AM §7|§4 " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				aman.addPlayer(all);
	    				}
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				if(nick(all).equals("RESET")) {
	    					afk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					afk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
	    			}else {
	    				if(nick(all).equals("RESET")) {
	    					all.setDisplayName("§7Player | " + all.getName());
		    				all.setPlayerListName("§7Player | " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§7Player | " + all.getCustomName());
		    				all.setPlayerListName("§7Player | " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}
	    			}
	    		}
	    	}else if(pp.inGroup("Admin")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				if(nick(all).equals("RESET")) {
	    					tafk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					tafk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7-§c " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
	    			}else {
	    				if(nick(all).equals("RESET")) {
	    					all.setDisplayName("§cAdministrator §7|§c " + all.getName());
		    				all.setPlayerListName("§cA §7|§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				admin.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§cAdministrator §7|§c " + all.getCustomName());
		    				all.setPlayerListName("§cA §7|§c " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				admin.addPlayer(all);
	    				}
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				if(nick(all).equals("RESET")) {
	    					afk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					afk.addPlayer(all);
		    				all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
	    			}else {
	    				if(nick(all).equals("RESET")) {
	    					all.setDisplayName("§7Player |  " + all.getName());
		    				all.setPlayerListName("§7Player | " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§7Player |  " + all.getCustomName());
		    				all.setPlayerListName("§7Player | " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}
	    			}
	    		}
	    	}else if(pp.inGroup("Support")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				tafk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					tafk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7-§c " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§9Support §7|§9 " + all.getName());
		    			all.setPlayerListName("§9S §7|§9 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			support.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§9Support §7|§9 " + all.getCustomName());
    	    			all.setPlayerListName("§9S §7|§9 " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			support.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("Mod")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				tafk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					tafk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7-§c " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§aMod §7|§a " + all.getName());
		    			all.setPlayerListName("§aModerator §7|§a " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			mod.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§aMod §7|§a " + all.getCustomName());
    	    			all.setPlayerListName("§aModerator §7|§a " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			mod.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("Builder")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				tafk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					tafk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7-§c " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§bBuilder §7|§b " + all.getName());
		    			all.setPlayerListName("§bB §7|§b " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			builder.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§bBuilder §7|§b " + all.getCustomName());
    	    			all.setPlayerListName("§bB §7|§b " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			builder.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("RediFMTeam")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				afk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					afk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§aRedi§6FM §7Team |§a " + all.getName());
		    			all.setPlayerListName("§aR§6FM§7T |§a " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			rft.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§aRedi§6FM §7Team |§a " + all.getCustomName());
    	    			all.setPlayerListName("§aR§6FM§7T |§a " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			rft.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("RLTM")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				afk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					afk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§3Retired Legend Team Member §7|§3 " + all.getName());
		    			all.setPlayerListName("§3RLTM §7|§3 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			rltm.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§3Retired Legend Team Member §7|§3 " + all.getCustomName());
    	    			all.setPlayerListName("§3RLTM §7|§3 " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			rltm.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("RTM")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				afk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					afk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§3Retired Team Member §7|§3 " + all.getName());
		    			all.setPlayerListName("§3RTM §7|§3 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			rtm.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§3Retired Team Member §7|§3 " + all.getCustomName());
    	    			all.setPlayerListName("§3RTM §7|§3 " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			rtm.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("Partner")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				afk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					afk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§aPartner §7|§ " + all.getName());
		    			all.setPlayerListName("§aPartner §7|§a " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			partner.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§aPartner §7|§ " + all.getCustomName());
    	    			all.setPlayerListName("§aPartner §7|§a " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			partner.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("Beta")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				afk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					afk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§5Beta §7|§c " + all.getName());
		    			all.setPlayerListName("§5Beta §7|§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			beta.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§5Beta §7|§c " + all.getCustomName());
    	    			all.setPlayerListName("§5Beta §7|§c " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			beta.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("Patron")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
		    			if(nick(all).equals("RESET")) {
		    				afk.addPlayer(all);
			    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					afk.addPlayer(all);
	    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
		    		}else {
		    			if(nick(all).equals("RESET")) {
		    				all.setDisplayName("§ePatron §7|§c " + all.getName());
			    			all.setPlayerListName("§ePatron §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
			    			patron.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§ePatron §7| " + all.getCustomName());
	    	    			all.setPlayerListName("§ePatron §7| " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    	    			patron.addPlayer(all);
	    				}
		    		}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
		    			if(nick(all).equals("RESET")) {
		    				afk.addPlayer(all);
			    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					afk.addPlayer(all);
	    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
		    		}else {
		    			if(nick(all).equals("RESET")) {
		    				all.setDisplayName("§7Player | " + all.getName());
			    			all.setPlayerListName("§7Player | " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§7Player | " + all.getCustomName());
	    	    			all.setPlayerListName("§7Player | " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	        				spieler.addPlayer(all);
	    				}
		    		}
	    		}
	    	}else if(pp.inGroup("NitroBooster")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
		    			if(nick(all).equals("RESET")) {
		    				afk.addPlayer(all);
			    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					afk.addPlayer(all);
	    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
		    		}else {
		    			if(nick(all).equals("RESET")) {
		    				all.setDisplayName("§1Nitro Booster §7|§1 " + all.getName());
			    			all.setPlayerListName("§1NB §7|§1 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
			    			nitrobooster.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§1Nitro Booster §7|§1 " + all.getCustomName());
	    	    			all.setPlayerListName("§1NB §7|§1 " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    	    			nitrobooster.addPlayer(all);
	    				}
		    		}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
		    			if(nick(all).equals("RESET")) {
		    				afk.addPlayer(all);
			    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}else {
	    					all.setCustomName(nick(all));
	    					afk.addPlayer(all);
	    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				}
		    		}else {
		    			if(nick(all).equals("RESET")) {
		    				all.setDisplayName("§7Player | " + all.getName());
			    			all.setPlayerListName("§7Player | " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    				spieler.addPlayer(all);
	    				}else {
	    					all.setCustomName(nick(all));
	    					all.setDisplayName("§7Player | " + all.getCustomName());
	    	    			all.setPlayerListName("§7Player | " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	        				spieler.addPlayer(all);
	    				}
		    		}
	    		}
	    	}else if(pp.inGroup("Friend")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				afk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					afk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§3Friend §7|§3 " + all.getName());
		    			all.setPlayerListName("§3Friend §7|§3 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			freund.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§3Friend §7|§3 " + all.getCustomName());
    	    			all.setPlayerListName("§3Friend §7|§3 " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			freund.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("VIPpls")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				afk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					afk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§eVIP§2+ §7|§e " + all.getName());
		    			all.setPlayerListName("§eVIP§2+ §7|§e " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			vippl.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§eVIP§2+ §7|§e " + all.getCustomName());
    	    			all.setPlayerListName("§eVIP§2+ §7|§e " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			vippl.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("VIP")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				afk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					afk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§eVIP §7|§e " + all.getName());
		    			all.setPlayerListName("§eVIP §7|§e " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			vip.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§eVIP §7|§e " + all.getCustomName());
    	    			all.setPlayerListName("§eVIP §7|§e " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			vip.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("Premiumpls")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				afk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					afk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§ePremium§2+ §7|§e " + all.getName());
		    			all.setPlayerListName("§ePrem§2+ §7|§e " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			prempl.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§ePremium§2+ §7|§e " + all.getCustomName());
    	    			all.setPlayerListName("§ePrem§2+ §7|§e " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			prempl.addPlayer(all);
    				}
	    		}
	    	}else if(pp.inGroup("Premium")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				afk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					afk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§ePremium §7|§e " + all.getName());
		    			all.setPlayerListName("§ePremium §7|§e " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
		    			prem.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§ePremium §7|§e " + all.getCustomName());
    	    			all.setPlayerListName("§ePremium §7|§e " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    	    			prem.addPlayer(all);
    				}
	    		}
	    	}else {
	    		if(Main.afk_list.contains(all.getName())) {
	    			if(nick(all).equals("RESET")) {
	    				afk.addPlayer(all);
		    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}else {
    					all.setCustomName(nick(all));
    					afk.addPlayer(all);
    	    			all.setPlayerListName("§9AFK §7- " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				}
	    		}else {
	    			if(nick(all).equals("RESET")) {
	    				all.setDisplayName("§7Player | " + all.getName());
		    			all.setPlayerListName("§7Player | " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
    				}else {
    					all.setCustomName(nick(all));
    					all.setDisplayName("§7Player | " + all.getCustomName());
    	    			all.setPlayerListName("§7Player | " + all.getCustomName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
        				spieler.addPlayer(all);
    				}
	    		}
	    	}
	    }
	}
	
	public static Team getTeam(Scoreboard sb, String Team, String prefix) {
		Team team = sb.registerNewTeam(Team);
		team.setPrefix(prefix);
		return team;
	}
	
	private static String igid(Player p) throws SQLException {
    	PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM playerigid WHERE uuid = ?");
	    ps.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
	    ResultSet rs = ps.executeQuery();
	    rs.next();
	    String uid = rs.getString("id");
	    return uid;
	}
	
	private static String igpre(Player p) throws SQLException {
		PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM playerigid WHERE uuid = ?");
		ps.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
		ResultSet rs = ps.executeQuery();
		rs.next();
		String pre = rs.getString("prefix");
		return pre;
	}
	
	private static String nick(Player p) throws SQLException {
		PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM playerigid WHERE uuid = ?");
		ps.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
		ResultSet rs = ps.executeQuery();
		rs.next();
		String nick = rs.getString("nickname");
		return nick;
	}
	
	@EventHandler
	public void onChatFormat(AsyncPlayerChatEvent e) throws SQLException, IOException {
		Player p = e.getPlayer();
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Name", p.getUniqueId().toString().replaceAll("-", ""));
		SimpleDateFormat fdate = new SimpleDateFormat("yy_MM_dd");
        String sfdate = fdate.format(new Date());
        SimpleDateFormat ctime = new SimpleDateFormat("HH-mm-ss");
        String sctime = ctime.format(new Date());
        File chat = new File("plugins/GSLS/Chatlogs/chat_" + sfdate + ".yml");
        if (!chat.exists()) {
        	chat.createNewFile();
        }
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(chat);
        if(p.hasPermission("mlps.isTeam")) {
        	ResultSet rs = Main.mysql.select("loginsys", hm);
        	rs.next();
        	if(rs.getBoolean("loggedin")) {
        		cfg.set("Chat.defaultprotocol." + sctime, "[STAFF | LOGGED IN] " + p.getName() + ": " + e.getMessage());
        		cfg.set("Chat.singlePerson." + p.getUniqueId().toString().replaceAll("-", "") + "." + p.getName() + "." + sctime, e.getMessage());
        		cfg.save(chat);
        	}else {
        		cfg.set("Chat.defaultprotocol." + sctime, "[STAFF] " + p.getName() + ": " + e.getMessage());
        		cfg.set("Chat.singlePerson." + p.getUniqueId().toString().replaceAll("-", "") + "." + p.getName() + "." + sctime, e.getMessage());
        		cfg.save(chat);
        	}
        }else {
        	cfg.set("Chat.defaultprotocol." + sctime, "[PLAYER] " + p.getName() + ": " + e.getMessage());
    		cfg.set("Chat.singlePerson." + p.getUniqueId().toString().replaceAll("-", "") + "." + p.getName() + "." + sctime, e.getMessage());
    		cfg.save(chat);
        }
        if(p.hasPermission("mlps.colorchat")) {
        	e.setFormat(p.getDisplayName() + " §8(§7" + igid(p) + "§8)§7: " + e.getMessage().replace("&", "§"));
        }else {
        	e.setFormat(p.getDisplayName() + " §8(§7" + igid(p) + "§8)§7: " + e.getMessage());
        }
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws SQLException {
		Player p = e.getPlayer();
		setScoreboard(p);
	}
	
	public static void startScheduler(int delay, int periodSB, int periodtabhf) {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					try {
						proxy.update();
						setScoreboard(all);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}.runTaskTimer(Main.instance, delay, periodSB);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
					String stime = time.format(new Date());
					TabAPI.sendTablist(all, "§aRedi§cCraft\n§aYour Minecraft-Network", "§7Here you are currently: §a" + Bukkit.getServerName() + " §7/§a " + Bukkit.getServerId() + "\n§7Current Time: §a" + stime);
				}
			}
		}.runTaskTimer(Main.instance, delay, periodtabhf);
	}

}
