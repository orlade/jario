package net.piemaster.jario.spatials;

import net.piemaster.jario.components.Transform;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.World;

public class GenericImage extends Spatial
{
	protected Transform transform;
	protected Image baseImage;
	protected Image flippedImage;
	
	protected Image currentImage;

	public GenericImage(World world, Entity owner)
	{
		super(world, owner);
	}
	
	public GenericImage(World world, Entity owner, String filename)
	{
		super(world, owner);
		
		try
		{
			baseImage = new Image(filename);
			baseImage.setCenterOfRotation(baseImage.getWidth()/2, baseImage.getHeight()/2);
			flippedImage = baseImage.getFlippedCopy(true, false);
			currentImage = baseImage;
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void initalize()
	{
		transform = owner.getComponent(Transform.class);
	}

	@Override
	public void render(Graphics g)
	{
		currentImage.setRotation(transform.getRotation());
		g.drawImage(currentImage, transform.getX(), transform.getY());
	}

	@Override
	public float getWidth()
	{
		if(currentImage != null)
		{
			return currentImage.getWidth();
		}
		return 0;
	}

	@Override
	public float getHeight()
	{
		if(currentImage != null)
		{
			return currentImage.getHeight();
		}
		return 0;
	}
}
