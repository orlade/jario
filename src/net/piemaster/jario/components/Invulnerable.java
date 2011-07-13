package net.piemaster.jario.components;

import com.artemis.Component;

public class Invulnerable extends Component
{
	private boolean deadly;
	
	public Invulnerable(boolean deadly)
	{
		this.deadly = deadly;
	}
	
	public Invulnerable()
	{
		this(false);
	}

	public boolean isDeadly()
	{
		return deadly;
	}

	public void setDeadly(boolean deadly)
	{
		this.deadly = deadly;
	}
}
