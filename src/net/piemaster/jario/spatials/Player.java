package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class Player extends GenericImage
{
	private static final String IMAGE_PATH = "assets/images/jar.png";
	
	public Player(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
