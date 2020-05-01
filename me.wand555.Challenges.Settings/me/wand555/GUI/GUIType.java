package me.wand555.GUI;

public enum GUIType {
	OVERVIEW(null), @Deprecated BACKPACK(null), PUNISHMENT(OVERVIEW), COLLECTED_ITEMS_LIST(OVERVIEW);

	private final GUIType goBack;
	
	GUIType(GUIType goBack) {
		this.goBack = goBack;
	}
	
	public GUIType getGoBack() {
		return this.goBack;
	}
}
