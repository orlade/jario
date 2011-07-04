package net.piemaster.jario.systems;

import net.piemaster.jario.EntityFactory;
import net.piemaster.jario.components.Acceleration;
import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Item.ItemType;
import net.piemaster.jario.components.Item;
import net.piemaster.jario.components.ItemDispenser;
import net.piemaster.jario.components.Jumping;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;
import net.piemaster.jario.systems.rendering.TerrainRenderSystem;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.util.Log;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class CollisionSystem extends EntitySystem
{
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Physical> physicalMapper;
	private ComponentMapper<CollisionMesh> meshMapper;
	private ComponentMapper<Jumping> jumpMapper;

	private static final float BUMP_FACTOR = 0.3f;

	public static enum EdgeType
	{
		EDGE_NONE, EDGE_TOP, EDGE_BOTTOM, EDGE_LEFT, EDGE_RIGHT
	};

	@SuppressWarnings("unchecked")
	public CollisionSystem()
	{
		super(Transform.class, CollisionMesh.class);
	}

	@Override
	public void initialize()
	{
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world.getEntityManager());
		physicalMapper = new ComponentMapper<Physical>(Physical.class, world.getEntityManager());
		meshMapper = new ComponentMapper<CollisionMesh>(CollisionMesh.class,
				world.getEntityManager());
		jumpMapper = new ComponentMapper<Jumping>(Jumping.class, world.getEntityManager());
	}

	// ---------------------------------------------------------------------------------------------

	private void processCollisions(ImmutableBag<Entity> source, ImmutableBag<Entity> against)
	{
		if (source != null && against != null)
		{
			for (int i = 0; source.size() > i; i++)
			{
				Entity a = source.get(i);

				for (int j = 0; against.size() > j; j++)
				{
					Entity b = against.get(j);
					
					// Ensure they're not the same
					if(a == b)
						break;

					if (collisionExists(a, b))
					{
						EdgeType edge = detectCollisionEdge(a, b);
						handleCollision(a, b, edge);
					}
				}
			}
		}
	}
	
	private void handleCollision(Entity a, Entity b, EdgeType edge)
	{
		String aGroup = world.getGroupManager().getGroupOf(a);
		String bGroup = world.getGroupManager().getGroupOf(b);

		// Determine types
		if(aGroup.equals("PLAYERS"))
		{
			if(bGroup.equals("TERRAIN"))
			{
				handlePlayerTerrainCollision(a, b, edge);
			}
			else if(bGroup.equals("ITEMBOXES"))
			{
				handlePlayerBoxCollision(a, b, edge);
				handleBoxPlayerCollision(b, a, reverseEdge(edge));
			}
			else if(bGroup.equals("ENEMIES"))
			{
				handlePlayerEnemyCollision(a, b, edge);
				handleEnemyPlayerCollision(b, a, reverseEdge(edge));
			}
			else if(bGroup.equals("ITEMS"))
			{
				handlePlayerItemCollision(a, b, edge);
				handleItemPlayerCollision(b, a, reverseEdge(edge));
			}
		}
		else if(aGroup.equals("ENEMIES"))
		{
			if(bGroup.equals("TERRAIN") || bGroup.equals("ITEMBOXES"))
			{
				handleEnemyTerrainCollision(a, b, edge);
			}
			else if(bGroup.equals("PLAYERS"))
			{
				handleEnemyPlayerCollision(a, b, edge);
				handlePlayerEnemyCollision(b, a, reverseEdge(edge));
			}
		}
		else if(aGroup.equals("ITEMBOXES"))
		{
			if(bGroup.equals("PLAYERS"))
			{
				handleBoxPlayerCollision(a, b, edge);
				handlePlayerBoxCollision(b, a, reverseEdge(edge));
			}
			if(bGroup.equals("ENEMIES"))
			{
//				handleBoxEnemyCollision(a, b, edge);
//				handleEnemyBoxCollision(b, a, reverseEdge(edge));
			}
		}
		else if(aGroup.equals("ITEMS"))
		{
			if(bGroup.equals("PLAYERS"))
			{
				handleItemPlayerCollision(a, b, edge);
				handlePlayerItemCollision(b, a, reverseEdge(edge));
			}
			if(bGroup.equals("TERRAIN") || bGroup.equals("ITEMBOXES"))
			{
				handleItemTerrainCollision(a, b, edge);
			}
		}
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities)
	{
		Entity player = world.getTagManager().getEntity("PLAYER");
		ImmutableBag<Entity> players = world.getGroupManager().getEntities("PLAYERS");
		ImmutableBag<Entity> terrain = world.getGroupManager().getEntities("TERRAIN");
		ImmutableBag<Entity> boxes = world.getGroupManager().getEntities("ITEMBOXES");
		ImmutableBag<Entity> items = world.getGroupManager().getEntities("ITEMS");
		ImmutableBag<Entity> enemies = world.getGroupManager().getEntities("ENEMIES");
		
		processCollisions(players, terrain);
		processCollisions(enemies, terrain);
		processCollisions(items, terrain);
		processCollisions(items, boxes);
		processCollisions(players, enemies);
		
		processCollisions(players, boxes);
		processCollisions(enemies, boxes);
		processCollisions(players, items);

//		// Check player against enemies
//		if (player != null && enemies != null && meshMapper.get(player).isActive())
//		{
//			boolean playerMoving = physicalMapper.get(player).isMoving();
//			for (int i = 0; enemies.size() > i; i++)
//			{
//				Entity enemy = enemies.get(i);
//				// Ignore inactive collision meshes
//				if (!meshMapper.get(enemy).isActive())
//					continue;
//
//				if ((playerMoving || physicalMapper.get(enemy).isMoving())
//						&& collisionExists(player, enemy))
//				{
//					EdgeType edge = detectCollisionEdge(player, enemy);
//					handlePlayerEnemyCollision(player, enemy, edge);
//					handleEnemyPlayerCollision(enemy, player, reverseEdge(edge));
//				}
//			}
//		}
//
//		// Check player against item boxes
//		if (player != null && boxes != null && meshMapper.get(player).isActive())
//		{
//			boolean playerMoving = physicalMapper.get(player).isMoving();
//			for (int i = 0; boxes.size() > i; i++)
//			{
//				Entity box = boxes.get(i);
//				// Ignore inactive collision meshes
//				if (!meshMapper.get(box).isActive())
//					continue;
//
//				if (playerMoving && collisionExists(player, box))
//				{
//					EdgeType edge = detectCollisionEdge(player, box);
//					handlePlayerBoxCollision(player, box, edge);
//					handleBoxPlayerCollision(box, player, reverseEdge(edge));
//				}
//			}
//		}
//
//		// Check player and enemies against terrain
//		if (terrain != null && (enemies != null || player != null))
//		{
//			// Loop terrain inside enemies to allow storage of enemy properties
//			// per terrain
//			for (int i = 0; terrain.size() > i; i++)
//			{
//				Entity block = terrain.get(i);
//
//				if (enemies != null)
//				{
//					for (int j = 0; enemies.size() > j; j++)
//					{
//						Entity enemy = enemies.get(j);
//
//						if (enemy.getComponent(Physical.class).isMoving()
//								&& collisionExists(enemy, block))
//						{
//							EdgeType edge = detectCollisionEdge(enemy, block);
//							handleEnemyTerrainCollision(enemy, block, edge);
//							handleTerrainEnemyCollision(block, enemy, edge);
//						}
//					}
//				}
//
//				// Items
//				if (items != null)
//				{
//					for (int j = 0; items.size() > j; j++)
//					{
//						Entity item = items.get(j);
//
//						if (item.getComponent(Physical.class).isMoving()
//								&& collisionExists(item, block))
//						{
//							EdgeType edge = detectCollisionEdge(item, block);
//							handleItemTerrainCollision(item, block, edge);
//							handleTerrainItemCollision(block, item, edge);
//						}
//					}
//				}
//
//				if (player != null && meshMapper.get(player).isActive())
//				{
//					if (player.getComponent(Physical.class).isMoving()
//							&& collisionExists(player, block))
//					{
//						EdgeType edge = detectCollisionEdge(player, block);
//						handlePlayerTerrainCollision(player, block, edge);
//						handleTerrainPlayerCollision(block, player, edge);
//					}
//				}
//			}
//		}
	}

	// ---------------------------------------------------------------------------------------------

	private void handlePlayerEnemyCollision(Entity player, Entity enemy, EdgeType edge)
	{
		// Jumped on enemy
		if (edge == EdgeType.EDGE_BOTTOM)
		{
			player.getComponent(Velocity.class).setY(-BUMP_FACTOR);
			player.getComponent(Physical.class).setGrounded(false);
		}
		// Hit by enemy
		else if (edge != EdgeType.EDGE_NONE)
		{
			Health health = player.getComponent(Health.class);
			health.addDamage(1);
			if (!health.isAlive())
			{
				velocityMapper.get(player).setY(-0.5f);
				velocityMapper.get(player).setX(0.1f * (edge == EdgeType.EDGE_LEFT ? 1 : -1));
				player.getComponent(Physical.class).setGrounded(false);
			}
		}
	}

	private void handleEnemyPlayerCollision(Entity enemy, Entity player, EdgeType edge)
	{
		// Jumped on by the player
		if (edge == EdgeType.EDGE_TOP)
		{
			Health health = enemy.getComponent(Health.class);
			health.addDamage(1);
			if (health.isAlive())
			{
				placeEntityOnOther(enemy, player, EdgeType.EDGE_BOTTOM);
			}
		}
	}

	private void handlePlayerTerrainCollision(Entity player, Entity terrain, EdgeType edge)
	{
		placeEntityOnOther(player, terrain, reverseEdge(edge));
	}

	private void handleTerrainPlayerCollision(Entity terrain, Entity player, EdgeType edge)
	{
	}

	private void handleEnemyTerrainCollision(Entity enemy, Entity terrain, EdgeType edge)
	{
		placeEntityOnOther(enemy, terrain, reverseEdge(edge));
	}

	private void handleTerrainEnemyCollision(Entity terrain, Entity enemy, EdgeType edge)
	{
	}

	private void handleItemTerrainCollision(Entity item, Entity terrain, EdgeType edge)
	{
		placeEntityOnOther(item, terrain, reverseEdge(edge));
	}

	private void handleTerrainItemCollision(Entity terrain, Entity item, EdgeType edge)
	{
	}

	private void handlePlayerBoxCollision(Entity player, Entity box, EdgeType edge)
	{
		placeEntityOnOther(player, box, reverseEdge(edge));
	}

	private void handleBoxPlayerCollision(Entity box, Entity player, EdgeType edge)
	{
		if (edge == EdgeType.EDGE_BOTTOM)
		{
			ItemDispenser holder = box.getComponent(ItemDispenser.class);
			if (!holder.isEmpty())
			{
				Entity item;
				Transform t = transformMapper.get(box);

				switch (holder.getType())
				{
				case MUSHROOM:
					item = EntityFactory.createMushroom(world, t.getX(), t.getY());
					break;

				case COIN:
				case FLOWER:
				case STAR:
				default:
					Log.warn("Unknown item type: " + holder.getType());
					return;
				}
				item.getComponent(Transform.class).addY(
						-item.getComponent(SpatialForm.class).getHeight());
				System.out.println("item height = "
						+ item.getComponent(SpatialForm.class).getHeight());
				item.refresh();

				holder.setItemId(item.getId());
				holder.setDispensing(true);
				holder.decrementNumber();
			}
		}
	}

	private void handlePlayerItemCollision(Entity player, Entity item, EdgeType edge)
	{
		ItemType type = item.getComponent(Item.class).getType();
		
		switch(type)
		{
		case MUSHROOM:
			player.getComponent(Health.class).addDamage(-1);
			break;
		default:
			Log.warn("Unknown item type: "+type);
			break;
		}
	}
	
	private void handleItemPlayerCollision(Entity item, Entity player, EdgeType edge)
	{
		world.deleteEntity(item);
	}

	// ------------------------------------------------------------------------------------------

	private boolean collisionExists(Entity e1, Entity e2)
	{
		Polygon p1 = meshMapper.get(e1).getPoly();
		Polygon p2 = meshMapper.get(e2).getPoly();
		return p1.intersects(p2);
	}

	/**
	 * Detects the edge of the collision between the collision meshes of two
	 * entities.
	 * 
	 * @param e1
	 *            The entity of interest.
	 * @param e2
	 *            The entity being collided with.
	 * @return The edge of entity 1 that collided with entity 2.
	 */
	private EdgeType detectCollisionEdge(Entity e1, Entity e2)
	{
		CollisionMesh m1 = meshMapper.get(e1);
		CollisionMesh m2 = meshMapper.get(e2);
		// Velocity v1 = velocityMapper.get(e1);
		// Velocity v2 = velocityMapper.get(e2);

		// Get the X collision value
		float xColl;
		if (m1.getX() < m2.getX())
			xColl = (m1.getX() + m1.getWidth()) - m2.getX();
		else
			xColl = -((m2.getX() + m2.getWidth()) - m1.getX());

		// Get the Y collision value
		float yColl;
		if (m1.getY() < m2.getY())
			yColl = (m1.getY() + m1.getHeight()) - m2.getY();
		else
			yColl = -((m2.getY() + m2.getHeight()) - m1.getY());

		// The collision is from the direction with the smaller value
		// If the collision value is positive, it's top/left
		EdgeType collEdge = EdgeType.EDGE_NONE;

		if (Math.abs(xColl) < Math.abs(yColl))
		// && (Math.signum(xColl) == Math.signum(v1.getX()) || v1.getX() == 0 ||
		// xColl == 0))
		{
			if (xColl > 0)
				collEdge = EdgeType.EDGE_RIGHT;
			else if (xColl < 0)
				collEdge = EdgeType.EDGE_LEFT;
		}
		else
		// else if(Math.signum(yColl) == Math.signum(v1.getY()) || yColl == 0)
		{
			if (yColl > 0)
				collEdge = EdgeType.EDGE_BOTTOM;
			else if (yColl < 0)
				collEdge = EdgeType.EDGE_TOP;
		}
		// System.out.println("xcoll = " + xColl + ", vel x = " + v1.getX() +
		// ", yColl = " + yColl
		// + ", vel y = " + v1.getY() + ", edge = " + collEdge);
		return collEdge;
	}

	/**
	 * Set position and stop relevant movement.
	 */
	void placeEntityOnOther(Entity e1, Entity e2, EdgeType edge)
	{
		CollisionMesh m1 = meshMapper.get(e1);
		CollisionMesh m2 = meshMapper.get(e2);
		Transform t1 = transformMapper.get(e1);
		Velocity v1 = velocityMapper.get(e1);
		Physical phys = physicalMapper.get(e1);
		Jumping jump = jumpMapper.get(e1);

		if (edge == EdgeType.EDGE_TOP)
		{
			// Set the Y coordinate to that of the terrain object
			t1.setY(m2.getY() - m1.getHeight());

			if (phys.isBouncyVertical() && jump != null)
			{
				v1.setY(-jump.getJumpFactor());
			}
			else
			{
				if (v1.getY() > 0)
				{
					haltVertical(e1);
				}
			}
			// Record that the actor is on the ground to avoid gravity
			phys.setGrounded(true);
		}
		else if (edge == EdgeType.EDGE_BOTTOM)
		{
			t1.setY(m2.getY() + m2.getHeight());

			if (v1.getY() < 0)
			{
				haltVertical(e1);
			}
			phys.setGrounded(false);
		}
		else if (edge == EdgeType.EDGE_LEFT)
		{
			t1.setX(m2.getX() - m1.getWidth());
			if (phys.isBouncyHorizontal())
			{
				v1.setX(-v1.getX());
			}
			else
			{
				// haltHorizontal(e1);
			}
		}
		else if (edge == EdgeType.EDGE_RIGHT)
		{
			t1.setX(m2.getX() + m2.getWidth());
			if (phys.isBouncyHorizontal())
			{
				v1.setX(-v1.getX());
			}
			else
			{
				// haltHorizontal(e1);
			}
		}
		// Update the collision mesh
		m1.setLocation(t1.getX(), t1.getY());
	}

	private void haltVertical(Entity ent)
	{
		ent.getComponent(Acceleration.class).setY(0);
		ent.getComponent(Velocity.class).setY(0);
	}

	@SuppressWarnings("unused")
	private void haltHorizontal(Entity ent)
	{
		ent.getComponent(Acceleration.class).setX(0);
		ent.getComponent(Velocity.class).setX(0);
	}

	private EdgeType reverseEdge(EdgeType edge)
	{
		switch (edge)
		{
		case EDGE_LEFT:
			return EdgeType.EDGE_RIGHT;
		case EDGE_RIGHT:
			return EdgeType.EDGE_LEFT;
		case EDGE_TOP:
			return EdgeType.EDGE_BOTTOM;
		case EDGE_BOTTOM:
			return EdgeType.EDGE_TOP;
		default:
			return EdgeType.EDGE_NONE;
		}
	}

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}
}
