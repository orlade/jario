package net.piemaster.jario.systems;

import net.piemaster.jario.EntityFactory;
import net.piemaster.jario.components.Acceleration;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Jumping;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Transform;
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
	private float speed = 0.4f;

	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Physical> physicalMapper;
	private ComponentMapper<Health> healthMapper;
	private ComponentMapper<Jumping> jumpMapper;
	private ComponentMapper<Transform> transformMapper;

	@SuppressWarnings("unchecked")
	public PlayerControlSystem(GameContainer container)
	{
		super(Player.class, Velocity.class, Health.class, Acceleration.class, Physical.class);
		this.container = container;
	}

	@Override
	public void initialize()
	{
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world.getEntityManager());
		physicalMapper = new ComponentMapper<Physical>(Physical.class, world.getEntityManager());
		healthMapper = new ComponentMapper<Health>(Health.class, world.getEntityManager());
		jumpMapper = new ComponentMapper<Jumping>(Jumping.class, world.getEntityManager());
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
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
		Transform transform = transformMapper.get(player);
		Jumping jump = jumpMapper.get(player);

		if (health.isAlive())
		{
			Velocity velocity = velocityMapper.get(player);
			Physical physical = physicalMapper.get(player);

			float multi = 1;

			if (container.getInput().isKeyDown(Input.KEY_LSHIFT))
			{
				multi = 2;
			}
			else if (container.getInput().isKeyDown(Input.KEY_LCONTROL))
			{
				multi = 0.1f;
			}

			if (key == Input.KEY_LEFT)
			{
				velocity.setX(-speed * multi);
				transform.setFacingRight(false);
				physical.setGrounded(false);
			}
			else if (key == Input.KEY_RIGHT)
			{
				velocity.setX(speed * multi);
				transform.setFacingRight(true);
				physical.setGrounded(false);
			}
			else if (key == Input.KEY_SPACE && physical.isGrounded() && jump != null)
			{
				velocity.setY(-jump.getJumpFactor());
				physical.setJumping(true);
				physical.setGrounded(false);
			}
			else if (key == Input.KEY_F)
			{
				Transform t = player.getComponent(Transform.class);
				Entity fireball = EntityFactory.createFireball(world, t.getX(), t.getY());
				if (!transform.isFacingRight())
				{
					Velocity v = fireball.getComponent(Velocity.class);
					v.setX(-v.getX());
				}
				fireball.refresh();
			}
		}
	}

	@Override
	public void keyReleased(int key, char c)
	{
		Entity player = world.getTagManager().getEntity("PLAYER");
		Velocity velocity = velocityMapper.get(player);

		if (key == Input.KEY_LEFT && velocity.getX() < 0)
		{
			velocity.setX(0);
		}
		else if (key == Input.KEY_RIGHT && velocity.getX() > 0)
		{
			velocity.setX(0);
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
