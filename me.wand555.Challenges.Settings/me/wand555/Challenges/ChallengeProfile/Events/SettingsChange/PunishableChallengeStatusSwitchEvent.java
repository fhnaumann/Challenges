package me.wand555.Challenges.ChallengeProfile.Events.SettingsChange;

import org.bukkit.entity.Player;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.Punishable;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGChallenge;
import me.wand555.Challenges.ChallengeProfile.Events.Overridable;
/**
 * Called when a player switches a challenge setting that is {@link Punishable} and involves a {@link PunishType}.
 * You don't need to fill in T when listening to this event.
 * 
 * @author wand555
 *
 * @param <T> The challenge this was called with (T extends GenericChallenge and T extends Punishable).
 */
public class PunishableChallengeStatusSwitchEvent<T> extends ChallengeStatusSwitchEvent<T> implements Overridable {

	private PunishType punishType;
	private String overrideMessage;
	
	/**
	 * 
	 * @param challenge The challenge that triggered this event.
	 * @param punishType The punishType the player selected.
	 * @param player The player who changed the setting.
	 */
	public PunishableChallengeStatusSwitchEvent(T challenge, PunishType punishType, Player player) {
		super(challenge, player);
		this.punishType = punishType;
	}
	
	/**
	 * Sets the punishType. This will override the input by the player.
	 * @param punishType
	 * @see PunishableChallengeStatusSwitchEvent#getPunishType()
	 * @see Overridable#setOverrideMessage(String)
	 */
	public void setPunishType(PunishType punishType) {
		this.punishType = punishType;
	}
	
	/**
	 * Gets the punishType. 
	 * @return the punishType
	 */
	public PunishType getPunishType() {
		return punishType;
	}
	
	@Override
	public String getOverrideMessage() {
		return overrideMessage;
	}
	
	@Override
	public void setOverrideMessage(String overrideMessage) {
		this.overrideMessage = overrideMessage;
	}
	
	@Override
	public boolean hasOverrideMessage() {
		return overrideMessage != null && !overrideMessage.isEmpty();
	}

}
