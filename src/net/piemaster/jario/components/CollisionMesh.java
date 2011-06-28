package net.piemaster.jario.components;

import org.newdawn.slick.geom.Polygon;

import com.artemis.Component;

public class CollisionMesh extends Component
{
	Polygon poly;
	
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
}
