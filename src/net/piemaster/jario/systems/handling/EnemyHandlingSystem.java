package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Enemy;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Invulnerable;
import net.piemaster.jario.components.Shell;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;
import net.piemaster.jario.systems.SoundSystem;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;

public class EnemyHandlingSystem extends EmptyHandlingSystem
{
	private ComponentMapper<Invulnerable> invulnMapper;
	
	@SuppressWarnings("unchecked")
	public EnemyHandlingSystem()
	{
		super(Enemy.class, Collisions.class);
	}

	@Override
	public void initialize()
	{
		super.initialize();
		
		invulnMapper = new ComponentMapper<Invulnerable>(Invulnerable.class, world);
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
		Invulnerable ei = invulnMapper.get(enemy);
		Invulnerable pi = invulnMapper.get(player);
		if(ei != null)
		{
			return;
		}
		else if(pi != null && pi.isDeadly())
		{
			takeDamage(Integer.MAX_VALUE, enemy, edge);
		}
		else
		{
			// Jumped on by the player
			if (edge == EdgeType.EDGE_TOP)
			{
				takeDamage(1, enemy, edge);
				placeEntityOnOther(enemy, player, reverseEdge(edge));
			}
		}
	}

	@Override
	protected void handleWeaponCollision(Entity enemy, Entity weapon, EdgeType edge)
	{
		Invulnerable ei = invulnMapper.get(enemy);
		if(ei != null)
		{
			return;
		}
		
		// Colliding with a shell
		Shell shell = weapon.getComponent(Shell.class);
		if(shell != null)
		{
			if(velocityMapper.get(weapon).getX() != 0)
			{
				takeDamage(1, enemy, edge);
			}
		}
		else
		{
			takeDamage(1, enemy, edge);
			SoundSystem.pushSound(SoundSystem.CRUSH_SOUND, enemy);
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
	
	// -------------------------------------------------------------------------
	
	private void takeDamage(int amount, Entity enemy, EdgeType edge)
	{
		Health health = healthMapper.get(enemy);
		if(health != null)
		{
			health.addDamage(amount);
		}
		SoundSystem.pushSound(SoundSystem.POP_SOUND, enemy);
	}
}
