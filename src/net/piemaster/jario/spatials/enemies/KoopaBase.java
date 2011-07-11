package net.piemaster.jario.spatials.enemies;

import net.piemaster.jario.spatials.generic.GenericFlipAnimation;

import com.artemis.Entity;
import com.artemis.World;

public class KoopaBase extends GenericFlipAnimation
{
	private static final String[] IMAGE_PATHS = {"/images/koopa.png", "/images/koopa_2.png"};

	public KoopaBase(World world, Entity e)
	{
		super(world, e, IMAGE_PATHS, 200);
	}
}
