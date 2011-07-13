package net.piemaster.jario.systems;

import net.piemaster.jario.components.Dispensing;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.Velocity;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class DispensingSystem extends EntityProcessingSystem
{
	public DispensingSystem()
	{
		super(Dispensing.class);
	}

	@Override
	public void initialize()
	{
	}

	@Override
	protected void process(Entity e)
	{
		Dispensing d = e.getComponent(Dispensing.class);
		if (!d.isExpired())
		{
			d.reduceLife(world.getDelta());
		}
		else
		{
			e.getComponent(Physical.class).setHasGravity(true);
			e.getComponent(Velocity.class).setBoth(d.getOutVelocityX(), d.getOutVelocityY());
			e.removeComponent(d);
			e.refresh();
		}
	}

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}

}
