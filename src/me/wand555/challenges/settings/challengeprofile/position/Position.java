package me.wand555.challenges.settings.challengeprofile.position;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Location;

public class Position {
	
	private String name;
	private Location location;
	private UUID creator;
	private String date;
	
	public Position(String name, Location location, UUID creator, Date date) {
		this.name = name;
		this.location = location;
		this.creator = creator;
		this.date = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(date);
	}
	
	public Position(String name, Location location, UUID creator, String date) {
		this.name = name;
		this.location = location;
		this.creator = creator;
		this.date = date;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * @return the creator
	 */
	public UUID getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(UUID creator) {
		this.creator = creator;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
}
