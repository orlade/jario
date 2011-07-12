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

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class ControlInputSystem extends EntityProcessingSystem
{
	private Input input;
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
		
		this.input = container.getInput();
	}

	@Override
	public void initialize()
	{
		inputMapper = new ComponentMapper<InputMovement>(InputMovement.class,
				world);
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world);
		accelMapper = new ComponentMapper<Acceleration>(Acceleration.class,
				world);
		physicalMapper = new ComponentMapper<Physical>(Physical.class, world);
		healthMapper = new ComponentMapper<Health>(Health.class, world);
		jumpMapper = new ComponentMapper<Jumping>(Jumping.class, world);
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
	}

	@Override
	protected void process(Entity e)
	{
		InputMovement in = inputMapper.get(e);
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
			if (input.isKeyDown(Input.KEY_LSHIFT))
			{
				multi = 2;
			}
			if (input.isKeyDown(Input.KEY_LCONTROL))
			{
				multi = 0.1f;
			}

			// Jumping
			if (input.isKeyPressed(in.getJump()) && physical.isGrounded() && jump != null)
			{
				velocity.setY(-jump.getJumpFactor());
				physical.setJumping(true);
				SoundSystem.pushSound(SoundSystem.JUMP_SOUND, e);
			}

			// Movement
			if (input.isKeyDown(in.getLeft()))
			{
				accel.setX(-accelAmount * multi);
				transform.setFacingRight(false);
			}
			else if (input.isKeyDown(in.getRight()))
			{
				accel.setX(accelAmount * multi);
				transform.setFacingRight(true);
			}

			// Shooting
			if (input.isKeyPressed(Input.KEY_F) && e.getComponent(FireballShooter.class) != null)
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
				SoundSystem.pushSound(SoundSystem.LASER_SOUND, e);
			}
			
			// DEBUG: Invulnerability
			if (input.isKeyPressed(Input.KEY_I))
			{
				InvulnerabilityHandler.setTemporaryInvulnerability(world, e, 2000, true);
			}
			// DEBUG: Move
			if (input.isKeyPressed(Input.KEY_L))
			{
				transformMapper.get(e).addX(10);
			}
			// DEBUG: Move
			if (input.isKeyPressed(Input.KEY_K))
			{
				transformMapper.get(e).addX(-10);
			}
		}
	}
}
