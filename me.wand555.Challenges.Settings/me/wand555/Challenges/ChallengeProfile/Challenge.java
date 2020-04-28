package me.wand555.Challenges.ChallengeProfile;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;

public interface Challenge {

	public void setActive(boolean active);
	public void setAround();
	public boolean isActive();
	public ChallengeType getChallengeType();
}
