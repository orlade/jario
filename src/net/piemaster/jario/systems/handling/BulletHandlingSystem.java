package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Fireball;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.entities.EntityType;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Entity;

public class BulletHandlingSystem extends ItemHandlingSystem
{
	@SuppressWarnings("unchecked")
	public BulletHandlingSystem()
	{
		super(Fireball.class, Collisions.class);
	}

	@Override
	protected void process(Entity e)
	{
		Collisions coll = e.getComponent(Collisions.class);

		for (int i = coll.getSize() - 1; i >= 0; --i)
		{
			Entity target = world.getEntity(coll.getTargetIds().remove(i));
			EdgeType edge = coll.getEdges().remove(i);
			String group = world.getGroupManager().getGroupOf(target);

			if (group.equals(EntityType.TERRAIN.toString()) || group.equals(EntityType.ITEMBOX.toString()))
			{
				handleTerrainCollision(e, target, edge);
			}
			else if (group.equals(EntityType.PLAYER.toString()))
			{
				handlePlayerCollision(e, target, edge);
			}
			else if (group.equals(EntityType.ENEMY.toString()))
			{
				handleEnemyCollision(e, target, edge);
			}
		}
	}

	@Override
	protected void handleTerrainCollision(Entity fireball, Entity terrain, EdgeType edge)
	{
		if(edge == EdgeType.EDGE_LEFT || edge == EdgeType.EDGE_RIGHT)
			world.deleteEntity(fireball);
		else
			super.handleTerrainCollision(fireball, terrain, edge);
	}

	@Override
	protected void handlePlayerCollision(Entity fireball, Entity player, EdgeType edge)
	{
	}

	protected void handleEnemyCollision(Entity fireball, Entity enemy, EdgeType edge)
	{
		enemy.getComponent(Health.class).addDamage(1);
		world.deleteEntity(fireball);
	}
}
