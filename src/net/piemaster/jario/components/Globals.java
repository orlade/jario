package net.piemaster.jario.components;

import com.artemis.Component;


public class Globals extends Component
{
	private float gravity = 0.003f;

	public float getGravity()
	{
		return gravity;
	}

	public void setGravity(float gravity)
	{
		this.gravity = gravity;
	}
}
