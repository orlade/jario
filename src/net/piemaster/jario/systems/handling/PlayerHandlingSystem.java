package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Item;
import net.piemaster.jario.components.Item.ItemType;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.entities.EntityType;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import org.newdawn.slick.util.Log;

import com.artemis.ComponentMapper;
import com.artemis.Entity;

public class PlayerHandlingSystem extends EntityHandlingSystem
{
	private static final float BUMP_FACTOR = 0.3f;
	
	protected ComponentMapper<Health> healthMapper;
	protected ComponentMapper<Item> itemMapper;

	@SuppressWarnings("unchecked")
	public PlayerHandlingSystem()
	{
		super(Player.class, Collisions.class);
	}

	@Override
	public void initialize()
	{
		super.initialize();

		healthMapper = new ComponentMapper<Health>(Health.class, world.getEntityManager());
		itemMapper = new ComponentMapper<Item>(Item.class, world.getEntityManager());

		// Map<String, Runnable> methodMap = new HashMap<String, Runnable>();
		//
		// methodMap.put(EntityType.ENEMY.toString(), new Runnable()
		// {
		// public void run()
		// {
		// System.out.println("help");
		// }
		// });
		//
		// methodMap.put(EntityType.TERRAIN.toString(), new Runnable()
		// {
		// public void run()
		// {
		// System.out.println("teleport");
		// }
		// });
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
			
			// If colliding with an object that has been slated for removal, continue
			if(group == null)
				continue;

			if (group.equals(EntityType.TERRAIN.toString()))
			{
				handlePlayerTerrainCollision(e, target, edge);
			}
			else if (group.equals(EntityType.ITEMBOX.toString()))
			{
				handlePlayerBoxCollision(e, target, edge);
			}
			else if (group.equals(EntityType.ENEMY.toString()))
			{
				handlePlayerEnemyCollision(e, target, edge);
			}
			else if (group.equals(EntityType.ITEM.toString()))
			{
				handlePlayerItemCollision(e, target, edge);
			}
		}
	}

	private void handlePlayerEnemyCollision(Entity player, Entity enemy, EdgeType edge)
	{
		// Jumped on enemy
		if (edge == EdgeType.EDGE_BOTTOM)
		{
			velocityMapper.get(player).setY(-BUMP_FACTOR);
			physicalMapper.get(player).setGrounded(false);
		}
		// Hit by enemy
		else if (edge != EdgeType.EDGE_NONE)
		{
			Health health = healthMapper.get(player);
			health.addDamage(1);
			if (!health.isAlive())
			{
				velocityMapper.get(player).setY(-0.5f);
				velocityMapper.get(player).setX(0.1f * (edge == EdgeType.EDGE_LEFT ? 1 : -1));
				physicalMapper.get(player).setGrounded(false);
			}
		}
	}

	private void handlePlayerTerrainCollision(Entity player, Entity terrain, EdgeType edge)
	{
		placeEntityOnOther(player, terrain, reverseEdge(edge));
	}

	private void handlePlayerBoxCollision(Entity player, Entity box, EdgeType edge)
	{
		placeEntityOnOther(player, box, reverseEdge(edge));
	}

	private void handlePlayerItemCollision(Entity player, Entity item, EdgeType edge)
	{
		ItemType type = itemMapper.get(item).getType();

		switch (type)
		{
		case MUSHROOM:
			healthMapper.get(player).addDamage(-1);
			break;
		default:
			Log.warn("Unknown item type: " + type);
			break;
		}
	}
}