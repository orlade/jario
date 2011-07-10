package net.piemaster.jario.spatials;

import com.artemis.Entity;
import com.artemis.World;

public class Shell extends GenericImage
{
	private static final String IMAGE_PATH = "/images/shell.png";
	
	public Shell(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}
}
