package net.piemaster.jario.spatials;

import net.piemaster.jario.spatials.generic.GenericImage;

import com.artemis.Entity;
import com.artemis.World;

public class Coin extends GenericImage
{
	private static final String IMAGE_PATH = "/images/coin.png";
	
	public Coin(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
