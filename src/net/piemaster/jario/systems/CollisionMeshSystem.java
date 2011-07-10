package net.piemaster.jario.systems;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Transform;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class CollisionMeshSystem extends EntityProcessingSystem
{
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<CollisionMesh> meshMapper;

	@SuppressWarnings("unchecked")
	public CollisionMeshSystem()
	{
		super(Transform.class, CollisionMesh.class);
	}

	@Override
	public void initialize()
	{
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
		meshMapper = new ComponentMapper<CollisionMesh>(CollisionMesh.class, world);
	}

	@Override
	protected void process(Entity e)
	{
		CollisionMesh mesh = meshMapper.get(e);
		if(mesh != null)
		{
			Transform t = transformMapper.get(e);
			meshMapper.get(e).setLocation(t.getX(), t.getY());
		}
	}
}
