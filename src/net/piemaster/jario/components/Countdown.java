package net.piemaster.jario.components;

import com.artemis.Component;

public class Countdown extends Component
{
	private int period;
	private int remaining;
	private boolean active;
	
	private String trigger;

	public Countdown(int period)
	{
		this.remaining = this.period = period;
	}

	public int getPeriod()
	{
		return period;
	}

	public void setPeriod(int period)
	{
		this.period = period;
		if(!active)
			remaining = period;
	}

	public void reduceTime(int time)
	{
		this.remaining -= time;
	}

	public boolean isExpired()
	{
		return remaining <= 0;
	}

	public int getRemaining()
	{
		return remaining;
	}

	public void setRemaining(int remaining)
	{
		this.remaining = remaining;
	}
	
	public void resetTimer()
	{
		this.setRemaining(period);
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public String getTrigger()
	{
		return trigger;
	}

	public void setTrigger(String trigger)
	{
		this.trigger = trigger;
	}
}
