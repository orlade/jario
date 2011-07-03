package net.piemaster.jario.systems;

import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class BoundarySystem extends EntityProcessingSystem
{
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<SpatialForm> spatialMapper;
	private ComponentMapper<Health> healthMapper;
	
	private int boundsStartX;
	private int boundsStartY;
	private int boundsEndX;
	private int boundsEndY;

	@SuppressWarnings("unchecked")
	public BoundarySystem(int boundsStartX, int boundsStartY, int boundsEndX, int boundsEndY)
	{
		super(Transform.class, Player.class);

		this.boundsStartX = boundsStartX;
		this.boundsStartY = boundsStartY;
		this.boundsEndX = boundsEndX;
		this.boundsEndY = boundsEndY;
	}

	@Override
	public void initialize()
	{
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
		spatialMapper = new ComponentMapper<SpatialForm>(SpatialForm.class, world.getEntityManager());
		healthMapper = new ComponentMapper<Health>(Health.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Transform transform = transformMapper.get(e);
		SpatialForm spatial = spatialMapper.get(e);

		if (transform.getX() < boundsStartX)
			transform.setLocation(boundsStartX, transform.getY());
		else if (transform.getX() + spatial.getWidth() > boundsEndX)
			transform.setLocation(boundsEndX - spatial.getWidth(), transform.getY());

		if (transform.getY() < boundsStartY)
			transform.setLocation(transform.getX(), boundsStartY);
		else if (transform.getY() > boundsEndY)
			healthMapper.get(e).setHealth(0);
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
