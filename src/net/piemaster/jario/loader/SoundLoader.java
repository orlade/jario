package net.piemaster.jario.loader;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundLoader
{
	public static Sound loadSound(String ref)
	{
//		InputStream in = SoundLoader.class.getResourceAsStream(ref);
//		if(in == null)
//		{
//			Log.warn("Sound not found: "+ref);
//		}
		
		try
		{
			return new Sound(ref);
		}
		catch (SlickException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}