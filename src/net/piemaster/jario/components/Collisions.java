package net.piemaster.jario.components;

import java.util.ArrayList;

import net.piemaster.jario.systems.CollisionSystem;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Component;

public class Collisions extends Component
{
	private ArrayList<Integer> targetIds;
	private ArrayList<EdgeType> edges;

	public Collisions()
	{
		this.targetIds = new ArrayList<Integer>();
		this.edges = new ArrayList<CollisionSystem.EdgeType>();
	}

	public ArrayList<Integer> getTargetIds()
	{
		return targetIds;
	}

	public ArrayList<EdgeType> getEdges()
	{
		return edges;
	}
	
	public void push(Integer targetId, EdgeType edge)
	{
		targetIds.add(targetId);
		edges.add(edge);
	}
	
	public int getSize()
	{
		return targetIds.size();
	}
	
	public void clear()
	{
		targetIds.clear();
		edges.clear();
	}
	
//	public void pop()
//	{
//		targetIds.remove(targetIds.size());
//		edges.remove(edges.size());
//	}
}
