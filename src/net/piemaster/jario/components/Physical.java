package net.piemaster.jario.components;

import com.artemis.Component;

public class Physical extends Component
{
	private boolean moving;
	private boolean jumping;
	private boolean grounded;

	private boolean bouncyHorizontal;
	private boolean bouncyVertical;

	public Physical()
	{
	}

	public Physical(boolean bouncyHorizontal, boolean bouncyVertical)
	{
		this.bouncyHorizontal = bouncyHorizontal;
		this.bouncyVertical = bouncyVertical;
	}

	public Physical(boolean moving, boolean jumping, boolean grounded)
	{
		this.moving = moving;
		this.jumping = jumping;
		this.grounded = grounded;
	}

	public Physical(boolean moving, boolean jumping, boolean grounded, boolean bouncyHorizontal,
			boolean bouncyVertical)
	{
		this(moving, jumping, grounded);
		this.bouncyHorizontal = bouncyHorizontal;
		this.bouncyVertical = bouncyVertical;
	}

	public boolean isMoving()
	{
		return moving;
	}

	public void setMoving(boolean moving)
	{
		this.moving = moving;
	}

	public boolean isJumping()
	{
		return jumping;
	}

	public void setJumping(boolean jumping)
	{
		this.jumping = jumping;
		if (jumping)
		{
			setMoving(true);
		}
	}

	public boolean isGrounded()
	{
		return grounded;
	}

	public void setGrounded(boolean grounded)
	{
		this.grounded = grounded;
		if (grounded)
		{
			setJumping(false);
		}
	}

	public boolean isBouncyVertical()
	{
		return bouncyVertical;
	}

	public void setBouncyVertical(boolean bouncyVertical)
	{
		this.bouncyVertical = bouncyVertical;
	}

	public boolean isBouncyHorizontal()
	{
		return bouncyHorizontal;
	}

	public void setBouncyHorizontal(boolean bouncyHorizontal)
	{
		this.bouncyHorizontal = bouncyHorizontal;
	}
}
