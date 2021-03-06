package net.piemaster.jario.systems.rendering;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Item.ItemType;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.spatials.Block;
import net.piemaster.jario.spatials.Terrain;
import net.piemaster.jario.spatials.Coin;
import net.piemaster.jario.spatials.Fireball;
import net.piemaster.jario.spatials.Flower;
import net.piemaster.jario.spatials.ItemBox;
import net.piemaster.jario.spatials.Missile;
import net.piemaster.jario.spatials.Mushroom;
import net.piemaster.jario.spatials.Platform;
import net.piemaster.jario.spatials.Shell;
import net.piemaster.jario.spatials.Star;
import net.piemaster.jario.spatials.enemies.Goomba;
import net.piemaster.jario.spatials.enemies.KoopaComposer;
import net.piemaster.jario.spatials.generic.Spatial;
import net.piemaster.jario.spatials.player.PlayerComposer;
import net.piemaster.jario.systems.CameraSystem;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.Log;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.utils.Bag;

public class RenderSystem extends EntityProcessingSystem
{
	private Graphics graphics;
	private Bag<Spatial> spatials;
	private ComponentMapper<SpatialForm> formMapper;
	private CameraSystem cameraSystem;

	public RenderSystem(GameContainer container)
	{
		super(SpatialForm.class);
		this.graphics = container.getGraphics();
	}

	@Override
	public void initialize()
	{
		formMapper = new ComponentMapper<SpatialForm>(SpatialForm.class, world);

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
		if(formMapper.get(e).isVisible())
		{
			Spatial spatial = spatials.get(e.getId());
			spatial.render(graphics);
		}
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

			SpatialForm form = formMapper.get(e);
			for (Runnable callback : form.getLoadedCallbacks())
			{
				callback.run();
			}
			form.clearLoadedCallbacks();
		}
	}

	@Override
	protected void removed(Entity e)
	{
		spatials.set(e.getId(), null);
	}

	private Spatial createSpatial(Entity e)
	{
		SpatialForm spatialForm = formMapper.get(e);
		String spatialFormFile = spatialForm.getSpatialFormFile();

		if ("PlayerImage".equalsIgnoreCase(spatialFormFile))
		{
			return setupGenericSpatialEntity(e, new PlayerComposer(world, e));
		}
		else if ("Goomba".equalsIgnoreCase(spatialFormFile))
		{
			return setupGenericSpatialEntity(e, new Goomba(world, e));
		}
		else if ("Parakoopa".equalsIgnoreCase(spatialFormFile))
		{
			return setupGenericSpatialEntity(e, new KoopaComposer(world, e));
		}
		else if ("Shell".equalsIgnoreCase(spatialFormFile))
		{
			return setupGenericSpatialEntity(e, new Shell(world, e));
		}
		else if ("ItemBox".equalsIgnoreCase(spatialFormFile))
		{
			return setupGenericSpatialEntity(e, new ItemBox(world, e));
		}
		else if (ItemType.MUSHROOM.toString().equalsIgnoreCase(spatialFormFile))
		{
			return setupGenericSpatialEntity(e, new Mushroom(world, e));
		}
		else if (ItemType.FLOWER.toString().equalsIgnoreCase(spatialFormFile))
		{
			return setupGenericSpatialEntity(e, new Flower(world, e));
		}
		else if (ItemType.STAR.toString().equalsIgnoreCase(spatialFormFile))
		{
			return setupGenericSpatialEntity(e, new Star(world, e));
		}
		else if (ItemType.COIN.toString().equalsIgnoreCase(spatialFormFile))
		{
			return setupGenericSpatialEntity(e, new Coin(world, e));
		}
		else if ("Fireball".equalsIgnoreCase(spatialFormFile))
		{
			return setupGenericSpatialEntity(e, new Fireball(world, e));
		}
		else if ("Block".equalsIgnoreCase(spatialFormFile))
		{
			return setupGenericSpatialEntity(e, new Block(world, e));
		}
		else if ("Terrain".equalsIgnoreCase(spatialFormFile))
		{
			int width = (int) e.getComponent(SpatialForm.class).getWidth();
			int height = (int) e.getComponent(SpatialForm.class).getHeight();
			return new Terrain(world, e, width, height);
		}
		else if ("Platform".equalsIgnoreCase(spatialFormFile))
		{
			int width = (int) e.getComponent(SpatialForm.class).getWidth();
			int height = (int) e.getComponent(SpatialForm.class).getHeight();
			return new Platform(world, e, width, height);
		}
		else if ("Missile".equalsIgnoreCase(spatialFormFile))
		{
			return new Missile(world, e);
		}
		else
		{
			Log.warn("Unknown spatial form: " + spatialFormFile);
			return null;
		}
	}

	/**
	 * For the given entity, set the dimensions of the collision mesh and spaital components.
	 */
	private Spatial setupGenericSpatialEntity(Entity e, Spatial spatial)
	{
		e.getComponent(CollisionMesh.class).setDimensions(spatial.getWidth(), spatial.getHeight());
		e.getComponent(SpatialForm.class).setWidth(spatial.getWidth());
		e.getComponent(SpatialForm.class).setHeight(spatial.getHeight());
		return spatial;
	}
}