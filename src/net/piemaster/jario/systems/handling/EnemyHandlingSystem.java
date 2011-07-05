package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Enemy;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Item;
import net.piemaster.jario.entities.EntityType;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.ComponentMapper;
import com.artemis.Entity;

public class EnemyHandlingSystem extends EntityHandlingSystem
{
	protected ComponentMapper<Health> healthMapper;
	protected ComponentMapper<Item> itemMapper;

	@SuppressWarnings("unchecked")
	public EnemyHandlingSystem()
	{
		super(Enemy.class, Collisions.class);
	}

	@Override
	public void initialize()
	{
		super.initialize();

		healthMapper = new ComponentMapper<Health>(Health.class, world.getEntityManager());
		itemMapper = new ComponentMapper<Item>(Item.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Collisions coll = e.getComponent(Collisions.class);

		for (int i = coll.getSize() - 1; i >= 0; --i)
		{
			System.out.println("Handling enemy collision #" + i);
			
			Entity target = world.getEntity(coll.getTargetIds().remove(i));
			EdgeType edge = coll.getEdges().remove(i);
			String group = world.getGroupManager().getGroupOf(target);

			if (group.equals(EntityType.TERRAIN.toString()) || group.equals(EntityType.ITEMBOX.toString()))
			{
				handleEnemyTerrainCollision(e, target, edge);
			}
			else if (group.equals(EntityType.PLAYER.toString()))
			{
				handleEnemyPlayerCollision(e, target, edge);
			}
		}
	}

	private void handleEnemyPlayerCollision(Entity enemy, Entity player, EdgeType edge)
	{
		// Jumped on by the player
		if (edge == EdgeType.EDGE_TOP)
		{
			Health health = healthMapper.get(enemy);
			health.addDamage(1);
			if (health.isAlive())
			{
				placeEntityOnOther(enemy, player, reverseEdge(edge));
			}
		}
	}

	private void handleEnemyTerrainCollision(Entity enemy, Entity terrain, EdgeType edge)
	{
		placeEntityOnOther(enemy, terrain, reverseEdge(edge));
	}
}
