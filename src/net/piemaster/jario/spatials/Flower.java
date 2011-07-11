package net.piemaster.jario.spatials;

import net.piemaster.jario.spatials.generic.GenericImage;

import com.artemis.Entity;
import com.artemis.World;

public class Flower extends GenericImage
{
	private static final String IMAGE_PATH = "/images/flower.png";
	
	public Flower(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
