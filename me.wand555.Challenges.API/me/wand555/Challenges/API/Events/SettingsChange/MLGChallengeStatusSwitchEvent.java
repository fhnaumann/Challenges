package me.wand555.Challenges.API.Events.SettingsChange;

import org.bukkit.entity.Player;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGChallenge;

/**
 * Called when the player enables the MLG Challenge.
 * @author wand555
 *
 */
public class MLGChallengeStatusSwitchEvent extends PunishableChallengeStatusSwitchEvent<MLGChallenge> {

	private int earliest;
	private int latest;
	private int height;
	private long timeToNextMLG;
	
	public MLGChallengeStatusSwitchEvent(MLGChallenge challenge, long timeToMLG, PunishType punishType, Player player) {
		super(challenge, punishType, player);
		this.earliest = challenge.getEarliest();
		this.latest = challenge.getLatest();
		this.height = challenge.getHeight();
		this.timeToNextMLG = timeToMLG;
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
	 * @return the timeToNextMLG
	 */
	public long getTimeToNextMLG() {
		return timeToNextMLG;
	}

	/**
	 * @param timeToNextMLG the timeToNextMLG to set
	 */
	public void setTimeToNextMLG(long timeToNextMLG) {
		this.timeToNextMLG = timeToNextMLG;
	}

}
