package me.wand555.challenges.settings.challengeprofile.types.lavaground;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.config.LanguageMessages;

public class LavaGroundChallenge extends GenericChallenge {
	
	private long timeToTransition = 40;
	private Set<BlockChangeTimer> changeTimers = new HashSet<BlockChangeTimer>();
	private boolean lavaStay;
	
	public LavaGroundChallenge() {
		super(ChallengeType.GROUND_IS_LAVA);
		activeChallenges.put(ChallengeType.GROUND_IS_LAVA, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		return createItem(Material.MAGMA_BLOCK, 
				LanguageMessages.guiItemFloorIsLavaName, 
				new ArrayList<String>(LanguageMessages.guiItemFloorIsLavaLore), 
				super.active);
	}
	
	public void startFresh(Location from) {
		changeTimers.add(new BlockChangeTimer(PLUGIN, 
				this, 
				from, 
				new LavaGroundBlockData(BlockStatus.FIRST_PHASE, 
						from.getBlock().getType())));
	}

	/**
	 * @return the changeTimers
	 */
	public Set<BlockChangeTimer> getChangeTimers() {
		return changeTimers;
	}

	/**
	 * @param changeTimers the changeTimers to set
	 */
	public void setChangeTimers(Set<BlockChangeTimer> changeTimers) {
		this.changeTimers = changeTimers;
	}
	/**
	 * @return the lavaStay
	 */
	public boolean isLavaStay() {
		return lavaStay;
	}

	/**
	 * @param lavaStay the lavaStay to set
	 */
	public void setLavaStay(boolean lavaStay) {
		this.lavaStay = lavaStay;
	}

	/**
	 * @return the timeToTransition
	 */
	public long getTimeToTransition() {
		return timeToTransition;
	}

	/**
	 * @param timeToTransition the timeToTransition to set
	 */
	public void setTimeToTransition(long timeToTransition) {
		this.timeToTransition = timeToTransition;
	}


}
