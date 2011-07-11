package net.piemaster.jario.spatials.generic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.World;

/**
 * A spatial composer is a special type of spatial that renders nothing of its own, but manages a
 * collection of child spatials, choosing which one to render in any given frame based on external
 * factors, such as the current state of its owner's SpatialForm component.
 * 
 * The composer is abstract and must be subclassed to implement the current spatial determination
 * logic. The result of this logic should be to assign a value to the currentSpatial variable, if
 * appropriate.
 * 
 * @author Oliver
 */
public abstract class EffectSpatialComposer extends SpatialComposer
{
	// TODO Remove the need for this secondary map somehow
	private Map<String, EffectSpatial> effectStateMap;

	public EffectSpatialComposer(World world, Entity e)
	{
		super(world, e);

		effectStateMap = new HashMap<String, EffectSpatial>();
	}

	@Override
	public void initalize()
	{
		super.initalize();

		for (EffectSpatial spatial : getEffectStates())
		{
			if(!getStates().contains(spatial))
			{
					spatial.initalize();
			}
		}
	}
	
	@Override
	protected Spatial getSpatial(String state)
	{
		Spatial spatial = super.getSpatial(state);
		if(spatial == null)
		{
			spatial = effectStateMap.get(state);
		}
		return spatial;
	}

	@Override
	protected void registerState(String state, Spatial spatial)
	{
		// TODO Make this less ugly
		if(spatial instanceof EffectSpatial)
		{
			effectStateMap.put(state, (EffectSpatial)spatial);
		}
	}
	
	protected Collection<EffectSpatial> getEffectStates()
	{
		return effectStateMap.values();
	}
}
