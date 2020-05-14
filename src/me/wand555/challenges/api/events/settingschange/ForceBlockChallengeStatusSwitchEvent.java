package me.wand555.challenges.api.events.settingschange;

import org.bukkit.entity.Player;

import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.settings.challengeprofile.types.onblock.OnBlockChallenge;

public class ForceBlockChallengeStatusSwitchEvent extends PunishableChallengeStatusSwitchEvent<OnBlockChallenge> {

	private int earliestToShow;
	private int latestToShow;
	private int earliestOnBlock;
	private int latestOnBlock;
	private long timeToBlockShown;
	
	public ForceBlockChallengeStatusSwitchEvent(OnBlockChallenge challenge, long timeToBlockShown, PunishType punishType, Player player) {
		super(challenge, punishType, player);
		this.earliestToShow = challenge.getEarliestToShow();
		this.latestToShow = challenge.getLatestToShow();
		this.earliestOnBlock = challenge.getEarliestOnBlock();
		this.latestOnBlock = challenge.getLatestOnBlock();
		this.setTimeToBlockShown(timeToBlockShown);
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
	 * @return the earliestOnBlock
	 */
	public int getEarliestOnBlock() {
		return earliestOnBlock;
	}

	/**
	 * @param earliestOnBlock the earliestOnBlock to set
	 */
	public void setEarliestOnBlock(int earliestOnBlock) {
		this.earliestOnBlock = earliestOnBlock;
	}

	/**
	 * @return the latestOnBlock
	 */
	public int getLatestOnBlock() {
		return latestOnBlock;
	}

	/**
	 * @param latestOnBlock the latestOnBlock to set
	 */
	public void setLatestOnBlock(int latestOnBlock) {
		this.latestOnBlock = latestOnBlock;
	}

	/**
	 * @return the timeToBlockShown
	 */
	public long getTimeToBlockShown() {
		return timeToBlockShown;
	}

	/**
	 * @param timeToBlockShown the timeToBlockShown to set
	 */
	public void setTimeToBlockShown(long timeToBlockShown) {
		this.timeToBlockShown = timeToBlockShown;
	}

}
