package me.wand555.Challenges.API.Events.SettingsChange;

import org.bukkit.entity.Player;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.HeightChallenge.HeightChallenge;

public class HeightChallengeStatusSwitchEvent extends PunishableChallengeStatusSwitchEvent<HeightChallenge> {

	private int earliestToShow;
	private int latestToShow;
	private int earliestToBeOnHeight;
	private int latestToBeOnHeight;
	private long timeToHeightShown;
	
	public HeightChallengeStatusSwitchEvent(HeightChallenge challenge, long timeToBlockShown, PunishType punishType, Player player) {
		super(challenge, punishType, player);
		this.earliestToShow = challenge.getEarliestToShow();
		this.latestToShow = challenge.getLatestToShow();
		this.earliestToBeOnHeight = challenge.getEarliestToBeOnHeight();
		this.latestToBeOnHeight = challenge.getLatestToBeOnHeight();
		this.timeToHeightShown = timeToBlockShown;
	}

	/**
	 * @return the earliestToShow
	 */
	public int getEarliestToShow() {
		return earliestToShow;
	}

	/**
	 * @param earliestToShow the earliestToShow to set
	 */
	public void setEarliestToShow(int earliestToShow) {
		this.earliestToShow = earliestToShow;
	}

	/**
	 * @return the latestToShow
	 */
	public int getLatestToShow() {
		return latestToShow;
	}

	/**
	 * @param latestToShow the latestToShow to set
	 */
	public void setLatestToShow(int latestToShow) {
		this.latestToShow = latestToShow;
	}

	/**
	 * @return the earliestToBeOnHeight
	 */
	public int getEarliestToBeOnHeight() {
		return earliestToBeOnHeight;
	}

	/**
	 * @param earliestToBeOnHeight the earliestToBeOnHeight to set
	 */
	public void setEarliestToBeOnHeight(int earliestToBeOnHeight) {
		this.earliestToBeOnHeight = earliestToBeOnHeight;
	}

	/**
	 * @return the latestToBeOnHeight
	 */
	public int getLatestToBeOnHeight() {
		return latestToBeOnHeight;
	}

	/**
	 * @param latestToBeOnHeight the latestToBeOnHeight to set
	 */
	public void setLatestToBeOnHeight(int latestToBeOnHeight) {
		this.latestToBeOnHeight = latestToBeOnHeight;
	}

	/**
	 * @return the timeToHeightShown
	 */
	public long getTimeToHeightShown() {
		return timeToHeightShown;
	}

	/**
	 * @param timeToHeightShown the timeToHeightShown to set
	 */
	public void setTimeToHeightShown(long timeToHeightShown) {
		this.timeToHeightShown = timeToHeightShown;
	}
	
	
}
