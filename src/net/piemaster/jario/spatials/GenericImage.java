package net.piemaster.jario.spatials;

import net.piemaster.jario.components.Transform;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.World;

public class GenericImage extends Spatial
{
	private Transform transform;
	private Image image;
	private String filename;

	public GenericImage(World world, Entity owner, String filename)
	{
		super(world, owner);
		this.filename = filename;
	}

	@Override
	public void initalize()
	{
		transform = owner.getComponent(Transform.class);
		
		try
		{
			image = new Image(filename);
			image.setCenterOfRotation(image.getWidth()/2, image.getHeight()/2);
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics g)
	{
		image.setRotation(transform.getRotation());
		g.drawImage(image, transform.getX(), transform.getY());
	}

	@Override
	public float getWidth()
	{
		if(image != null)
		{
			return image.getWidth();
		}
		return 0;
	}

	@Override
	public float getHeight()
	{
		if(image != null)
		{
			return image.getHeight();
		}
		return 0;
	}
}
