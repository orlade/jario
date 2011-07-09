package net.piemaster.jario.spatials;

import net.piemaster.jario.spatials.effects.AlphaStrobe;

import com.artemis.Entity;
import com.artemis.World;

public class Star extends GenericFlipImage
{
	private static final String IMAGE_PATH = "/images/star.png";

	public Star(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);
	}

	@Override
	public void initalize()
	{
		super.initalize();
		addEffect(new AlphaStrobe(100, 0.3f, 1f));
	}
}
