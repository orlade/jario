package net.piemaster.jario.systems.rendering;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.spatials.Block;
import net.piemaster.jario.spatials.Explosion;
import net.piemaster.jario.spatials.Goomba;
import net.piemaster.jario.spatials.Missile;
import net.piemaster.jario.spatials.Parakoopa;
import net.piemaster.jario.spatials.Player;
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

		if ("PlayerImage".equalsIgnoreCase(spatialFormFile))
		{
			Player player = new Player(world, e);
			e.getComponent(CollisionMesh.class).setDimensions(player.getWidth(), player.getHeight());
			return player;
		}
		else if ("Goomba".equalsIgnoreCase(spatialFormFile))
		{
			Goomba goomba = new Goomba(world, e);
			e.getComponent(CollisionMesh.class).setDimensions(goomba.getWidth(), goomba.getHeight());
			return goomba;
		}
		else if ("Parakoopa".equalsIgnoreCase(spatialFormFile))
		{
			Parakoopa pk = new Parakoopa(world, e);
			e.getComponent(CollisionMesh.class).setDimensions(pk.getWidth(), pk.getHeight());
			return pk;
		}
		else if ("Block".equalsIgnoreCase(spatialFormFile))
		{
			int width = e.getComponent(SpatialForm.class).getWidth();
			int height = e.getComponent(SpatialForm.class).getHeight();
			return new Block(world, e, width, height);
		}
		else if ("Missile".equalsIgnoreCase(spatialFormFile))
		{
			return new Missile(world, e);
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