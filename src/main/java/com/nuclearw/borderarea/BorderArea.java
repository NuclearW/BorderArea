package com.nuclearw.borderarea;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import com.nuclearw.borderarea.BorderAreaPlayerListener;
import com.nuclearw.borderarea.BorderAreaVehicleListener;

public class BorderArea extends JavaPlugin {
	static String mainDirectory = "plugins" + File.separator + "BorderArea";
	static File borderpoints = new File(mainDirectory + File.separator + "points");
	static Properties prop = new Properties();

	private final BorderAreaPlayerListener playerListener = new BorderAreaPlayerListener(this);
	private final BorderAreaVehicleListener vehicleListener = new BorderAreaVehicleListener(this);

	public int xmin = 0;
	public int xmax = 0;
	public int zmin = 0;
	public int zmax = 0;

	Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		int points[][] = new int[2][2];

		log.info("BorderArea "+this.getDescription().getVersion()+" loaded.");

		//Make the file
		new File(mainDirectory).mkdir();
		if(!borderpoints.exists()) {
			try {
				borderpoints.createNewFile();
				FileOutputStream out = new FileOutputStream(borderpoints);
				prop.put("point1-x", "-100");
				prop.put("point1-z", "-100");
				prop.put("point2-x", "100");
				prop.put("point2-z", "100");
				prop.store(out, "Border area points go here.");
				out.flush();
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		FileInputStream in;
		try {
			in = new FileInputStream(borderpoints);
			prop.load(in);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		//Load points from file
		points[0][0] = Integer.parseInt(prop.getProperty("point1-x"));
		points[0][1] = Integer.parseInt(prop.getProperty("point1-z"));
		points[1][0] = Integer.parseInt(prop.getProperty("point2-x"));
		points[1][1] = Integer.parseInt(prop.getProperty("point2-z"));

		//Find which value is min/max
		if(points[0][0] < points[1][0]) {
			xmin = points[0][0];
			xmax = points[1][0];
		} else {
			xmin = points[1][0];
			xmax = points[0][0];
		}

		if(points[0][1] < points[1][1]) {
			zmin = points[0][1];
			zmax = points[1][1];
		} else {
			zmin = points[1][1];
			zmax = points[0][1];
		}

		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Highest, this);
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Highest, this);
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Event.Priority.Highest, this);
		getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_MOVE, vehicleListener, Event.Priority.Highest, this);
	}

	public void onDisable() {
		log.info("BorderArea "+this.getDescription().getVersion()+" unloaded.");
	}

	public boolean validLocation(Location check) {
		int x = check.getBlockX();
		int z = check.getBlockZ();

		if(x > xmax || x < xmin) return false;
		if(z > zmax || z < zmin) return false;

		return true;
	}
}
