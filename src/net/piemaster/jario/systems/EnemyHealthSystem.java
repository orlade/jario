package net.piemaster.jario.systems;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Physical;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class EnemyHealthSystem extends EntityProcessingSystem
{
	private ComponentMapper<Health> healthMapper;

	@SuppressWarnings("unchecked")
	public EnemyHealthSystem()
	{
		super(Health.class, CollisionMesh.class);
	}

	@Override
	public void initialize()
	{
		healthMapper = new ComponentMapper<Health>(Health.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Health health = healthMapper.get(e);
		if(!health.isAlive())
		{
			e.getComponent(Physical.class).setGrounded(false);
			e.getComponent(CollisionMesh.class).setActive(false);
			world.getGroupManager().remove(e);
			e.refresh();
		}
	}

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}

}
