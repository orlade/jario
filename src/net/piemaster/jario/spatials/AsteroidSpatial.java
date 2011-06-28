package net.piemaster.jario.spatials;

import java.util.Random;

import net.piemaster.jario.components.Asteroid;
import net.piemaster.jario.components.Transform;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

public class AsteroidSpatial extends Spatial
{
	private Transform transform;
	private Polygon rock;

	public AsteroidSpatial(World world, Entity owner)
	{
		super(world, owner);
	}

	@Override
	public void initalize()
	{
		transform = new ComponentMapper<Transform>(Transform.class, world.getEntityManager()).get(owner);
		Asteroid a = new ComponentMapper<Asteroid>(Asteroid.class, world.getEntityManager()).get(owner);
		int size = a.getSize();
		
		Random r = new Random();
		rock = new Polygon();
		int skew = 1;
		int radius = 10;
		
		int sides = 5 + r.nextInt(5);
		for(int i=0; i < sides; i++)
		{
			float angle = (float)(2*i*Math.PI/sides) + r.nextFloat();
			float c = (float)Math.cos(angle);
			float s = (float)Math.sin(angle);
			int rx = r.nextInt(skew*2+1)-skew;
			int ry = r.nextInt(skew*2+1)-skew;
			rock.addPoint(size*radius*c + rx, size*radius*s + ry);
		}
		rock.setClosed(true);
	}

	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.setAntiAlias(true);
		rock.setLocation(transform.getX(), transform.getY());
		g.fill(rock);
	}

}
