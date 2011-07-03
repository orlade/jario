package net.piemaster.jario;

import net.piemaster.jario.components.Acceleration;
import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Enemy;
import net.piemaster.jario.components.Expires;
import net.piemaster.jario.components.Globals;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Jumping;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Respawn;
import net.piemaster.jario.components.Score;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;

public class EntityFactory
{
	public static Entity createPlayer(World world)
	{
		Entity player = world.createEntity();
		player.setGroup("SHIPS");
		player.setTag("PLAYER");
		player.addComponent(new Transform());
		player.addComponent(new Velocity());
		player.addComponent(new Acceleration());
		player.addComponent(new Physical());
		player.addComponent(new SpatialForm("PlayerImage"));
		player.addComponent(new CollisionMesh(60, 108));
		player.addComponent(new Health(1));
		player.addComponent(new Player());
		player.addComponent(new Score());
		player.addComponent(new Globals());
		player.addComponent(new Jumping());
		player.addComponent(new Respawn(2000));
		
		return player;
	}

	public static Entity createBlock(World world, float x, float y, int width, int height)
	{
		Entity block = world.createEntity();
		block.setGroup("TERRAIN");
		block.addComponent(new Transform(x, y));
		block.addComponent(new SpatialForm("Block", width, height));
		block.addComponent(new CollisionMesh(x, y, width, height));
		
		return block;
	}

	public static Entity createGoomba(World world, float x, float y)
	{
		float goombaSpeed = -0.1f;
		
		Entity goomba = world.createEntity();
		goomba.setGroup("ENEMIES");
		goomba.addComponent(new Transform(x, y));
		goomba.addComponent(new Velocity(goombaSpeed, 0));
		goomba.addComponent(new Acceleration());
		goomba.addComponent(new Physical(true, false));
		goomba.addComponent(new SpatialForm("Goomba"));
		goomba.addComponent(new CollisionMesh(x, y, 0, 0));
		goomba.addComponent(new Health(1));
		goomba.addComponent(new Globals());
		goomba.addComponent(new Enemy());
		
		return goomba;
	}

	public static Entity createParakoopa(World world, float x, float y)
	{
		float koopaSpeed = -0.1f;
		
		Entity para = world.createEntity();
		para.setGroup("ENEMIES");
		para.addComponent(new Transform(x, y));
		para.addComponent(new Velocity(koopaSpeed, 0));
		para.addComponent(new Acceleration());
		para.addComponent(new Physical(true, true));
		para.addComponent(new SpatialForm("Parakoopa"));
		para.addComponent(new CollisionMesh(x, y, 0, 0));
		para.addComponent(new Health(2));
		para.addComponent(new Globals());
		para.addComponent(new Jumping());
		para.addComponent(new Enemy());
		
		return para;
	}
	
	public static Entity createMissile(World world)
	{
		Entity missile = world.createEntity();
		missile.setGroup("BULLETS");

		missile.addComponent(new Transform());
		missile.addComponent(new SpatialForm("Missile"));
		missile.addComponent(new Velocity());
		missile.addComponent(new Expires(2000));

		return missile;
	}
	public static Entity createMissile(World world, Transform parent)
	{
		Entity missile = world.createEntity();
		missile.setGroup("BULLETS");

		missile.addComponent(new Transform(parent.getX(), parent.getY(), parent.getRotation()));
		missile.addComponent(new SpatialForm("Missile"));
		missile.addComponent(new Velocity());
		missile.addComponent(new Expires(2000));

		return missile;
	}

	public static Entity createBulletExplosion(World world, float x, float y)
	{
		Entity e = world.createEntity();

		e.setGroup("EFFECTS");

		e.addComponent(new Transform(x, y));
		e.addComponent(new SpatialForm("BulletExplosion"));
		e.addComponent(new Expires(1000));

		return e;
	}

	public static Entity createShipExplosion(World world, float x, float y)
	{
		Entity e = world.createEntity();

		e.setGroup("EFFECTS");

		e.addComponent(new Transform(x, y));
		e.addComponent(new SpatialForm("ShipExplosion"));
		e.addComponent(new Expires(1000));

		return e;
	}

}
