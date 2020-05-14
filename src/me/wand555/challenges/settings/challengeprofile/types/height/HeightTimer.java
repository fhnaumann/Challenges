package me.wand555.challenges.settings.challengeprofile.types.height;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.challenges.api.events.violation.CallViolationEvent;
import me.wand555.challenges.settings.challengeprofile.ChallengeEndReason;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.BossBarStatus;
import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.settings.config.LanguageMessages;
import me.wand555.challenges.start.Challenges;

public class HeightTimer extends BukkitRunnable implements CallViolationEvent {

	private long totalTimeTo;
	private long timeTo;
	
	private HeightChallenge heightChallenge;
	
	public HeightTimer(Challenges plugin, HeightChallenge heightChallenge) {
		this.heightChallenge = heightChallenge;
		timeTo = ThreadLocalRandom.current()
				.nextLong(heightChallenge.getEarliestToShow(), heightChallenge.getLatestToShow()+1);
		totalTimeTo = timeTo;
		this.runTaskTimer(plugin, 0L, 20L);
		heightChallenge.setDefaults();
	}
	
	public HeightTimer(Challenges plugin, HeightChallenge heightChallenge, long totalTimeTo, long timeTo, boolean firstCreation) {
		this.totalTimeTo = totalTimeTo;
		this.timeTo = timeTo;
		this.heightChallenge = heightChallenge;
		if(firstCreation) {
			heightChallenge.setDefaults();
		}
		else {
			if(heightChallenge.getStatus() == BossBarStatus.SHOWN) {
				heightChallenge.getNormalHeight().setBossbarMessageShown(LanguageMessages.onHeightShown
						.replace("[HEIGHT]", Integer.toString(heightChallenge.getNormalHeight().getToBeOnHeight())));
				heightChallenge.getNetherHeight().setBossbarMessageShown(LanguageMessages.onHeightShown
						.replace("[HEIGHT]", Integer.toString(heightChallenge.getNetherHeight().getToBeOnHeight())));
			}
		}
		this.runTaskTimer(plugin, 0L, 20L);
	}
	
	@Override
	public void run() {
		if(ChallengeProfile.getInstance().getParticipants().size() == 0) return;
		if(!ChallengeProfile.getInstance().canTakeEffect()) {
			//ChallengeProfile.getInstance().getParticipantsAsPlayers().stream().forEach(p -> onBlockChallenge.removePlayerFromBossBar(p));
			//this.cancel();
			return;
		}
		
		System.out.println(timeTo);
		if(timeTo <= 0) {
			//System.out.println(onBlockChallenge.getStatus());
			if(heightChallenge.getStatus() == BossBarStatus.HIDDEN) {
				timeTo = ThreadLocalRandom.current()
						.nextLong(heightChallenge.getEarliestToBeOnHeight(), (heightChallenge.getLatestToBeOnHeight()+1));
				totalTimeTo = timeTo;
				heightChallenge.fromHiddenToShownChange(totalTimeTo);
				
			}
			else {
				
				Set<Player> notStandingOnHeight = ChallengeProfile.getInstance().getParticipants().stream()
					.filter(p -> !playersOnHeight(p)).collect(Collectors.toSet());
				if(notStandingOnHeight.size() != 0) {
					if(heightChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
						ChallengeProfile.getInstance()
							.endChallenge(heightChallenge, 
									ChallengeEndReason.NOT_ON_HEIGHT, 
									new Object[] {
											heightChallenge.getNormalHeight().getToBeOnHeight(), 
											heightChallenge.getNetherHeight().getToBeOnHeight()}, 
									notStandingOnHeight.toArray(new Player[notStandingOnHeight.size()]));
						timeTo = ThreadLocalRandom.current()
								.nextLong(heightChallenge.getEarliestToShow(), (heightChallenge.getLatestToShow()+1));
						totalTimeTo = timeTo;
						heightChallenge.fromShownToHiddenChange();
					}
					else {
						Player[] causers = notStandingOnHeight.toArray(new Player[notStandingOnHeight.size()]);
						String message = heightChallenge.createReasonMessage(heightChallenge.getPunishCause(),
								heightChallenge.getPunishType(), causers);
						callHeightEventAndActUpon(heightChallenge, message, 
								heightChallenge.getNormalHeight().getToBeOnHeight(), 
								heightChallenge.getNetherHeight().getToBeOnHeight(), causers);
						
						timeTo = ThreadLocalRandom.current()
								.nextLong(heightChallenge.getEarliestToShow(), (heightChallenge.getLatestToShow()+1));
						totalTimeTo = timeTo;
						heightChallenge.fromShownToHiddenChange();
					}
					
				}
				else {
					String message = heightChallenge.createLogMessage(heightChallenge.getPunishCause());
					ChallengeProfile.getInstance().sendMessageToAllParticipants(message);
					timeTo = ThreadLocalRandom.current()
							.nextLong(heightChallenge.getEarliestToShow(), (heightChallenge.getLatestToShow()+1));
					totalTimeTo = timeTo;
					heightChallenge.fromShownToHiddenChange();
				}	
				
			}
		}
		else {	
			timeTo -= 1;		
			if(heightChallenge.getStatus() == BossBarStatus.SHOWN) {
				heightChallenge.adjustProgress(timeTo < 0 ? 0 : timeTo);
				heightChallenge.adjustColorIfCase();
			}
			
		}
	}

	private boolean playersOnHeight(Player player) {
		if(player.getWorld().getEnvironment() == Environment.NETHER) {
			return player.getLocation().getBlockY() == heightChallenge.getNetherHeight().getToBeOnHeight();
		}
		else {
			return player.getLocation().getBlockY() == heightChallenge.getNormalHeight().getToBeOnHeight();
		}
	}
	
	/**
	 * @return the totalTimeTo
	 */
	public long getTotalTimeTo() {
		return totalTimeTo;
	}

	/**
	 * @param totalTimeTo the totalTimeTo to set
	 */
	public void setTotalTimeTo(long totalTimeTo) {
		this.totalTimeTo = totalTimeTo;
	}

	/**
	 * @return the timeTo
	 */
	public long getTimeTo() {
		return timeTo;
	}

	/**
	 * @param timeTo the timeTo to set
	 */
	public void setTimeTo(long timeTo) {
		this.timeTo = timeTo;
	}

}
