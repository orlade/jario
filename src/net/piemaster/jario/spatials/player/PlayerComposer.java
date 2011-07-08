package net.piemaster.jario.spatials.player;

import net.piemaster.jario.spatials.SpatialComposer;

import org.newdawn.slick.Graphics;

import com.artemis.Entity;
import com.artemis.World;

public class PlayerComposer extends SpatialComposer
{
	public PlayerComposer(World world, Entity e)
	{
		super(world, e);
		
		stateMap.put("", new PlayerBase(world, owner));
		stateMap.put("BIG", new PlayerBig(world, owner));
		stateMap.put("FLOWER", new PlayerFlower(world, owner));
	}
	
	@Override
	public void render(Graphics g)
	{
		refreshCurrentSpatial();
		super.render(g);
	}

	protected void refreshCurrentSpatial()
	{
		setCurrentSpatial(stateMap.get(form.getCurrentState()));
	}
}
