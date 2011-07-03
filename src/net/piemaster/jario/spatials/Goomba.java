package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class Goomba extends GenericImage
{
	private static final String IMAGE_PATH = "assets/images/goomba.png";
	
	public Goomba(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
