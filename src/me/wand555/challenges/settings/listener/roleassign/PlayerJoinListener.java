package me.wand555.challenges.settings.listener.roleassign;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.CustomHealthChallenge;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.height.HeightChallenge;
import me.wand555.challenges.settings.challengeprofile.types.mlg.MLGChallenge;
import me.wand555.challenges.settings.challengeprofile.types.onblock.OnBlockChallenge;
import me.wand555.challenges.settings.config.WorldUtil;
import me.wand555.challenges.start.Challenges;
import me.wand555.challenges.worldlinking.WorldLinkManager;

public class PlayerJoinListener implements Listener {

	public PlayerJoinListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * Deals with adding the player to the participants list if a challenge is running.
	 * Problem: A player has to manually reconnect because of health scale bug in bukkit/spigot.
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerJoinListener(PlayerJoinEvent event) {
		if(WorldLinkManager.worlds.contains(event.getPlayer().getWorld())) {
			ChallengeProfile.getInstance().addToParticipants(event.getPlayer());
			ChallengeProfile.getInstance().addToScoreBoard(event.getPlayer());
			CustomHealthChallenge cHealthChallenge = GenericChallenge.getChallenge(ChallengeType.CUSTOM_HEALTH);
			OnBlockChallenge onBlockChallenge = GenericChallenge.getChallenge(ChallengeType.ON_BLOCK);
			HeightChallenge heightChallenge = GenericChallenge.getChallenge(ChallengeType.BE_AT_HEIGHT);
			Player p = event.getPlayer();
			if(ChallengeProfile.getInstance().canTakeEffect()) {
				if(cHealthChallenge.isActive()) {			
					p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(cHealthChallenge.getAmount());
					p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
					p.setHealthScale(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
				}
				else {
					AttributeInstance maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
					maxHealth.setBaseValue(maxHealth.getDefaultValue());
					p.setHealthScale(p.getHealth());
					maxHealth.setBaseValue(maxHealth.getDefaultValue());
					p.setHealthScale(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
					p.damage(0);
					//p.kickPlayer("Custom HP were changed. Please join back now.");
				}		
				
				if(onBlockChallenge.isActive()) {
					onBlockChallenge.addPlayerToBossBar(p);
				}
			}
			
			if(ChallengeProfile.getInstance().isInMLGRightNow) {
				if(event.getPlayer().getWorld().equals(Bukkit.getWorld("MLGWorld"))) {
					WorldUtil.loadPlayerInformationInChallengeAndApply(event.getPlayer());
					MLGChallenge mlgChallenge = GenericChallenge.getChallenge(ChallengeType.MLG);
					mlgChallenge.getInMLGWorld().remove(event.getPlayer().getUniqueId());
				}
			}	
			
			if(onBlockChallenge.isActive()) {
				onBlockChallenge.addPlayerToBossBar(p);
			}
			if(heightChallenge.isActive()) {
				heightChallenge.addPlayerToBossBar(p);
			}
		}
	}
}
