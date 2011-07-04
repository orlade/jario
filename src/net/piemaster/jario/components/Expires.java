package net.piemaster.jario.components;

import com.artemis.Component;

public class Expires extends Component
{
	private int lifetime;
	private int remaining;
	private boolean active;

	public Expires(int lifeTime)
	{
		this.remaining = this.lifetime = lifeTime;
	}

	public int getLifetime()
	{
		return lifetime;
	}

	public void setLifetime(int lifetime)
	{
		this.lifetime = lifetime;
	}

	public void reduceLife(int lifetime)
	{
		this.remaining -= lifetime;
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
		this.setRemaining(lifetime);
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}
}
