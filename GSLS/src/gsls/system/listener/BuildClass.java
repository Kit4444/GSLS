package gsls.system.listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import gsls.system.Main;

public class BuildClass implements CommandExecutor, Listener{
	
	private static ArrayList<String> build = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(args.length == 0) {
				p.sendMessage(Main.prefix + "§7Usage: /build <me|Name>");
			}else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("me")) {
					if(p.hasPermission("mlps.canBuild")) {
						if(build.contains(p.getName())) {
							build.remove(p.getName());
							p.setGameMode(GameMode.SURVIVAL);
							p.sendMessage(Main.prefix + "§7You can't build anymore.");
						}else {
							build.add(p.getName());
							p.setGameMode(GameMode.CREATIVE);
							p.sendMessage(Main.prefix + "§7You can build now.");
						}
					}else {
						p.sendMessage(Main.noperm("mlps.canBuild"));
					}
				}else {
					String player = args[0];
					if(player == null) {
						p.sendMessage(Main.prefix + "§7This player is currently offline. Name: §a" + player);
					}else {
						Player p2 = Bukkit.getPlayerExact(player);
						if(p.hasPermission("mlps.canBuild.others")) {
							if(build.contains(p2.getName())) {
								build.remove(p2.getName());
								p.setGameMode(GameMode.SURVIVAL);
								p.sendMessage(Main.prefix + "§7You disallowed §a" + p2.getName() + " §7to build.");
								p2.sendMessage(Main.prefix + "§7You got disallowed to build from " + p.getDisplayName());
							}else {
								build.add(p2.getName());
								p.setGameMode(GameMode.CREATIVE);
								p.sendMessage(Main.prefix + "§7You allowed §a" + p2.getName() + " §7to build.");
								p2.sendMessage(Main.prefix + "§7You got allowed to build from " + p.getDisplayName());
							}
						}else {
							p.sendMessage(Main.noperm("mlps.canBuild.others"));
						}
					}
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onBuildPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		String block = e.getItemInHand().getType().toString();
		if(build.contains(p.getName())) {
			e.setCancelled(false);
		}else {
			if(build.contains(p.getName())) {
				e.setCancelled(false);
			}else {
				e.setCancelled(true);
				if(p.hasPermission("mlps.canBuild")) {
					p.sendMessage(Main.prefix + "§cYou need to activate build-mode.");
					p.sendMessage(Main.prefix + "§7Otherwise, you can't place the Block: §9" + block);
				}else {
					p.sendMessage(Main.prefix + "§cYou can't do that!");
				}
			}
		}
	}
	
	@EventHandler
	public void onBuildBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		String block = e.getBlock().getType().toString();
		if(build.contains(p.getName())) {
			e.setCancelled(false);
		}else {
			e.setCancelled(true);
			if(p.hasPermission("mlps.canBuild")) {
				p.sendMessage(Main.prefix + "§cYou need to activate build-mode.");
				p.sendMessage(Main.prefix + "§7Otherwise, you can't break the Block: §9" + block);
			}else {
				p.sendMessage(Main.prefix + "§cYou can't do that!");
			}
		}
	}

}
