package com.nuclearw.borderarea;

import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import com.nuclearw.borderarea.BorderArea;

public class BorderAreaVehicleListener implements Listener {
	public static BorderArea plugin;

	public BorderAreaVehicleListener(BorderArea instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onVehicleMove(VehicleMoveEvent event) {
		if(plugin.validLocation(event.getTo())) return;

		Vehicle vehicle = event.getVehicle();

		vehicle.eject();
	}
}
