package net.piemaster.jario.spatials;

import net.piemaster.jario.components.Transform;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

public class Missile extends Spatial
{
	private Transform transform;
	private Polygon missile;
	
	private static final float LENGTH = 6;
	private static final float WIDTH = 2;

	public Missile(World world, Entity owner)
	{
		super(world, owner);
	}

	@Override
	public void initalize()
	{
		ComponentMapper<Transform> transformMapper = new ComponentMapper<Transform>(
				Transform.class, world.getEntityManager());
		transform = transformMapper.get(owner);

		missile = new Polygon();
		
		float c = (float)Math.cos(transform.getRotationAsRadians());
		float s = (float)Math.sin(transform.getRotationAsRadians());
		
		missile.addPoint(-LENGTH*s - WIDTH*c, LENGTH*c - WIDTH*s);
		missile.addPoint(-LENGTH*s, LENGTH*c);
		missile.addPoint(LENGTH*s, -LENGTH*c);
		missile.addPoint(LENGTH*s - WIDTH*c, -LENGTH*c - WIDTH*s);
		missile.setClosed(true);
	}

	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.setAntiAlias(true);
		missile.setLocation(transform.getX(), transform.getY());
		g.fill(missile);
		
//		g.setColor(Color.white);
//		g.setAntiAlias(true);
//		g.fillRect(transform.getX() - 1, transform.getY() - 3, 2, 6);
	}

}
