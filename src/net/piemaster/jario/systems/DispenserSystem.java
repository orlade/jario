package net.piemaster.jario.systems;

import net.piemaster.jario.components.ItemDispenser;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class DispenserSystem extends EntityProcessingSystem
{
	private ComponentMapper<ItemDispenser> dispenserMapper;

	public DispenserSystem()
	{
		super(ItemDispenser.class);
	}

	@Override
	public void initialize()
	{
		dispenserMapper = new ComponentMapper<ItemDispenser>(ItemDispenser.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		ItemDispenser d = dispenserMapper.get(e);
		if(d.isActive())
		{
			if(!d.isExpired())
			{
				d.reduceLife(world.getDelta());
			}
			else if(d.isDispensing())
			{
				d.setDispensing(false);
				d.setActive(false);
				d.resetTimer();
			}
		}
	}

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}

}
