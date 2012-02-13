package com.nuclearw.borderarea;

import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import com.nuclearw.borderarea.BorderArea;

public class BorderAreaVehicleListener extends VehicleListener {
	public static BorderArea plugin;

	public BorderAreaVehicleListener(BorderArea instance) {
		plugin = instance;
	}

	public void onVehicleMove(VehicleMoveEvent event) {
		if(plugin.validLocation(event.getTo())) return;

		Vehicle vehicle = event.getVehicle();

		vehicle.eject();
	}
}
