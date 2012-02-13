package com.nuclearw.borderarea;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.nuclearw.borderarea.BorderArea;

public class BorderAreaPlayerListener extends PlayerListener {
	public static BorderArea plugin;

	public BorderAreaPlayerListener(BorderArea instance) {
		plugin = instance;
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(plugin.validLocation(player.getLocation())) return;
		player.teleport(player.getWorld().getSpawnLocation());
	}

	public void onPlayerMove(PlayerMoveEvent event) {
		if(plugin.validLocation(event.getTo())) return;

		Player player = event.getPlayer();

		if(plugin.validLocation(event.getFrom())) {
			player.sendMessage("You have reached the border.");
			player.teleport(event.getFrom());
			event.setTo(event.getFrom());
			return;
		} else {
			if(plugin.validLocation(event.getTo().add(1.0, 0.0, 0.0))) {
				player.sendMessage("You have reached the border.");
				player.teleport(event.getTo().add(1.0, 0.0, 0.0));
				event.setTo(event.getTo().add(1.0, 0.0, 0.0));
				return;
			} else if(plugin.validLocation(event.getTo().add(-1.0, 0.0, 0.0))) {
				player.sendMessage("You have reached the border.");
				player.teleport(event.getTo().add(-1.0, 0.0, 0.0));
				event.setTo(event.getTo().add(-1.0, 0.0, 0.0));
				return;
			} else if(plugin.validLocation(event.getTo().add(0.0, 0.0, 1.0))) {
				player.sendMessage("You have reached the border.");
				player.teleport(event.getTo().add(0.0, 0.0, 1.0));
				event.setTo(event.getTo().add(0.0, 0.0, 1.0));
				return;
			} else if(plugin.validLocation(event.getTo().add(0.0, 0.0, -1.0))) {
				player.sendMessage("You have reached the border.");
				player.teleport(event.getTo().add(0.0, 0.0, -1.0));
				event.setTo(event.getTo().add(0.0, 0.0, -1.0));
				return;
			}
			player.damage(2);
			return;
		}
	}

	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if(plugin.validLocation(event.getTo())) return;

		Player player = event.getPlayer();

		if(plugin.validLocation(event.getFrom())) {
			event.setTo(event.getFrom());
			player.sendMessage("You have reached the border.");
			player.teleport(event.getFrom());
		} else {
			player.teleport(player.getWorld().getSpawnLocation());
		}
	}
}