package net.piemaster.jario.spatials;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Health;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.artemis.Entity;
import com.artemis.World;

public class Player extends GenericImage
{
	private static final String IMAGE_PATH = "assets/images/jar.png";
	
	protected Health health;
	protected CollisionMesh mesh;

	protected Image bigImage;
	protected Image flippedBigImage;

	public Player(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);

		// TODO Avoid duplicating this logic from PlayerHandlingSystem
		bigImage = baseImage.getScaledCopy(baseImage.getWidth(), baseImage.getHeight()*2);
		flippedBigImage = bigImage.getFlippedCopy(true, false);
	}
	
	@Override
	public void initalize()
	{
		super.initalize();

		health = owner.getComponent(Health.class);
		mesh = owner.getComponent(CollisionMesh.class);
	}

	@Override
	public void render(Graphics g)
	{
		if(health.getHealth() > 1)
		{
			if (transform.isFacingRight())
				currentImage = bigImage;
			else
				currentImage = flippedBigImage;
		}
		else
		{
			if (transform.isFacingRight())
				currentImage = baseImage;
			else
				currentImage = flippedImage;
		}

		super.render(g);
	}
}
