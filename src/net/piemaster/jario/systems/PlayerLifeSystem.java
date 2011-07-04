package net.piemaster.jario.systems;

import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Respawn;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class PlayerLifeSystem extends EntityProcessingSystem
{
	private ComponentMapper<Health> healthMapper;

	@SuppressWarnings("unchecked")
	public PlayerLifeSystem()
	{
		super(Player.class, Health.class, Transform.class, Velocity.class, SpatialForm.class, Respawn.class);
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
			Respawn respawn = e.getComponent(Respawn.class);
			if(!respawn.isActive())
			{
				respawn.resetTimer();
				respawn.setActive(true);
			}
		}
	}

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}

}
