package net.piemaster.jario.systems;

import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class CullingSystem extends EntityProcessingSystem
{
	private ComponentMapper<Health> healthMapper;
	private ComponentMapper<Transform> transMapper;
	private ComponentMapper<SpatialForm> spatialMapper;

	private CameraSystem cameraSystem;

	@SuppressWarnings("unchecked")
	public CullingSystem()
	{
		super(Transform.class, Velocity.class, SpatialForm.class);
	}

	@Override
	public void initialize()
	{
		healthMapper = new ComponentMapper<Health>(Health.class, world);
		transMapper = new ComponentMapper<Transform>(Transform.class, world);
		spatialMapper = new ComponentMapper<SpatialForm>(SpatialForm.class,
				world);

		cameraSystem = world.getSystemManager().getSystem(CameraSystem.class);
	}

	@Override
	protected void process(Entity e)
	{
		// Don't cull the player
		if (e == world.getTagManager().getEntity("PLAYER"))
			return;

		Health health = healthMapper.get(e);
		Transform t = transMapper.get(e);
		SpatialForm spatial = spatialMapper.get(e);

		// If dead and off the screen
		if ((health == null || !health.isAlive())
				&& (t.getX() + spatial.getWidth() < cameraSystem.getStartX() || t.getX() > cameraSystem
						.getEndX())
				|| (t.getY() + spatial.getHeight() < cameraSystem.getStartY() || t.getY() > cameraSystem
						.getEndY()))
		{
			world.deleteEntity(e);
		}
	}

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}

}
