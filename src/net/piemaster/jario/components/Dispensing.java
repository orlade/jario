package net.piemaster.jario.components;

public class Dispensing extends Expires
{
	private static final int DEFAULT_LIFE = 1000;

	private float outVelocityX;
	private float outVelocityY;

	public Dispensing()
	{
		super(DEFAULT_LIFE);
	}

	public Dispensing(int duration)
	{
		super(duration);
	}

	public Dispensing(float outVelocityX, float outVelocityY)
	{
		super(DEFAULT_LIFE);

		this.outVelocityX = outVelocityX;
		this.outVelocityY = outVelocityY;
	}

	public Dispensing(int duration, float outVelocityX, float outVelocityY)
	{
		this(duration);

		this.outVelocityX = outVelocityX;
		this.outVelocityY = outVelocityY;
	}

	public float getOutVelocityX()
	{
		return outVelocityX;
	}

	public void setOutVelocityX(float outVelocityX)
	{
		this.outVelocityX = outVelocityX;
	}

	public float getOutVelocityY()
	{
		return outVelocityY;
	}

	public void setOutVelocityY(float outVelocityY)
	{
		this.outVelocityY = outVelocityY;
	}

	public static int getDefaultLife()
	{
		return DEFAULT_LIFE;
	}
}
