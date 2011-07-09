package net.piemaster.jario.spatials.effects;

public class NullEffect implements SpatialEffect
{
	public NullEffect()
	{
	}

	@Override
	public void update(int delta)
	{
	}

	@Override
	public float getAlpha()
	{
		return 1;
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
}
