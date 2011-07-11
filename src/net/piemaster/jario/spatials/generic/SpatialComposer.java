package net.piemaster.jario.spatials.generic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.SpatialForm;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.Log;

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
public abstract class SpatialComposer extends Spatial
{
	protected SpatialForm form;
	protected CollisionMesh mesh;
	
	private Map<String, Spatial> stateMap;
	private Spatial currentSpatial;

	public SpatialComposer(World world, Entity e)
	{
		super(world, e);

		stateMap = new HashMap<String, Spatial>();
	}

	@Override
	public void initalize()
	{
		form = owner.getComponent(SpatialForm.class);
		mesh = owner.getComponent(CollisionMesh.class);

		for (Spatial spatial : getStates())
		{
			spatial.initalize();
		}
	}

	@Override
	public void render(Graphics g)
	{
		refreshCurrentSpatial();
		
		if (currentSpatial != null)
		{
			currentSpatial.render(g);
		}
	}

	protected void refreshCurrentSpatial()
	{
		setCurrentSpatial(getSpatial(form.getCurrentState()));
	}
	
	protected void registerState(String state, Spatial spatial)
	{
		stateMap.put(state, spatial);
	}
	
	protected Collection<Spatial> getStates()
	{
		return stateMap.values();
	}
	
	protected Spatial getSpatial(String state)
	{
		return stateMap.get(state);
	}

	@Override
	public float getWidth()
	{
		if (currentSpatial != null)
		{
			return currentSpatial.getWidth();
		}
		return 0;
	}

	@Override
	public float getHeight()
	{
		if (currentSpatial != null)
		{
			return currentSpatial.getHeight();
		}
		return 0;
	}

	public Spatial getCurrentSpatial()
	{
		return currentSpatial;
	}

	public void setCurrentSpatial(Spatial currentSpatial)
	{
		this.currentSpatial = currentSpatial;
		
		// Update spatial form properties
		if(form != null)
		{
			form.setWidth(currentSpatial.getWidth());
			form.setHeight(currentSpatial.getHeight());
		}
		else
		{
			Log.warn("No spatial form registered for spatial: "+getClass());
		}
		// Update collision mesh dimensions
		if(mesh != null)
		{
			mesh.setDimensions(currentSpatial.getWidth(), currentSpatial.getHeight());
		}
	}
}
