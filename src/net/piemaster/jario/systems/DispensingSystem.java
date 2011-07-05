package net.piemaster.jario.systems;

import net.piemaster.jario.components.Item;
import net.piemaster.jario.components.ItemDispenser;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class DispensingSystem extends EntityProcessingSystem
{
	private ComponentMapper<ItemDispenser> holderMapper;

	@SuppressWarnings("unchecked")
	public DispensingSystem()
	{
		super(Transform.class, ItemDispenser.class);
	}

	@Override
	public void initialize()
	{
		holderMapper = new ComponentMapper<ItemDispenser>(ItemDispenser.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		ItemDispenser holder = holderMapper.get(e);
		if(holder.isDispensing())
		{
			Entity item = world.getEntity(holder.getItemId());
			if(!holder.isExpired())
			{
				SpatialForm spatial = item.getComponent(SpatialForm.class);
				float yDist = spatial.getHeight() / holder.getLifetime();
				Velocity v = item.getComponent(Velocity.class);
				v.setY(-yDist);
				holder.reduceLife(world.getDelta());
			}
			else
			{
				item.getComponent(Item.class).setDispensing(false);
				item.getComponent(Physical.class).setHasGravity(true);
				item.getComponent(Velocity.class).setX(0.3f);
				holder.setDispensing(false);
			}
		}
	}

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}

}
