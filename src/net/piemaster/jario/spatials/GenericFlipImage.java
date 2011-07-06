package net.piemaster.jario.spatials;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.artemis.Entity;
import com.artemis.World;

public class GenericFlipImage extends GenericImage
{
	protected Image flippedImage;

	public GenericFlipImage(World world, Entity owner)
	{
		super(world, owner);
	}
	
	public GenericFlipImage(World world, Entity owner, String filename)
	{
		super(world, owner, filename);

		flippedImage = baseImage.getFlippedCopy(true, false);
		flippedImage.setCenterOfRotation(flippedImage.getWidth()/2, flippedImage.getHeight()/2);
	}

	@Override
	public void render(Graphics g)
	{
		determineCurrentImage();
		
		currentImage.setRotation(transform.getRotation());
		g.drawImage(currentImage, transform.getX(), transform.getY());
	}
	
	protected void determineCurrentImage()
	{
		if (transform.isFacingRight())
			currentImage = baseImage;
		else
			currentImage = flippedImage;
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
