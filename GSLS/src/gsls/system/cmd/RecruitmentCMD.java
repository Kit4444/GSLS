package gsls.system.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gsls.api.mysql.lb.MySQL;
import gsls.system.Main;

public class RecruitmentCMD implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(args.length == 0) {
				p.sendMessage(Main.prefix + "§7Usage: /recruitment <GMT|ST|CMT|MT|BT>");
			}else if(args.length == 1) {
				if (args[0].equalsIgnoreCase("gmt")) {
                    try {
                        PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM recruitment WHERE id = ?");
                        ps.setInt(1, 1);
                        ResultSet rs = ps.executeQuery();
                        rs.next();
                        String desc = rs.getString("description");
                        String planned = rs.getString("planed");
                        String opendatemy = rs.getString("opendate");
                        String closedatemy = rs.getString("closedate");
                        String opendate = this.dfConv(opendatemy);
                        String closedate = this.dfConv(closedatemy);
                        p.sendMessage("§7-----------------------------------------------------");
                        p.sendMessage("§7Recruitment for §cGame Moderation Team§7 is currently:");
                        if (planned.equalsIgnoreCase("false")) {
                            if (opendatemy.equalsIgnoreCase("false")) {
                                p.sendMessage("§cnot active");
                            } else {
                                p.sendMessage("§cnot active §7until §f" + opendate);
                            }
                        } else if (planned.equalsIgnoreCase("true")) {
                            if (closedatemy.equalsIgnoreCase("false")) {
                                p.sendMessage("§aactive");
                            } else {
                                p.sendMessage("§aactive §7until §f" + closedate);
                            }
                        }
                        p.sendMessage("§7Description: §f" + desc);
                    }
                    catch (SQLException e) {
                        p.sendMessage(String.valueOf(Main.prefix) + "§cFailed! Please report this to MauriceLPs!");
                    }
                } else if (args[0].equalsIgnoreCase("cmt")) {
                    try {
                        PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM recruitment WHERE id = ?");
                        ps.setInt(1, 3);
                        ResultSet rs = ps.executeQuery();
                        rs.next();
                        String desc = rs.getString("description");
                        String planned = rs.getString("planed");
                        String opendatemy = rs.getString("opendate");
                        String closedatemy = rs.getString("closedate");
                        String opendate = this.dfConv(opendatemy);
                        String closedate = this.dfConv(closedatemy);
                        p.sendMessage("§7-----------------------------------------------------");
                        p.sendMessage("§7Recruitment for §eCommunity Moderator§7 is currently:");
                        if (planned.equalsIgnoreCase("false")) {
                            if (opendatemy.equalsIgnoreCase("false")) {
                                p.sendMessage("§cnot active");
                            } else {
                                p.sendMessage("§cnot active §7until §f" + opendate);
                            }
                        } else if (planned.equalsIgnoreCase("true")) {
                            if (closedatemy.equalsIgnoreCase("false")) {
                                p.sendMessage("§aactive");
                            } else {
                                p.sendMessage("§aactive §7until §f" + closedate);
                            }
                        }
                        p.sendMessage("§7Description: §f" + desc);
                    }
                    catch (SQLException e) {
                        p.sendMessage(String.valueOf(Main.prefix) + "§cFailed! Please report this to MauriceLPs!");
                    }
                } else if (args[0].equalsIgnoreCase("st")) {
                    try {
                        PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM recruitment WHERE id = ?");
                        ps.setInt(1, 5);
                        ResultSet rs = ps.executeQuery();
                        rs.next();
                        String desc = rs.getString("description");
                        String planned = rs.getString("planed");
                        String opendatemy = rs.getString("opendate");
                        String closedatemy = rs.getString("closedate");
                        String opendate = this.dfConv(opendatemy);
                        String closedate = this.dfConv(closedatemy);
                        p.sendMessage("§7-----------------------------------------------------");
                        p.sendMessage("§7Recruitment for §bSupport Team§7 is currently:");
                        if (planned.equalsIgnoreCase("false")) {
                            if (opendatemy.equalsIgnoreCase("false")) {
                                p.sendMessage("§cnot active");
                            } else {
                                p.sendMessage("§cnot active §7until §f" + opendate);
                            }
                        } else if (planned.equalsIgnoreCase("true")) {
                            if (closedatemy.equalsIgnoreCase("false")) {
                                p.sendMessage("§aactive");
                            } else {
                                p.sendMessage("§aactive §7until §f" + closedate);
                            }
                        }
                        p.sendMessage("§7Description: §f" + desc);
                    }
                    catch (SQLException e) {
                        p.sendMessage(String.valueOf(Main.prefix) + "§cFailed! Please report this to MauriceLPs!");
                    }
                } else if (args[0].equalsIgnoreCase("mt")) {
                    try {
                        PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM recruitment WHERE id = ?");
                        ps.setInt(1, 4);
                        ResultSet rs = ps.executeQuery();
                        rs.next();
                        String desc = rs.getString("description");
                        String planned = rs.getString("planed");
                        String opendatemy = rs.getString("opendate");
                        String closedatemy = rs.getString("closedate");
                        String opendate = this.dfConv(opendatemy);
                        String closedate = this.dfConv(closedatemy);
                        p.sendMessage("§7-----------------------------------------------------");
                        p.sendMessage("§7Recruitment for §6Media Team§7 is currently:");
                        if (planned.equalsIgnoreCase("false")) {
                            if (opendatemy.equalsIgnoreCase("false")) {
                                p.sendMessage("§cnot active");
                            } else {
                                p.sendMessage("§cnot active §7until §f" + opendate);
                            }
                        } else if (planned.equalsIgnoreCase("true")) {
                            if (closedatemy.equalsIgnoreCase("false")) {
                                p.sendMessage("§aactive");
                            } else {
                                p.sendMessage("§aactive §7until §f" + closedate);
                            }
                        }
                        p.sendMessage("§7Description: §f" + desc);
                    }
                    catch (SQLException e) {
                        p.sendMessage(String.valueOf(Main.prefix) + "§cFailed! Please report this to MauriceLPs!");
                    }
                } else if (args[0].equalsIgnoreCase("bt")) {
                    try {
                        PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM recruitment WHERE id = ?");
                        ps.setInt(1, 2);
                        ResultSet rs = ps.executeQuery();
                        rs.next();
                        String desc = rs.getString("description");
                        String planned = rs.getString("planed");
                        String opendatemy = rs.getString("opendate");
                        String closedatemy = rs.getString("closedate");
                        String opendate = this.dfConv(opendatemy);
                        String closedate = this.dfConv(closedatemy);
                        p.sendMessage("§7-----------------------------------------------------");
                        p.sendMessage("§7Recruitment for §9Build Team§7 is currently:");
                        if (planned.equalsIgnoreCase("false")) {
                            if (opendatemy.equalsIgnoreCase("false")) {
                                p.sendMessage("§cnot active");
                            } else {
                                p.sendMessage("§cnot active §7until §f" + opendate);
                            }
                        } else if (planned.equalsIgnoreCase("true")) {
                            if (closedatemy.equalsIgnoreCase("false")) {
                                p.sendMessage("§aactive");
                            } else {
                                p.sendMessage("§aactive §7until §f" + closedate);
                            }
                        }
                        p.sendMessage("§7Description: §f" + desc);
                    }
                    catch (SQLException e) {
                        p.sendMessage(String.valueOf(Main.prefix) + "§cFailed! Please report this to MauriceLPs!");
                    }
                }
			}else {
				p.sendMessage(Main.prefix + "§7Usage: /recruitment <GMT|ST|CMT|MT|BT>");
			}
		}
		return false;
	}
	
		private String dfConv(String ms) {
	        Date d = new Date(Math.multiplyExact((long)Long.valueOf(ms), 1000L));
	        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");
	        String dfS = df.format(d);
	        return dfS;
	    }

}
