package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class Goomba extends GenericImage
{
	private static final String IMAGE_PATH = "assets/goomba.png";
	private static final float WIDTH = 48;
	private static final float HEIGHT = 33;
	
	public Goomba(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
	
	@Override
	public float getWidth()
	{
		return WIDTH;
	}
	
	@Override
	public float getHeight()
	{
		return HEIGHT;
	}
}
