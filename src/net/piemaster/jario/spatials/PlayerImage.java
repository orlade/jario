package net.piemaster.jario.spatials;

import net.piemaster.jario.components.Transform;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

public class PlayerImage extends Spatial
{
	private Transform transform;
	private Image shipImg;

	public PlayerImage(World world, Entity owner)
	{
		super(world, owner);
	}

	@Override
	public void initalize()
	{
		ComponentMapper<Transform> transformMapper = new ComponentMapper<Transform>(
				Transform.class, world.getEntityManager());
		transform = transformMapper.get(owner);
		
		try
		{
			shipImg = new Image("assets/jar.png");
			shipImg.setCenterOfRotation(shipImg.getWidth()/2, shipImg.getHeight()/2);
		}
		catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics g)
	{
		shipImg.setRotation(transform.getRotation());
//		g.drawImage(shipImg, transform.getX() - shipImg.getWidth()/2, transform.getY() - shipImg.getHeight()/2);
		g.drawImage(shipImg, transform.getX(), transform.getY());
	}

}
