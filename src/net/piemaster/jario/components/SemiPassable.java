package net.piemaster.jario.components;

import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Component;

public class SemiPassable extends Component
{
	private boolean top;
	private boolean bottom;
	private boolean left;
	private boolean right;
	
	public SemiPassable()
	{
	}
	
	public SemiPassable(boolean top, boolean bottom, boolean left, boolean right)
	{
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}
	
	public boolean isPassable(EdgeType edge)
	{
		switch(edge)
		{
		case EDGE_TOP:
			return top;
		case EDGE_BOTTOM:
			return bottom;
		case EDGE_LEFT:
			return left;
		case EDGE_RIGHT:
			return right;
		default:
			return true;
		}
	}

	public boolean isTop()
	{
		return top;
	}

	public void setTop(boolean top)
	{
		this.top = top;
	}

	public boolean isBottom()
	{
		return bottom;
	}

	public void setBottom(boolean bottom)
	{
		this.bottom = bottom;
	}

	public boolean isLeft()
	{
		return left;
	}

	public void setLeft(boolean left)
	{
		this.left = left;
	}

	public boolean isRight()
	{
		return right;
	}

	public void setRight(boolean right)
	{
		this.right = right;
	}
}
