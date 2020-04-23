package me.wand555.Challenges.ChallengeProfile;

public enum ChallengeEndReason {
	FINISHED(false),
	NO_TIME_LEFT(false),
	NATURAL_DEATH(true),
	NO_DAMAGE(true),
	NO_BLOCK_PLACE(true),
	NO_BLOCK_BREAK(true),
	NO_CRAFTING(true), 
	NO_SNEAKING(true),
	FAILED_MLG(true), 
	NOT_ON_BLOCK(true), 
	TOO_MANY_ITEMS_GLOBAL(false)
	;
	
	private final boolean restorable;
	
	private ChallengeEndReason(boolean restorable) {
		this.restorable = restorable;
	}
	
	public boolean isRestorable() {
		return this.restorable;
	}
}
