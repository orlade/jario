package net.piemaster.jario.spatials.player;

import net.piemaster.jario.spatials.generic.GenericFlipImage;

import com.artemis.Entity;
import com.artemis.World;

public class PlayerBig extends GenericFlipImage
{
	private static final String IMAGE_PATH = "/images/jar_big.png";

	public PlayerBig(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
