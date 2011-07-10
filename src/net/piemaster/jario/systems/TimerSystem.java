package net.piemaster.jario.systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.piemaster.jario.components.Timer;
import net.piemaster.jario.systems.delayed.ListenerSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class TimerSystem extends EntityProcessingSystem
{
	private ComponentMapper<Timer> timerMapper;
	private HashMap<String, ArrayList<ListenerSystem>> handlerMap;

	public TimerSystem()
	{
		super(Timer.class);
	}

	@Override
	public void initialize()
	{
		timerMapper = new ComponentMapper<Timer>(Timer.class, world);
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
		Timer timer = timerMapper.get(e);

		if (timer.isActive())
		{
			timer.addTime(world.getDelta());
			
			ArrayList<String> triggers = getTriggers(timer);
			
			for(String trigger : triggers)
			{
				if (trigger != null)
				{
					for(ListenerSystem handler : handlerMap.get(trigger))
					{
						handler.process(e, trigger);
					}
				}
			}
		}
	}
	
	/**
	 * Determines which trigger values have been tripped this frame.
	 * @param timer The Timer component to analyse.
	 * @return A list of tripped trigger strings.
	 */
	protected ArrayList<String> getTriggers(Timer timer, boolean pop)
	{
		ArrayList<String> triggers = new ArrayList<String>();
		
		ArrayList<String> popTriggers = null;
		if(pop)
		{
			popTriggers = new ArrayList<String>();
		}
		
		for(Entry<Integer, ArrayList<String>> entry : timer.getTriggers())
		{
			if(entry.getKey() < timer.getTime())
			{
				triggers.addAll(entry.getValue());
				if(pop)
				{
					popTriggers.addAll(entry.getValue());
				}
			}
		}

		if(pop)
		{
			for(String trigger : popTriggers)
			{
				timer.unregisterTrigger(trigger);
			}
		}
		return triggers;
	}

	/**
	 * Gets current triggers and pops them from the registry by default.
	 */
	protected ArrayList<String> getTriggers(Timer timer)
	{
		return getTriggers(timer, true);
	}
}
