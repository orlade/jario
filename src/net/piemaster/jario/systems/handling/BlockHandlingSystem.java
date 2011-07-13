package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Breakable;
import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.systems.SoundSystem;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Entity;

public class BlockHandlingSystem extends EmptyHandlingSystem
{
	@SuppressWarnings("unchecked")
	public BlockHandlingSystem()
	{
		super(Breakable.class, Collisions.class);
	}

	@Override
	protected void handlePlayerCollision(Entity block, Entity player, EdgeType edge)
	{
		if (edge == EdgeType.EDGE_BOTTOM)
		{
			SoundSystem.pushSound(SoundSystem.BLOCK_BREAK_SOUND, block);
			world.deleteEntity(block);
		}
	}
}
