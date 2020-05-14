package me.wand555.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.GUI.Holders.BackpackHolder;
import me.wand555.GUI.Holders.PunishmentHolder;
import me.wand555.GUI.Holders.SettingsHolder;

public class InventoryManager {

	private static InventoryManager inventoryManager;
	
	private final Object[] slotToChallenge = initializeSlotToChallenge();
	
	private SettingsHolder settingsHolder;
	private PunishmentHolder punishmentHolder;
	private Inventory alreadyCollectedGUI;
	private BackpackHolder backpackHolder;
	
	public static final int SETTINGS_GUI_SIZE = 27;
	public static final int PUNISHMENT_GUI_SIZE = 36;
	public static final int ALREADY_COLLECTED_GUI_SIZE = 54;
	public static final int BACKPACK_GUI_SIZE = 27;
	
	private Object[] initializeSlotToChallenge() {	
		Object[] slotToChallenge = new Object[SETTINGS_GUI_SIZE];
		slotToChallenge[0] = GenericChallenge.getChallenge(ChallengeType.END_ON_DEATH);
		slotToChallenge[1] = GenericChallenge.getChallenge(ChallengeType.NETHER_FORTRESS_SPAWN);
		slotToChallenge[2] = GenericChallenge.getChallenge(ChallengeType.NO_DAMAGE);
		slotToChallenge[3] = GenericChallenge.getChallenge(ChallengeType.NO_REG);
		slotToChallenge[4] = GenericChallenge.getChallenge(ChallengeType.NO_REG_HARD);
		slotToChallenge[5] = GenericChallenge.getChallenge(ChallengeType.CUSTOM_HEALTH);
		slotToChallenge[6] = GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH);
		slotToChallenge[7] = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_PLACING);
		slotToChallenge[8] = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_BREAKING);
		slotToChallenge[9] = GenericChallenge.getChallenge(ChallengeType.NO_CRAFTING);
		slotToChallenge[10] = GenericChallenge.getChallenge(ChallengeType.NO_SNEAKING);
		slotToChallenge[11] = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_BLOCK_DROPS);
		slotToChallenge[12] = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_MOB_DROPS);
		slotToChallenge[13] = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_CRAFTING);
		slotToChallenge[14] = GenericChallenge.getChallenge(ChallengeType.MLG);
		slotToChallenge[15] = GenericChallenge.getChallenge(ChallengeType.ON_BLOCK);
		slotToChallenge[16] = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
		slotToChallenge[17] = GenericChallenge.getChallenge(ChallengeType.NO_SAME_ITEM);
		slotToChallenge[18] = GenericChallenge.getChallenge(ChallengeType.GROUND_IS_LAVA);
		slotToChallenge[19] = GenericChallenge.getChallenge(ChallengeType.BE_AT_HEIGHT);
		return slotToChallenge;
	}
	
	InventoryManager() {}
	
	public static InventoryManager getInventoryManager() {
		if(inventoryManager == null) inventoryManager = new InventoryManager();
		return inventoryManager;
	}
	
	/**
	 * @return the alreadyCollectedGUI
	 */
	public Inventory getAlreadyCollectedGUI() {
		if(alreadyCollectedGUI == null) 
			alreadyCollectedGUI = Bukkit.getServer().createInventory(null, ALREADY_COLLECTED_GUI_SIZE, ChatColor.DARK_GREEN + "Collected Items");
		return alreadyCollectedGUI;
	}

	/**
	 * @param alreadyCollectedGUI the alreadyCollectedGUI to set
	 */
	public void setAlreadyCollectedGUI(Inventory alreadyCollectedGUI) {
		this.alreadyCollectedGUI = alreadyCollectedGUI;
	}

	/**
	 * @return the punishmentGUI
	 */
	public Inventory getPunishmentGUI() {
		if(punishmentHolder == null)
			punishmentHolder = new PunishmentHolder();
		return punishmentHolder.getInventory();
	}

	/**
	 * @param punishmentGUI the punishmentGUI to set
	 */
	public void setPunishmentGUI(PunishmentHolder punishmentHolder) {
		this.punishmentHolder = punishmentHolder;
	}
	
	
	public <T extends GenericChallenge> void changeSetting(int index, T challenge) {
		if(index != 26) this.settingsHolder.changeSetting(index, challenge);
		else this.settingsHolder.getInventory().setItem(index, getBackpackItem());
	}

	/**
	 * @return the settingsGUI
	 */
	public Inventory getSettingsGUI() {
		if(settingsHolder == null)
			settingsHolder = new SettingsHolder();
		return settingsHolder.getInventory();
	}

	/**
	 * @param settingsGUI the settingsGUI to set
	 */
	public void setSettingsGUI(SettingsHolder settingsHolder) {
		this.settingsHolder = settingsHolder;
	}

	/**
	 * @return the backpackGUI
	 */
	public Inventory getBackpackGUI() {
		if(backpackHolder == null)
			backpackHolder = new BackpackHolder();
		return backpackHolder.getInventory();
	}
	
	public ItemStack getBackpackItem() {
		return backpackHolder.createBackpackItem();
	}

	/**
	 * @param backpackGUI the backpackGUI to set
	 */
	public void setBackpackGUI(BackpackHolder backpackHolder) {
		this.backpackHolder = backpackHolder;
	}

	/**
	 * @return the slotToChallenge
	 */
	public Object[] getSlotToChallenge() {
		return slotToChallenge;
	}
}
