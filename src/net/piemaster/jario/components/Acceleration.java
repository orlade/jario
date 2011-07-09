package net.piemaster.jario.components;

public class Acceleration extends Vector2f
{
	private float permanentX;
	private float permanentY;

	public Acceleration()
	{
		super();
	}

	public Acceleration(float x, float y, float permanentX, float permanentY)
	{
		super(x, y);
		this.permanentX = permanentX;
		this.permanentY = permanentY;
	}

	public Acceleration(float x, float y, boolean permanent)
	{
		super(x, y);

		if (permanent)
		{
			this.permanentX = x;
			this.permanentY = y;
		}
	}

	public float getPermanentX()
	{
		return permanentX;
	}

	public void setPermanentX(float permanentX)
	{
		this.permanentX = permanentX;
	}

	public float getPermanentY()
	{
		return permanentY;
	}

	public void setPermanentY(float permanentY)
	{
		this.permanentY = permanentY;
	}

	public void reverse()
	{
		reverse(true, true);
	}

	public void reverse(boolean reverseX, boolean reverseY)
	{
		if (reverseX)
			permanentX = -permanentX;
		if (reverseY)
			permanentY = -permanentY;
		reset();
	}

	public void reset()
	{
		setX(permanentX);
		setY(permanentY);
	}
}
