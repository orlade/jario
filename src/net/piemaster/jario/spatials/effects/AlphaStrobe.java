package net.piemaster.jario.spatials.effects;

public class AlphaStrobe implements SpatialEffect
{
	private int period;
	private int time;
	private boolean on;
	
	private float minAlpha;
	private float maxAlpha;
	
	public AlphaStrobe(int period)
	{
		this.period = period;
		this.minAlpha = 0;
		this.maxAlpha = 1;
	}

	public AlphaStrobe(int period, float minAlpha, float maxAlpha)
	{
		this.period = period;
		this.minAlpha = minAlpha;
		this.maxAlpha = maxAlpha;
	}
	
	@Override
	public void update(int delta)
	{
		time += delta;
		if(time > period)
		{
			time %= period;
			on = !on;
		}
	}

	@Override
	public float getAlpha()
	{
		return on ? maxAlpha : minAlpha;
	}

	@Override
	public float getScaleX()
	{
		return 1;
	}

	@Override
	public float getScaleY()
	{
		return 1;
	}

	public int getPeriod()
	{
		return period;
	}

	public void setPeriod(int period)
	{
		this.period = period;
	}

	public boolean isOn()
	{
		return on;
	}

	public void setOn(boolean on)
	{
		this.on = on;
	}

	public float getMinAlpha()
	{
		return minAlpha;
	}

	public void setMinAlpha(float minAlpha)
	{
		this.minAlpha = minAlpha;
	}

	public float getMaxAlpha()
	{
		return maxAlpha;
	}

	public void setMaxAlpha(float maxAlpha)
	{
		this.maxAlpha = maxAlpha;
	}
}
