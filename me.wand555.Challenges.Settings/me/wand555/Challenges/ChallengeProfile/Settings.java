package me.wand555.Challenges.ChallengeProfile;

public class Settings {
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
	
	/*
	public  boolean endOnDeath = true;
	public  void setEndOnDeath() {endOnDeath = !endOnDeath;}
	
	public  boolean spawnNearFortress = false;
	public  void setSpawnNearFortress() {spawnNearFortress = !spawnNearFortress;}
	
	public  boolean noDamage = false;
	public  void setNoDamage() {noDamage = !noDamage;}
	
	public  boolean noReg = false;
	public  void setNoReg() {noReg = !noReg;}
	
	public  boolean noRegHard = false;
	public  void setNoRegHard() {noRegHard = !noRegHard;}
	
	public  boolean isCustomHealth = false;
	public  void setIsCustomHealth() {isCustomHealth = !isCustomHealth;}
	public  int customHP = 20;
	public  void setCustomHP(int amount) {customHP = amount;}
	
	public  boolean isSharedHealth = false;
	public  void setIsSharedHealth() {isSharedHealth = !isSharedHealth;}
	public  double sharedHP = customHP;
	public  void setSharedHP(double amount) {sharedHP = amount;}
	
	public  boolean noBlockPlace = false;
	public  void setNoBlockPlace() {noBlockPlace = !noBlockPlace;}
	
	public  boolean noBlockBreaking = false;
	public  void setNoBlockBreaking() {noBlockBreaking = !noBlockBreaking;}
	
	public  boolean noCrafting = false;
	public  void setNoCrafting() {noCrafting = !noCrafting;}
	
	public  boolean noSneaking = false;
	public  void setNoSneaking() {noSneaking = !noSneaking;}
	
	public  boolean isRandomizedBlockDrops = false;
	public  void setRandomizedBlockDrops() {isRandomizedBlockDrops = !isRandomizedBlockDrops;}
	
	public  boolean isRandomizedMobDrops = false;
	public  void setRandomizedMobDrops() {isRandomizedMobDrops = !isRandomizedMobDrops;}
	
	public  boolean isRandomizedCrafting = false;
	public  void setRandomizedCrafting() {isRandomizedCrafting = !isRandomizedCrafting;}
	
	public  boolean isMLG = false;
	public  void setMLG() {isMLG = !isMLG;}
	
	*/
	public  boolean isInMLGRightNow = false;
	public  void setInMLGRightNow() {isInMLGRightNow = !isInMLGRightNow;}
	
	
	public  void restoreDefault() {
		hasStarted = false;
		isPaused = false;
		isDone = false;
		isInMLGRightNow = false;
	}
}
