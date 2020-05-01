package me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.netty.util.internal.ThreadLocalRandom;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.BossBarShown;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.BossBarStatus;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.Punishable;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ReasonNotifiable;
import me.wand555.Challenges.Config.LanguageMessages;
import me.wand555.Challenges.Timer.DateUtil;

public class OnBlockChallenge extends GenericChallenge implements Punishable, ReasonNotifiable, BossBarShown {

	private Material toStayOn;
	private BossBarStatus status;
	
	private int earliestToShow;
	private int latestToShow;
	private int earliestOnBlock;
	private int latestOnBlock;
	private BossBar bossbar;
	private OnBlockTimer timer;
	private String bossBarMessageShown;
	
	private PunishType punishType;
	
	private final List<Material> blockMaterials = Stream.of(Material.values())
			.filter(Material::isBlock)
			.filter(Material::isItem)
			.collect(Collectors.toList());
	
	public OnBlockChallenge() {
		super(ChallengeType.ON_BLOCK);
		activeChallenges.put(ChallengeType.ON_BLOCK, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		return createPunishmentItem(Material.END_STONE_BRICK_SLAB, 
				LanguageMessages.guiOnBlockName, 
				new ArrayList<String>(LanguageMessages.guiOnBlockLore),
				punishType, 
				super.active);
	}

	/**
	 * When the status changes from HIDDEN to SHOWN
	 */
	@Override
	public void fromHiddenToShownChange(long totalTimeTo) {
		setStatus(BossBarStatus.SHOWN);
		toStayOn = blockMaterials.get(ThreadLocalRandom.current().nextInt(blockMaterials.size()));
		bossBarMessageShown = LanguageMessages.onBlockShown
				.replace("[BLOCK]", WordUtils.capitalize(toStayOn.toString().toLowerCase().replace('_', ' ')));
		bossbar.setTitle(bossBarMessageShown.replace("[TIME]", DateUtil.formatNoHourDuration(totalTimeTo)));
		bossbar.setProgress(1);
		bossbar.setColor(BarColor.BLUE);
	}
	
	@Override
	public void fromShownToHiddenChange() {
		setStatus(BossBarStatus.HIDDEN);
		bossbar.setTitle(LanguageMessages.onBlockHidden);
		bossBarMessageShown = null;
		bossbar.setProgress(1);
		bossbar.setColor(BarColor.WHITE);
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

	/**
	 * @return the toStayOn
	 */
	public Material getToStayOn() {
		return toStayOn;
	}

	/**
	 * @param toStayOn the toStayOn to set
	 */
	public void setToStayOn(Material toStayOn) {
		this.toStayOn = toStayOn;
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
	 * @return the earliest
	 */
	public int getEarliestToShow() {
		return earliestToShow;
	}

	/**
	 * @param earliest the earliest to set
	 */
	public void setEarliestToShow(int earliestToShow) {
		this.earliestToShow = earliestToShow;
	}

	/**
	 * @return the latest
	 */
	public int getLatestToShow() {
		return latestToShow;
	}

	/**
	 * @param latest the latest to set
	 */
	public void setLatestToShow(int latestToShow) {
		this.latestToShow = latestToShow;
	}

	/**
	 * @return the bossbar
	 */
	@Override
	public BossBar getBossBar() {
		return bossbar;
	}

	/**
	 * @param bossbar the bossbar to set
	 */
	@Override
	public void setBossBar(BossBar bossbar) {
		this.bossbar = bossbar;
	}

	/**
	 * @return the timer
	 */
	public OnBlockTimer getTimer() {
		return timer;
	}

	/**
	 * @param timer the timer to set
	 */
	public void setTimer(OnBlockTimer timer) {
		this.timer = timer;
	}

	@Override
	public void adjustColorIfCase() {
		switch(status) {
		case SHOWN:
			if(bossbar.getProgress() >= 0.8d) bossbar.setColor(BarColor.BLUE);
			else {
				if(bossbar.getProgress() >= 0.5d) bossbar.setColor(BarColor.GREEN);
				else {
					if(bossbar.getProgress() >= 0.3d) bossbar.setColor(BarColor.YELLOW);
					else {
						bossbar.setColor(BarColor.RED);
					}
				}
			}
			break;
		default: break;
		}
	}

	@Override
	public void createBossBar(String title, BarColor color) {
		this.bossbar = Bukkit.getServer().createBossBar(title, color, BarStyle.SEGMENTED_20);
		this.bossbar.setProgress(1d);
	}

	@Override
	public void addPlayerToBossBar(Player player) {
		this.bossbar.addPlayer(player);
	}

	@Override
	public void removePlayerFromBossBar(Player player) {
		this.bossbar.removePlayer(player);
	}

	@Override
	public void adjustProgress(long time) {
		this.bossbar.setProgress(timer.getTimeTo()/((double)timer.getTotalTimeTo()));
		if(status == BossBarStatus.SHOWN) {
			this.bossbar.setTitle(bossBarMessageShown.replace("[TIME]", DateUtil.formatNoHourDuration(time)));
		}
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
	 * @return the bossBarMessageShown
	 */
	public String getBossBarMessageShown() {
		return bossBarMessageShown;
	}

	/**
	 * @param bossBarMessageShown the bossBarMessageShown to set
	 */
	public void setBossBarMessageShown(String bossBarMessageShown) {
		this.bossBarMessageShown = bossBarMessageShown;
	}

	@Override
	public void setDefaults() {
		setStatus(BossBarStatus.HIDDEN);
		createBossBar(LanguageMessages.onBlockHidden, BarColor.WHITE);
	}
	
	
}
