package net.piemaster.jario.components;

import com.artemis.Component;

public abstract class Vector2f extends Component
{
	private float x;
	private float y;

	public Vector2f()
	{
	}

	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(float x, float y, boolean onGround)
	{
		this.x = x;
		this.y = y;
	}

	public float getAngle()
	{
		return (float) Math.atan2(y, x);
	}
	
	public float getAngleAsRadians()
	{
		return (float) Math.toRadians(getAngle());
	}
	
	public void reset()
	{
		x = y = 0;
	}
	
	public boolean isZero()
	{
		return x == 0 && y == 0;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public void addX(float x)
	{
		this.x += x;
	}

	public void addY(float y)
	{
		this.y += y;
	}
}
