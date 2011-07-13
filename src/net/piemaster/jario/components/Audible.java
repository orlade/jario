package net.piemaster.jario.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Sound;

import com.artemis.Component;

public class Audible extends Component
{
	private List<Sound> soundQueue;
	private Map<String, Sound> loopMap;

	public Audible()
	{
		soundQueue = new ArrayList<Sound>();
		loopMap = new HashMap<String, Sound>();
	}
	
	public void pushSound(Sound sound)
	{
		soundQueue.add(sound);
	}
	
	public List<Sound> getSounds()
	{
		return soundQueue;
	}
	
	public void clearSounds()
	{
		soundQueue.clear();
	}
	
	public void pushLoop(String key, Sound sound)
	{
		loopMap.put(key, sound);
	}
	
	public Collection<Sound> getLoops()
	{
		return loopMap.values();
	}
	
	public void stopLoop(String key)
	{
		if(loopMap.containsKey(key))
		{
			loopMap.get(key).stop();
			loopMap.remove(key);
		}
	}
	
	public void clearLoops()
	{
		loopMap.clear();
	}
}
