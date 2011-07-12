package net.piemaster.jario.systems;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Physical;
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
		respawnMapper = new ComponentMapper<Respawn>(Respawn.class, world);
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
		healthMapper = new ComponentMapper<Health>(Health.class, world);
		spatialMapper = new ComponentMapper<SpatialForm>(SpatialForm.class,
				world);
	}

	@Override
	protected void process(Entity e)
	{
		Respawn respawn = respawnMapper.get(e);

		// If already respawning
		if (respawn.isActive())
		{
			respawn.reduceLife(world.getDelta());
			if (respawn.isExpired())
			{
				transformMapper.get(e).setLocation(respawn.getRespawnX(), respawn.getRespawnY());
				transformMapper.get(e).setRotation(0);
				healthMapper.get(e).resetHealth();
				healthMapper.get(e).setInvulnerable(false);
				spatialMapper.get(e).setVisible(true);
				e.getComponent(Physical.class).setHasFriction(true);

				CollisionMesh collMesh = e.getComponent(CollisionMesh.class);
				if (collMesh != null)
				{
					collMesh.setActive(true);
				}

				respawn.setActive(false);
				respawn.resetTimer();
			}
		}
		// Not respawning yet
		else
		{
			Health health = healthMapper.get(e);
			if (!health.isAlive())
			{
				respawn.resetTimer();
				respawn.setActive(true);
				
				if(e == world.getTagManager().getEntity("PLAYER"))
				{
					SoundSystem.pushSound(SoundSystem.FAIL_SOUND, e);
				}
			}
		}
	}
}
