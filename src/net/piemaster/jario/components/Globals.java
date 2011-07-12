package net.piemaster.jario.components;

import com.artemis.Component;


public class Globals extends Component
{
	private float gravity = 0.003f;
	private float friction = 0.01f;

	public float getGravity()
	{
		return gravity;
	}

	public void setGravity(float gravity)
	{
		this.gravity = gravity;
	}

	public float getFriction()
	{
		return friction;
	}

	public void setFriction(float friction)
	{
		this.friction = friction;
	}
}
