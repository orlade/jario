package net.piemaster.jario.components;

import com.artemis.Component;

public class SpatialForm extends Component
{
	private String spatialFormFile;
	private boolean visible;
	private int width;
	private int height;

	public SpatialForm(String spatialFormFile)
	{
		this(spatialFormFile, true);
	}

	public SpatialForm(String spatialFormFile, int width, int height)
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

	public SpatialForm(String spatialFormFile, int width, int height, boolean visible)
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

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
