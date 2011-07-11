package net.piemaster.jario.spatials.enemies;

import net.piemaster.jario.spatials.generic.SpatialComposer;

import com.artemis.Entity;
import com.artemis.World;

public class KoopaComposer extends SpatialComposer
{
	public KoopaComposer(World world, Entity e)
	{
		super(world, e);
		
		registerState("", new KoopaBase(world, owner));
		registerState("PARA", new ParakoopaBase(world, owner));
	}

	@Override
	protected void refreshCurrentSpatial()
	{
		setCurrentSpatial(getSpatial(form.getCurrentState()));
	}
}
