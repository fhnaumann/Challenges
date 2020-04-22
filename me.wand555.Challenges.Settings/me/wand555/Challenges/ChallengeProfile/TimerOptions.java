package me.wand555.Challenges.ChallengeProfile;

import me.wand555.Challenges.Timer.TimerOrder;

public interface TimerOptions {

	public void startTimer(TimerOrder order);
	public void pauseTimer();
	public void resumeTimer();
	public void endTimer();
	
	public void checkConditionsAndApply();
}
