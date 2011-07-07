package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class Mushroom extends GenericImage
{
	private static final String IMAGE_PATH = "/images/mushroom.png";
	
	public Mushroom(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
