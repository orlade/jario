package net.piemaster.jario.systems.handling;

import java.util.HashMap;
import java.util.Map;

import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.systems.CollisionSystem.EdgeType;
import net.piemaster.jario.systems.handling.utils.CollisionCommand;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public abstract class EntityHandlingSystem extends EntityProcessingSystem
{
	protected Map<String, CollisionCommand> handlers;

	public EntityHandlingSystem(Class<? extends Component> requiredType,
			Class<? extends Component>... otherTypes)
	{
		super(requiredType, otherTypes);
	}

	@Override
	public void initialize()
	{
		handlers = new HashMap<String, CollisionCommand>();
	}
	
	@Override
	protected void process(Entity e)
	{
		Collisions coll = e.getComponent(Collisions.class);

		for (int i = 0; i < coll.getSize(); ++i)
		{
			Entity target = world.getEntity(coll.getTargetIds().get(i));
			EdgeType edge = coll.getEdges().get(i);
			String group = world.getGroupManager().getGroupOf(target);
			
			// If colliding with an object that has been slated for removal, continue
			if(group == null)
				continue;
			
			// Run the registered handler
			handlers.get(group).handle(e, target, edge);
		}
		coll.clear();
	}

	// -------------------------------------------------------------------------

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}
}
