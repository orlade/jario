package net.piemaster.jario.systems;

import net.piemaster.jario.components.Transform;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class BoundarySystem extends EntityProcessingSystem
{
	private ComponentMapper<Transform> transformMapper;
	private int boundsStartX;
	private int boundsStartY;
	private int boundsEndX;
	private int boundsEndY;

	public BoundarySystem(int boundsStartX, int boundsStartY, int boundsEndX, int boundsEndY)
	{
		super(Transform.class);

		this.boundsStartX = boundsStartX;
		this.boundsStartY = boundsStartY;
		this.boundsEndX = boundsEndX;
		this.boundsEndY = boundsEndY;
	}

	@Override
	public void initialize()
	{
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Transform transform = transformMapper.get(e);

		if (transform.getX() < boundsStartX)
			transform.setLocation(boundsStartX, transform.getY());// physics.applyForce(world.getDelta()
																	// * 300,
																	// 0);
		else if (transform.getX() > boundsEndX)
			transform.setLocation(boundsEndX, transform.getY());// physics.applyForce(world.getDelta()
		// * -300, 0);

		if (transform.getY() < boundsStartY)
			transform.setLocation(transform.getX(), boundsStartY);// physics.applyForce(0,
																	// world.getDelta()
																	// * 300);
		else if (transform.getY() > boundsEndY)
			transform.setLocation(transform.getX(), boundsEndY);// physics.applyForce(0,
		// world.getDelta()
		// * -300);
	}

	public int getBoundsEndX()
	{
		return boundsEndX;
	}

	public int getBoundsEndY()
	{
		return boundsEndY;
	}

	public int getBoundsStartX()
	{
		return boundsStartX;
	}

	public int getBoundsStartY()
	{
		return boundsStartY;
	}

	public int getBoundaryWidth()
	{
		return boundsEndX - boundsStartX;
	}

	public int getBoundaryHeight()
	{
		return boundsEndY - boundsStartY;
	}

}
