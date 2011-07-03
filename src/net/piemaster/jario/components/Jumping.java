package net.piemaster.jario.components;

import com.artemis.Component;


public class Jumping extends Component
{
	private float jumpFactor = 1;
	private boolean jumping;
	
	public Jumping()
	{
	}
	
	public Jumping(float factor)
	{
		this.jumpFactor = factor;
	}
	
	public float getJumpFactor()
	{
		return jumpFactor;
	}
	public void setJumpFactor(float jumpFactor)
	{
		this.jumpFactor = jumpFactor;
	}
	public boolean isJumping()
	{
		return jumping;
	}
	public void setJumping(boolean jumping)
	{
		this.jumping = jumping;
	}
}
