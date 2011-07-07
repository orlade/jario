package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Parakoopa;

import com.artemis.Entity;

public class ParakoopaHandlingSystem extends EnemyHandlingSystem
{
	@SuppressWarnings("unchecked")
	public ParakoopaHandlingSystem()
	{
		super(Parakoopa.class, Collisions.class);
	}
	
	@Override
	protected void process(Entity e)
	{
		super.process(e);

		Health health = healthMapper.get(e);
		if(health.getHealth() < health.getMaximumHealth())
		{
			physicalMapper.get(e).setBouncyVertical(false);
			if(velocityMapper.get(e).getY() < 0)
				haltVertical(e);
		}
	}
}
