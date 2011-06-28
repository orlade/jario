package net.piemaster.jario.spatials;

import org.newdawn.slick.Graphics;

import com.artemis.Entity;
import com.artemis.World;

public abstract class Spatial
{
	protected World world;
	protected Entity owner;

	public Spatial(World world, Entity owner)
	{
		this.world = world;
		this.owner = owner;
	}

	public abstract void initalize();

	public abstract void render(Graphics g);

	public abstract float getWidth();
	public abstract float getHeight();
}
