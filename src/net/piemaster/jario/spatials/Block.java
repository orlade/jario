package net.piemaster.jario.spatials;

import net.piemaster.jario.loader.ImageLoader;
import net.piemaster.jario.spatials.generic.GenericImage;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.World;

public class Block extends GenericImage
{
	private static final String IMAGE_PATH = "/images/block.png";
	
	public Block(World world, Entity e)
	{
		super(world, e);
		
		try
		{
			baseImage = ImageLoader.loadImage(IMAGE_PATH);
			baseImage.setCenterOfRotation(baseImage.getWidth()/2, baseImage.getHeight()/2);
			currentImage = baseImage;
		}
		catch (SlickException ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Override
	public void initalize()
	{
		super.initalize();
	}
	
	@Override
	public void render(Graphics g)
	{
		super.render(g);
	}
}
