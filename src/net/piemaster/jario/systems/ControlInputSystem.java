package net.piemaster.jario.systems;

import net.piemaster.jario.components.Acceleration;
import net.piemaster.jario.components.FireballShooter;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.InputMovement;
import net.piemaster.jario.components.Jumping;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;
import net.piemaster.jario.entities.EntityFactory;
import net.piemaster.jario.systems.delayed.InvulnerabilityHandler;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class ControlInputSystem extends EntityProcessingSystem implements KeyListener
{
	private GameContainer container;
	private float accelAmount = 0.0025f;

	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Acceleration> accelMapper;
	private ComponentMapper<Physical> physicalMapper;
	private ComponentMapper<Health> healthMapper;
	private ComponentMapper<Jumping> jumpMapper;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<InputMovement> inputMapper;

	@SuppressWarnings("unchecked")
	public ControlInputSystem(GameContainer container)
	{
		super(InputMovement.class, Velocity.class, Health.class, Physical.class);
		this.container = container;
	}

	@Override
	public void initialize()
	{
		container.getInput().addKeyListener(this);
		
		inputMapper = new ComponentMapper<InputMovement>(InputMovement.class,
				world.getEntityManager());
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world.getEntityManager());
		accelMapper = new ComponentMapper<Acceleration>(Acceleration.class,
				world.getEntityManager());
		physicalMapper = new ComponentMapper<Physical>(Physical.class, world.getEntityManager());
		healthMapper = new ComponentMapper<Health>(Health.class, world.getEntityManager());
		jumpMapper = new ComponentMapper<Jumping>(Jumping.class, world.getEntityManager());
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		InputMovement input = inputMapper.get(e);
		Health health = healthMapper.get(e);
		Transform transform = transformMapper.get(e);
		Jumping jump = jumpMapper.get(e);

		if (health.isAlive())
		{
			Velocity velocity = velocityMapper.get(e);
			Acceleration accel = accelMapper.get(e);
			Physical physical = physicalMapper.get(e);

			// Movement modifiers
			float multi = 1;
			if (container.getInput().isKeyDown(Input.KEY_LSHIFT))
			{
				multi = 2;
			}
			if (container.getInput().isKeyDown(Input.KEY_LCONTROL))
			{
				multi = 0.1f;
			}

			// Jumping
			if (container.getInput().isKeyPressed(input.getJump()) && physical.isGrounded() && jump != null)
			{
				velocity.setY(-jump.getJumpFactor());
				physical.setJumping(true);
			}

			// Movement
			if (container.getInput().isKeyDown(input.getLeft()))
			{
				accel.setX(-accelAmount * multi);
				transform.setFacingRight(false);
			}
			else if (container.getInput().isKeyDown(input.getRight()))
			{
				accel.setX(accelAmount * multi);
				transform.setFacingRight(true);
			}
			else
			{
				accel.setX(0);
				velocity.setX(0);
			}

			// Shooting
			if (container.getInput().isKeyPressed(Input.KEY_F) && e.getComponent(FireballShooter.class) != null)
			{
				Entity fireball = EntityFactory.createFireball(world, transform.getX(),
						transform.getY() + 30);
				if (!transform.isFacingRight())
				{
					Velocity v = velocityMapper.get(fireball);
					v.setX(-v.getX());
				}
				transformMapper.get(fireball).setFacingRight(transform.isFacingRight());
				fireball.refresh();
			}
			
			// DEBUG: Invulnerability
			if (container.getInput().isKeyPressed(Input.KEY_I))
			{
				InvulnerabilityHandler.setTemporaryInvulnerability(world, e, 2000, true);
			}
			// DEBUG: Move
			if (container.getInput().isKeyPressed(Input.KEY_L))
			{
				transformMapper.get(e).addX(10);
			}
			// DEBUG: Move
			if (container.getInput().isKeyPressed(Input.KEY_K))
			{
				transformMapper.get(e).addX(-10);
			}
		}
	}

	@Override
	public void keyPressed(int key, char c)
	{
	}

	@Override
	public void keyReleased(int key, char c)
	{
	}

	@Override
	public boolean isAcceptingInput()
	{
		return true;
	}

	@Override
	public void inputStarted()
	{
	}

	@Override
	public void inputEnded()
	{
	}

	@Override
	public void setInput(Input input)
	{
	}
}
