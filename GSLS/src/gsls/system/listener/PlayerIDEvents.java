package gsls.system.listener;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import gsls.api.mysql.lb.MySQL;
import gsls.system.Main;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerIDEvents implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				try {
					Player p = e.getPlayer();
					PermissionUser po = PermissionsEx.getUser(p);
					PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE playerigid SET rank = ?, Name = ?, iscurrentlyingame = ?, ifingamewhichserver = ?, untrimmeduuid = ? WHERE uuid = ?");
					ps.setString(6, p.getUniqueId().toString().replaceAll("-", ""));
					ps.setString(2, p.getName());
					ps.setBoolean(3, true);
					ps.setString(4, Bukkit.getServerName());
					ps.setString(5, p.getUniqueId().toString());
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
						ps.setString(1, "Support");
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
					}else if(po.inGroup("Partner")) {
						ps.setString(1, "Partner");
					}else if (po.inGroup("Beta")) {
						ps.setString(1, "Beta-Tester");
					}else if(po.inGroup("Patron")) {
						ps.setString(1, "Patron");
					}else if(po.inGroup("NitroBooster")) {
						ps.setString(1, "Nitro Booster");
					}else if(po.inGroup("VIPpls")) {
						ps.setString(1, "VIP+");
					}else if(po.inGroup("VIP")) {
						ps.setString(1, "VIP");
					}else if(po.inGroup("Premiumpls")) {
						ps.setString(1, "Premium+");
					}else if(po.inGroup("Premium")) {
						ps.setString(1, "Premium");
					}else if (po.inGroup("Friend")) {
						ps.setString(1, "Friend");
					}else {
						ps.setString(1, "User");
					}
					ps.executeUpdate();
				}catch (SQLException ee) {
					ee.printStackTrace();
				}
			}
		}, 2);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				try {
					Player p = e.getPlayer();
					PermissionUser po = PermissionsEx.getUser(p);
					PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE playerigid SET rank = ?, Name = ?, iscurrentlyingame = ?, ifingamewhichserver = ? WHERE uuid = ?");
					ps.setString(5, p.getUniqueId().toString().replaceAll("-", ""));
					ps.setString(2, p.getName());
					ps.setBoolean(3, false);
					ps.setString(4, Bukkit.getServerName());
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
						ps.setString(1, "Support");
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
					}else if(po.inGroup("Partner")) {
						ps.setString(1, "Partner");
					}else if (po.inGroup("Beta")) {
						ps.setString(1, "Beta-Tester");
					}else if(po.inGroup("Patron")) {
						ps.setString(1, "Patron");
					}else if(po.inGroup("NitroBooster")) {
						ps.setString(1, "Nitro Booster");
					}else if(po.inGroup("VIPpls")) {
						ps.setString(1, "VIP+");
					}else if(po.inGroup("VIP")) {
						ps.setString(1, "VIP");
					}else if(po.inGroup("Premiumpls")) {
						ps.setString(1, "Premium§2+");
					}else if(po.inGroup("Premium")) {
						ps.setString(1, "Premium");
					}else if (po.inGroup("Friend")) {
						ps.setString(1, "Friend");
					}else {
						ps.setString(1, "User");
					}
					ps.executeUpdate();
				}catch (SQLException ee) {
					ee.printStackTrace();
				}
			}
		}, 2);
	}
	
	public static void updateUserDB(int delay, int period) {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					try {
						PermissionUser po = PermissionsEx.getUser(all);
						PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE playerigid SET rank = ?, iscurrentlyingame = ?, ifingamewhichserver = ? WHERE uuid = ?");
						ps.setString(4, all.getUniqueId().toString().replaceAll("-", ""));
						ps.setBoolean(2, true);
						ps.setString(3, Bukkit.getServerName());
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
							ps.setString(1, "Support");
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
						}else if(po.inGroup("Partner")) {
							ps.setString(1, "Partner");
						}else if (po.inGroup("Beta")) {
							ps.setString(1, "Beta-Tester");
						}else if(po.inGroup("Patron")) {
							ps.setString(1, "Patron");
						}else if(po.inGroup("NitroBooster")) {
							ps.setString(1, "Nitro Booster");
						}else if(po.inGroup("VIPpls")) {
							ps.setString(1, "VIP+");
						}else if(po.inGroup("VIP")) {
							ps.setString(1, "VIP");
						}else if(po.inGroup("Premiumpls")) {
							ps.setString(1, "Premium+");
						}else if(po.inGroup("Premium")) {
							ps.setString(1, "Premium");
						}else if (po.inGroup("Friend")) {
							ps.setString(1, "Friend");
						}else {
							ps.setString(1, "User");
						}
						ps.executeUpdate();
						Bukkit.getConsoleSender().sendMessage(Main.prefix + "§aUserDB-Stats updated for user " + all.getDisplayName());
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}.runTaskTimerAsynchronously(Main.instance, delay, period);
	}

}
