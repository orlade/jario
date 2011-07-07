package net.piemaster.jario.systems.rendering;

import net.piemaster.jario.loader.ImageLoader;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class TerrainRenderSystem extends EntitySystem
{
	private Graphics g;
	private Image tile;
	private net.piemaster.jario.systems.CameraSystem cs;

	public TerrainRenderSystem(GameContainer container)
	{
		super();

		this.g = container.getGraphics();
	}

	@Override
	public void initialize()
	{
		try
		{
			tile = ImageLoader.loadImage("/images/tile.png");
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}

		cs = world.getSystemManager().getSystem(net.piemaster.jario.systems.CameraSystem.class);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities)
	{

		float offsetX = cs.getStartX() % tile.getWidth();
		float offsetY = cs.getStartY() % tile.getHeight();

		int tilesWidth = (int) Math.ceil(cs.getWidth() / tile.getWidth()) + 1;
		int tilesHeight = (int) Math.ceil(cs.getHeight() / tile.getHeight()) + 1;

		g.scale(cs.getZoom(), cs.getZoom());

		g.translate(-offsetX, -offsetY);
		{
			for (int x = -1; tilesWidth > x; x++)
			{
				for (int y = -1; tilesHeight > y; y++)
				{
					tile.draw(x * tile.getWidth(), y * tile.getHeight());
				}
			}
		}
		g.resetTransform();

	}

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}

}
