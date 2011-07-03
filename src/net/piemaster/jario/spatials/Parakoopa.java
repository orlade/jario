package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class Parakoopa extends GenericImage
{
	private static final String IMAGE_PATH = "assets/images/parakoopa.png";
	
	public Parakoopa(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
