package net.piemaster.jario.spatials.generic;

import net.piemaster.jario.components.Transform;
import net.piemaster.jario.loader.ImageLoader;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.World;

public class GenericAnimation extends Spatial
{
	protected Transform transform;
	protected Animation baseAnim;
	protected Animation currentAnim;

	public GenericAnimation(World world, Entity owner)
	{
		super(world, owner);
	}

	public GenericAnimation(World world, Entity owner, String[] filenames, int duration)
	{
		super(world, owner);
		
		try
		{
			Image[] images = new Image[filenames.length];
			
			for(int i = 0; i < filenames.length; ++i)
			{
				images[i] = ImageLoader.loadImage(filenames[i]);
				images[i].setCenterOfRotation(images[i].getWidth()/2, images[i].getHeight()/2);
			}
			
			baseAnim = new Animation(images, duration, true);
			currentAnim = baseAnim;
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
		currentAnim.draw(transform.getX(), transform.getY());
	}

	@Override
	public float getWidth()
	{
		if(currentAnim != null)
		{
			return currentAnim.getWidth();
		}
		return 0;
	}

	@Override
	public float getHeight()
	{
		if(currentAnim != null)
		{
			return currentAnim.getHeight();
		}
		return 0;
	}
}
