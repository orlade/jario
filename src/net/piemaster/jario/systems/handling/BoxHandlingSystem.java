package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Dispensing;
import net.piemaster.jario.components.ItemDispenser;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.entities.EntityFactory;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;
import net.piemaster.jario.systems.SoundSystem;

import org.newdawn.slick.util.Log;

import com.artemis.Entity;

public class BoxHandlingSystem extends EmptyHandlingSystem
{
	@SuppressWarnings("unchecked")
	public BoxHandlingSystem()
	{
		super(ItemDispenser.class, Collisions.class);
	}

	@Override
	protected void handlePlayerCollision(Entity box, Entity player, EdgeType edge)
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
					SoundSystem.pushSound(SoundSystem.ITEM_WOOP_SOUND, box);
					break;

				case COIN:
					item = EntityFactory.createCoin(world, t.getX(), t.getY());
					d.setLifetime(0);
					d.setOutVelocityY(-0.5f);
					SoundSystem.pushSound(SoundSystem.NINTENDO_SOUND, box);
					break;

				case FLOWER:
					item = EntityFactory.createFlower(world, t.getX(), t.getY());
					SoundSystem.pushSound(SoundSystem.ITEM_WOOP_SOUND, box);
					break;

				case STAR:
					item = EntityFactory.createStar(world, t.getX(), t.getY());
					d.setOutVelocityX(0.2f);
					SoundSystem.pushSound(SoundSystem.ITEM_WOOP_SOUND, box);
					break;

				default:
					Log.warn("Unknown item type: " + holder.getType());
					return;
				}
				transformMapper.get(item).addY(-item.getComponent(SpatialForm.class).getHeight());

				// Set the item as dispensing
				item.addComponent(d);
				// Set the item as passing through the box
				registerPassing(item.getId(), box.getId());

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
