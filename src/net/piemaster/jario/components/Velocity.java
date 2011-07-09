package net.piemaster.jario.components;


public class Velocity extends Vector2f
{
	private Float maxX;
	private Float maxY;
	
	public Velocity()
	{
		super();
	}
	
	public Velocity(float x, float y)
	{
		super(x, y);
	}
	
	public Velocity(float x, float y, Float maxX, Float maxY)
	{
		super(x, y);
		
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	@Override
	public void setX(float x)
	{
		if(maxX != null && x > maxX)
		{
			x = maxX.floatValue();
		}
		super.setX(x);
	}
	
	@Override
	public void setY(float y)
	{
		if(maxY != null && y > maxY)
		{
			y = maxY.floatValue();
		}
		super.setY(y);
	}

	public Float getMaxX()
	{
		return maxX;
	}

	public void setMaxX(Float maxX)
	{
		this.maxX = maxX;
	}

	public Float getMaxY()
	{
		return maxY;
	}

	public void setMaxY(Float maxY)
	{
		this.maxY = maxY;
	}
}
