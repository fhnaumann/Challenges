package me.wand555.Challenge.DeathRun;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.wand555.Challenge.DeathRun.Conversations.Prompts.extra.DeathRunGoal;
import me.wand555.Challenge.DeathRun.distancecalculations.DeathRunPlace;
import me.wand555.Challenge.DeathRun.distancecalculations.DeathRunPlaceManager;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.timer.SecondTimer;
import me.wand555.challenges.settings.timer.TimerMessage;
import me.wand555.challenges.settings.timer.TimerOrder;
import me.wand555.challenges.start.Challenges;

public class DeathRunHandler {

	
	private static final Challenges PLUGIN = Challenges.getPlugin(Challenges.class);
	private static final String[] entryColors = Stream.of(ChatColor.values()).map(ChatColor::toString).toArray(String[]::new);
	
	private static DeathRunHandler deathRunHandler;
	
	private DeathRunHandler() {}
	
	private final World world = Bukkit.getWorld("ChallengeOverworld");
	
	private HashMap<Player, Boolean> hasDeathRunStats = new HashMap<Player, Boolean>();
	
	private int goalDistance;
	private int maxDistance;
	private int maxTime;
	private DeathRunGoal endingType;
	private DeathRunDirection direction;
	private int border;
	private boolean outOnDeath;
	private Scoreboard distanceBoard;
	private Countdown countdownTimer;
	private HashSet<Location> glassBlocks = new HashSet<Location>();
	private Location spawnPoint;
	
	
	
	
	public void startDeathRun(Challenges plugin) {	
		ChallengeProfile.getInstance().getSecondTimer().setMessageType(TimerMessage.DEATHRUN_RUNNING);
		
		glassBlocks.forEach(loc -> loc.getBlock().setType(Material.AIR));
		
	}
	
	public void startDeathRunCountdown(Challenges plugin) {
		countdownTimer = new Countdown(plugin, 10);
		
	}
	
	public void initializeDeathRun() {
		generateSpawnArea();
		/*ChallengeProfile.getInstance().sendMessageToAllParticipants("Preloading chunks. Might take a while!");
		ChallengeProfile.getInstance().getParticipants().forEach(p -> p.kickPlayer("Preloading worlds, keep an eye on the console"));
		Bukkit.getScheduler().runTaskLater(PLUGIN, () -> {
			preloadChunks();
			Bukkit.broadcastMessage("Loaded Chunks. Players may join back.");
		}, 5L);
		*/
		
		//statt dem hier, lieber alle kicken und bei playerjoinevent schauen, ob hasDeathrunstats true und zum spawn tp'en.
		ChallengeProfile.getInstance().getParticipants().forEach(p -> {
			p.teleport(spawnPoint);
			hasDeathRunStats.put(p, true);
		});
		ChallengeProfile.getInstance().getSecondTimer().setMessageType(TimerMessage.DEATHRUN_START);
	}
	
	private void preloadChunks() {
		goalDistance = 1000;
		border = 100;
		switch(direction) {
		case NORTH:
			for(int d=0; d>=-goalDistance; d-=16) {
				for(int b=-16; b<=16; b+=16) {
					System.out.println("Distance: " + d + "  Border: " + b);
					world.getChunkAt(b, d).load(true);
				}
			}
			break;
		case EAST:
			
			break;
		case SOUTH:
			
			break;
		case WEST:
			
			break;	
		}
	}
	
