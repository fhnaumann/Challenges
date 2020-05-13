package me.wand555.Challenge.DeathRun;

public enum DeathRunDirection {

	NORTH("north"), 
	EAST("east"), 
	SOUTH("south"), 
	WEST("west");
	
	private final String string;

	DeathRunDirection(String string) {
		this.string = string;
	}
	
	public String getString() {
		return this.string;
	}
	
	public static DeathRunDirection matchDeathRunDirection(String string) {
		if(string.equalsIgnoreCase(NORTH.getString())) return NORTH;
		if(string.equalsIgnoreCase(EAST.getString())) return EAST;
		if(string.equalsIgnoreCase(SOUTH.getString())) return SOUTH;
		if(string.equalsIgnoreCase(WEST.getString())) return WEST;
		return null;
	}
}
