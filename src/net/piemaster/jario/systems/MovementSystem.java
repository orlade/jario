package net.piemaster.jario.systems;

import net.piemaster.jario.components.Acceleration;
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
	
	private final float GRAVITY_ACCEL = 0.0005f;

	@SuppressWarnings("unchecked")
	public MovementSystem(GameContainer container)
	{
		super(Transform.class, Velocity.class, Acceleration.class, Physical.class);
	}

	@Override
	public void initialize()
	{
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world.getEntityManager());
		accelMapper = new ComponentMapper<Acceleration>(Acceleration.class, world.getEntityManager());
		physicalMapper = new ComponentMapper<Physical>(Physical.class, world.getEntityManager());
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Acceleration accel = accelMapper.get(e);
		Velocity vel = velocityMapper.get(e);
		Physical physical = physicalMapper.get(e);
		Transform transform = transformMapper.get(e);
		
//		float a = accel.getAcceleration();
//		float ar = accel.getAngleAsRadians();
//		float v = vel.getVelocity();
//		float r = vel.getAngleAsRadians();
//		
//		float ax = a * TrigLUT.cos(ar);
//		float ay = a * TrigLUT.sin(ar);
//
//		if(!accel.isOnGround())
//		{
//			ay -= 9.8;
//		}
//		
//		float nx = world.getDelta() * (v * TrigLUT.cos(r) + ax);
//		float ny = world.getDelta() * (v * TrigLUT.sin(r) + ay);
		
		// If the entity is moving
		physical.setMoving(!vel.isZero() || !accel.isZero());
		if(physical.isMoving() || physical.isJumping())
		{
			// Update acceleration with gravity
			accel.addY(GRAVITY_ACCEL);
			
			// Update velocity with acceleration
			vel.addX(accel.getX() * world.getDelta());
			vel.addY(accel.getY() * world.getDelta());
			
			physical.setMoving(!vel.isZero());
			
			// Calculate position from velocity
			transform.addX(vel.getX() * world.getDelta());
			transform.addY(vel.getY() * world.getDelta());
			
			// Reset acceleration for the frame
			accel.reset();
		}
	}

}
