package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public interface BossBarShown {

	public void createBossBar(String title, BarColor color);
	public BossBar getBossBar();
	public void setBossBar(BossBar bossbar);
	
	public void setDefaults();
	/**
	 * Internally adjusts the color the bossbar should have after changing.
	 * Some implementations may not use this and do nothing here.
	 */
	public void adjustColorIfCase();
	public void adjustProgress(long time);
	
	public void addPlayerToBossBar(Player player);
	public void removePlayerFromBossBar(Player player);
	
	public void fromHiddenToShownChange(long totalTimeTo);
	public void fromShownToHiddenChange();
}
