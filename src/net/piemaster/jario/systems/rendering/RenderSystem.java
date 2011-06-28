package net.piemaster.jario.systems.rendering;

import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.spatials.AsteroidSpatial;
import net.piemaster.jario.spatials.Block;
import net.piemaster.jario.spatials.Explosion;
import net.piemaster.jario.spatials.Missile;
import net.piemaster.jario.spatials.PlayerImage;
import net.piemaster.jario.spatials.PlayerShip;
import net.piemaster.jario.spatials.Spatial;
import net.piemaster.jario.systems.CameraSystem;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.utils.Bag;

public class RenderSystem extends EntityProcessingSystem
{
	private Graphics graphics;
	private Bag<Spatial> spatials;
	private ComponentMapper<SpatialForm> spatialFormMapper;
	private CameraSystem cameraSystem;

	public RenderSystem(GameContainer container)
	{
		super(SpatialForm.class);
		this.graphics = container.getGraphics();
	}

	@Override
	public void initialize()
	{
		spatialFormMapper = new ComponentMapper<SpatialForm>(SpatialForm.class, world.getEntityManager());

		cameraSystem = world.getSystemManager().getSystem(CameraSystem.class);

		spatials = new Bag<Spatial>();
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
		Spatial spatial = spatials.get(e.getId());
		spatial.render(graphics);
	}

	@Override
	protected void end()
	{
		graphics.resetTransform();
	}

	@Override
	protected void added(Entity e)
	{
		Spatial spatial = createSpatial(e);
		if (spatial != null)
		{
			spatial.initalize();
			spatials.set(e.getId(), spatial);
		}
	}

	@Override
	protected void removed(Entity e)
	{
		spatials.set(e.getId(), null);
	}

	private Spatial createSpatial(Entity e)
	{
		SpatialForm spatialForm = spatialFormMapper.get(e);
		String spatialFormFile = spatialForm.getSpatialFormFile();

		if ("PlayerShip".equalsIgnoreCase(spatialFormFile))
		{
			return new PlayerShip(world, e);
		}
		else if ("PlayerImage".equalsIgnoreCase(spatialFormFile))
		{
			return new PlayerImage(world, e);
		}
		if ("Block".equalsIgnoreCase(spatialFormFile))
		{
			return new Block(world, e);
		}
		else if ("Missile".equalsIgnoreCase(spatialFormFile))
		{
			return new Missile(world, e);
		}
		else if ("Asteroid".equalsIgnoreCase(spatialFormFile))
		{
			return new AsteroidSpatial(world, e);
		}
		else if ("BulletExplosion".equalsIgnoreCase(spatialFormFile))
		{
			return new Explosion(world, e, 10);
		}
		else if ("ShipExplosion".equalsIgnoreCase(spatialFormFile))
		{
			return new Explosion(world, e, 30);
		}

		return null;
	}
}