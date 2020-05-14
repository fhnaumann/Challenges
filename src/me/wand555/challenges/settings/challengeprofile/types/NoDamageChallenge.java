package me.wand555.challenges.settings.challengeprofile.types;

import java.util.ArrayList;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.challenges.settings.config.LanguageMessages;

public class NoDamageChallenge extends GenericChallenge implements Punishable, ReasonNotifiable {

	private PunishType punishType;
	
	public NoDamageChallenge() {
		super(ChallengeType.NO_DAMAGE);
		activeChallenges.put(ChallengeType.NO_DAMAGE, this);
	}
	
	@Override
	public ItemStack getDisplayItem() {
		return createPunishmentItem(Material.WITHER_ROSE, 
				LanguageMessages.guiNoDamageName, 
				new ArrayList<String>(LanguageMessages.guiNoDamageLore), 
				punishType,
				super.active);
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
	
	public String createNoDamageReasonMessage(String causerName, Entity damageDealtBy, DamageCause cause, double amount) {
		return LanguageMessages.violationNoDamage
				.replace("[PLAYER]", causerName)
				.replace("[AMOUNT]", Double.toString(amount))
				.replace("[PUNISHMENT]", getFittingPunishmentMessage2(getPunishType()))
				.replace("[REASON]", damageDealtBy == null ? 
						WordUtils.capitalize(cause.toString().toLowerCase().replace('_', ' '))
						: WordUtils.capitalize(damageDealtBy.getType().toString().toLowerCase().replace('_', ' ')));
	}
	
	public String createDamageLogMessage(String causerName, Entity damageDealtBy, DamageCause cause, double amount) {
		return LanguageMessages.logDamage
				.replace("[PLAYER]", causerName)
				.replace("[AMOUNT]", Double.toString(amount))
				.replace("[REASON]", damageDealtBy == null ? 
						WordUtils.capitalize(cause.toString().toLowerCase().replace('_', ' '))
						: WordUtils.capitalize(damageDealtBy.getType().toString().toLowerCase().replace('_', ' ')));
	}
}
