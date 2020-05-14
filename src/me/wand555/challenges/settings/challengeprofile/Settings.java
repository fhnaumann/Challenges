package me.wand555.challenges.settings.challengeprofile;

import me.wand555.challenges.start.Challenges;

public class Settings {
	public boolean isRestarted = false;
	public  boolean hasStarted = false;
	public  void setStarted() {hasStarted = !hasStarted;}
	public  boolean isPaused = false;
	public  void setPaused() {isPaused = !isPaused;}
	/**
	 * Needs to be true to be able to perform /challenge reset
	 * True when challenge failed or completed.
	 */
	public  boolean isDone = false;
	public  void setDone() {isDone = !isDone;}
	
	/**
	 * Utility method so on action I can only call this. MLG Endings are handled seperately.
	 * @return if effect is valid to take place
	 */
	public  boolean canTakeEffect() {return hasStarted && !isPaused && !isDone && !isInMLGRightNow;}
	public  boolean isInMLGRightNow = false;
	public  void setInMLGRightNow() {isInMLGRightNow = !isInMLGRightNow;}
	
	public boolean logDamage = Challenges.getPlugin(Challenges.class).getConfig().getBoolean("logDamage");
	
	private ChallengeMode mode;
	
	public void switchTo(ChallengeMode mode) {
		this.mode = mode;
	}
	
	public boolean isNormalMode() {
		return mode == ChallengeMode.NORMAL;
	}
	
	public boolean isDeathRunMode() {
		return mode == ChallengeMode.DEATHRUN;
	}
	
	public  void restoreDefault() {
		//isRestarted = false;
		hasStarted = false;
		isPaused = false;
		isDone = false;
		isInMLGRightNow = false;
		this.mode = ChallengeMode.NORMAL;
	}
}


