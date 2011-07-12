package net.piemaster.jario.components;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Sound;

import com.artemis.Component;

public class Audible extends Component
{
	private List<Sound> soundQueue;

	public Audible()
	{
		soundQueue = new ArrayList<Sound>();
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
}
