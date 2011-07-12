package net.piemaster.jario.systems.handling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.piemaster.jario.components.Collisions;

import com.artemis.Component;
import com.artemis.Entity;

public abstract class PersistentEntityHandlingSystem extends EntityHandlingSystem
{
	// If one entity is "passing through" another entity, then the former will ignore collision
	// handling with the latter
	private static Map<Integer, List<Integer>> passing;
	// List of key-value pairs to unregister in the NEXT frame
	private static Map<Integer, List<Integer>> delayedUnregistry;

	// Temporary variable
	private List<Integer> remaining;

	public PersistentEntityHandlingSystem(Class<? extends Component> requiredType,
			Class<? extends Component>... otherTypes)
	{
		super(requiredType, otherTypes);
	}

	@Override
	public void initialize()
	{
		super.initialize();

		passing = new HashMap<Integer, List<Integer>>();
		delayedUnregistry = new HashMap<Integer, List<Integer>>();
	}

	@Override
	protected void process(Entity e)
	{
		// Handle delayed unregistrations first
		handleDelayedUnregistration(e);

		// Check if this entity is being passed through
		if (passing.containsKey(e.getId()))
		{
			// If so, get the list of passers
			remaining = new ArrayList<Integer>(passing.get(e.getId()));

			// Process collisions (overridden)
			super.process(e);

			// For the leftover passers that weren't removed
			for (Integer j : remaining)
			{
				// No longer passing, remove from list
				unregisterPassing(j, e.getId());
			}
			// Processing complete, reset remaining list
			remaining = null;
		}
		// If not being passed through at all, handling normally
		else
		{
			super.process(e);
		}
	}

	@Override
	protected void processCollision(Entity e, Collisions coll, int index)
	{
		// Check if the colliding entity is just passing through
		int targetId = coll.getTargetIds().get(index);
		if (remaining != null)
		{
			// Remove the collider if they exist as previous passer
			remaining.remove((Integer) targetId);
		}

		// If this entity is passing through the collider, ignore the collision from this end
		if (passing.containsKey(targetId) && passing.get(targetId).contains(e.getId()))
		{
			return;
		}
		// Otherwise process it normally
		else
		{
			super.processCollision(e, coll, index);
		}
	}

	// -------------------------------------------------------------------------

	/**
	 * Register one entity as passing through another.
	 */
	public void registerPassing(int passer, int passable)
	{
		if (!passing.containsKey(passable))
		{
			passing.put(passable, new ArrayList<Integer>());
		}
		passing.get(passable).add(passer);
	}

	/**
	 * Unregister one entity's state of passing through the other.
	 * 
	 * @param delay
	 *            Whether to delay the unregistration until processing the next frame. This allows
	 *            passables to handle collisions in the special case of passability changing while
	 *            the passer is still passing. For example, if an enemy is passing through the
	 *            player while he is recovering, and the recovering timer runs out, then the player
	 *            can manually handle being hit, and delay the passing unregistration so the enemy
	 *            doesn't notice the collision.
	 */
	public void unregisterPassing(Integer passer, Integer passable, boolean delay)
	{
		if (delay)
		{
			if (!delayedUnregistry.containsKey(passable))
			{
				delayedUnregistry.put(passable, new ArrayList<Integer>());
			}
			delayedUnregistry.get(passable).add(passer);
		}
		else
		{
			passing.get(passable).remove(passer);

			// If there are no entities left passing through this one
			if (passing.get(passable).isEmpty())
			{
				// Remove this entity from the map
				passing.remove(passable);
			}
		}
	}

	/**
	 * Unregister one entity's state of passing through the other.
	 */
	public void unregisterPassing(Integer passer, Integer passable)
	{
		unregisterPassing(passer, passable, false);
	}

	/**
	 * Unregister all passing pairs registered in the delayed unregistry from the main passing
	 * registry.
	 * 
	 * @param e
	 *            The entity to remove passers of.
	 */
	private void handleDelayedUnregistration(Entity e)
	{
		Integer passable = e.getId();
		if (delayedUnregistry.containsKey(passable))
		{
			for (Integer passer : delayedUnregistry.get(e.getId()))
			{
				unregisterPassing(passer, passable);
			}
			delayedUnregistry.remove(passable);
		}
	}

	/**
	 * Is the given passer currently passing through the given passable?
	 */
	public boolean isPassing(Integer passer, Integer passable)
	{
		return (passing.get(passable) != null && passing.get(passable).contains(passer));
	}

	// -------------------------------------------------------------------------

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}
}
