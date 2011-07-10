package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.Coin;
import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Score;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Entity;

public class CoinHandlingSystem extends ItemHandlingSystem
{
	@SuppressWarnings("unchecked")
	public CoinHandlingSystem()
	{
		super(Coin.class, Collisions.class);
	}

	@Override
	protected void handleItemBoxCollision(Entity coin, Entity box, EdgeType edge)
	{
		world.getTagManager().getEntity("PLAYER").getComponent(Score.class).incrementScore();
		world.deleteEntity(coin);
	}
}
