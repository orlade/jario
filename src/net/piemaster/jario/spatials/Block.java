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
	
	private static final float WIDTH = 200;
	private static final float HEIGHT = 50;

	public Block(World world, Entity owner)
	{
		super(world, owner);
	}

	@Override
	public void initalize()
	{
		ComponentMapper<Transform> transformMapper = new ComponentMapper<Transform>(
				Transform.class, world.getEntityManager());
		transform = transformMapper.get(owner);

		polygon = new Polygon();
		
		polygon.addPoint(0, 0);
		polygon.addPoint(WIDTH, 0);
		polygon.addPoint(WIDTH, HEIGHT);
		polygon.addPoint(0, HEIGHT);
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
}
