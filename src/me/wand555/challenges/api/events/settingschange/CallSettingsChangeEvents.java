package me.wand555.challenges.api.events.settingschange;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.CustomHealthChallenge;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.settings.challengeprofile.types.Punishable;
import me.wand555.challenges.settings.challengeprofile.types.RandomChallenge;
import me.wand555.challenges.settings.challengeprofile.types.height.HeightChallenge;
import me.wand555.challenges.settings.challengeprofile.types.height.HeightTimer;
import me.wand555.challenges.settings.challengeprofile.types.itemcollectionlimit.ItemCollectionLimitGlobalChallenge;
import me.wand555.challenges.settings.challengeprofile.types.mlg.MLGChallenge;
import me.wand555.challenges.settings.challengeprofile.types.mlg.MLGTimer;
import me.wand555.challenges.settings.challengeprofile.types.onblock.OnBlockChallenge;
import me.wand555.challenges.settings.challengeprofile.types.onblock.OnBlockTimer;
import me.wand555.challenges.settings.gui.GUI;
import me.wand555.challenges.settings.gui.GUIType;
import me.wand555.challenges.start.Challenges;

public interface CallSettingsChangeEvents {

	default <T extends GenericChallenge> void callSettingsChangeNormalEventAndActUpon(T rawType, Player p, int indexClicked, GUI gui) {
		GenericChallenge genericChallenge = (GenericChallenge) rawType;
		ChallengeStatusSwitchEvent<T> switchEvent = new ChallengeStatusSwitchEvent<T>(rawType, p);
		Bukkit.getServer().getPluginManager().callEvent(switchEvent);
		if(!switchEvent.isCancelled()) {
			genericChallenge.setAround();	
			genericChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());	
			
			
			if(rawType.getClass().equals(MLGChallenge.class)) {
				MLGChallenge mlgChallenge = (MLGChallenge) rawType;
				if(mlgChallenge.getTimer() != null) {
					mlgChallenge.getTimer().cancel();
					mlgChallenge.setTimer(null);
					mlgChallenge.setEarliest(0);
					mlgChallenge.setLatest(0);
				}
			}
			else if(rawType.getClass().equals(OnBlockChallenge.class)) {
				OnBlockChallenge onBlockChallenge = (OnBlockChallenge) rawType;
				if(onBlockChallenge.getTimer() != null) {
					onBlockChallenge.getBossBar().removeAll();
					onBlockChallenge.getTimer().cancel();
					onBlockChallenge.setTimer(null);
					onBlockChallenge.setEarliestToShow(0);
					onBlockChallenge.setLatestToShow(0);
					onBlockChallenge.setEarliestOnBlock(0);
					onBlockChallenge.setLatestOnBlock(0);
					onBlockChallenge.setBossBarMessageShown(null);
					onBlockChallenge.setToStayOn(null);
				}
			}
			else if(rawType.getClass().equals(ItemCollectionLimitGlobalChallenge.class)) {
				ItemCollectionLimitGlobalChallenge iCLGChallenge = (ItemCollectionLimitGlobalChallenge) rawType;
				iCLGChallenge.setCurrentAmount(0);
				iCLGChallenge.setActive(false);
				iCLGChallenge.setLimit(0);
				iCLGChallenge.getUniqueItems().clear();
			}
			else if(rawType.getClass().equals(HeightChallenge.class)) {
				HeightChallenge heightChallenge = (HeightChallenge) rawType;
				heightChallenge.setActive(false);
				heightChallenge.setEarliestToShow(0);
				heightChallenge.setLatestToShow(0);
				heightChallenge.setEarliestToBeOnHeight(0);
				heightChallenge.setLatestToBeOnHeight(0);
				heightChallenge.getNormalHeight().getBossbar().removeAll();
				heightChallenge.getNormalHeight().setBossbar(null);
				heightChallenge.getNetherHeight().getBossbar().removeAll();
				heightChallenge.getNetherHeight().setBossbar(null);
				if(heightChallenge.getTimer() != null) {
					heightChallenge.getTimer().cancel();
					heightChallenge.setTimer(null);
				}
				
			}
		}
		else {
			if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());
		}
		//System.out.println("index called from normal settings change event: " + indexClicked);
		gui.createGUI(p, GUIType.OVERVIEW, indexClicked, rawType);	
	}
	
	default <T extends GenericChallenge & Punishable> void callSettingsChangePunishableEventAndActUpon(T rawType, PunishType type, Player p, GUI gui, Challenges plugin) {
		GenericChallenge genericChallenge = (GenericChallenge) rawType;
		
		if(rawType.getClass().equals(MLGChallenge.class)) {
			MLGChallenge mlgChallenge = (MLGChallenge) rawType;			
			long timeToMLG = ThreadLocalRandom.current()
					.nextLong(mlgChallenge.getEarliest()*20, (mlgChallenge.getLatest()+1)*20);
			MLGChallengeStatusSwitchEvent switchEvent = new MLGChallengeStatusSwitchEvent(mlgChallenge, timeToMLG, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				mlgChallenge.setPunishType(switchEvent.getPunishType());
				mlgChallenge.setAround();
				mlgChallenge.setEarliest(switchEvent.getEarliest());
				mlgChallenge.setLatest(switchEvent.getLatest());
				mlgChallenge.setHeight(switchEvent.getHeight());
				mlgChallenge.setTimer(new MLGTimer(plugin, switchEvent.getTimeToNextMLG(), switchEvent.getTimeToNextMLG()));
				mlgChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());	
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		else if(rawType.getClass().equals(OnBlockChallenge.class)) {
			OnBlockChallenge onBlockChallenge = (OnBlockChallenge) rawType;
			long timeToBlockShown = ThreadLocalRandom.current()
					.nextLong(onBlockChallenge.getEarliestToShow(), (onBlockChallenge.getLatestToShow()+1));
			ForceBlockChallengeStatusSwitchEvent switchEvent = new ForceBlockChallengeStatusSwitchEvent(onBlockChallenge, timeToBlockShown, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				onBlockChallenge.setAround();
				onBlockChallenge.setPunishType(switchEvent.getPunishType());
				onBlockChallenge.setEarliestToShow(switchEvent.getEarliestToShow());
				onBlockChallenge.setLatestToShow(switchEvent.getLatestToShow());
				onBlockChallenge.setEarliestOnBlock(switchEvent.getEarliestOnBlock());
				onBlockChallenge.setLatestOnBlock(switchEvent.getLatestOnBlock());
				onBlockChallenge.setTimer(new OnBlockTimer(plugin, onBlockChallenge, switchEvent.getTimeToBlockShown(), switchEvent.getTimeToBlockShown(), true));
				ChallengeProfile.getInstance().getParticipants().forEach(player -> onBlockChallenge.addPlayerToBossBar(player));
				onBlockChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());	
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		else if(rawType.getClass().equals(HeightChallenge.class)) {
			HeightChallenge heightChallenge = (HeightChallenge) rawType;
			long timeToHeightShown = ThreadLocalRandom.current()
					.nextLong(heightChallenge.getEarliestToShow(), (heightChallenge.getLatestToShow()+1));
			HeightChallengeStatusSwitchEvent switchEvent = new HeightChallengeStatusSwitchEvent(heightChallenge, timeToHeightShown, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				heightChallenge.setAround();
				heightChallenge.setPunishType(switchEvent.getPunishType());
				heightChallenge.setEarliestToShow(switchEvent.getEarliestToShow());
				heightChallenge.setLatestToShow(switchEvent.getLatestToShow());
				heightChallenge.setEarliestToBeOnHeight(switchEvent.getEarliestToBeOnHeight());
				heightChallenge.setLatestToBeOnHeight(switchEvent.getLatestToBeOnHeight());
				heightChallenge.setTimer(new HeightTimer(plugin, heightChallenge, switchEvent.getTimeToHeightShown(), switchEvent.getTimeToHeightShown(), true));
				ChallengeProfile.getInstance().getParticipants().forEach(player -> heightChallenge.addPlayerToBossBar(player));
				heightChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());	
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		else {
			PunishableChallengeStatusSwitchEvent<T> switchEvent = new PunishableChallengeStatusSwitchEvent<T>(rawType, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				Punishable punishable = (Punishable) rawType;	
				punishable.setPunishType(switchEvent.getPunishType());
				genericChallenge.setAround();
				genericChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());			
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		//System.out.println("From Map: " + gui.punishmentChallengeTypeOpenGUI.get(p.getUniqueId()));
		//System.out.println("Raw Type: " + rawType);
		gui.createGUI(p, GUIType.PUNISHMENT.getGoBack(), gui.punishmentChallengeTypeOpenGUI.get(p.getUniqueId()), rawType);
		//gui.punishmentChallengeTypeOpenGUI.remove(p.getUniqueId());
	}
	
	default <T extends GenericChallenge & Punishable> void callSettingsChangePunishableEventAndActUpon(T rawType, PunishType type, Player p, int indexClicked, GUI gui, Challenges plugin) {
		GenericChallenge genericChallenge = (GenericChallenge) rawType;
		
		if(rawType.getClass().equals(MLGChallenge.class)) {
			MLGChallenge mlgChallenge = (MLGChallenge) rawType;			
			long timeToMLG = ThreadLocalRandom.current()
					.nextLong(mlgChallenge.getEarliest()*20, (mlgChallenge.getLatest()+1)*20);
			MLGChallengeStatusSwitchEvent switchEvent = new MLGChallengeStatusSwitchEvent(mlgChallenge, timeToMLG, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				mlgChallenge.setPunishType(switchEvent.getPunishType());
				mlgChallenge.setAround();
				mlgChallenge.setEarliest(switchEvent.getEarliest());
				mlgChallenge.setLatest(switchEvent.getLatest());
				mlgChallenge.setHeight(switchEvent.getHeight());
				mlgChallenge.setTimer(new MLGTimer(plugin, switchEvent.getTimeToNextMLG(), switchEvent.getTimeToNextMLG()));
				mlgChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());	
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		else if(rawType.getClass().equals(OnBlockChallenge.class)) {
			OnBlockChallenge onBlockChallenge = (OnBlockChallenge) rawType;
			long timeToBlockShown = ThreadLocalRandom.current()
					.nextLong(onBlockChallenge.getEarliestToShow(), (onBlockChallenge.getLatestToShow()+1));
			ForceBlockChallengeStatusSwitchEvent switchEvent = new ForceBlockChallengeStatusSwitchEvent(onBlockChallenge, timeToBlockShown, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				onBlockChallenge.setAround();
				onBlockChallenge.setPunishType(switchEvent.getPunishType());
				onBlockChallenge.setEarliestToShow(switchEvent.getEarliestToShow());
				onBlockChallenge.setLatestToShow(switchEvent.getLatestToShow());
				onBlockChallenge.setEarliestOnBlock(switchEvent.getEarliestOnBlock());
				onBlockChallenge.setLatestOnBlock(switchEvent.getLatestOnBlock());
				onBlockChallenge.setTimer(new OnBlockTimer(plugin, onBlockChallenge, switchEvent.getTimeToBlockShown(), switchEvent.getTimeToBlockShown(), true));
				ChallengeProfile.getInstance().getParticipants().forEach(player -> onBlockChallenge.addPlayerToBossBar(player));
				onBlockChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());	
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		else if(rawType.getClass().equals(HeightChallenge.class)) {
			HeightChallenge heightChallenge = (HeightChallenge) rawType;
			long timeToHeightShown = ThreadLocalRandom.current()
					.nextLong(heightChallenge.getEarliestToShow(), (heightChallenge.getLatestToShow()+1));
			HeightChallengeStatusSwitchEvent switchEvent = new HeightChallengeStatusSwitchEvent(heightChallenge, timeToHeightShown, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				heightChallenge.setAround();
				heightChallenge.setPunishType(switchEvent.getPunishType());
				heightChallenge.setEarliestToShow(switchEvent.getEarliestToShow());
				heightChallenge.setLatestToShow(switchEvent.getLatestToShow());
				heightChallenge.setEarliestToBeOnHeight(switchEvent.getEarliestToBeOnHeight());
				heightChallenge.setLatestToBeOnHeight(switchEvent.getLatestToBeOnHeight());
				heightChallenge.setTimer(new HeightTimer(plugin, heightChallenge, switchEvent.getTimeToHeightShown(), switchEvent.getTimeToHeightShown(), true));
				ChallengeProfile.getInstance().getParticipants().forEach(player -> heightChallenge.addPlayerToBossBar(player));
				heightChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());	
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		else {
			PunishableChallengeStatusSwitchEvent<T> switchEvent = new PunishableChallengeStatusSwitchEvent<T>(rawType, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				Punishable punishable = (Punishable) rawType;	
				punishable.setPunishType(switchEvent.getPunishType());
				genericChallenge.setAround();
				genericChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());			
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		//System.out.println("From Map: " + gui.punishmentChallengeTypeOpenGUI.get(p.getUniqueId()));
		//System.out.println("Raw Type: " + rawType);
		gui.createGUI(p, GUIType.PUNISHMENT.getGoBack(), indexClicked, rawType);
		//gui.punishmentChallengeTypeOpenGUI.remove(p.getUniqueId());
	}
	
	default void callSettingsChangeCustomHealthEventAndActUpon(CustomHealthChallenge customHealthChallenge, int amount, Player p, int indexClicked, GUI gui) {
		CustomHealthChallengeStatusSwitchEvent switchEvent = new CustomHealthChallengeStatusSwitchEvent(customHealthChallenge, amount, p);
		Bukkit.getServer().getPluginManager().callEvent(switchEvent);
		if(!switchEvent.isCancelled()) {
			customHealthChallenge.setAround();
			customHealthChallenge.setAmount(switchEvent.getCustomHP());
			customHealthChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());
		}
		else {
			if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());		
		}
		if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		gui.createGUI(p, GUIType.OVERVIEW, indexClicked, customHealthChallenge);
	}
	
	default void callSettingsChangeRandomChallengeEventAndActUpon(RandomChallenge randomChallenge, HashMap<Material, Material> randomizedMapped, Player p, int indexClicked, GUI gui) {
		RandomChallengeStatusSwitchEvent<RandomChallenge> switchEvent = new RandomChallengeStatusSwitchEvent<RandomChallenge>(randomChallenge, randomizedMapped, p);
		Bukkit.getServer().getPluginManager().callEvent(switchEvent);
		if(!switchEvent.isCancelled()) {
			randomChallenge.setAround();
			randomChallenge.setRandomizedMapped(Maps.newLinkedHashMap(switchEvent.getRandomizedMapped()));
			randomChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());
			if(!randomChallenge.isActive()) RandomChallenge.clearRandomizationIfCase();
		}
		else {
			if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());	
		}
		if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		gui.createGUI(p, GUIType.OVERVIEW, indexClicked, randomChallenge);
	}
	
	default void callSettingsChangeItemCollectionLimitGlobalChallengeEventAndActUpon(ItemCollectionLimitGlobalChallenge iCLGChallenge, int limit, Player p, int indexClicked, GUI gui) {
		ItemCollectionLimitGlobalChallengeStatusSwitchEvent switchEvent = new ItemCollectionLimitGlobalChallengeStatusSwitchEvent(iCLGChallenge, limit, p);
		Bukkit.getServer().getPluginManager().callEvent(switchEvent);
		if(!switchEvent.isCancelled()) {
			iCLGChallenge.setAround();
			iCLGChallenge.setLimit(switchEvent.getLimit());
			iCLGChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipants());
			/*if(!iCLGChallenge.isActive()) {
				iCLGChallenge.setCurrentAmount(0);
				iCLGChallenge.setLimit(0);
				iCLGChallenge.getUniqueItems().clear();
			}*/
		}
		else {
			if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());
		}
		if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		gui.createGUI(p, GUIType.OVERVIEW, indexClicked, iCLGChallenge);
	}
}
