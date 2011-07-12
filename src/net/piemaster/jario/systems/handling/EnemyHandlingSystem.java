package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Enemy;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Shell;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Component;
import com.artemis.Entity;

public class EnemyHandlingSystem extends EmptyHandlingSystem
{
	@SuppressWarnings("unchecked")
	public EnemyHandlingSystem()
	{
		super(Enemy.class, Collisions.class);
	}

	/**
	 * Generic constructor to allow for subclasses.
	 */
	public EnemyHandlingSystem(Class<? extends Component> requiredType,
			Class<? extends Component>... otherTypes)
	{
		super(requiredType, otherTypes);
	}

	@Override
	protected void handlePlayerCollision(Entity enemy, Entity player, EdgeType edge)
	{
		// Jumped on by the player
		if (edge == EdgeType.EDGE_TOP)
		{
			Health health = healthMapper.get(enemy);
			if(health != null)
			{
				health.addDamage(1);
				
				placeEntityOnOther(enemy, player, reverseEdge(edge));
			}
		}
	}

	@Override
	protected void handleWeaponCollision(Entity enemy, Entity weapon, EdgeType edge)
	{
		// Colliding with a shell
		Shell shell = weapon.getComponent(Shell.class);
		if(shell != null)
		{
			if(velocityMapper.get(weapon).getX() != 0)
			{
				Health health = healthMapper.get(enemy);
				if(health != null)
				{
					health.addDamage(1);
				}
			}
		}
		else
		{
			Health health = healthMapper.get(enemy);
			if(health != null)
			{
				health.addDamage(1);
			}
		}
	}

	@Override
	protected void handleItemBoxCollision(Entity enemy, Entity box, EdgeType edge)
	{
		// Delegate, same as terrain
		handleTerrainCollision(enemy, box, edge);
	}

	@Override
	protected void handleTerrainCollision(Entity enemy, Entity terrain, EdgeType edge)
	{
		placeEntityOnOther(enemy, terrain, reverseEdge(edge));
	}
}
