package net.piemaster.jario.spatials.effects;

public interface SpatialEffect
{
	public void update(int delta);
	
	public float getAlpha();
	
	public float getScaleX();
	public float getScaleY();
}
