package net.piemaster.jario.loader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.piemaster.jario.components.Item.ItemType;
import net.piemaster.jario.entities.EntityFactory;

import org.newdawn.slick.util.Log;

import com.artemis.Entity;
import com.artemis.World;

public class MapLoader
{
	private static final char IGNORE_CHAR = '#';

	private World world;
	
	private List<Entity> contents;

	public MapLoader(World world)
	{
		this.world = world;
		contents = new ArrayList<Entity>();
	}

	/**
	 * Create the entities required for a level.
	 * 
	 * @param mapFile
	 *            The path to the map file.
	 * @throws FileNotFoundException
	 */
	public List<Entity> buildMap(String mapFile) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(getClass().getResourceAsStream(mapFile));
		try
		{
			while (scanner.hasNextLine())
			{
				processLine(scanner.nextLine());
			}
			return contents;
		}
		finally
		{
			scanner.close();
		}
	}

	/**
	 * Process the line by creating an entity (or ignoring).
	 * 
	 * @param line
	 *            The line of text to process.
	 */
	private void processLine(String line)
	{
		Entity e = null;
		
		// Remove anything following a hash
		int index = line.indexOf(IGNORE_CHAR);
		if (index != -1)
		{
			line = line.substring(0, index);
		}
		if(line.length() == 0)
		{
			return;
		}

		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(" ");
		
		String type = null;
		float x = 0, y = 0;
		int w = 0, h = 0;
		if(scanner.hasNext())
		{
			type = scanner.next();
			x = Float.parseFloat(scanner.next());
			y = Float.parseFloat(scanner.next());
		}
		if(scanner.hasNext())
		{
			w = Integer.parseInt(scanner.next());
			h = Integer.parseInt(scanner.next());
		}

		if(type.equals("block"))
		{
			e = EntityFactory.createBlock(world, x, y, w, h);
		}
		else if(type.equals("terrain"))
		{
			e = EntityFactory.createTerrain(world, x, y, w, h);
		}
		else if(type.equals("platform"))
		{
			e = EntityFactory.createPlatform(world, x, y, w, h);
		}
		else if(type.equals("goomba"))
		{
			e = EntityFactory.createGoomba(world, x, y);
		}
		else if(type.equals("parakoopa"))
		{
			e = EntityFactory.createParakoopa(world, x, y);
		}
		else if(type.equals("koopa"))
		{
			e = EntityFactory.createKoopa(world, x, y);
		}
		else if(type.equals("itembox"))
		{
			ItemType itemType = ItemType.COIN;
			int num = 1;
			int duration = 1000;
			
			if(scanner.hasNext())
				itemType = ItemType.valueOf(ItemType.class, scanner.next());
			if(scanner.hasNext())
				num = Integer.parseInt(scanner.next());
			if(scanner.hasNext())
				duration = Integer.parseInt(scanner.next());
			
			e = EntityFactory.createItemBox(world, x, y, itemType, num, duration);
		}
		else if(type.equals("player"))
		{
			e = EntityFactory.createPlayer(world, x, y);
		}
		else if(type.equals("start"))
		{
			e = EntityFactory.createStartPoint(world, x, y);
		}
		else if(type.equals("end"))
		{
			e = EntityFactory.createEndPoint(world, x, y);
		}
		else
		{
			Log.warn("MAP LOADER: Cannot create entity, unknown type: '"+type+"'");
		}
		
		// Add the entity to the list of contents
		if(e != null)
		{
			contents.add(e);
		}
	}
	
}
