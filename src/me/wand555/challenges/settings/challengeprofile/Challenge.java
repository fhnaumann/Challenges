package me.wand555.challenges.settings.challengeprofile;

import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;

public interface Challenge {

	public void setActive(boolean active);
	public void setAround();
	public boolean isActive();
	public ChallengeType getChallengeType();
}
