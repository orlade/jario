package net.piemaster.jario.systems.handling.utils;

import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Entity;

public class CollisionInfo
{
	private Entity a;
	private Entity b;
	private EdgeType edge;
	
	public CollisionInfo(Entity a, Entity b, EdgeType edge)
	{
		this.a = a;
		this.b = b;
		this.edge = edge;
	}

	public Entity getA()
	{
		return a;
	}

	public Entity getB()
	{
		return b;
	}

	public EdgeType getEdge()
	{
		return edge;
	}
}
