package net.piemaster.jario.systems;

import net.piemaster.jario.components.Acceleration;
import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Jumping;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;

import org.newdawn.slick.geom.Polygon;

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

	@Override
	protected void processEntities(ImmutableBag<Entity> entities)
	{
		Entity player = world.getTagManager().getEntity("PLAYER");
		ImmutableBag<Entity> terrain = world.getGroupManager().getEntities("TERRAIN");
		ImmutableBag<Entity> enemies = world.getGroupManager().getEntities("ENEMIES");

		// Check player against enemies
		if (player != null && enemies != null)
		{
			for (int i = 0; enemies.size() > i; i++)
			{
				Entity enemy = enemies.get(i);

				if ((physicalMapper.get(player).isMoving() || physicalMapper.get(enemy).isMoving())
						&& collisionExists(player, enemy))
				{
					EdgeType edge = detectCollisionEdge(player, enemy);
					// Jumped on enemy
					if (edge == EdgeType.EDGE_BOTTOM)
					{
						enemy.getComponent(Health.class).addDamage(1);
						player.getComponent(Velocity.class).setY(-BUMP_FACTOR);
						player.getComponent(Physical.class).setGrounded(false);
					}
					// Hit by enemy
					else
					{
						player.getComponent(Health.class).addDamage(1);
						enemy.getComponent(Velocity.class).setY(-BUMP_FACTOR);
						enemy.getComponent(Physical.class).setGrounded(false);
					}
				}
			}
		}

		// Check player and enemies against terrain
		if (terrain != null && (enemies != null || player != null))
		{
			for (int i = 0; terrain.size() > i; i++)
			{
				Entity block = terrain.get(i);

				if (enemies != null)
				{
					for (int j = 0; enemies.size() > j; j++)
					{
						Entity enemy = enemies.get(j);

						if (enemy.getComponent(Physical.class).isMoving()
								&& collisionExists(enemy, block))
						{
							EdgeType edge = detectCollisionEdge(enemy, block);
							placeEntityOnTerrain(enemy, block, reverseEdge(edge));
						}
					}
				}

				if (player != null)
				{
					if (player.getComponent(Physical.class).isMoving()
							&& collisionExists(player, block))
					{
						EdgeType edge = detectCollisionEdge(player, block);
						placeEntityOnTerrain(player, block, reverseEdge(edge));
					}
				}
			}
		}
	}

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
		{
			if (xColl > 0)
				collEdge = EdgeType.EDGE_RIGHT;
			else if (xColl < 0)
				collEdge = EdgeType.EDGE_LEFT;
		}
		else
		{
			if (yColl > 0)
				collEdge = EdgeType.EDGE_BOTTOM;
			else if (yColl < 0)
				collEdge = EdgeType.EDGE_TOP;
		}
		return collEdge;
	}

	/**
	 * Set position and stop relevant movement.
	 */
	void placeEntityOnTerrain(Entity e1, Entity e2, EdgeType edge)
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
				haltVertical(e1);
			}
			// Record that the actor is on the ground to avoid gravity
			phys.setGrounded(true);
		}
		else if (edge == EdgeType.EDGE_BOTTOM)
		{
			t1.setY(m2.getY() + m2.getHeight());

			if (phys.isBouncyVertical())
			{
				v1.setY(-jump.getJumpFactor());
			}
			else
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
				haltHorizontal(e1);
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
				haltHorizontal(e1);
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
