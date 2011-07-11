package net.piemaster.jario.systems.handling;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.FireballShooter;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Item.ItemType;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Recovering;
import net.piemaster.jario.components.Score;
import net.piemaster.jario.components.SemiPassable;
import net.piemaster.jario.components.Shell;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Velocity;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;
import net.piemaster.jario.systems.RecoverySystem;
import net.piemaster.jario.systems.delayed.InvulnerabilityHandler;

import org.newdawn.slick.util.Log;

import com.artemis.ComponentMapper;
import com.artemis.ComponentType;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;

public class PlayerHandlingSystem extends EmptyHandlingSystem
{
	private static final float BUMP_FACTOR = 0.3f;
	
	private ComponentMapper<Recovering> recoveryMapper;

	@SuppressWarnings("unchecked")
	public PlayerHandlingSystem()
	{
		super(Player.class, Collisions.class);
	}
	
	@Override
	public void initialize()
	{
		super.initialize();
		
		recoveryMapper = new ComponentMapper<Recovering>(Recovering.class, world);
	}
	
	// -------------------------------------------------------------------------

	@Override
	protected void handleEnemyCollision(Entity player, Entity enemy, EdgeType edge)
	{
//		System.out.println("Handling player collision with enemy");
		// If the player is recovering
		if(recoveryMapper.get(player) != null)
		{
//			System.out.println("Recovering!");
			// If not already registered as passing, do so now
			if(!isPassing(enemy.getId(), player.getId()))
			{
				// Mark the enemy as passing through
				registerPassing(enemy.getId(), player.getId());
			}
		}
		// Otherwise handle normally
		else
		{
			if(isPassing(enemy.getId(), player.getId()))
			{
				System.out.println("Unregistering.");
				unregisterPassing(enemy.getId(), player.getId());
			}
			// Jumped on enemy
			if (edge == EdgeType.EDGE_BOTTOM)
			{
				bump(player);
				System.out.println("STOMP");
			}
			// Hit by enemy
			else if (edge != EdgeType.EDGE_NONE)
			{
				System.out.println("Is player invulnerable?" + healthMapper.get(player).isInvulnerable());
				System.out.println("OUCH");
				takeDamage(1, player, edge);
			}
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
		SemiPassable sp = terrain.getComponent(SemiPassable.class);
		if(sp == null || !sp.isPassable(reverseEdge(edge)))
		{
			placeEntityOnOther(player, terrain, reverseEdge(edge));
		}
		else
		{
			registerPassing(player.getId(), terrain.getId());
		}
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
				
				ComponentType t = ComponentTypeManager.getTypeFor(FireballShooter.class);
				if(player.getComponent(t) != null)
						player.removeComponent(t);
				
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
				// Mark the player as recovering
				System.out.println("ADDING to player");
				player.addComponent(new Recovering(1000));
				System.out.println("the System is "+world.getSystemManager().getSystem(RecoverySystem.class));
				player.refresh();
//				world.getSystemManager().getSystem(RecoverySystem.class).pubadded(player);
				
			}
		}
	}
	
	private void bump(Entity player)
	{
		velocityMapper.get(player).setY(-BUMP_FACTOR);
		physicalMapper.get(player).setGrounded(false);
	}
}
