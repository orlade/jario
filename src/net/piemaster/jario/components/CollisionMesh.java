package net.piemaster.jario.components;

import org.newdawn.slick.geom.Polygon;

import com.artemis.Component;

public class CollisionMesh extends Component
{
	private Polygon poly;
	private boolean active = true;

	public CollisionMesh()
	{
		poly = new Polygon();
	}

	public CollisionMesh(float x, float y, int width, int height)
	{
		this(width, height);
		poly.setLocation(x, y);
	}

	public CollisionMesh(int width, int height)
	{
		setPoly(createBoxPoly(width, height));
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

	public void setDimensions(float width, float height)
	{
		if (width != getWidth() || height != getHeight())
		{
			setPoly(createBoxPoly(width, height));
		}
	}

	public void setWidth(float width)
	{
		setPoly(createBoxPoly(width, poly.getHeight()));
	}

	public void setHeight(float height)
	{
		setPoly(createBoxPoly(poly.getWidth(), height));
	}

	protected Polygon createBoxPoly(float width, float height)
	{
		Polygon p = new Polygon();
		p.addPoint(0, 0);
		p.addPoint(width, 0);
		p.addPoint(width, height);
		p.addPoint(0, height);
		p.setClosed(true);
		return p;
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

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}
}
