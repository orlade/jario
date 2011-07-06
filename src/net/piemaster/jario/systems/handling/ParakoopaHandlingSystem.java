package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Parakoopa;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Entity;

public class ParakoopaHandlingSystem extends EnemyHandlingSystem
{
	@SuppressWarnings("unchecked")
	public ParakoopaHandlingSystem()
	{
		super(Parakoopa.class, Collisions.class);
	}

	@Override
	public void initialize()
	{
		super.initialize();
	}

	@Override
	protected void handlePlayerCollision(Entity parakoopa, Entity player, EdgeType edge)
	{
		super.handlePlayerCollision(parakoopa, player, edge);
		
		Health health = healthMapper.get(parakoopa);
		if(health.getHealth() < health.getMaximumHealth())
		{
			physicalMapper.get(parakoopa).setBouncyVertical(false);
		}
	}
}
