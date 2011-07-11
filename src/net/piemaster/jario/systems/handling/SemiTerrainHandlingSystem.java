package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.SemiPassable;

public class SemiTerrainHandlingSystem extends EmptyHandlingSystem
{
	@SuppressWarnings("unchecked")
	public SemiTerrainHandlingSystem()
	{
		super(SemiPassable.class, Collisions.class);
	}
}