	private void generateSpawnArea() {
		direction = DeathRunDirection.NORTH;
		switch(direction) {
		case NORTH:
			Location center = world.getHighestBlockAt(0, 10).getRelative(BlockFace.DOWN).getLocation();
			for(int x=-10; x<=10; x++) {
				for(int y=0; y<=255; y++) {
					for(int z=-10; z<=10; z++) {
						Block block = world.getBlockAt(center.getBlockX()+x, center.getBlockY()+y, center.getBlockZ()+z);
						if(y == 0) block.setType(Material.BEDROCK);
						else block.setType(Material.AIR);
						if((x == -10 || x == 10 || z == 10) && y <= 5) block.setType(Material.BEDROCK);
						if(z == -10 && y != 0 && y <= 5) {
							block.setType(Material.GLASS);
							glassBlocks.add(block.getLocation());
						}
					}
				}	
			}
			spawnPoint = center.clone().add(0.5, 1.2, 0.5);
			return;
		case EAST:
			
			break;
		case SOUTH:
			
			break;
		case WEST:
			
			break;
		}
	}
	
	public void updateScoreBoard() {
		Objective obj = distanceBoard.getObjective(DisplaySlot.SIDEBAR);
		DeathRunPlace[] placings = DeathRunPlaceManager.getPlaceManager().getDistanceRun();
		for(int i=0; i<placings.length; i++) {
			if(placings.length > entryColors.length) return;
			String name = placings[i].getPlayer().getName();
			Team playerDistance = distanceBoard.registerNewTeam(name);
			playerDistance.setPrefix(name);
			playerDistance.addEntry(entryColors[i]);
			obj.getScore(entryColors[i]).setScore(placings[i].getDistance());
		}		
	}
	
	public void addPlayer(Player p) {
		addToScoreBoard(p);
		DeathRunPlaceManager placeManager = DeathRunPlaceManager.getPlaceManager();
		DeathRunPlace[] newPlacings = new DeathRunPlace[placeManager.getDistanceRun().length+1];
		newPlacings = placeManager.getDistanceRun();
		newPlacings[newPlacings.length-1] = new DeathRunPlace(p);
		placeManager.setDistanceRun(newPlacings);
	}
	
	public void removePlayer(Player p) {
		removeFromScoreBoard(p);
		DeathRunPlaceManager placeManager = DeathRunPlaceManager.getPlaceManager();
		DeathRunPlace[] newPlacings = new DeathRunPlace[placeManager.getDistanceRun().length-1];
		for(int i=0; i<placeManager.getDistanceRun().length-1; i++) newPlacings[i] = placeManager.getDistanceRun()[i];
		placeManager.setDistanceRun(newPlacings);
	}
	
	/**
	 * Also loads in the hashmap with the distance
	 */
	public void initializeDistanceBoard() {
		DeathRunPlaceManager.getPlaceManager().initializePlacings();
		distanceBoard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = distanceBoard.registerNewObjective("Distance", "dummy", "distance");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	private void addToScoreBoard(Player p) {
		p.setScoreboard(distanceBoard);
	}
	
	private void removeFromScoreBoard(Player p) {
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	
	public boolean isDistanceGoal() {
		return endingType == DeathRunGoal.DISTANCE_GOAL || endingType == DeathRunGoal.BOTH_GOAL;
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
	 * @return the maxDistance
	 */
	public int getMaxDistance() {
		return maxDistance;
	}

	/**
	 * @param maxDistance the maxDistance to set
	 */
	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
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
	public DeathRunGoal getEndingType() {
		return endingType;
	}

	/**
	 * @param endingType the endingType to set
	 */
	public void setEndingType(DeathRunGoal endingType) {
		this.endingType = endingType;
	}

	/**
	 * @return the direction
	 */
	public DeathRunDirection getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(DeathRunDirection direction) {
		this.direction = direction;
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

	/**
	 * @return the hasDeathRunStats
	 */
	public HashMap<Player, Boolean> getHasDeathRunStats() {
		return hasDeathRunStats;
	}

	/**
	 * @param hasDeathRunStats the hasDeathRunStats to set
	 */
	public void setHasDeathRunStats(HashMap<Player, Boolean> hasDeathRunStats) {
		this.hasDeathRunStats = hasDeathRunStats;
	}
	
	public Set<Player> getPlayers() {
		return hasDeathRunStats.keySet();
	}
	
	
}
