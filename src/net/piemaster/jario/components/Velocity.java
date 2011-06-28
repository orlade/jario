package net.piemaster.jario.components;

import com.artemis.Component;

public class Velocity extends Component
{
	private float velocity;
	private float angle;

	public Velocity()
	{
	}

	public Velocity(float vector)
	{
		this.velocity = vector;
	}

	public Velocity(float velocity, float angle)
	{
		this.velocity = velocity;
		this.angle = angle;
	}

	public float getVelocity()
	{
		return velocity;
	}

	public void setVelocity(float velocity)
	{
		this.velocity = velocity;
	}

	public void setAngle(float angle)
	{
		this.angle = angle;
	}

	public float getAngle()
	{
		return angle;
	}

	public void addAngle(float a)
	{
		angle = (angle + a) % 360;
	}

	public float getAngleAsRadians()
	{
		return (float) Math.toRadians(angle);
	}

}
