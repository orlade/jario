package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Parakoopa;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.entities.EntityFactory;

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

		// If health has been lost, remove bounciness
		Health health = healthMapper.get(e);
		if(health.getHealth() < health.getMaximumHealth())
		{
			physicalMapper.get(e).setBouncyVertical(false);
		}
		// If dead, convert to shell
		if(!health.isAlive())
		{
			world.deleteEntity(e);
			Transform t = transformMapper.get(e);
			EntityFactory.createShell(world, t.getX(), t.getY()).refresh();
		}
	}
}
