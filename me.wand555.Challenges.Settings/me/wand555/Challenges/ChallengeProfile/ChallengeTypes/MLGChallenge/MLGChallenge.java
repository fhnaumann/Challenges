package me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.Punishable;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ReasonNotifiable;
import me.wand555.Challenges.Config.LanguageMessages;
import me.wand555.Challenges.Config.WorldUtil;

public class MLGChallenge extends GenericChallenge implements Punishable, ReasonNotifiable {

	/**
	 * This map only holds entries of players who are currently in the MLG world.
	 * The boolean is whether they failed or beaten the MLG.
	 * not done = null
	 * beaten = true
	 * failed = false
	 */
	private HashMap<UUID, Boolean> inMLGWorld = new HashMap<UUID, Boolean>();
	private int earliest;
	private int latest;
	private int height;
	private MLGTimer timer;
	private final World world;
	
	private PunishType punishType;
	
	public MLGChallenge() {
		super(ChallengeType.MLG);
		this.world = Bukkit.getWorld("MLGWorld");
		activeChallenges.put(type, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		return createPunishmentItem(Material.WATER_BUCKET, 
				LanguageMessages.guiRandomMLGName, 
				new ArrayList<String>(LanguageMessages.guiMLGLore),
				punishType,
				super.active);
	}
	
	public void onMLGPrepare(Set<Player> players) {
		inMLGWorld.clear();
		if(players.size() == 0) {
			this.setTimer(new MLGTimer(Challenges.getPlugin(Challenges.class), this));
			return;
		}
		ChallengeProfile.getInstance().setInMLGRightNow();
		double factor = 0.5;
		for(Player p : players) {
			if(p.isDead()) continue;
			WorldUtil.storePlayerInformationInChallenge(p);
			p.getInventory().clear();
			p.getInventory().setItemInMainHand(new ItemStack(Material.WATER_BUCKET));
			p.setGameMode(GameMode.SURVIVAL);
			p.teleport(new Location(world, factor, height+4, 0.5));
			inMLGWorld.put(p.getUniqueId(), null);
			factor += 20;
		}
		factor = 0.5;
	}
	
	public void onMLGDone(Player p, boolean beaten) {		
		if(beaten) {
			WorldUtil.loadPlayerInformationInChallengeAndApply(p);
			inMLGWorld.put(p.getUniqueId(), true);
			//inMLGWorld.remove(p.getUniqueId());
			//wenn leer, oder jeder Spieler einen bestimmten Wert zugeordnet hat		
			
			if(inMLGWorld.entrySet().stream().allMatch(entry -> entry.getValue() != null && entry.getValue())) {
				String message = createLogMessage(getPunishCause());
				ChallengeProfile.getInstance().sendMessageToAllParticipants(message);
				this.setTimer(new MLGTimer(Challenges.getPlugin(Challenges.class), this));
				ChallengeProfile.getInstance().setInMLGRightNow();
				//inMLGWorld.clear();
				return;
			}
			
			if(inMLGWorld.entrySet().stream().allMatch(entry -> entry.getValue() != null)) {
				this.setTimer(new MLGTimer(Challenges.getPlugin(Challenges.class), this));
				ChallengeProfile.getInstance().setInMLGRightNow();
				//inMLGWorld.clear();
				return;
			}
		}
		else {
			if(getPunishType() == PunishType.CHALLENGE_OVER) {
				inMLGWorld.keySet().stream()
				.map(Bukkit::getPlayer)
				.filter(p1 -> p1 != null)
				.forEach(WorldUtil::loadPlayerInformationInChallengeAndApply);
				ChallengeProfile.getInstance().endChallenge(ChallengeEndReason.FAILED_MLG, p);
				
				ChallengeProfile.getInstance().setInMLGRightNow();
				inMLGWorld.clear();
			}
			else {
				inMLGWorld.put(p.getUniqueId(), false);
				//inMLGWorld.remove(p.getUniqueId());
				WorldUtil.loadPlayerInformationInChallengeAndApply(p);
				enforcePunishment(punishType, ChallengeProfile.getInstance().getParticipantsAsPlayers(), p);
				String message = createReasonMessage(getPunishCause(), getPunishType(), p);
				ChallengeProfile.getInstance().sendMessageToAllParticipants(message);
				if(inMLGWorld.entrySet().stream().allMatch(entry -> entry.getValue() != null)) {	
					this.setTimer(new MLGTimer(Challenges.getPlugin(Challenges.class), this));
					ChallengeProfile.getInstance().setInMLGRightNow();
					//inMLGWorld.clear();
					//Bukkit.getScheduler().runTaskLater(Challenges.getPlugin(Challenges.class), () -> inMLGWorld.clear(), 30L);
				}
				/*
				if(inMLGWorld.isEmpty() || inMLGWorld.entrySet().stream().allMatch(entry -> entry.getValue() == null || entry.getValue() == false)) {	
					String messagePassed = createPassedMessage(getPunishCause());
					ChallengeProfile.getInstance().sendMessageToAllParticipants(messagePassed);		
					this.setTimer(new MLGTimer(Challenges.getPlugin(Challenges.class), this));
					ChallengeProfile.getInstance().setInMLGRightNow();
					Bukkit.getScheduler().runTaskLater(Challenges.getPlugin(Challenges.class), () -> inMLGWorld.clear(), 5L);
				}
				*/
			}
			
			
		}
	}
	
	public void addPlayerToMLGWorld(UUID uuid) {
		inMLGWorld.put(uuid, false);
	}
	
	public void removePlayerFromMLGWorld(UUID uuid) {
		inMLGWorld.remove(uuid);
	}
	
	public HashMap<UUID, Boolean> getInMLGWorld() {
		return inMLGWorld;
	}

	/**
	 * @return the earliest
	 */
	public int getEarliest() {
		return earliest;
	}

	/**
	 * @param earliest the earliest to set
	 */
	public void setEarliest(int earliest) {
		this.earliest = earliest;
	}

	/**
	 * @return the latest
	 */
	public int getLatest() {
		return latest;
	}

	/**
	 * @param latest the latest to set
	 */
	public void setLatest(int latest) {
		this.latest = latest;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @param inMLGWorld the inMLGWorld to set
	 */
	public void setInMLGWorld(HashMap<UUID, Boolean> inMLGWorld) {
		this.inMLGWorld = inMLGWorld;
	}

	/**
	 * @return the timer
	 */
	public MLGTimer getTimer() {
		return timer;
	}

	/**
	 * @param timer the timer to set
	 */
	public void setTimer(MLGTimer timer) {
		this.timer = timer;
	}

	@Override
	public PunishType getPunishType() {
		return punishType;
	}

	@Override
	public void setPunishType(PunishType punishType) {
		this.punishType = punishType;
		
	}

	@Override
	public ChallengeType getPunishCause() {
		return super.type;
	}
}
