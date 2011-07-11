package net.piemaster.jario.systems;

import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.InputMovement;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;

import org.newdawn.slick.state.StateBasedGame;

import com.artemis.ComponentMapper;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class LevelWinSystem extends EntityProcessingSystem
{
//	private StateBasedGame sbg;

	private ComponentMapper<Health> healthMapper;
	private ComponentMapper<Transform> transformMapper;

	@SuppressWarnings("unchecked")
	public LevelWinSystem(StateBasedGame sbg)
	{
		super(Player.class, Transform.class);

//		this.sbg = sbg;
	}

	@Override
	public void initialize()
	{
		healthMapper = new ComponentMapper<Health>(Health.class, world);
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
	}

	@Override
	protected void process(Entity e)
	{
		Transform t = transformMapper.get(e);
		Transform et = transformMapper.get(world.getTagManager().getEntity("END_POINT"));

		if (t.getX() > et.getX() && healthMapper.get(e).isAlive())
		{
			e.removeComponent(ComponentTypeManager.getTypeFor(InputMovement.class));
			e.refresh();
			e.getComponent(Physical.class).setHasFriction(false);
			e.getComponent(Velocity.class).setX(0.1f);
		}
	}
}
