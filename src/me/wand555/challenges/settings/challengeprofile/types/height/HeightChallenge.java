package me.wand555.challenges.settings.challengeprofile.types.height;

import java.util.ArrayList;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.wand555.challenges.settings.challengeprofile.types.BossBarShown;
import me.wand555.challenges.settings.challengeprofile.types.BossBarStatus;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.settings.challengeprofile.types.Punishable;
import me.wand555.challenges.settings.challengeprofile.types.ReasonNotifiable;
import me.wand555.challenges.settings.config.LanguageMessages;
import me.wand555.challenges.settings.timer.DateUtil;

public class HeightChallenge extends GenericChallenge implements Punishable, ReasonNotifiable, BossBarShown {

	private NormalHeight normalHeight;
	private NetherHeight netherHeight;
	private BossBarStatus status;
	
	private int earliestToShow;
	private int latestToShow;
	private int earliestToBeOnHeight;
	private int latestToBeOnHeight;
	private HeightTimer timer;
	
	private PunishType punishType;
	
	public HeightChallenge() {
		super(ChallengeType.BE_AT_HEIGHT);
		activeChallenges.put(ChallengeType.BE_AT_HEIGHT, this);
	}

	@Override
	public PunishType getPunishType() {
		return punishType;
	}

	@Override
	public void setPunishType(PunishType punishType) {
		this.punishType = punishType;
	}

	@Override
	public ChallengeType getPunishCause() {
		return super.type;
	}

	@Override
	public ItemStack getDisplayItem() {
		return createItem(Material.FEATHER, 
				LanguageMessages.guiToBeOnHeightName, 
				new ArrayList<String>(LanguageMessages.guiToBeOnHeightLore), 
				super.active);
	}

