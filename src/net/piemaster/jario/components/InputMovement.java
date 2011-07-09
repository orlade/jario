package net.piemaster.jario.components;

import com.artemis.Component;

public class InputMovement extends Component
{
	// Key codes
	private int left;
	private int right;
	private int jump;

	public InputMovement(int left, int right, int jump)
	{
		this.left = left;
		this.right = right;
		this.jump = jump;
	}

	public int getLeft()
	{
		return left;
	}

	public void setLeft(int left)
	{
		this.left = left;
	}

	public int getRight()
	{
		return right;
	}

	public void setRight(int right)
	{
		this.right = right;
	}

	public int getJump()
	{
		return jump;
	}

	public void setJump(int jump)
	{
		this.jump = jump;
	}
}
