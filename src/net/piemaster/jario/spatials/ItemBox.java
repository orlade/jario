package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class ItemBox extends GenericImage
{
	private static final String IMAGE_PATH = "assets/images/itembox.png";
	
	public ItemBox(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
