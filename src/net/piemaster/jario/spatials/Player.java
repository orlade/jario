package net.piemaster.jario.spatials;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Health;

import org.newdawn.slick.Image;

import com.artemis.Entity;
import com.artemis.World;

public class Player extends GenericFlipImage
{
	private static final String IMAGE_PATH = "/images/jar.png";
	
	protected Health health;
	protected CollisionMesh mesh;

	protected Image bigImage;
	protected Image flippedBigImage;

	public Player(World world, Entity e)
	{
		super(world, e, IMAGE_PATH);

		bigImage = baseImage.getScaledCopy(baseImage.getWidth(), baseImage.getHeight()*2);
		bigImage.setCenterOfRotation(bigImage.getWidth()/2, bigImage.getHeight()/2);
		
		flippedBigImage = bigImage.getFlippedCopy(true, false);
		flippedBigImage.setCenterOfRotation(flippedBigImage.getWidth()/2, flippedBigImage.getHeight()/2);
	}
	
	@Override
	public void initalize()
	{
		super.initalize();

		health = owner.getComponent(Health.class);
		mesh = owner.getComponent(CollisionMesh.class);
	}

	@Override
	protected void determineCurrentImage()
	{
		// TODO Avoid duplicating this logic from PlayerHandlingSystem
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
		if(health.isInvulnerable())
			currentImage.setAlpha(0.5f);
		else
			currentImage.setAlpha(1);
	}
}
