package net.piemaster.jario.systems;

import net.piemaster.jario.components.Recovering;

import com.artemis.ComponentMapper;
import com.artemis.ComponentTypeManager;
import com.artemis.DelayedEntityProcessingSystem;
import com.artemis.Entity;

public class RecoverySystem extends DelayedEntityProcessingSystem
{
	private ComponentMapper<Recovering> recoveryMapper;
	private int shortestRemaining;

	public RecoverySystem()
	{
		super(Recovering.class);
	}

	@Override
	public void initialize()
	{
		recoveryMapper = new ComponentMapper<Recovering>(Recovering.class, world);
	}

	@Override
	protected void begin()
	{
		shortestRemaining = Integer.MAX_VALUE;
	}

	@Override
	protected void process(Entity e, int accumulatedDelta)
	{
		Recovering recovering = recoveryMapper.get(e);

		recovering.reduceTime(accumulatedDelta);

		if (recovering.isExpired())
		{
			System.out.println("Removing recovery");
			e.removeComponent(ComponentTypeManager.getTypeFor(Recovering.class));
		}
		else if (recovering.getRemaining() < shortestRemaining)
		{
			shortestRemaining = recovering.getRemaining();
		}
	}

	@Override
	protected void end()
	{
		if (shortestRemaining < Integer.MAX_VALUE && shortestRemaining > 0)
		{
			startDelayedRun(shortestRemaining);
		}
	}
	
	public void pubadded(Entity e)
	{
		added(e);
	}

	@Override
	protected void added(Entity e)
	{
		System.out.println("ADDING in system!");
		Recovering recovering = recoveryMapper.get(e);
		if (!isRunning() || recovering.getRemaining() < getRemainingTimeUntilProcessing())
		{
			startDelayedRun(recovering.getRemaining());
		}
	}
}
