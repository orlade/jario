package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class Flower extends GenericImage
{
	private static final String IMAGE_PATH = "assets/images/flower.png";
	
	public Flower(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
