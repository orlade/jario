package net.piemaster.jario.systems;

import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Velocity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class PlayerControlSystem extends EntityProcessingSystem implements KeyListener
{
	private GameContainer container;
	private float speed = 0.3f;
	
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Health> healthMapper;

	@SuppressWarnings("unchecked")
	public PlayerControlSystem(GameContainer container)
	{
		super(Player.class, Velocity.class);
		this.container = container;
	}

	@Override
	public void initialize()
	{
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world.getEntityManager());
		healthMapper = new ComponentMapper<Health>(Health.class, world.getEntityManager());
		container.getInput().addKeyListener(this);
	}

	@Override
	protected void process(Entity e)
	{
	}

	@Override
	public void keyPressed(int key, char c)
	{
		Entity player = world.getTagManager().getEntity("PLAYER");
		Health health = healthMapper.get(player);
		
		if(health.isAlive())
		{
			Velocity velocity = velocityMapper.get(player);
			
			if (key == Input.KEY_LEFT)
			{
				velocity.setVelocity(-speed);
			}
			else if (key == Input.KEY_RIGHT)
			{
				velocity.setVelocity(speed);
			}
			else if (key == Input.KEY_SPACE)
			{
			}
		}
	}

	@Override
	public void keyReleased(int key, char c)
	{
		Entity player = world.getTagManager().getEntity("PLAYER");
		Velocity velocity = velocityMapper.get(player);
		
		if (key == Input.KEY_LEFT && velocity.getVelocity() < 0)
		{
			velocity.setVelocity(0);
		}
		else if (key == Input.KEY_RIGHT && velocity.getVelocity() > 0)
		{
			velocity.setVelocity(0);
		}
		else if (key == Input.KEY_SPACE)
		{
		}
	}

	@Override
	public void inputEnded()
	{
	}

	@Override
	public void inputStarted()
	{
	}

	@Override
	public boolean isAcceptingInput()
	{
		return true;
	}

	@Override
	public void setInput(Input input)
	{
	}
}
