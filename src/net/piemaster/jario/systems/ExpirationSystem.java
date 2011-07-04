package net.piemaster.jario.systems;

import net.piemaster.jario.components.Expires;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class ExpirationSystem extends EntityProcessingSystem
{

	private ComponentMapper<Expires> expiresMapper;

	public ExpirationSystem()
	{
		super(Expires.class);
	}

	@Override
	public void initialize()
	{
		expiresMapper = new ComponentMapper<Expires>(Expires.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Expires expires = expiresMapper.get(e);
		expires.reduceLife(world.getDelta());

		if (expires.isExpired())
		{
			world.deleteEntity(e);
		}
	}
}
