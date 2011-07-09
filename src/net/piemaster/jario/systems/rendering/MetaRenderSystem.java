package net.piemaster.jario.systems.rendering;

import net.piemaster.jario.components.Meta;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.systems.CameraSystem;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class MetaRenderSystem extends EntityProcessingSystem
{
	private Graphics graphics;
	private ComponentMapper<Transform> transMapper;
	private CameraSystem cameraSystem;
	
	private final Color DRAW_COLOUR = new Color(255, 255, 0, 1f);

	@SuppressWarnings("unchecked")
	public MetaRenderSystem(GameContainer container)
	{
		super(Meta.class, Transform.class);
		
		this.graphics = container.getGraphics();
	}

	@Override
	public void initialize()
	{
		transMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
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
		Transform t = transMapper.get(e);
		
		graphics.setColor(DRAW_COLOUR);
		graphics.setAntiAlias(true);
		graphics.drawOval(t.getX(), t.getY(), 10, 10);
//		graphics.fillOval(t.getX(), t.getY(), 10, 10);
	}

	@Override
	protected void end()
	{
		graphics.resetTransform();
	}
}