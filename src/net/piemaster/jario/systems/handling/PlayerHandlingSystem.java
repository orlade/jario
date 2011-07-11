package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.FireballShooter;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Item.ItemType;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Score;
import net.piemaster.jario.components.Shell;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Velocity;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;
import net.piemaster.jario.systems.delayed.InvulnerabilityHandler;

import org.newdawn.slick.util.Log;

import com.artemis.ComponentTypeManager;
import com.artemis.Entity;

public class PlayerHandlingSystem extends EmptyHandlingSystem
{
	private static final float BUMP_FACTOR = 0.3f;

	@SuppressWarnings("unchecked")
	public PlayerHandlingSystem()
	{
		super(Player.class, Collisions.class);
	}
	
	// -------------------------------------------------------------------------

	@Override
	protected void handleEnemyCollision(Entity player, Entity enemy, EdgeType edge)
	{
		// Jumped on enemy
		if (edge == EdgeType.EDGE_BOTTOM)
		{
			bump(player);
		}
		// Hit by enemy
		else if (edge != EdgeType.EDGE_NONE)
		{
			takeDamage(1, player, edge);
		}
	}

	@Override
	protected void handleItemCollision(Entity player, Entity item, EdgeType edge)
	{
		ItemType type = itemMapper.get(item).getType();
			
		switch (type)
		{
		case FLOWER:
			if(player.getComponent(FireballShooter.class) == null)
			{
				player.addComponent(new FireballShooter());
				SpatialForm spatial = spatialMapper.get(player);
				spatial.setCurrentState("FLOWER");
			}
			// No break
			
		case MUSHROOM:
			Health health = healthMapper.get(player);
			if(health.getHealth() == 1)
			{
				health.addDamage(-1);
				CollisionMesh mesh = meshMapper.get(player);
				transformMapper.get(player).addY(-mesh.getHeight());
			}
			SpatialForm spatial = spatialMapper.get(player);
			if(spatial.getCurrentState() == "")
			{
				spatial.setCurrentState("BIG");
			}
			break;
			
		case STAR:
			InvulnerabilityHandler.setTemporaryInvulnerability(world, player, 10000, true);
			break;
			
		case COIN:
			player.getComponent(Score.class).incrementScore();
			break;
			
		default:
			Log.warn("Unknown item type: " + type);
			break;
		}
	}

	@Override
	protected void handleWeaponCollision(Entity player, Entity weapon, EdgeType edge)
	{
		// Determine the weapon type
		
		// Colliding with a shell
		Shell shell = weapon.getComponent(Shell.class);
		if(shell != null)
		{
			Velocity shellVel = weapon.getComponent(Velocity.class);
			if(shellVel.getX() != 0)
			{
				if(edge == EdgeType.EDGE_BOTTOM)
				{
					placeEntityOnOther(player, weapon, reverseEdge(edge));
					bump(player);
				}
				else
				{
					takeDamage(1, player, edge);
				}
			}
			// Not moving
			else
			{
				bump(player);
			}
		}
	}

	@Override
	protected void handleTerrainCollision(Entity player, Entity terrain, EdgeType edge)
	{
		placeEntityOnOther(player, terrain, reverseEdge(edge));
	}

	@Override
	protected void handleItemBoxCollision(Entity player, Entity box, EdgeType edge)
	{
		placeEntityOnOther(player, box, reverseEdge(edge));
	}
	
	// -------------------------------------------------------------------------
	
	private void takeDamage(int amount, Entity player, EdgeType edge)
	{
		Health health = healthMapper.get(player);
		if(!health.isInvulnerable())
		{
			if(health.getHealth() > 1)
			{
				transformMapper.get(player).addY(meshMapper.get(player).getHeight()/2);
				spatialMapper.get(player).setCurrentState("");
				
				player.removeComponent(ComponentTypeManager.getTypeFor(FireballShooter.class));
				
			}
			health.addDamage(amount);
			if (!health.isAlive())
			{
				velocityMapper.get(player).setY(-0.5f);
				velocityMapper.get(player).setX(0.1f * (edge == EdgeType.EDGE_LEFT ? 1 : -1));
				physicalMapper.get(player).setGrounded(false);
			}
			else
			{
				// Make the player invulnerable briefly
				InvulnerabilityHandler.setTemporaryInvulnerability(world, player, 1000, true);
			}
		}
	}
	
	private void bump(Entity player)
	{
		velocityMapper.get(player).setY(-BUMP_FACTOR);
		physicalMapper.get(player).setGrounded(false);
	}
}
