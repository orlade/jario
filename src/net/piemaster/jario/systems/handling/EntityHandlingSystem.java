package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Acceleration;
import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Jumping;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public abstract class EntityHandlingSystem extends EntityProcessingSystem
{
	protected ComponentMapper<Transform> transformMapper;
	protected ComponentMapper<Velocity> velocityMapper;
	protected ComponentMapper<Acceleration> accelMapper;
	protected ComponentMapper<Physical> physicalMapper;
	protected ComponentMapper<SpatialForm> spatialMapper;
	protected ComponentMapper<CollisionMesh> meshMapper;
	protected ComponentMapper<Jumping> jumpMapper;

	public EntityHandlingSystem(Class<? extends Component> requiredType,
			Class<? extends Component>... otherTypes)
	{
		super(requiredType, otherTypes);
	}

	// private Bag<CollisionInfo> colls;
	// private Bag<EntityHandling> handles;

	// private ComponentMapper<SpatialForm> spatialFormMapper;

	@Override
	public void initialize()
	{
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world.getEntityManager());
		accelMapper = new ComponentMapper<Acceleration>(Acceleration.class, world.getEntityManager());
		physicalMapper = new ComponentMapper<Physical>(Physical.class, world.getEntityManager());
		spatialMapper = new ComponentMapper<SpatialForm>(SpatialForm.class,
				world.getEntityManager());
		meshMapper = new ComponentMapper<CollisionMesh>(CollisionMesh.class,
				world.getEntityManager());
		jumpMapper = new ComponentMapper<Jumping>(Jumping.class, world.getEntityManager());

		// spatialFormMapper = new
		// ComponentMapper<SpatialForm>(SpatialForm.class,
		// world.getEntityManager());

		// colls = new Bag<CollisionInfo>();
		// handles = new Bag<EntityHandling>();
	}

	// @Override
	// protected void begin()
	// {
	// }
	//
	// @Override
	// protected void processEntities(ImmutableBag<Entity> entities)
	// {
	// for (int i = 0; colls.size() > i; i++)
	// {
	// CollisionInfo coll = colls.get(i);
	// handleCollision(coll.getA(), coll.getB(), coll.getEdge());
	// }
	// }
	//
	// @Override
	// protected void end()
	// {
	// }
	//
	// @Override
	// protected void added(Entity e)
	// {
	// EntityHandling handle = createHandler(e);
	// if (handle != null)
	// {
	// handle.initialise();
	// handles.set(e.getId(), handle);
	// }
	// }
	//
	// @Override
	// protected void removed(Entity e)
	// {
	// handles.set(e.getId(), null);
	// }
	//
	// public void pushCollision(Entity a, Entity b, EdgeType edge)
	// {
	// colls.add(new CollisionInfo(a, b, edge));
	// }
	//
	// private EntityHandling createHandler(Entity e)
	// {
	// String group = world.getGroupManager().getGroupOf(e);
	//
	// if (group.equals("PLAYERS"))
	// return new PlayerHandlingSystem(this);
	// else if (group.equals("ENEMIES"))
	// return new EnemyHandlingSystem();
	// else
	// {
	// Log.warn("Unknown group for collision handler creation: "+group);
	// return null;
	// }
	// }
	//
	// private void handleCollision(Entity a, Entity b, EdgeType edge)
	// {
	// EntityHandling handler = handles.get(a.getId());
	// handler.handleCollision(b, edge);
	//
	//
	// String aGroup = world.getGroupManager().getGroupOf(a);
	// String bGroup = world.getGroupManager().getGroupOf(b);
	//
	// // Determine types
	// if (aGroup.equals("PLAYERS"))
	// {
	// if (bGroup.equals("TERRAIN"))
	// {
	// handlePlayerTerrainCollision(a, b, edge);
	// }
	// else if (bGroup.equals("ITEMBOXES"))
	// {
	// handlePlayerBoxCollision(a, b, edge);
	// handleBoxPlayerCollision(b, a, reverseEdge(edge));
	// }
	// else if (bGroup.equals("ENEMIES"))
	// {
	// handlePlayerEnemyCollision(a, b, edge);
	// handleEnemyPlayerCollision(b, a, reverseEdge(edge));
	// }
	// else if (bGroup.equals("ITEMS"))
	// {
	// handlePlayerItemCollision(a, b, edge);
	// handleItemPlayerCollision(b, a, reverseEdge(edge));
	// }
	// }
	// else if (aGroup.equals("ENEMIES"))
	// {
	// if (bGroup.equals("TERRAIN") || bGroup.equals("ITEMBOXES"))
	// {
	// handleEnemyTerrainCollision(a, b, edge);
	// }
	// else if (bGroup.equals("PLAYERS"))
	// {
	// handleEnemyPlayerCollision(a, b, edge);
	// handlePlayerEnemyCollision(b, a, reverseEdge(edge));
	// }
	// }
	// else if (aGroup.equals("ITEMBOXES"))
	// {
	// if (bGroup.equals("PLAYERS"))
	// {
	// handleBoxPlayerCollision(a, b, edge);
	// handlePlayerBoxCollision(b, a, reverseEdge(edge));
	// }
	// if (bGroup.equals("ENEMIES"))
	// {
	// // handleBoxEnemyCollision(a, b, edge);
	// // handleEnemyBoxCollision(b, a, reverseEdge(edge));
	// }
	// }
	// else if (aGroup.equals("ITEMS"))
	// {
	// if (bGroup.equals("PLAYERS"))
	// {
	// handleItemPlayerCollision(a, b, edge);
	// handlePlayerItemCollision(b, a, reverseEdge(edge));
	// }
	// if (bGroup.equals("TERRAIN") || bGroup.equals("ITEMBOXES"))
	// {
	// handleItemTerrainCollision(a, b, edge);
	// }
	// }
	// }
	//
	// //
	// ---------------------------------------------------------------------------------------------
	//
	// private void handlePlayerEnemyCollision(Entity player, Entity enemy,
	// EdgeType edge)
	// {
	// // Jumped on enemy
	// if (edge == EdgeType.EDGE_BOTTOM)
	// {
	// player.getComponent(Velocity.class).setY(-BUMP_FACTOR);
	// player.getComponent(Physical.class).setGrounded(false);
	// }
	// // Hit by enemy
	// else if (edge != EdgeType.EDGE_NONE)
	// {
	// Health health = player.getComponent(Health.class);
	// health.addDamage(1);
	// if (!health.isAlive())
	// {
	// velocityMapper.get(player).setY(-0.5f);
	// velocityMapper.get(player).setX(0.1f * (edge == EdgeType.EDGE_LEFT ? 1 :
	// -1));
	// player.getComponent(Physical.class).setGrounded(false);
	// }
	// }
	// }
	//
	// private void handleEnemyPlayerCollision(Entity enemy, Entity player,
	// EdgeType edge)
	// {
	// // Jumped on by the player
	// if (edge == EdgeType.EDGE_TOP)
	// {
	// Health health = enemy.getComponent(Health.class);
	// health.addDamage(1);
	// if (health.isAlive())
	// {
	// placeEntityOnOther(enemy, player, EdgeType.EDGE_BOTTOM);
	// }
	// }
	// }
	//
	// private void handlePlayerTerrainCollision(Entity player, Entity terrain,
	// EdgeType edge)
	// {
	// placeEntityOnOther(player, terrain, reverseEdge(edge));
	// }
	//
	// private void handleTerrainPlayerCollision(Entity terrain, Entity player,
	// EdgeType edge)
	// {
	// }
	//
	// private void handleEnemyTerrainCollision(Entity enemy, Entity terrain,
	// EdgeType edge)
	// {
	// placeEntityOnOther(enemy, terrain, reverseEdge(edge));
	// }
	//
	// private void handleTerrainEnemyCollision(Entity terrain, Entity enemy,
	// EdgeType edge)
	// {
	// }
	//
	// private void handleItemTerrainCollision(Entity item, Entity terrain,
	// EdgeType edge)
	// {
	// placeEntityOnOther(item, terrain, reverseEdge(edge));
	// }
	//
	// private void handleTerrainItemCollision(Entity terrain, Entity item,
	// EdgeType edge)
	// {
	// }
	//
	// private void handlePlayerBoxCollision(Entity player, Entity box, EdgeType
	// edge)
	// {
	// placeEntityOnOther(player, box, reverseEdge(edge));
	// }
	//
	// private void handleBoxPlayerCollision(Entity box, Entity player, EdgeType
	// edge)
	// {
	// if (edge == EdgeType.EDGE_BOTTOM)
	// {
	// ItemDispenser holder = box.getComponent(ItemDispenser.class);
	// if (!holder.isEmpty())
	// {
	// Entity item;
	// Transform t = transformMapper.get(box);
	//
	// switch (holder.getType())
	// {
	// case MUSHROOM:
	// item = EntityFactory.createMushroom(world, t.getX(), t.getY());
	// break;
	//
	// case COIN:
	// case FLOWER:
	// case STAR:
	// default:
	// Log.warn("Unknown item type: " + holder.getType());
	// return;
	// }
	// item.getComponent(Transform.class).addY(
	// -item.getComponent(SpatialForm.class).getHeight());
	// System.out.println("item height = "
	// + item.getComponent(SpatialForm.class).getHeight());
	// item.refresh();
	//
	// holder.setItemId(item.getId());
	// holder.setDispensing(true);
	// holder.decrementNumber();
	// }
	// }
	// }
	//
	// private void handlePlayerItemCollision(Entity player, Entity item,
	// EdgeType edge)
	// {
	// ItemType type = item.getComponent(Item.class).getType();
	//
	// switch (type)
	// {
	// case MUSHROOM:
	// player.getComponent(Health.class).addDamage(-1);
	// break;
	// default:
	// Log.warn("Unknown item type: " + type);
	// break;
	// }
	// }
	//
	// private void handleItemPlayerCollision(Entity item, Entity player,
	// EdgeType edge)
	// {
	// world.deleteEntity(item);
	// }

	// ---------------------------------------------------------------------------------------------

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
			float diff = (edge == EdgeType.EDGE_LEFT) ?  -m1.getWidth() : m2.getWidth();
			t1.setX(m2.getX() + diff);
			if (phys.isBouncyHorizontal())
			{
				// If walking INTO the placed edge
				int vDir = (int) Math.signum(v1.getX());
				int edgeDir = edge == EdgeType.EDGE_LEFT ? 1 : -1;
				if(vDir == edgeDir)
				{
					t1.setFacingRight(!t1.isFacingRight());
					v1.setX(-v1.getX());
					if(a1 != null)
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

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}
}
