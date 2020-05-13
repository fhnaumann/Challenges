package me.wand555.Challenge.DeathRun.Conversations.Prompts.extra;

public enum DeathRunGoal {

	DISTANCE_GOAL("distance"),
	TIME_GOAL("time"),
	BOTH_GOAL("both");

	private final String string;
	
	DeathRunGoal(String string) {
		this.string = string;
	}
	
	public String getString() {
		return this.string;
	}
	
	public static DeathRunGoal matchDeathRunGoal(String string) {
		if(string.equalsIgnoreCase(DISTANCE_GOAL.getString())) return DISTANCE_GOAL;
		if(string.equalsIgnoreCase(TIME_GOAL.getString())) return TIME_GOAL;
		if(string.equalsIgnoreCase(BOTH_GOAL.getString())) return BOTH_GOAL;
		return null;
	}
}
