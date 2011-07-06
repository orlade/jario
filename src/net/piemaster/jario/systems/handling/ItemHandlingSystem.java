package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Item;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Entity;

public class ItemHandlingSystem extends EnemyHandlingSystem
{
	@SuppressWarnings("unchecked")
	public ItemHandlingSystem()
	{
		super(Item.class, Collisions.class);
	}

	@Override
	protected void handlePlayerCollision(Entity item, Entity player, EdgeType edge)
	{
		world.deleteEntity(item);
	}
}
