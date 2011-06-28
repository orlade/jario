package net.piemaster.jario.components;

import com.artemis.Component;

public class Health extends Component
{
	private float health;
	private float maximumHealth;

	public Health(float health)
	{
		this.health = this.maximumHealth = health;
	}

	public float getHealth()
	{
		return health;
	}

	public float getMaximumHealth()
	{
		return maximumHealth;
	}

	public int getHealthPercentage()
	{
		return Math.round(health / maximumHealth * 100f);
	}

	public void addDamage(int damage)
	{
		health -= damage;
		if (health < 0)
			health = 0;
	}

	public void resetHealth()
	{
		health = maximumHealth;
	}

	public boolean isAlive()
	{
		return health > 0;
	}

}
