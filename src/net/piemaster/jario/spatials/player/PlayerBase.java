package net.piemaster.jario.spatials.player;

import net.piemaster.jario.spatials.GenericFlipImage;

import com.artemis.Entity;
import com.artemis.World;

public class PlayerBase extends GenericFlipImage
{
	private static final String IMAGE_PATH = "/images/jar.png";

	public PlayerBase(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
