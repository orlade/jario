package net.piemaster.jario.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.artemis.Component;

public class Timer extends Component
{
	private int time;
	private boolean active = true;

	private Map<Integer, ArrayList<String>> triggerMap = new HashMap<Integer, ArrayList<String>>();

	public Timer()
	{
	}

	public int getTime()
	{
		return time;
	}

	public void addTime(int time)
	{
		this.time += time;
	}

	public void reset()
	{
		this.time = 0;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public Set<Entry<Integer, ArrayList<String>>> getTriggers()
	{
		return triggerMap.entrySet();
	}

	// TODO Maybe make a (static?) method of TimerSystem
	public void registerTrigger(int delay, String trigger)
	{
		int key = time + delay;
		if (!triggerMap.containsKey(key))
		{
			ArrayList<String> value = new ArrayList<String>();
			value.add(trigger);
			triggerMap.put(key, value);
		}
		else
		{
			triggerMap.get(key).add(trigger);
		}
	}

	/**
	 * Removes all registered instances of the given trigger string.
	 * 
	 * @param trigger
	 *            The trigger string to erase.
	 */
	// TODO Maybe make a (static?) method of TimerSystem
	public void unregisterTrigger(String trigger)
	{
		ArrayList<Integer> popKeys = new ArrayList<Integer>();
		
		Set<Entry<Integer, ArrayList<String>>> entries = triggerMap.entrySet();
		for (Entry<Integer, ArrayList<String>> entry : entries)
		{
			entry.getValue().remove(trigger);
			
			if (entry.getValue().isEmpty())
			{
				popKeys.add(entry.getKey());
			}
		}
		for(Integer key : popKeys)
		{
			triggerMap.remove(key);
		}
	}
}
