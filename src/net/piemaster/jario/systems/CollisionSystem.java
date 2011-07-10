package net.piemaster.jario.systems;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.entities.EntityType;

import org.newdawn.slick.geom.Polygon;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.GroupManager;
import com.artemis.utils.ImmutableBag;

public class CollisionSystem extends EntitySystem
{
	private ComponentMapper<CollisionMesh> meshMapper;
	private ComponentMapper<Collisions> collMapper;

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
		meshMapper = new ComponentMapper<CollisionMesh>(CollisionMesh.class, world);
		collMapper = new ComponentMapper<Collisions>(Collisions.class, world);
	}

	// -------------------------------------------------------------------------

	/**
	 * Detects collisions and registers a CollisionInfo object with the Collisions component of each
	 * entity that was colliding with another.
	 * 
	 * @param source
	 *            A bag of entities to test for collisions with the against bag.
	 * @param against
	 *            A bag of entities to test for collisions with the source bag.
	 */
	private void processCollisions(ImmutableBag<Entity> source, ImmutableBag<Entity> against)
	{
		if (source != null && against != null)
		{
			for (int i = 0; source.size() > i; i++)
			{
				Entity a = source.get(i);

				if (!meshMapper.get(a).isActive())
					continue;

				for (int j = 0; against.size() > j; j++)
				{
					Entity b = against.get(j);

					if (!meshMapper.get(b).isActive())
						continue;

					// Ensure they're not the same
					if (a == b)
						break;

					if (collisionExists(a, b))
					{
						EdgeType edge = detectCollisionEdge(a, b);

						if (edge != EdgeType.EDGE_NONE)
						{
							Collisions ca = collMapper.get(a);
							if (ca != null)
								ca.push(b.getId(), edge);

							Collisions cb = collMapper.get(b);
							if (cb != null)
								cb.push(a.getId(), reverseEdge(edge));
						}
					}
				}
			}
		}
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities)
	{
		// Get the relevant groups
		GroupManager groupMgr = world.getGroupManager();
		ImmutableBag<Entity> players = groupMgr.getEntities(EntityType.PLAYER.toString());
		ImmutableBag<Entity> terrain = groupMgr.getEntities(EntityType.TERRAIN.toString());
		ImmutableBag<Entity> boxes = groupMgr.getEntities(EntityType.ITEMBOX.toString());
		ImmutableBag<Entity> items = groupMgr.getEntities(EntityType.ITEM.toString());
		ImmutableBag<Entity> enemies = groupMgr.getEntities(EntityType.ENEMY.toString());
		ImmutableBag<Entity> weapons = groupMgr.getEntities(EntityType.WEAPON.toString());

		// Process collisions between relevant pairs of groups
		processCollisions(players, terrain);
		processCollisions(enemies, terrain);
		processCollisions(items, terrain);
		processCollisions(items, boxes);
		processCollisions(players, enemies);

		processCollisions(weapons, players);
		processCollisions(weapons, enemies);
		processCollisions(weapons, terrain);
		processCollisions(weapons, boxes);
		processCollisions(weapons, weapons);

		processCollisions(players, boxes);
		processCollisions(enemies, boxes);
		processCollisions(players, items);
	}

	// -------------------------------------------------------------------------

	private boolean collisionExists(Entity e1, Entity e2)
	{
		Polygon p1 = meshMapper.get(e1).getPoly();
		Polygon p2 = meshMapper.get(e2).getPoly();
		return p1.intersects(p2);
	}

	/**
	 * Detects the edge of the collision between the collision meshes of two entities.
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
		return collEdge;
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
