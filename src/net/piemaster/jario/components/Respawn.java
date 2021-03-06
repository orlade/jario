package net.piemaster.jario.components;


public class Respawn extends Expires
{
	private float respawnX;
	private float respawnY;
	
	public Respawn(int initTime)
	{
		this(initTime, 0, 0);
	}

	public Respawn(int initTime, float respawnX, float respawnY)
	{
		super(initTime);
		
		this.respawnX = respawnX;
		this.respawnY = respawnY;
	}

	public float getRespawnX()
	{
		return respawnX;
	}

	public float getRespawnY()
	{
		return respawnY;
	}

	public void setRespawnX(float respawnX)
	{
		this.respawnX = respawnX;
	}

	public void setRespawnY(float respawnY)
	{
		this.respawnY = respawnY;
	}
	
	public void setRespawnLocation(float x, float y)
	{
		this.respawnX = x;
		this.respawnY = y;
	}
}
