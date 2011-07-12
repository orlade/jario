package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Shell;
import net.piemaster.jario.components.Velocity;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

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
		Velocity vel = velocityMapper.get(shell);

		if (vel.getX() == 0)
			kick(shell, player, edge);
		else if (edge == EdgeType.EDGE_TOP)
			stop(shell);
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

	// -------------------------------------------------------------------------

	private void kick(Entity shell, EdgeType collEdge)
	{
		int dir = (collEdge == EdgeType.EDGE_RIGHT) ? -1 : 1;

		Velocity vel = velocityMapper.get(shell);
		vel.setX(shellMapper.get(shell).getKickSpeed() * dir);
	}

	private void kick(Entity shell, Entity collider, EdgeType collEdge)
	{
		placeEntityOnOther(shell, collider, reverseEdge(collEdge));

		if (collEdge == EdgeType.EDGE_TOP || collEdge == EdgeType.EDGE_BOTTOM)
		{
			float cMid = transformMapper.get(collider).getX()
					+ spatialMapper.get(collider).getWidth() / 2;
			float sMid = transformMapper.get(shell).getX() + spatialMapper.get(shell).getWidth()
					/ 2;
			collEdge = (cMid < sMid) ? EdgeType.EDGE_LEFT : EdgeType.EDGE_RIGHT;
		}
		kick(shell, collEdge);
	}

	private void stop(Entity shell)
	{
		velocityMapper.get(shell).setX(0);
	}
}
