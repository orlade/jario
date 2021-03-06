package net.piemaster.jario.systems;

import net.piemaster.jario.components.Acceleration;
import net.piemaster.jario.components.Globals;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;

import org.newdawn.slick.GameContainer;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class MovementSystem extends EntityProcessingSystem
{
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Acceleration> accelMapper;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Physical> physicalMapper;
	private ComponentMapper<Globals> globalsMapper;

	@SuppressWarnings("unchecked")
	public MovementSystem(GameContainer container)
	{
		super(Transform.class, Velocity.class, Acceleration.class, Physical.class, Globals.class);
	}

	@Override
	public void initialize()
	{
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world);
		accelMapper = new ComponentMapper<Acceleration>(Acceleration.class, world);
		physicalMapper = new ComponentMapper<Physical>(Physical.class, world);
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
		globalsMapper = new ComponentMapper<Globals>(Globals.class, world);
	}

	@Override
	protected void process(Entity e)
	{
		Acceleration accel = accelMapper.get(e);
		Velocity vel = velocityMapper.get(e);
		Physical physical = physicalMapper.get(e);
		Transform transform = transformMapper.get(e);
		Globals globals = globalsMapper.get(e);
		
		// If the entity is moving
		if(!physical.isGrounded() || (!vel.isZero() || !accel.isZero()))
		{
			// Update acceleration with gravity
			if(physical.isHasGravity())
			{
				accel.addY(globals.getGravity());
			}
			// Update acceleration with friction
			if(physical.isHasFriction())
			{
				accel.addX(vel.getX() * -globals.getFriction());
			}
			
			// Update velocity with acceleration
			vel.addX(accel.getX() * world.getDelta());
			vel.addY(accel.getY() * world.getDelta());
			
			physical.setMoving(!vel.isZero());
			physical.setGrounded(false);
			
			// Calculate position from velocity
			transform.addX(vel.getX() * world.getDelta());
			transform.addY(vel.getY() * world.getDelta());
			
			if(Math.abs(vel.getX()) < 0.001f)
			{
				vel.setX(0);
			}
			
			// Reset acceleration for the frame
			accel.reset();
		}
	}
}
