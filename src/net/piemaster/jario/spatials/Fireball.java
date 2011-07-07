package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class Fireball extends GenericFlipImage
{
	private static final String IMAGE_PATH = "/images/fireball.png";
	
	public Fireball(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
