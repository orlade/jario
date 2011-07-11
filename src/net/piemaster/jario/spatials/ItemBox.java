package net.piemaster.jario.spatials;

import net.piemaster.jario.components.ItemDispenser;
import net.piemaster.jario.loader.ImageLoader;
import net.piemaster.jario.spatials.generic.GenericImage;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.World;

public class ItemBox extends GenericImage
{
	private static final String IMAGE_PATH = "/images/itembox.png";
	private static final String EMPTY_IMAGE_PATH = "/images/itembox_empty.png";
	
	private Image emptyImage;
	private ItemDispenser dispenser;
	
	public ItemBox(World world, Entity e)
	{
		super(world, e);
		
		try
		{
			baseImage = ImageLoader.loadImage(IMAGE_PATH);
			baseImage.setCenterOfRotation(baseImage.getWidth()/2, baseImage.getHeight()/2);
			emptyImage = ImageLoader.loadImage(EMPTY_IMAGE_PATH);
			emptyImage.setCenterOfRotation(emptyImage.getWidth()/2, emptyImage.getHeight()/2);
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
		
		dispenser = owner.getComponent(ItemDispenser.class);
	}
	
	@Override
	public void render(Graphics g)
	{
		if(dispenser.isEmpty())
		{
			currentImage = emptyImage;
		}
		super.render(g);
	}
}
