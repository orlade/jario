package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class Fireball extends GenericImage
{
	private static final String IMAGE_PATH = "assets/images/fireball.png";
	
	public Fireball(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
