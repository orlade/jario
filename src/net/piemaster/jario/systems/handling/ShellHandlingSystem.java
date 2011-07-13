package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Invulnerable;
import net.piemaster.jario.components.Recovering;
import net.piemaster.jario.components.Shell;
import net.piemaster.jario.components.Velocity;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;
import net.piemaster.jario.systems.SoundSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;

public class ShellHandlingSystem extends EnemyHandlingSystem
{
	private ComponentMapper<Shell> shellMapper;

	@SuppressWarnings("unchecked")
	public ShellHandlingSystem()
	{
		super(Shell.class, Collisions.class);
	}

	@Override
	public void initialize()
	{
		super.initialize();

		shellMapper = new ComponentMapper<Shell>(Shell.class, world);
	}

	@Override
	protected void handlePlayerCollision(Entity shell, Entity player, EdgeType edge)
	{
		if(player.getComponent(Recovering.class) != null)
			return;
		
		Invulnerable invuln = player.getComponent(Invulnerable.class);
		if(invuln != null)
		{
			if(!invuln.isDeadly())
				return;
			else
			{
				// Let the HealthSystem do the generic death stuff
				healthMapper.get(shell).addDamage(1);
				velocityMapper.get(shell).setY(-0.3f);
				EdgeType sideEdge = determineSideEdge(shell, player, edge);
				velocityMapper.get(shell).setX(0.1f * (sideEdge == EdgeType.EDGE_LEFT ? 1 : -1));
				SoundSystem.pushSound(SoundSystem.POP_SOUND, shell);
			}
		}
		else
		{
			Velocity vel = velocityMapper.get(shell);
			
			if (vel.getX() == 0)
				kick(shell, player, edge);
			else if (edge == EdgeType.EDGE_TOP)
				stop(shell);
		}
	}

	@Override
	protected void handleWeaponCollision(Entity shell, Entity weapon, EdgeType edge)
	{
		Velocity vel = velocityMapper.get(shell);

		if (vel.getX() == 0)
			kick(shell, edge);
		else
		{
			int dir = (edge == EdgeType.EDGE_RIGHT) ? -1 : 1;
			int vDir = (int) Math.signum(vel.getX());
			if (dir == vDir)
				stop(shell);
			else
				kick(shell, edge);
		}
	}
	
	@Override
	protected void handleTerrainCollision(Entity shell, Entity terrain, EdgeType edge)
	{
		super.handleTerrainCollision(shell, terrain, edge);
		
		if(edge == EdgeType.EDGE_LEFT || edge == EdgeType.EDGE_RIGHT)
		{
			SoundSystem.pushSound(SoundSystem.QUIET_CLICK_SOUND, shell);
		}
	}

	// -------------------------------------------------------------------------

	private void kick(Entity shell, EdgeType collEdge)
	{
		int dir = (collEdge == EdgeType.EDGE_RIGHT) ? -1 : 1;

		Velocity vel = velocityMapper.get(shell);
		vel.setX(shellMapper.get(shell).getKickSpeed() * dir);
		
		SoundSystem.pushSound(SoundSystem.POP_SOUND, shell);
	}

	private void kick(Entity shell, Entity collider, EdgeType collEdge)
	{
		placeEntityOnOther(shell, collider, reverseEdge(collEdge));

		kick(shell, determineSideEdge(shell, collider, collEdge));
	}
	
	private EdgeType determineSideEdge(Entity shell, Entity collider, EdgeType collEdge)
	{
		if (collEdge == EdgeType.EDGE_TOP || collEdge == EdgeType.EDGE_BOTTOM)
		{
			float cMid = transformMapper.get(collider).getX()
					+ spatialMapper.get(collider).getWidth() / 2;
			float sMid = transformMapper.get(shell).getX() + spatialMapper.get(shell).getWidth()
					/ 2;
			return (cMid < sMid) ? EdgeType.EDGE_LEFT : EdgeType.EDGE_RIGHT;
		}
		else
		{
			return collEdge;
		}
	}

	private void stop(Entity shell)
	{
		velocityMapper.get(shell).setX(0);
		SoundSystem.pushSound(SoundSystem.POP_SOUND, shell);
	}
}
