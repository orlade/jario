package net.piemaster.jario.systems;

import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;

import org.newdawn.slick.GameContainer;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class PlayerShipMovementSystem extends EntityProcessingSystem
{
	private GameContainer container;
	private ComponentMapper<Transform> transformMapper;

	@SuppressWarnings("unchecked")
	public PlayerShipMovementSystem(GameContainer container)
	{
		super(Transform.class, Player.class, Velocity.class);
		this.container = container;
	}

	@Override
	public void initialize()
	{
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Transform transform = transformMapper.get(e);

		if (transform.getX() > container.getWidth())
		{
			transform.setX(0);
		}
		else if(transform.getX() < 0)
		{
			transform.setX(container.getWidth());
		}
		
		if (transform.getY() > container.getHeight())
		{
			transform.setY(0);
		}
		else if(transform.getY() < 0)
		{
			transform.setY(container.getHeight());
		}
	}

}
