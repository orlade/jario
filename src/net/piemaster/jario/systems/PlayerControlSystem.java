package net.piemaster.jario.systems;

import net.piemaster.jario.EntityFactory;
import net.piemaster.jario.components.Acceleration;
import net.piemaster.jario.components.FireballShooter;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Jumping;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;
import net.piemaster.jario.systems.delayed.InvulnerabilityHandler;

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
				if (player.getComponent(FireballShooter.class) != null)
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
			}
			// Press I for two seconds of invulnerability
			else if (key == Input.KEY_I)
			{
				InvulnerabilityHandler.setTemporaryInvulnerability(world, player, 2000, true);
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
