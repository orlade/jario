package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class Parakoopa extends GenericAnimation
{
	private static final String[] IMAGE_PATHS = {"/images/parakoopa.png", "/images/parakoopa_2.png"};
	
	public Parakoopa(World world, Entity e)
	{
		super(world, e, IMAGE_PATHS, 200);
	}
}
