package me.wand555.challenges.settings.challengeprofile;

import me.wand555.challenges.settings.timer.TimerOrder;

public interface TimerOptions {

	public void startTimer(TimerOrder order);
	public void pauseTimer();
	public void resumeTimer();
	public void endTimer();
	
	public void checkConditionsAndApply();
}
