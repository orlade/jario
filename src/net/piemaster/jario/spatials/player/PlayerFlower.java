package net.piemaster.jario.spatials.player;

import net.piemaster.jario.spatials.generic.GenericFlipImage;

import com.artemis.Entity;
import com.artemis.World;

public class PlayerFlower extends GenericFlipImage
{
	private static final String IMAGE_PATH = "/images/jar_flower.png";
	
	public PlayerFlower(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
