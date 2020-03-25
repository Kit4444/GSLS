package gsls.system;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import gsls.api.mysql.lb.MySQL;

public class LanguageHandler {
	
	public static void loadCFG() {
		File file = new File("plugins/GSLS/language.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		//vorlage: cfg.addDefault("Language..cmd.", "&7");
		
		//general
		cfg.addDefault("Language.EN.noPermission", "&cInsufficent Permission!");
		cfg.addDefault("Language.DE.noPermission", "&cUnzureichende Berechtigung!");
		//cfg.addDefault("Language.FR.noPermission", "");
		//cfg.addDefault("Language.CZ.noPermission", "");
		
		//en
		cfg.addDefault("Language.EN.cmd.afk.join", "&7You are now AFK.");
		cfg.addDefault("Language.EN.cmd.afk.leave", "&7You are no longer AFK.");
		cfg.addDefault("Language.EN.cmd.login.already", "&7You are already logged in.");
		cfg.addDefault("Language.EN.cmd.login.success", "&7You are now logged in.");
		cfg.addDefault("Language.EN.cmd.logout.already", "&7You are already logged out.");
		cfg.addDefault("Language.EN.cmd.logout.success", "&7You are now logged out.");
		cfg.addDefault("Language.EN.cmd.tg.hide", "&7Your group is now hidden.");
		cfg.addDefault("Language.EN.cmd.tg.visible", "&7Your group is now visible.");
		cfg.addDefault("Language.EN.Navigator.offmessage", "");
		cfg.addDefault("Language.EN.Navigator.lockmessage", "");
		//de
		cfg.addDefault("Language.DE.cmd.afk.join", "&7You are now AFK.");
		cfg.addDefault("Language.DE.cmd.afk.leave", "&7You are no longer AFK.");
		cfg.addDefault("Language.DE.cmd.login.already", "&7You are already logged in.");
		cfg.addDefault("Language.DE.cmd.login.success", "&7You are now logged in.");
		cfg.addDefault("Language.DE.cmd.logout.already", "&7You are already logged out.");
		cfg.addDefault("Language.DE.cmd.logout.success", "&7You are now logged out.");
		cfg.addDefault("Language.DE.cmd.tg.hide", "&7Your group is now hidden.");
		cfg.addDefault("Language.DE.cmd.tg.visible", "&7Your group is now visible.");
		//fr
		
		//cz
		
		//save
		cfg.options().copyDefaults(true);
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String prefix() {
		String pre = Prefix.returnPrefix("prefix");
		return pre;
	}
	
	public static void noperm(Player p) {
		File file = new File("plugins/GSLS/language.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		String np = null;
		if(lang(p).equalsIgnoreCase("en-uk")) {
			np = cfg.getString("Language.EN.noPermission").replace("&", "§");
		}else if(lang(p).equalsIgnoreCase("de-de")) {
			np = cfg.getString("Language.DE.noPermission").replace("&", "§");
		}else if(lang(p).equalsIgnoreCase("fr-fr")) {
			np = cfg.getString("Language.FR.noPermission").replace("&", "§");
		}else if(lang(p).equalsIgnoreCase("cz-cz")) {
			np = cfg.getString("Language.CZ.noPermission").replace("&", "§");
		}else {
			np = cfg.getString("Language.EN.noPermission").replace("&", "§");
		}
		String npp = prefix() + np;
		p.sendMessage(npp);
	}
	
	public static void unavailable(Player p) {
		File file = new File("plugins/RCUSS/language.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		String s = null;
		if(lang(p).equalsIgnoreCase("en-uk")) {
			s = cfg.getString("Language.EN.notAvailable").replace("&", "§");
		}else if(lang(p).equalsIgnoreCase("de-de")) {
			s = cfg.getString("Language.DE.notAvailable").replace("&", "§");
		}else if(lang(p).equalsIgnoreCase("fr-fr")) {
			s = cfg.getString("Language.FR.notAvailable").replace("&", "§");
		}else {
			s = "§4INVALID LANGUAGE-FORMAT";
		}
		String ss = prefix() + s;
		p.sendMessage(ss);
	}
	
	public static String returnString(String lang, String path) {
		File file = new File("plugins/GSLS/language.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		String s = null;
		if(lang.equalsIgnoreCase("en-uk")) {
			if(cfg.contains("Language.EN." + path)) {
				s = cfg.getString("Language.EN." + path).replace("&", "§");
			}else {
				s = "§cThis path doesn't exists.";
			}
		}else if(lang.equalsIgnoreCase("de-de")) {
			if(cfg.contains("Language.DE." + path)) {
				s = cfg.getString("Language.DE." + path).replace("&", "§");
			}else {
				s = "§cDieser Pfad existiert nicht.";
			}
		}else if(lang.equalsIgnoreCase("fr-fr")) {
			if(cfg.contains("Language.FR." + path)) {
				s = cfg.getString("Language.FR." + path).replace("&", "§");
			}else {
				s = "§cCe chemin n'existe pas.";
			}
		}else if(lang.equalsIgnoreCase("cz-cz")) {
			if(cfg.contains("Language.CZ." + path)) {
				s = cfg.getString("Language.CZ." + path).replace("&", "§");
			}else {
				s = "§c";
			}
		}else if(lang.equalsIgnoreCase("Main")) {
			if(cfg.contains("Language.Main." + path)) {
				s = cfg.getString("Language.Main." + path).replace("&", "§");
			}else {
				s = "§cThis path doesn't exists";
			}
		}
		return s;
	}
	
	public static String lang(Player p) {
		String lang = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM playerigid WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			lang = rs.getString("language");
		}catch (SQLException e) {
		}
		return lang;
	}
	
	public static void sendMSGReady(Player p, String path) {
		if(lang(p).equalsIgnoreCase("en-uk")) {
			p.sendMessage(prefix() + returnString("en-uk", path));
		}else if(lang(p).equalsIgnoreCase("de-de")) {
			p.sendMessage(prefix() + returnString("de-de", path));
		}else if(lang(p).equalsIgnoreCase("fr-fr")) {
			p.sendMessage(prefix() + returnString("fr-fr", path));
		}else if(lang(p).equalsIgnoreCase("cz-cz")) {
			p.sendMessage(prefix() + returnString("cz-cz", path));
		}
	}
	
	public static String returnStringReady(Player p, String path) {
		String s = null;
		if(lang(p).equalsIgnoreCase("en-uk")) {
			s = returnString("en-uk", path);
		}else if(lang(p).equalsIgnoreCase("de-de")) {
			s = returnString("de-de", path);
		}else if(lang(p).equalsIgnoreCase("fr-fr")) {
			s = returnString("fr-fr", path);
		}else if(lang(p).equalsIgnoreCase("cz-cz")) {
			s = returnString("cz-cz", path);
		}
		return s;
	}

}