	/**
	 * @return the status
	 */
	public BossBarStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(BossBarStatus status) {
		this.status = status;
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
	 * @return the timer
	 */
	public HeightTimer getTimer() {
		return timer;
	}

	/**
	 * @param timer the timer to set
	 */
	public void setTimer(HeightTimer timer) {
		this.timer = timer;
	}

	@Override
	public void createBossBar(String title, BarColor color) {
		getNormalHeight().setBossbar(Bukkit.getServer().createBossBar(title, color, BarStyle.SEGMENTED_20));
		getNetherHeight().setBossbar(Bukkit.getServer().createBossBar(title, color, BarStyle.SEGMENTED_20));
		getNormalHeight().getBossbar().setProgress(1d);
		getNetherHeight().getBossbar().setProgress(1d);
	}

	@Override
	public BossBar getBossBar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBossBar(BossBar bossbar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefaults() {
		setStatus(BossBarStatus.HIDDEN);
		createBossBar(LanguageMessages.onHeightHidden, BarColor.WHITE);
	}

	/**
	 * @return the normalHeight
	 */
	public NormalHeight getNormalHeight() {
		if(normalHeight == null) normalHeight = new NormalHeight();
		return normalHeight;
	}

	/**
	 * @return the netherHeight
	 */
	public NetherHeight getNetherHeight() {
		if(netherHeight == null) netherHeight = new NetherHeight();
		return netherHeight;
	}

	@Override
	public void adjustColorIfCase() {
		switch(status) {
		case SHOWN:
			if(getNormalHeight().getBossbar().getProgress() >= 0.8d) getNormalHeight().getBossbar().setColor(BarColor.BLUE);
			else {
				if(getNormalHeight().getBossbar().getProgress() >= 0.5d) getNormalHeight().getBossbar().setColor(BarColor.GREEN);
				else {
					if(getNormalHeight().getBossbar().getProgress() >= 0.3d) getNormalHeight().getBossbar().setColor(BarColor.YELLOW);
					else {
						getNormalHeight().getBossbar().setColor(BarColor.RED);
					}
				}
			}
			if(getNetherHeight().getBossbar().getProgress() >= 0.8d) getNetherHeight().getBossbar().setColor(BarColor.BLUE);
			else {
				if(getNetherHeight().getBossbar().getProgress() >= 0.5d) getNetherHeight().getBossbar().setColor(BarColor.GREEN);
				else {
					if(getNetherHeight().getBossbar().getProgress() >= 0.3d) getNetherHeight().getBossbar().setColor(BarColor.YELLOW);
					else {
						getNetherHeight().getBossbar().setColor(BarColor.RED);
					}
				}
			}
			break;
		default: break;
		}
	}

	@Override
	public void adjustProgress(long time) {
		getNormalHeight().getBossbar().setProgress(timer.getTimeTo()/((double)timer.getTotalTimeTo()));
		getNetherHeight().getBossbar().setProgress(timer.getTimeTo()/((double)timer.getTotalTimeTo()));
		if(status == BossBarStatus.SHOWN) {
			getNormalHeight().getBossbar()
				.setTitle(getNormalHeight().getBossbarMessageShown().replace("[TIME]", DateUtil.formatNoHourDuration(time)));
			getNetherHeight().getBossbar()
			.setTitle(getNetherHeight().getBossbarMessageShown().replace("[TIME]", DateUtil.formatNoHourDuration(time)));
		}
	}

	@Override
	public void addPlayerToBossBar(Player player) {
		if(player.getWorld().getEnvironment() == Environment.NETHER) getNetherHeight().getBossbar().addPlayer(player);
		else getNormalHeight().getBossbar().addPlayer(player);
	}

	@Override
	public void removePlayerFromBossBar(Player player) {
		if(player.getWorld().getEnvironment() == Environment.NETHER) {
			getNormalHeight().getBossbar().removePlayer(player);
		}
		else getNetherHeight().getBossbar().removePlayer(player);
	}

	@Override
	public void fromHiddenToShownChange(long totalTimeTo) {
		setStatus(BossBarStatus.SHOWN);
		getNormalHeight().setToBeOnHeight(ThreadLocalRandom.current().nextInt(2, getNormalHeight().maxPossible));
		getNormalHeight().setBossbarMessageShown(LanguageMessages.onHeightShown
				.replace("[HEIGHT]", Integer.toString(getNormalHeight().getToBeOnHeight())));
		getNormalHeight().getBossbar().setTitle(getNormalHeight().getBossbarMessageShown()
				.replace("[TIME]", DateUtil.formatNoHourDuration(totalTimeTo)));
		getNormalHeight().getBossbar().setProgress(1);
		getNormalHeight().getBossbar().setColor(BarColor.BLUE);
		
		getNetherHeight().setToBeOnHeight(ThreadLocalRandom.current().nextInt(2, getNetherHeight().maxPossible));
		getNetherHeight().setBossbarMessageShown(LanguageMessages.onHeightShown
				.replace("[HEIGHT]", Integer.toString(getNetherHeight().getToBeOnHeight())));
		getNetherHeight().getBossbar().setTitle(getNetherHeight().getBossbarMessageShown()
				.replace("[TIME]", DateUtil.formatNoHourDuration(totalTimeTo)));
		getNetherHeight().getBossbar().setProgress(1);
		getNetherHeight().getBossbar().setColor(BarColor.BLUE);
	}

	@Override
	public void fromShownToHiddenChange() {
		setStatus(BossBarStatus.HIDDEN);
		getNormalHeight().getBossbar().setTitle(LanguageMessages.onHeightHidden);
		getNormalHeight().setBossbarMessageShown(null);
		getNormalHeight().getBossbar().setProgress(1);
		getNormalHeight().getBossbar().setColor(BarColor.WHITE);
		
		getNetherHeight().getBossbar().setTitle(LanguageMessages.onHeightHidden);
		getNetherHeight().setBossbarMessageShown(null);
		getNetherHeight().getBossbar().setProgress(1);
		getNetherHeight().getBossbar().setColor(BarColor.WHITE);
	}

}
