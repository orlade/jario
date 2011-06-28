package net.piemaster.jario.spatials;

import net.piemaster.jario.components.Transform;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

public class Block extends Spatial
{
	private Transform transform;
	private Polygon polygon;
	private int width;
	private int height;

	public Block(World world, Entity owner, int width, int height)
	{
		super(world, owner);

		this.width = width;
		this.height = height;
	}

	@Override
	public void initalize()
	{
		ComponentMapper<Transform> transformMapper = new ComponentMapper<Transform>(
				Transform.class, world.getEntityManager());
		transform = transformMapper.get(owner);

		polygon = new Polygon();
		
		polygon.addPoint(0, 0);
		polygon.addPoint(width, 0);
		polygon.addPoint(width, height);
		polygon.addPoint(0, height);
		polygon.setClosed(true);
	}

	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.setAntiAlias(true);
		polygon.setLocation(transform.getX(), transform.getY());
		g.fill(polygon);
	}

	@Override
	public float getWidth()
	{
		return width;
	}

	@Override
	public float getHeight()
	{
		return height;
	}
}
