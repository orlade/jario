package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Weapon;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Entity;

public class WeaponHandlingSystem extends ItemHandlingSystem
{
	@SuppressWarnings("unchecked")
	public WeaponHandlingSystem()
	{
		super(Weapon.class, Collisions.class);
	}

	@Override
	protected void handleTerrainCollision(Entity weapon, Entity terrain, EdgeType edge)
	{
		if(edge == EdgeType.EDGE_LEFT || edge == EdgeType.EDGE_RIGHT)
			world.deleteEntity(weapon);
		else
			super.handleTerrainCollision(weapon, terrain, edge);
	}

	@Override
	protected void handleEnemyCollision(Entity weapon, Entity enemy, EdgeType edge)
	{
		world.deleteEntity(weapon);
	}
}
