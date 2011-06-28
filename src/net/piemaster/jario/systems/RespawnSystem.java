package net.piemaster.jario.systems;

import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Respawn;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class RespawnSystem extends EntityProcessingSystem
{
	private ComponentMapper<Respawn> respawnMapper;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Health> healthMapper;
	private ComponentMapper<SpatialForm> spatialMapper;

	@SuppressWarnings("unchecked")
	public RespawnSystem()
	{
		super(Respawn.class, Transform.class, Health.class, SpatialForm.class);
	}

	@Override
	public void initialize()
	{
		respawnMapper = new ComponentMapper<Respawn>(Respawn.class, world.getEntityManager());
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
		healthMapper = new ComponentMapper<Health>(Health.class, world.getEntityManager());
		spatialMapper = new ComponentMapper<SpatialForm>(SpatialForm.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Respawn respawn = respawnMapper.get(e);
		
		if (respawn.isActive())
		{
			respawn.reduceLifeTime(world.getDelta());
			if(respawn.isExpired())
			{
				transformMapper.get(e).setLocation(respawn.getRespawnX(), respawn.getRespawnY());
				transformMapper.get(e).setRotation(0);
				healthMapper.get(e).resetHealth();
				spatialMapper.get(e).setVisible(true);
				respawn.setActive(false);
				respawn.resetTimer();
			}
		}

	}
}
