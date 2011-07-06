package net.piemaster.jario.components;

import java.util.ArrayList;

import com.artemis.Component;

public class SpatialForm extends Component
{
	private String spatialFormFile;
	private boolean visible;
	private float width;
	private float height;
	
	private ArrayList<Runnable> loadedCallbacks = new ArrayList<Runnable>();

	public SpatialForm(String spatialFormFile)
	{
		this(spatialFormFile, true);
	}

	public SpatialForm(String spatialFormFile, float width, float height)
	{
		this(spatialFormFile, true);
		this.width = width;
		this.height = height;
	}
	
	public SpatialForm(String spatialFormFile, boolean visible)
	{
		this.spatialFormFile = spatialFormFile;
		this.visible = visible;
	}

	public SpatialForm(String spatialFormFile, float width, float height, boolean visible)
	{
		this(spatialFormFile, width, height);
		this.visible = visible;
	}

	public String getSpatialFormFile()
	{
		return spatialFormFile;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public void toggleVisible()
	{
		this.visible = !visible;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}
	
	public ArrayList<Runnable> getLoadedCallbacks()
	{
		return loadedCallbacks;
	}
	
	public void pushLoadedCallback(Runnable callback)
	{
		loadedCallbacks.add(callback);
	}
	
	public void clearLoadedCallbacks()
	{
		loadedCallbacks.clear();
	}
	
	public int numLoadedCallbacks()
	{
		return loadedCallbacks.size();
	}
}
