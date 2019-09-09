package gsls.system.listener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
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

import gsls.api.mc.ServerPingAPI;
import gsls.api.mc.TabAPI;
import gsls.api.mysql.lb.MySQL;
import gsls.system.Main;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ScoreboardCLS implements Listener{
	
	static ServerPingAPI proxy = new ServerPingAPI("0.0.0.0", 25565);
	
	@SuppressWarnings("deprecation")
	public static void setScoreboard(Player p) throws SQLException {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = sb.registerNewObjective("aaa", "bbb");
		PermissionUser po = PermissionsEx.getUser(p);
		int puser = proxy.getOnline();
		int pusermax = proxy.getMax();
		
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		if(puser < 20) {
			o.setDisplayName("§aRedi§cCraft §7» §a" + puser + " §7/§9 " + pusermax);
		}else if(puser < 40) {
			o.setDisplayName("§aRedi§cCraft §7» §e" + puser + " §7/§9 " + pusermax);
		}else if(puser < 60) {
			o.setDisplayName("§aRedi§cCraft §7» §6" + puser + " §7/§9 " + pusermax);
		}else if(puser < 80) {
			o.setDisplayName("§aRedi§cCraft §7» §c" + puser + " §7/§9 " + pusermax);
		}else if(puser > 80) {
			o.setDisplayName("§aRedi§cCraft §7» §4" + puser + " §7/§9 " + pusermax);
		}
		o.getScore("§7Rank:").setScore(4);
	    if (po.inGroup("Developer")) {
	      o.getScore("  §5Developer").setScore(3);
	    }else if (po.inGroup("Projectmanager")) {
	      o.getScore("  §dProjectmanager").setScore(3);
	    }else if (po.inGroup("SCMan")) {
		  o.getScore("  §4Senior Community Manager").setScore(3);
		}else if (po.inGroup("SGMan")) {
		  o.getScore("  §4Senior Game Manager").setScore(3);
		}else if (po.inGroup("SCMMan")) {
		  o.getScore("  §eSenior Comm. Mod. Man.").setScore(3);
		}else if (po.inGroup("CMan")) {
	      o.getScore("  §4Community Manager").setScore(3);
	    }else if (po.inGroup("GMan")) {
	      o.getScore("  §cGame Manager").setScore(3);
	    }else if (po.inGroup("CMMan")) {
	      o.getScore("  §eCommunity Mod. Manager").setScore(3);
	    }else if (po.inGroup("SMan")) {
	      o.getScore("  §bSupport Manager").setScore(3);
	    }else if (po.inGroup("MMan")) {
	      o.getScore("  §6Media Manager").setScore(3);
	    }else if (po.inGroup("Masterbuilder")) {
	      o.getScore("  §9Builders Manager").setScore(3);
	    }else if (po.inGroup("GM")) {
	      o.getScore("  §cGame Moderator").setScore(3);
	    }else if (po.inGroup("ST")) {
	      o.getScore("  §bSupport Team").setScore(3);
	    }else if (po.inGroup("CM")) {
	      o.getScore("  §eCommunity Moderator").setScore(3);
	    }else if (po.inGroup("MM")) {
	      o.getScore("  §6Media Team").setScore(3);
	    }else if (po.inGroup("Builder")) {
	      o.getScore("  §9Builder").setScore(3);
	    }else if (po.inGroup("TGM")) {
	      o.getScore("  §cTrial Game Moderator").setScore(3);
	    }else if (po.inGroup("TST")) {
	      o.getScore("  §bTrial Support Team").setScore(3);
	    }else if (po.inGroup("TCM")) {
	      o.getScore("  §eTrial Community Moderator").setScore(3);
	    }else if (po.inGroup("TMM")) {
	      o.getScore("  §6Trial Media Team").setScore(3);
	    }else if (po.inGroup("TBuild")) {
	      o.getScore("  §9Trial Builder").setScore(3);
	    }else if (po.inGroup("RediFMTeam")) {
	      o.getScore("  §2Redi§6FM §7Team").setScore(3);
	    }else if (po.inGroup("RLTM")) {
	      o.getScore("  §aRetired Legend Team Member").setScore(3);
	    }else if (po.inGroup("RTM")) {
	      o.getScore("  §aRetired Team Member").setScore(3);
	    }else if (po.inGroup("Beta")) {
	      o.getScore("  §1Beta-Tester").setScore(3);
	    }else if (po.inGroup("Friend")) {
	      o.getScore("  §6Friend").setScore(3);
	    }else {
	      o.getScore("  §3User").setScore(3);
	    } 
	    o.getScore("§8").setScore(2);
	    o.getScore("§7ID:").setScore(1);
	    o.getScore("  §9" + igid(p)).setScore(0);
	    
	    p.setScoreboard(sb);
	    
	    Team dev = getTeam(sb, "00000", "§5Dev §7|§5 ");
	    Team pm = getTeam(sb, "00010", "§dPM §7|§d ");
		Team scman = getTeam(sb, "00012", "§4SCM §7| §4");
		Team sgman = getTeam(sb, "00014", "§4SGMM §7| §4");
		Team scmman = getTeam(sb, "00016", "§eSCMM §7| §e");
	    Team cman = getTeam(sb, "00020", "§4CM §7|§4 ");
	    Team gman = getTeam(sb, "00030", "§cGMM §7|§c ");
	    Team cmman = getTeam(sb, "00040", "§eCMM §7|§e ");
	    Team sman = getTeam(sb, "00050", "§bSM §7|§b ");
	    Team mman = getTeam(sb, "00060", "§6MM §7|§6 ");
	    Team mb = getTeam(sb, "00070", "§9BM §7|§9 ");
	    Team gm = getTeam(sb, "00080", "§cGM §7|§c ");
	    Team st = getTeam(sb, "00090", "§bST §7|§b ");
	    Team cm = getTeam(sb, "00100", "§eCM §7|§e ");
	    Team mt = getTeam(sb, "00110", "§6MT §7|§6 ");
	    Team build = getTeam(sb, "00120", "§9B §7|§9 ");
	    Team tgm = getTeam(sb, "00130", "§cTGM §7|§c ");
	    Team tst = getTeam(sb, "00140", "§bTST §7|§b ");
	    Team tcm = getTeam(sb, "00150", "§eTCM §7|§e ");
	    Team tmt = getTeam(sb, "00160", "§6TMT §7|§6 ");
	    Team tb = getTeam(sb, "00170", "§9TB §7|§9 ");
	    Team rft = getTeam(sb, "00180", "§aR§6FM§7T |§a ");
	    Team rltm = getTeam(sb, "00190", "§aRLTM §7|§a ");
	    Team rtm = getTeam(sb, "00200", "§aRTM §7|§a ");
	    Team partner = getTeam(sb, "00210", "§aP §7|§a ");
	    Team beta = getTeam(sb, "00220", "§1Beta §7|§c ");
	    Team nitrobooster = getTeam(sb, "00222", "§1NB §7|§1 ");
	    Team freund = getTeam(sb, "00230", "§6Friend §7| ");
	    Team vippl = getTeam(sb, "00232", "§6VIP§2+ §7| ");
	    Team vip = getTeam(sb, "00234", "§eVIP §7| ");
	    Team prempl = getTeam(sb, "00236", "§6Prem§2+ §7| ");
	    Team prem = getTeam(sb, "00238", "§ePremium §7| ");
	    Team spieler = getTeam(sb, "00240", "§3Player §7| ");
	    Team tafk = getTeam(sb, "00249", "§9AFK §7-§c ");
	    Team afk = getTeam(sb, "00250", "§9AFK §7-");
	    for(Player all : Bukkit.getOnlinePlayers()) {
	    	PermissionUser pp = PermissionsEx.getUser(all);
	    	PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM loginsys WHERE Name = ?");
	    	ps.setString(1, all.getUniqueId().toString().replaceAll("-", ""));
	    	ResultSet rs = ps.executeQuery();
	    	rs.next();
	    	if(pp.inGroup("Developer")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§5Developer §7|§5 " + all.getName());
	    				dev.addPlayer(all);
	    				all.setPlayerListName("§5Dev §7|§5 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("Projectmanager")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§dProjectmanager §7|§d " + all.getName());
	    				all.setPlayerListName("§dPM §7|§d " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				pm.addPlayer(all);
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("SCMan")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§4Senior Community Manager §7|§4 " + all.getName());
	    				all.setPlayerListName("§4SCM §7|§4 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				scman.addPlayer(all);
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("SGMan")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§4Senior Game Moderation Manager §7|§4 " + all.getName());
	    				all.setPlayerListName("§4SGMM §7|§4 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				sgman.addPlayer(all);
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("SCMMan")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§eSenior Comm. Mod. Manager §7|§ " + all.getName());
	    				all.setPlayerListName("§eSCMM §7|§e " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				scmman.addPlayer(all);
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("CMan")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§4Community Manager §7|§4 " + all.getName());
	    				all.setPlayerListName("§4CM §7|§4 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				cman.addPlayer(all);
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("GMan")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§cGame Moderation Manager §7|§c " + all.getName());
	    				all.setPlayerListName("§cGMM §7|§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				gman.addPlayer(all);
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("CMMan")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§eCommunity Mod. Manager §7|§e " + all.getName());
	    				all.setPlayerListName("§eCMM §7|§e " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				cmman.addPlayer(all);
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("SMan")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§bSupport Manager §7|§b " + all.getName());
	    				all.setPlayerListName("§bSM §7|§b " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				sman.addPlayer(all);
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("MMan")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§6Media Manager §7|§6 " + all.getName());
	    				all.setPlayerListName("§6MM §7|§6 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				mman.addPlayer(all);
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("Masterbuilder")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§9Builders Manager §7|§9 " + all.getName());
	    				all.setPlayerListName("§9BM §7|§9 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				mb.addPlayer(all);
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("GM")) {
	    		if(rs.getBoolean("loggedin")) {
	    			if(Main.afk_list.contains(all.getName())) {
	    				tafk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§cGame Moderator §7|§c " + all.getName());
	    				all.setPlayerListName("§cGM §7|§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				gm.addPlayer(all);
	    			}
	    		}else {
	    			if(Main.afk_list.contains(all.getName())) {
	    				afk.addPlayer(all);
	    				all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			}else {
	    				all.setDisplayName("§3Player §7|§3 " + all.getName());
	    				all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    				spieler.addPlayer(all);
	    			}
	    		}
	    	}else if(pp.inGroup("ST")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			tafk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§bSupport Team §7|§ " + all.getName());
	    			all.setPlayerListName("§bST §7|§b " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			st.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("CM")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			tafk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§eCommunity Moderator §7|§e " + all.getName());
	    			all.setPlayerListName("§eCM §7|§e " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			cm.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("MT")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			tafk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§6Media Team §7|§6 " + all.getName());
	    			all.setPlayerListName("§6MT §7|§6 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			mt.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("Builder")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			tafk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§9Builder §7|§9 " + all.getName());
	    			all.setPlayerListName("§9B §7|§9 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			build.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("TGM")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			tafk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§cTrial Game Moderator §7|§c " + all.getName());
	    			all.setPlayerListName("§cTGM §7|§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			tgm.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("TST")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			tafk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§bTrial Support Team §7|§b " + all.getName());
	    			all.setPlayerListName("§bTST §7|§b " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			tst.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("TCM")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			tafk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§eTrial Community Moderator §7|§e " + all.getName());
	    			all.setPlayerListName("§eTCM §7|§e " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			tcm.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("TMT")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			tafk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§6Trial Media Team §7|§6 " + all.getName());
	    			all.setPlayerListName("§6TMT §7|§6 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			tmt.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("TBuild")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			tafk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7-§c " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§9Trial Builder §7|§9 " + all.getName());
	    			all.setPlayerListName("§9TB §7|§9 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			tb.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("RediFMTeam")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§aRedi§6FM§7T |§a " + all.getName());
	    			all.setPlayerListName("§aR§6FM§7T |§a " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			rft.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("RLTM")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§aRetired Legend Team Member §7|§a " + all.getName());
	    			all.setPlayerListName("§aRLTM §7|§a " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			rltm.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("RTM")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§aRetired Team Member §7|§ " + all.getName());
	    			all.setPlayerListName("§aRTM §7|§a " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			rtm.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("Partner")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§aPartner §7|§ " + all.getName());
	    			all.setPlayerListName("§aPartner §7|§a " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			partner.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("Beta")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§1Beta §7|§1 " + all.getName());
	    			all.setPlayerListName("§1Beta §7|§1 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			beta.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("NitroBoost")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§1Nitro Booster §7|§1 " + all.getName());
	    			all.setPlayerListName("§1NB §7|§1 " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			nitrobooster.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("Friend")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§3Friend §7| " + all.getName());
	    			all.setPlayerListName("§3Friend §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			freund.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("VIPpls")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§6VIP§2+ §7| " + all.getName());
	    			all.setPlayerListName("§6VIP§2+ §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			vippl.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("VIP")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§eVIP §7| " + all.getName());
	    			all.setPlayerListName("§eVIP §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			vip.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("Premiumpls")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§6Premium§2+ §7| " + all.getName());
	    			all.setPlayerListName("§6Prem§2+ §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			prempl.addPlayer(all);
	    		}
	    	}else if(pp.inGroup("Premium")) {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§ePremium §7| " + all.getName());
	    			all.setPlayerListName("§ePremium §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    			prem.addPlayer(all);
	    		}
	    	}else {
	    		if(Main.afk_list.contains(all.getName())) {
	    			afk.addPlayer(all);
	    			all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
	    		}else {
	    			all.setDisplayName("§3Player §7|§3 " + all.getName());
	    			all.setPlayerListName("§3Player §7| " + all.getName() + " §7| §aID§7: " + igpre(all) + " §7" + igid(all));
    				spieler.addPlayer(all);
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
	
	@EventHandler
	public void onChatFormat(AsyncPlayerChatEvent e) throws SQLException {
		Player p = e.getPlayer();
		e.setFormat(p.getDisplayName() + " §8(§7" + igid(p) + "§8)§7: " + e.getMessage());
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
