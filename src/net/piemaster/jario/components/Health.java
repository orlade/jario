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

	/**
	 * Reduces the current health by the given amount. If invulnerable = true and damage is
	 * positive, it will be ignored.
	 */
	public void addDamage(int damage)
	{
		health -= damage;
	}

	public void resetHealth()
	{
		health = maximumHealth;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public boolean isAlive()
	{
		return health > 0;
	}
}
