package net.piemaster.jario.systems.handling;

import net.piemaster.jario.EntityFactory;
import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Dispensing;
import net.piemaster.jario.components.Item;
import net.piemaster.jario.components.ItemDispenser;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.entities.EntityType;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import org.newdawn.slick.util.Log;

import com.artemis.ComponentMapper;
import com.artemis.Entity;

public class BoxHandlingSystem extends EntityHandlingSystem
{
	protected ComponentMapper<SpatialForm> spatialMapper;
	protected ComponentMapper<Item> itemMapper;

	@SuppressWarnings("unchecked")
	public BoxHandlingSystem()
	{
		super(ItemDispenser.class, Collisions.class);
	}

	@Override
	public void initialize()
	{
		super.initialize();

		itemMapper = new ComponentMapper<Item>(Item.class, world.getEntityManager());
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
			if (group == null)
				continue;

			if (group.equals(EntityType.PLAYER.toString()))
			{
				handlePlayerCollision(e, target, edge);
			}
		}
	}

	private void handlePlayerCollision(Entity box, Entity player, EdgeType edge)
	{
		if (edge == EdgeType.EDGE_BOTTOM)
		{
			ItemDispenser holder = box.getComponent(ItemDispenser.class);
			if (!holder.isEmpty() && !holder.isDispensing())
			{
				final Entity item;
				Transform t = transformMapper.get(box);
				Dispensing d = new Dispensing();

				switch (holder.getType())
				{
				case MUSHROOM:
					item = EntityFactory.createMushroom(world, t.getX(), t.getY());
					d.setOutVelocityX(0.2f);
					break;

				case COIN:
					item = EntityFactory.createCoin(world, t.getX(), t.getY());
					d.setLifetime(0);
					d.resetTimer();
					d.setOutVelocityY(-0.5f);
					break;

				case FLOWER:
					item = EntityFactory.createFlower(world, t.getX(), t.getY());
					break;

				case STAR:
				default:
					Log.warn("Unknown item type: " + holder.getType());
					return;
				}
				transformMapper.get(item).addY(-item.getComponent(SpatialForm.class).getHeight());

				// Set the item as dispensing
				item.addComponent(d);
				meshMapper.get(item).setActive(false);
				spatialMapper.get(item).pushLoadedCallback(new Runnable()
				{
					@Override
					public void run()
					{
						setDispenseSpeed(item);
					}
				});
				item.refresh();

				// Set the dispenser as dispensing
				holder.setItemId(item.getId());
				holder.setDispensing(true);
				holder.setActive(true);
				holder.decrementNumber();
			}
		}
	}

	private void setDispenseSpeed(Entity item)
	{
		SpatialForm spatial = spatialMapper.get(item);
		int lifetime = item.getComponent(Dispensing.class).getLifetime();
		if (lifetime <= 0)
		{
			transformMapper.get(item).addY(-spatial.getHeight());
		}
		else
		{
			float yDist = spatial.getHeight() / lifetime;
			velocityMapper.get(item).setY(-yDist);
		}
	}
}
