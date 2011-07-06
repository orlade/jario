package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class Coin extends GenericImage
{
	private static final String IMAGE_PATH = "assets/images/coin.png";
	
	public Coin(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
