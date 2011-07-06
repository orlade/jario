package net.piemaster.jario.loader;

import java.io.FileNotFoundException;
import java.util.Scanner;

import net.piemaster.jario.EntityFactory;
import net.piemaster.jario.components.Item.ItemType;

import com.artemis.World;

public class MapLoader
{
	private static final char IGNORE_CHAR = '#';

	private World world;

	public MapLoader(World world)
	{
		this.world = world;
	}

	/**
	 * Create the entities required for a level.
	 * 
	 * @param mapFile
	 *            The path to the map file.
	 * @throws FileNotFoundException
	 */
	public void buildMap(String mapFile) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(getClass().getResourceAsStream(mapFile));
		try
		{
			while (scanner.hasNextLine())
			{
				processLine(scanner.nextLine());
			}
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
			EntityFactory.createBlock(world, x, y, w, h).refresh();
		}
		else if(type.equals("goomba"))
		{
			EntityFactory.createGoomba(world, x, y).refresh();
		}
		else if(type.equals("parakoopa"))
		{
			EntityFactory.createParakoopa(world, x, y).refresh();
		}
		else if(type.equals("itembox"))
		{
			if(scanner.hasNext())
			{
				ItemType itemType = ItemType.valueOf(ItemType.class, scanner.next());
				EntityFactory.createItemBox(world, x, y, itemType, 1, 1000).refresh();
			}
			else
			{
				EntityFactory.createItemBox(world, x, y).refresh();
			}
			
		}
		else if(type.equals("player"))
		{
			EntityFactory.createPlayer(world, x, y).refresh();
		}
		else
		{
			System.out.println("Unknown type: '"+type+"'");
		}
	}
}
