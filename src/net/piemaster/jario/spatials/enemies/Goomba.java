package net.piemaster.jario.spatials.enemies;

import net.piemaster.jario.spatials.generic.GenericFlipAnimation;

import com.artemis.Entity;
import com.artemis.World;

public class Goomba extends GenericFlipAnimation
{
	private static final String[] IMAGE_PATHS = {"/images/goomba.png", "/images/goomba_2.png"};
	
	public Goomba(World world, Entity e)
	{
		super(world, e, IMAGE_PATHS, 200);
	}
}
