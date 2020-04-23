package me.wand555.Challenges.RestoreProfile;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGTimer;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockChallenge;
import me.wand555.Challenges.Config.LanguageMessages;
import me.wand555.Challenges.WorldLinkingManager.WorldLinkManager;

public class RestoreChallenge {

	private final Challenges PLUGIN = Challenges.getPlugin(Challenges.class);
	
	private final HashSet<RestorePlayerData> playerData;
	private final long OLD_TIME;
	private final ChallengeEndReason reason;
	
	private final long TIME_TO_MLG;
	private final long TOTAL_TIME_TO_MLG;
	
	public RestoreChallenge(Set<Player> players, long time, ChallengeEndReason reason) {
		playerData = new HashSet<RestorePlayerData>(players.stream().map(RestorePlayerData::new).collect(Collectors.toSet()));
		this.OLD_TIME = time;
		this.reason = reason;
		MLGChallenge mlgChallenge = GenericChallenge.getChallenge(ChallengeType.MLG);
		if(mlgChallenge.isActive()) {
			if(mlgChallenge.getTimer() != null) {
				TIME_TO_MLG = mlgChallenge.getTimer().getTimeToMLG() >= 0 ? mlgChallenge.getTimer().getTimeToMLG() : 0;
				TOTAL_TIME_TO_MLG = mlgChallenge.getTimer().getTotalTimeToMLG() >= 0 ? mlgChallenge.getTimer().getTotalTimeToMLG() : 0;
			}
			else {
				TIME_TO_MLG = 0;
				TOTAL_TIME_TO_MLG = 0;
			}
		}
		else {
			TIME_TO_MLG = 0;
			TOTAL_TIME_TO_MLG = 0;
		}
		
		OnBlockChallenge onBlockChallenge = GenericChallenge.getChallenge(ChallengeType.ON_BLOCK);
		if(onBlockChallenge.isActive()) {
			if(onBlockChallenge.getTimer() != null) {
				
			}
		}
	}
	
	public void restoreChallenge() {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		if(reason.isRestorable()) {
			cProfile.setDone();
			cProfile.resumeTimer();
			cProfile.getSecondTimer().setTime(OLD_TIME);
			/*
			MLGChallenge mlgChallenge = GenericChallenge.getChallenge(ChallengeType.MLG);
			if(TIME_TO_MLG <= 0) {
				System.out.println("restored1");
				mlgChallenge.setTimer(new MLGTimer(PLUGIN, mlgChallenge));
			}
			else {
				System.out.println("restored2");
				mlgChallenge.setTimer(new MLGTimer(PLUGIN, TOTAL_TIME_TO_MLG, TIME_TO_MLG));
			}
			*/
			
			playerData.forEach(rpd -> {
				Player p = Bukkit.getPlayer(rpd.getUuid());
				if(p == null) return;
				if(!WorldLinkManager.worlds.contains(p.getWorld())) return;
				p.teleport(rpd.getPlayerLoc());
				p.setGameMode(rpd.getGameMode());
				p.getInventory().setContents(rpd.getInvContent().toArray(new ItemStack[rpd.getInvContent().size()]));
				p.sendMessage(LanguageMessages.challengeRestored);
			});
			playerData.clear();
		}
		else {
			cProfile.sendMessageToAllParticipants(LanguageMessages.notRestorable);
		}
		
		
	}
}
