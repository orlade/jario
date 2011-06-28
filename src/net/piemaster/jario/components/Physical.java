package net.piemaster.jario.components;

import com.artemis.Component;

public class Physical extends Component
{
	private boolean moving;
	private boolean jumping;
	private boolean grounded;

	public Physical()
	{
	}
	
	public Physical(boolean moving, boolean jumping, boolean grounded)
	{
		this.moving = moving;
		this.jumping = jumping;
		this.grounded = grounded;
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
		if(jumping)
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
		if(grounded)
		{
			setJumping(false);
		}
	}
}
