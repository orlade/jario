package net.piemaster.jario.components;

import com.artemis.Component;

public class Weapon extends Component
{
	private int shotBy;

	public Weapon()
	{
	}
	
	public Weapon(int shotBy)
	{
		this.shotBy = shotBy;
	}

	public int getShotBy()
	{
		return shotBy;
	}

	public void setShotBy(int shotBy)
	{
		this.shotBy = shotBy;
	}
}
