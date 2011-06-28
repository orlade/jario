package net.piemaster.jario.components;

import com.artemis.Component;

public class Asteroid extends Component
{
	private int size;
	
	public Asteroid(int size)
	{
		this.size = size;
	}
	
	public int getSize()
	{
		return size;
	}
}
