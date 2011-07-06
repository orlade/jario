package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Item;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Component;
import com.artemis.Entity;

public class ItemHandlingSystem extends EnemyHandlingSystem
{
	@SuppressWarnings("unchecked")
	public ItemHandlingSystem()
	{
		super(Item.class, Collisions.class);
	}
	
	/**
	 * Generic constructor to allow for subclasses.
	 */
	public ItemHandlingSystem(Class<? extends Component> requiredType,
			Class<? extends Component>... otherTypes)
	{
		super(requiredType, otherTypes);
	}

	@Override
	protected void handlePlayerCollision(Entity item, Entity player, EdgeType edge)
	{
		world.deleteEntity(item);
	}
}
