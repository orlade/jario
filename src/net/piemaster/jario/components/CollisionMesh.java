package net.piemaster.jario.components;

import org.newdawn.slick.geom.Polygon;

import com.artemis.Component;

public class CollisionMesh extends Component
{
	Polygon poly;
	
	public CollisionMesh(float x, float y, int width, int height)
	{
		this(width, height);
		poly.setLocation(x, y);
	}
	
	public CollisionMesh(int width, int height)
	{
		poly = new Polygon();
		
		poly.addPoint(0, 0);
		poly.addPoint(width, 0);
		poly.addPoint(width, height);
		poly.addPoint(0, height);
		poly.setClosed(true);
	}

	public CollisionMesh(Polygon poly)
	{
		this.poly = poly;
	}

	public Polygon getPoly()
	{
		return poly;
	}

	public void setPoly(Polygon poly)
	{
		this.poly = poly;
	}
	
	public void setLocation(float x, float y)
	{
		poly.setLocation(x, y);
	}
	
	public float getX()
	{
		return poly.getX();
	}
	
	public float getY()
	{
		return poly.getY();
	}
	
	public float getWidth()
	{
		return poly.getWidth();
	}
	
	public float getHeight()
	{
		return poly.getHeight();
	}
}
