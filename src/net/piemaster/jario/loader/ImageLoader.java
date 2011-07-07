package net.piemaster.jario.loader;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageLoader
{
	public static Image loadImage(String ref) throws SlickException
	{
		return new Image(ImageLoader.class.getResourceAsStream(ref), ref, false);
	}
}
