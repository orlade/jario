package net.piemaster.jario.systems;

import net.piemaster.jario.EntityFactory;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class PlayerShipControlSystem extends EntityProcessingSystem implements KeyListener
{
	private GameContainer container;
	private boolean moveForward;
	private boolean moveBack;
	private boolean turnLeft;
	private boolean turnRight;
	private boolean shoot;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Health> healthMapper;

	@SuppressWarnings("unchecked")
	public PlayerShipControlSystem(GameContainer container)
	{
		super(Transform.class, Player.class, Health.class);
		this.container = container;
	}

	@Override
	public void initialize()
	{
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world.getEntityManager());
		healthMapper = new ComponentMapper<Health>(Health.class, world.getEntityManager());
		container.getInput().addKeyListener(this);
	}

	@Override
	protected void process(Entity e)
	{
		if(healthMapper.get(e).isAlive())
		{
			Transform transform = transformMapper.get(e);
			Velocity vel = velocityMapper.get(e);
			
			if (turnLeft)
			{
				transform.addRotation(world.getDelta() * -0.3f);
			}
			if (turnRight)
			{
				transform.addRotation(world.getDelta() * 0.3f);
			}
			if (moveForward || moveBack)
			{
				float thrust = moveForward ? 0.0003f : -0.0003f;
				float curX = vel.getVelocity() * (float)Math.cos(vel.getAngleAsRadians());
				float curY = vel.getVelocity() * (float)Math.sin(vel.getAngleAsRadians());
				
				float addX = world.getDelta() * thrust * (float)Math.sin(transform.getRotationAsRadians());
				float addY = world.getDelta() * thrust * (float)-Math.cos(transform.getRotationAsRadians());
				
				float newX = curX + addX;
				float newY = curY + addY;
				
				vel.setAngle((float)Math.toDegrees(Math.atan2(newY, newX)));
				vel.setVelocity((float)Math.sqrt(Math.pow(newX, 2) + Math.pow(newY, 2)));
			}
			
			if (shoot)
			{
				Entity missile = EntityFactory.createMissile(world, transform);
				float startX = transform.getX() + 20 * (float)Math.sin(transform.getRotationAsRadians());
				float startY = transform.getY() + 20 * (float)-Math.cos(transform.getRotationAsRadians());
				missile.getComponent(Transform.class).setLocation(startX, startY);
				missile.getComponent(Velocity.class).setVelocity(-0.5f);
				missile.getComponent(Velocity.class).setAngle(transform.getRotation() + 90);
				missile.refresh();
				
				shoot = false;
			}
		}
	}

	@Override
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_LEFT)
		{
			turnLeft = true;
			turnRight = false;
		}
		else if (key == Input.KEY_RIGHT)
		{
			turnRight = true;
			turnLeft = false;
		}
		else if (key == Input.KEY_UP)
		{
			moveForward = true;
			moveBack = false;
		}
		else if (key == Input.KEY_DOWN)
		{
			moveBack = true;
			moveForward = false;
		}
		else if (key == Input.KEY_SPACE)
		{
			shoot = true;
		}
	}

	@Override
	public void keyReleased(int key, char c)
	{
		if (key == Input.KEY_LEFT)
		{
			turnLeft = false;
		}
		else if (key == Input.KEY_RIGHT)
		{
			turnRight = false;
		}
		else if (key == Input.KEY_UP)
		{
			moveForward = false;
		}
		else if (key == Input.KEY_DOWN)
		{
			moveBack = false;
		}
		else if (key == Input.KEY_SPACE)
		{
			shoot = false;
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
