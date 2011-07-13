package net.piemaster.jario.loader;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.piemaster.jario.components.Transform;
import net.piemaster.jario.systems.CameraSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

/**
 * Uses the camera system to stream in level contents as the player moves through the level.
 * 
 * @author Oliver
 */
public class LevelBuilder
{
	private World world;
	private CameraSystem cam;
	private List<Entity> contents;
	private ComponentMapper<Transform> transformMapper;

	private int index;
	private float margin;

	public LevelBuilder(World world)
	{
		this(world, 32f);
	}

	public LevelBuilder(World world, float margin)
	{
		this.world = world;
		this.margin = margin;
	}

	public void initialize()
	{
		cam = world.getSystemManager().getSystem(CameraSystem.class);
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);

		MapLoader loader = new MapLoader(world);
		try
		{
			contents = loader.buildMap("/levels/level_1.map");
			Collections.sort(contents, new TransformXComparator());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void update()
	{
		while(index < contents.size())
		{
			Entity e = contents.get(index);
			Transform t = transformMapper.get(e);
			
			if (t == null || t.getX() - cam.getEndX() < margin)
			{
				e.refresh();
				++index;
			}
			else
			{
				break;
			}
		}
	}

	public class TransformXComparator implements Comparator<Entity>
	{
		@Override
		public int compare(Entity e1, Entity e2)
		{
			Transform t1 = transformMapper.get(e1);
			Transform t2 = transformMapper.get(e2);

			// Entities without transform components should be created first
			if (t1 == null && t2 == null)
			{
				return 0;
			}
			else if (t1 == null)
				return -1;
			else if (t2 == null)
				return 1;
			// Entities with transform components are ordered by X value
			else
			{
				float x1 = t1.getX();
				float x2 = t2.getX();

				return Float.compare(x1, x2);
			}
		}
	}
}
