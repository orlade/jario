package net.piemaster.jario.spatials.generic;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.artemis.Entity;
import com.artemis.World;

public class GenericFlipAnimation extends GenericAnimation
{
	private Animation flippedAnim;

	public GenericFlipAnimation(World world, Entity owner)
	{
		super(world, owner);
	}
	
	public GenericFlipAnimation(World world, Entity owner, String[] filenames, int duration)
	{
		super(world, owner, filenames, duration);
		
		Image[] flippedImages = new Image[baseAnim.getFrameCount()];
		
		for(int i = 0; i < baseAnim.getFrameCount(); ++i)
		{
			Image baseImage = baseAnim.getImage(i);
			flippedImages[i] = baseImage.getFlippedCopy(true, false);
		}
		
		flippedAnim = new Animation(flippedImages, duration, true);
	}

	@Override
	public void render(Graphics g)
	{
		determineCurrentAnim();
		
		super.render(g);
	}
	
	protected void determineCurrentAnim()
	{
		if (transform.isFacingRight())
			currentAnim = baseAnim;
		else
			currentAnim = flippedAnim;
	}
}
