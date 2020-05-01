package me.wand555.Challenges.ChallengeProfile.ChallengeTypes.HeightChallenge;

import org.bukkit.boss.BossBar;

public class NormalHeight {

	public final int maxPossible = 256;
	
	private int toBeOnHeight;
	private BossBar bossbar;
	private String bossbarMessageShown;
	/**
	 * @return the toBeOnHeight
	 */
	public int getToBeOnHeight() {
		return toBeOnHeight;
	}
	/**
	 * @param toBeOnHeight the toBeOnHeight to set
	 */
	public void setToBeOnHeight(int toBeOnHeight) {
		this.toBeOnHeight = toBeOnHeight;
	}
	/**
	 * @return the bossbar
	 */
	public BossBar getBossbar() {
		return bossbar;
	}
	/**
	 * @param bossbar the bossbar to set
	 */
	public void setBossbar(BossBar bossbar) {
		this.bossbar = bossbar;
	}
	/**
	 * @return the bossbarMessageShown
	 */
	public String getBossbarMessageShown() {
		return bossbarMessageShown;
	}
	/**
	 * @param bossbarMessageShown the bossbarMessageShown to set
	 */
	public void setBossbarMessageShown(String bossbarMessageShown) {
		this.bossbarMessageShown = bossbarMessageShown;
	}
	
	
}
