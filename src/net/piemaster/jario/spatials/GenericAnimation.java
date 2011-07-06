package net.piemaster.jario.spatials;

import net.piemaster.jario.components.Transform;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.World;

public class GenericAnimation extends Spatial
{
	private Transform transform;
	private Animation anim;
	private Animation flippedAnim;

	public GenericAnimation(World world, Entity owner, String[] filenames, int duration)
	{
		super(world, owner);
		
		try
		{
			Image[] images = new Image[filenames.length];
			Image[] flippedImages = new Image[filenames.length];
			
			for(int i = 0; i < filenames.length; ++i)
			{
				images[i] = new Image(filenames[i]);
				images[i].setCenterOfRotation(images[i].getWidth()/2, images[i].getHeight()/2);
				flippedImages[i] = images[i].getFlippedCopy(true, false);
			}
			
			anim = new Animation(images, duration, true);
			flippedAnim = new Animation(flippedImages, duration, true);
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
		if(transform.isFacingRight())
		{
			anim.draw(transform.getX(), transform.getY());
		}
		else
		{
			flippedAnim.draw(transform.getX(), transform.getY());
		}
	}

	@Override
	public float getWidth()
	{
		if(anim != null)
		{
			return anim.getWidth();
		}
		return 0;
	}

	@Override
	public float getHeight()
	{
		if(anim != null)
		{
			return anim.getHeight();
		}
		return 0;
	}
}
