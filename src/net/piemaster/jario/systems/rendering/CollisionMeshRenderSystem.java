package net.piemaster.jario.systems.rendering;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.systems.CameraSystem;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class CollisionMeshRenderSystem extends EntityProcessingSystem
{
	private Graphics graphics;
	private ComponentMapper<CollisionMesh> meshMapper;
	private CameraSystem cameraSystem;
	
	private final Color ACTIVE_FILL = new Color(255, 0, 0, 0.3f);
	private final Color INACTIVE_FILL = new Color(0, 0, 255, 0.3f);

	public CollisionMeshRenderSystem(GameContainer container)
	{
		super(CollisionMesh.class);
		this.graphics = container.getGraphics();
	}

	@Override
	public void initialize()
	{
		meshMapper = new ComponentMapper<CollisionMesh>(CollisionMesh.class, world.getEntityManager());
		cameraSystem = world.getSystemManager().getSystem(CameraSystem.class);
	}

	@Override
	protected void begin()
	{
		graphics.scale(cameraSystem.getZoom(), cameraSystem.getZoom());
		graphics.translate(-cameraSystem.getStartX(), -cameraSystem.getStartY());
	}

	@Override
	protected void process(Entity e)
	{
		CollisionMesh mesh = meshMapper.get(e);
		Polygon poly = meshMapper.get(e).getPoly();
		graphics.setColor(mesh.isActive() ? ACTIVE_FILL : INACTIVE_FILL);
		graphics.setAntiAlias(true);
		graphics.fill(poly);
	}

	@Override
	protected void end()
	{
		graphics.resetTransform();
	}
}