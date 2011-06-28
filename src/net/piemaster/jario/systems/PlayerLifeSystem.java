package net.piemaster.jario.systems;

import net.piemaster.jario.EntityFactory;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Respawn;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class PlayerLifeSystem extends EntitySystem
{
	private ComponentMapper<Health> healthMapper;
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<SpatialForm> spatialMapper;

	@SuppressWarnings("unchecked")
	public PlayerLifeSystem()
	{
		super(Player.class, Health.class, Transform.class, Velocity.class, SpatialForm.class, Respawn.class);
	}

	@Override
	public void initialize()
	{
		healthMapper = new ComponentMapper<Health>(Health.class, world.getEntityManager());
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world.getEntityManager());
		spatialMapper = new ComponentMapper<SpatialForm>(SpatialForm.class, world.getEntityManager());
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities)
	{
		for (int i = 0; entities.size() > i; i++)
		{
			Entity player = entities.get(i);
			Health health = healthMapper.get(player);
			if(!health.isAlive())
			{
				Respawn respawn = player.getComponent(Respawn.class);
				if(!respawn.isActive())
				{
					velocityMapper.get(player).setVelocity(0);
					spatialMapper.get(player).setVisible(false);
					respawn.resetTimer();
					respawn.setActive(true);
					
					Transform tp = player.getComponent(Transform.class);
					EntityFactory.createShipExplosion(world, tp.getX(), tp.getY()).refresh();
				}
			}
		}
	}

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}

}
