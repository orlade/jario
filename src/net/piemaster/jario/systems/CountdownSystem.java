package net.piemaster.jario.systems;

import java.util.ArrayList;
import java.util.HashMap;

import net.piemaster.jario.components.Countdown;
import net.piemaster.jario.systems.delayed.ListenerSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class CountdownSystem extends EntityProcessingSystem
{
	private ComponentMapper<Countdown> countMapper;

	private HashMap<String, ArrayList<ListenerSystem>> handlerMap;

	public CountdownSystem()
	{
		super(Countdown.class);
	}

	@Override
	public void initialize()
	{
		countMapper = new ComponentMapper<Countdown>(Countdown.class, world);
		handlerMap = new HashMap<String, ArrayList<ListenerSystem>>();
	}

	public void register(String trigger, ListenerSystem handler)
	{
		if (!handlerMap.containsKey(trigger))
		{
			handlerMap.put(trigger, new ArrayList<ListenerSystem>());
		}
		handlerMap.get(trigger).add(handler);
	}
	
	public void unregister(String trigger, ListenerSystem handler)
	{
		if (handlerMap.containsKey(trigger))
		{
			handlerMap.get(trigger).remove(handler);
		}
	}

	@Override
	protected void process(Entity e)
	{
		Countdown countdown = countMapper.get(e);

		if (countdown.isActive())
		{
			if (!countdown.isExpired())
			{
				countdown.reduceTime(world.getDelta());
			}
			else
			{
				String trigger = countdown.getTrigger();
				if (trigger != null)
				{
					for(ListenerSystem handler : handlerMap.get(trigger))
					{
						handler.process(e, trigger);
					}
				}
				countdown.setActive(false);
				countdown.resetTimer();
			}
		}
	}
}
