package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Acceleration;
import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Item;
import net.piemaster.jario.components.Jumping;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;
import net.piemaster.jario.entities.EntityType;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;
import net.piemaster.jario.systems.handling.utils.CollisionCommand;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;

public abstract class EmptyHandlingSystem extends EntityHandlingSystem
{
	// Convenience mappers
	protected ComponentMapper<Transform> transformMapper;
	protected ComponentMapper<Velocity> velocityMapper;
	protected ComponentMapper<Acceleration> accelMapper;
	protected ComponentMapper<Physical> physicalMapper;
	protected ComponentMapper<Health> healthMapper;
	protected ComponentMapper<SpatialForm> spatialMapper;
	protected ComponentMapper<CollisionMesh> meshMapper;
	protected ComponentMapper<Item> itemMapper;
	protected ComponentMapper<Jumping> jumpMapper;

	/**
	 * Extension constructor.
	 */
	public EmptyHandlingSystem(Class<? extends Component> requiredType,
			Class<? extends Component>... otherTypes)
	{
		super(requiredType, otherTypes);
	}

	@Override
	/**
	 * Initialise the mappers and register the empty handlers.
	 */
	public void initialize()
	{
		super.initialize();

		// Create component mappers
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world);
		accelMapper = new ComponentMapper<Acceleration>(Acceleration.class, world);
		physicalMapper = new ComponentMapper<Physical>(Physical.class, world);
		healthMapper = new ComponentMapper<Health>(Health.class, world);
		spatialMapper = new ComponentMapper<SpatialForm>(SpatialForm.class, world);
		meshMapper = new ComponentMapper<CollisionMesh>(CollisionMesh.class, world);
		itemMapper = new ComponentMapper<Item>(Item.class, world);
		jumpMapper = new ComponentMapper<Jumping>(Jumping.class, world);

		// Register collision handlers
		handlers.put(EntityType.TERRAIN.toString(), new CollisionCommand()
		{
			@Override
			public void handle(Entity source, Entity target, EdgeType edge)
			{
				handleTerrainCollision(source, target, edge);
			}
		});

		handlers.put(EntityType.ENEMY.toString(), new CollisionCommand()
		{
			@Override
			public void handle(Entity source, Entity target, EdgeType edge)
			{
				handleEnemyCollision(source, target, edge);
			}
		});

		handlers.put(EntityType.ITEM.toString(), new CollisionCommand()
		{
			@Override
			public void handle(Entity source, Entity target, EdgeType edge)
			{
				handleItemCollision(source, target, edge);
			}
		});

		handlers.put(EntityType.ITEMBOX.toString(), new CollisionCommand()
		{
			@Override
			public void handle(Entity source, Entity target, EdgeType edge)
			{
				handleItemBoxCollision(source, target, edge);
			}
		});

		handlers.put(EntityType.WEAPON.toString(), new CollisionCommand()
		{
			@Override
			public void handle(Entity source, Entity target, EdgeType edge)
			{
				handleWeaponCollision(source, target, edge);
			}
		});

		handlers.put(EntityType.PLAYER.toString(), new CollisionCommand()
		{
			@Override
			public void handle(Entity source, Entity target, EdgeType edge)
			{
				handlePlayerCollision(source, target, edge);
			}
		});
	}

	// -------------------------------------------------------------------------
	// Empty collision handler implementations/hooks
	// -------------------------------------------------------------------------

	protected void handleTerrainCollision(Entity source, Entity target, EdgeType edge)
	{
	}

	protected void handleEnemyCollision(Entity source, Entity target, EdgeType edge)
	{
	}

	protected void handleItemCollision(Entity source, Entity target, EdgeType edge)
	{
	}

	protected void handleItemBoxCollision(Entity source, Entity target, EdgeType edge)
	{
	}

	protected void handleWeaponCollision(Entity source, Entity target, EdgeType edge)
	{
	}

	protected void handlePlayerCollision(Entity source, Entity target, EdgeType edge)
	{
	}

	// -------------------------------------------------------------------------
	// Utility methods
	// -------------------------------------------------------------------------

	/**
	 * Set position and stop relevant movement.
	 */
	public void placeEntityOnOther(Entity e1, Entity e2, EdgeType edge)
	{
		CollisionMesh m1 = meshMapper.get(e1);
		CollisionMesh m2 = meshMapper.get(e2);
		Transform t1 = transformMapper.get(e1);
		Velocity v1 = velocityMapper.get(e1);
		Acceleration a1 = accelMapper.get(e1);
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
		else if (edge == EdgeType.EDGE_LEFT || edge == EdgeType.EDGE_RIGHT)
		{
			float diff = (edge == EdgeType.EDGE_LEFT) ? -m1.getWidth() : m2.getWidth();
			t1.setX(m2.getX() + diff);
			if (phys.isBouncyHorizontal())
			{
				// If walking INTO the placed edge
				int vDir = (int) Math.signum(v1.getX());
				int edgeDir = edge == EdgeType.EDGE_LEFT ? 1 : -1;
				if (vDir == edgeDir)
				{
					t1.setFacingRight(!t1.isFacingRight());
					v1.setX(-v1.getX());
					if (a1 != null)
						a1.reverse(true, false);
				}
			}
			else
			{
				// haltHorizontal(e1);
			}
		}
	}

	public void haltVertical(Entity ent)
	{
		ent.getComponent(Acceleration.class).setY(0);
		ent.getComponent(Velocity.class).setY(0);
	}

	public void haltHorizontal(Entity ent)
	{
		ent.getComponent(Acceleration.class).setX(0);
		ent.getComponent(Velocity.class).setX(0);
	}

	protected EdgeType reverseEdge(EdgeType edge)
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
}
