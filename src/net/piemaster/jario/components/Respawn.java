package net.piemaster.jario.components;


public class Respawn extends Expires
{
	private int initTime;
	private int respawnX;
	private int respawnY;
	private boolean active;
	
	public Respawn(int initTime)
	{
		this(initTime, 0, 0);
	}

	public Respawn(int initTime, int respawnX, int respawnY)
	{
		super(initTime);
		
		this.initTime = initTime;
		this.respawnX = respawnX;
		this.respawnY = respawnY;
	}

	public int getRespawnX()
	{
		return respawnX;
	}

	public int getRespawnY()
	{
		return respawnY;
	}

	public void setRespawnX(int respawnX)
	{
		this.respawnX = respawnX;
	}

	public void setRespawnY(int respawnY)
	{
		this.respawnY = respawnY;
	}
	
	public void setRespawnLocation(int x, int y)
	{
		this.respawnX = x;
		this.respawnY = y;
	}
	
	public void resetTimer()
	{
		this.setLifeTime(initTime);
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
