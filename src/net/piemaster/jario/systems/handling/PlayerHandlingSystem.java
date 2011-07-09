package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.FireballShooter;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Item;
import net.piemaster.jario.components.Score;
import net.piemaster.jario.components.Item.ItemType;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.entities.EntityType;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;
import net.piemaster.jario.systems.delayed.InvulnerabilityHandler;

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
				handleTerrainCollision(e, target, edge);
			}
			else if (group.equals(EntityType.ITEMBOX.toString()))
			{
				handleBoxCollision(e, target, edge);
			}
			else if (group.equals(EntityType.ENEMY.toString()))
			{
				handleEnemyCollision(e, target, edge);
			}
			else if (group.equals(EntityType.ITEM.toString()))
			{
				handleItemCollision(e, target, edge);
			}
		}
	}

	private void handleEnemyCollision(Entity player, Entity enemy, EdgeType edge)
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
			if(health.getHealth() > 1)
			{
				transformMapper.get(player).addY(meshMapper.get(player).getHeight()/2);
				spatialMapper.get(player).setCurrentState("");
				
				FireballShooter shooter = player.getComponent(FireballShooter.class);
				if(shooter != null)
					player.removeComponent(shooter);
				
			}
			health.addDamage(1);
			if (!health.isAlive())
			{
				velocityMapper.get(player).setY(-0.5f);
				velocityMapper.get(player).setX(0.1f * (edge == EdgeType.EDGE_LEFT ? 1 : -1));
				physicalMapper.get(player).setGrounded(false);
			}
			else
			{
				// Make the player invulnerable briefly
				InvulnerabilityHandler.setTemporaryInvulnerability(world, player, 1000, true);
			}
		}
	}

	private void handleTerrainCollision(Entity player, Entity terrain, EdgeType edge)
	{
		placeEntityOnOther(player, terrain, reverseEdge(edge));
	}

	private void handleBoxCollision(Entity player, Entity box, EdgeType edge)
	{
		placeEntityOnOther(player, box, reverseEdge(edge));
	}

	private void handleItemCollision(Entity player, Entity item, EdgeType edge)
	{
		ItemType type = itemMapper.get(item).getType();
			
		switch (type)
		{
		case FLOWER:
			if(player.getComponent(FireballShooter.class) == null)
			{
				player.addComponent(new FireballShooter());
				SpatialForm spatial = spatialMapper.get(player);
				spatial.setCurrentState("FLOWER");
			}
			// No break
			
		case MUSHROOM:
			Health health = healthMapper.get(player);
			if(health.getHealth() == 1)
			{
				health.addDamage(-1);
				CollisionMesh mesh = meshMapper.get(player);
				transformMapper.get(player).addY(-mesh.getHeight());
			}
			SpatialForm spatial = spatialMapper.get(player);
			if(spatial.getCurrentState() == "")
			{
				spatial.setCurrentState("BIG");
			}
			break;
			
		case COIN:
			player.getComponent(Score.class).incrementScore();
			break;
			
		default:
			Log.warn("Unknown item type: " + type);
			break;
		}
	}
}
