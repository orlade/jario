package net.piemaster.jario.components;

import com.artemis.Component;

public class Shell extends Component
{
	private float kickSpeed;
//	private float hurtSpeed;

	public Shell()
	{
		kickSpeed = 0.4f;
	}
	
	public Shell(float kickSpeed)
	{
		this.kickSpeed = kickSpeed;
	}
	
	public float getKickSpeed()
	{
		return kickSpeed;
	}

	public void setKickSpeed(float kickSpeed)
	{
		this.kickSpeed = kickSpeed;
	}
}
