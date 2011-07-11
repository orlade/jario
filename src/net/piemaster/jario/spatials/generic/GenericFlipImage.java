package net.piemaster.jario.spatials.generic;

import net.piemaster.jario.spatials.effects.SpatialEffect;

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
		
		super.render(g);
	}
	
	protected void determineCurrentImage()
	{
		if (transform.isFacingRight())
			currentImage = baseImage;
		else
			currentImage = flippedImage;
	}

	@Override
	public void applyEffect(SpatialEffect effect)
	{
		Image current = currentImage;
		Image nonCurrent = (currentImage == baseImage) ? flippedImage : baseImage;
		
		currentImage = nonCurrent;
		super.applyEffect(effect);
		currentImage = current;
		super.applyEffect(effect);
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
