package net.piemaster.jario.loader;

import java.io.InputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

public class ImageLoader
{
	public static Image loadImage(String ref) throws SlickException
	{
		InputStream in = ImageLoader.class.getResourceAsStream(ref);
		if(in == null)
		{
			Log.warn("Image not found: "+ref);
		}
		return new Image(in, ref, false);
	}
}
