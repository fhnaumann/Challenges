package me.wand555.Challenge.DeathRun.Conversations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;

public class DeathRunHandler {

	private static final HashMap<Integer, ChatColor> entryColors = mapEntryColors();
	
	private static DeathRunHandler deathRunHandler;
	
	private DeathRunHandler() {}
	
	private int goalDistance;
	private int maxTime;
	private EndingType endingType;
	private int border;
	private boolean outOnDeath;
	private Scoreboard distanceBoard;
	
	private Map<Player, Integer> distanceRun = new HashMap<Player, Integer>();
	
	
	private static HashMap<Integer, ChatColor> mapEntryColors() {
		HashMap<Integer, ChatColor> mapped = new HashMap<Integer, ChatColor>();
		int counter = 0;
		for(ChatColor color : ChatColor.values()) {
			mapped.put(counter, color);
		}
		return mapped;
	}
	
	public void updateScoreBoard() {
		Objective obj = distanceBoard.getObjective(DisplaySlot.SIDEBAR);
		int counter = 0;
		for(Entry<Player, Integer> entry : distanceRun.entrySet()) {
			Team playerDistance = distanceBoard.registerNewTeam(entry.getKey().getName());
			playerDistance.setPrefix(entry.getKey().getName());
			playerDistance.addEntry(entryColors.get(counter).toString());
			obj.getScore(entryColors.get(counter).toString()).setScore(entry.getValue());
		}
		
	}
	
	/**
	 * Also loads in the hashmap with the distance
	 */
	public void initializeDistanceBoard() {
		distanceRun = ChallengeProfile.getInstance().getParticipants().stream().collect(Collectors.toMap(Function.identity(), v -> Integer.valueOf(0)));
		distanceBoard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = distanceBoard.registerNewObjective("Distance", "dummy", "distance");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public void addToScoreBoard(Player p) {
		p.setScoreboard(distanceBoard);
	}
	
	public void removeFromScoreBoard(Player p) {
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	
	
	/**
	 * @return the goalDistance
	 */
	public int getGoalDistance() {
		return goalDistance;
	}

	/**
	 * @param goalDistance the goalDistance to set
	 */
	public void setGoalDistance(int goalDistance) {
		this.goalDistance = goalDistance;
	}

	/**
	 * @return the maxTime
	 */
	public int getMaxTime() {
		return maxTime;
	}

	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	/**
	 * @return the endingType
	 */
	public EndingType getEndingType() {
		return endingType;
	}

	/**
	 * @param endingType the endingType to set
	 */
	public void setEndingType(EndingType endingType) {
		this.endingType = endingType;
	}

	/**
	 * @return the border
	 */
	public int getBorder() {
		return border;
	}

	/**
	 * @param border the border to set
	 */
	public void setBorder(int border) {
		this.border = border;
	}

	/**
	 * @return the outOnDeath
	 */
	public boolean isOutOnDeath() {
		return outOnDeath;
	}

	/**
	 * @param outOnDeath the outOnDeath to set
	 */
	public void setOutOnDeath(boolean outOnDeath) {
		this.outOnDeath = outOnDeath;
	}

	public static DeathRunHandler getDeathRunHandler() {
		if(deathRunHandler == null) deathRunHandler = new DeathRunHandler();
		return deathRunHandler;
	}
	
	private enum EndingType {
		DISTANCE, TIME, BOTH;
	}
}
