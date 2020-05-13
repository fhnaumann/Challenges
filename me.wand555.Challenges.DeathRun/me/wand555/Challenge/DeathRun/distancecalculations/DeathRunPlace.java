package me.wand555.Challenge.DeathRun.distancecalculations;

import org.bukkit.entity.Player;

public class DeathRunPlace {

	private Player player;
	private int distance;
	
	public DeathRunPlace(Player player) {
		this.player = player;
		this.distance = 0;
	}
	
	public DeathRunPlace(Player player, int distance) {
		this.player = player;
		this.distance = distance;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	} 
}
