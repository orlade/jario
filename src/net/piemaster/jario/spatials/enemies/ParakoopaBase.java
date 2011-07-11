package net.piemaster.jario.spatials.enemies;

import net.piemaster.jario.spatials.generic.GenericFlipAnimation;

import com.artemis.Entity;
import com.artemis.World;

public class ParakoopaBase extends GenericFlipAnimation
{
	private static final String[] IMAGE_PATHS = {"/images/parakoopa.png", "/images/parakoopa_2.png"};
	
	public ParakoopaBase(World world, Entity e)
	{
		super(world, e, IMAGE_PATHS, 200);
	}
}
