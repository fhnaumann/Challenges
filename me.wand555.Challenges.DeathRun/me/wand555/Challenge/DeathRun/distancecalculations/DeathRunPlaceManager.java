package me.wand555.Challenge.DeathRun.distancecalculations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;

import me.wand555.Challenge.DeathRun.DeathRunHandler;

public class DeathRunPlaceManager {

	private static DeathRunPlaceManager placeManager;
	
	private DeathRunPlaceManager() {}
	
	public static DeathRunPlaceManager getPlaceManager() {
		if(placeManager == null) placeManager = new DeathRunPlaceManager();
		return placeManager;
	}
	
	private DeathRunHandler deathRunHandler = DeathRunHandler.getDeathRunHandler();
	
	/**
	 * Player = player
	 * Integer[0] the distance run (in blocks)
	 * Integer[1] the place they're currently in
	 */
	private DeathRunPlace[] distanceRun;
	
	public void initializePlacings() {
		distanceRun = DeathRunHandler.getDeathRunHandler().getPlayers()
				.stream().map(DeathRunPlace::new).toArray(DeathRunPlace[]::new);
	}
	
	/**
	 * Called every 40 ticks to see if something has changed
	 * @return
	 */
	public DeathRunPlace[] calculateNewPlacings() {
		for(int i=0; i<distanceRun.length; i++) {
			DeathRunPlace toSort = distanceRun[i];
			if(deathRunHandler.isDistanceGoal()) 
				if(toSort.getDistance() >= deathRunHandler.getGoalDistance())
					;//end deathrun here with winner
			int j = i;
			while(j>0 && distanceRun[j-1].getDistance() > toSort.getDistance()) {
				distanceRun[j] = distanceRun[j-1];
				j--;
			}
			distanceRun[j] = toSort;
		}
		return distanceRun;
	}
	
	/**
	 * @return the distanceRun
	 */
	public DeathRunPlace[] getDistanceRun() {
		return distanceRun;
	}

	/**
	 * @param distanceRun the distanceRun to set
	 */
	public void setDistanceRun(DeathRunPlace[] distanceRun) {
		this.distanceRun = distanceRun;
	}
	
	
	
}
