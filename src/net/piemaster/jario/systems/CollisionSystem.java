package net.piemaster.jario.systems;

import net.piemaster.jario.EntityFactory;
import net.piemaster.jario.components.Asteroid;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Score;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class CollisionSystem extends EntitySystem
{
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Health> healthMapper;
	private ComponentMapper<Asteroid> asteroidMapper;

	@SuppressWarnings("unchecked")
	public CollisionSystem()
	{
		super(Transform.class);
	}

	@Override
	public void initialize()
	{
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world.getEntityManager());
		healthMapper = new ComponentMapper<Health>(Health.class, world.getEntityManager());
		asteroidMapper = new ComponentMapper<Asteroid>(Asteroid.class, world.getEntityManager());
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities)
	{
		ImmutableBag<Entity> bullets = world.getGroupManager().getEntities("BULLETS");
		ImmutableBag<Entity> rocks = world.getGroupManager().getEntities("ASTEROIDS");
		Entity player = world.getTagManager().getEntity("PLAYER");

		if (rocks != null && (bullets != null || player != null))
		{
			rockLoop: for (int a = 0; rocks.size() > a; a++)
			{
				Entity rock = rocks.get(a);

				if (bullets != null)
				{
					for (int b = 0; bullets.size() > b; b++)
					{
						Entity bullet = bullets.get(b);

						if (collisionExists(bullet, rock))
						{
							Transform tb = transformMapper.get(bullet);
							EntityFactory.createBulletExplosion(world, tb.getX(), tb.getY())
									.refresh();

							world.deleteEntity(bullet);
							world.getGroupManager().remove(bullet);
							bullets = world.getGroupManager().getEntities("BULLETS");

							Health health = healthMapper.get(rock);
							health.addDamage(1);

							if (!health.isAlive())
							{
								handleAsteroidCollision(rock);
								player.getComponent(Score.class).incrementScore();

								continue rockLoop;
							}
						}
					}
				}

				if (player != null)
				{
					if (healthMapper.get(player).isAlive() && collisionExists(player, rock))
					{
						handleAsteroidCollision(rock);

						Health health = healthMapper.get(player);
						health.addDamage(1);

						// world.deleteEntity(player);
						// world.getTagManager().unregister("PLAYER");
						// player = null;
					}
				}
			}
		}
	}

	private boolean collisionExists(Entity e1, Entity e2)
	{
		Asteroid a = asteroidMapper.get(e2);
		int distance = 20;
		if (e2 != null)
		{
			distance = a.getSize() * 10;
		}

		Transform t1 = transformMapper.get(e1);
		Transform t2 = transformMapper.get(e2);
		return t1.getDistanceTo(t2) < distance;
	}

	private void handleAsteroidCollision(Entity rock)
	{
		Transform ts = transformMapper.get(rock);
		EntityFactory.createShipExplosion(world, ts.getX(), ts.getY()).refresh();

		Asteroid roid = asteroidMapper.get(rock);
		if (roid.getSize() > 1)
		{
			Entity e = EntityFactory
					.createAsteroid(world, ts.getX(), ts.getY(), roid.getSize() - 1);
			e.getComponent(Transform.class).setLocation(ts.getX(), ts.getY());
			e.getComponent(Velocity.class).setVelocity(0.05f);
			e.getComponent(Velocity.class).setAngle(velocityMapper.get(rock).getAngle() + 45);
			e.refresh();

			e = EntityFactory.createAsteroid(world, ts.getX(), ts.getY(), roid.getSize() - 1);
			e.getComponent(Velocity.class).setVelocity(0.05f);
			e.getComponent(Velocity.class).setAngle(velocityMapper.get(rock).getAngle() - 45);
			e.refresh();
		}

		world.deleteEntity(rock);
	}

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}

}
