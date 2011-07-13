package net.piemaster.jario.entities;

import net.piemaster.jario.components.Acceleration;
import net.piemaster.jario.components.Audible;
import net.piemaster.jario.components.Coin;
import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.Enemy;
import net.piemaster.jario.components.Expires;
import net.piemaster.jario.components.Fireball;
import net.piemaster.jario.components.Globals;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.InputMovement;
import net.piemaster.jario.components.Item;
import net.piemaster.jario.components.Item.ItemType;
import net.piemaster.jario.components.Breakable;
import net.piemaster.jario.components.ItemDispenser;
import net.piemaster.jario.components.Jumping;
import net.piemaster.jario.components.Meta;
import net.piemaster.jario.components.Parakoopa;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Respawn;
import net.piemaster.jario.components.Score;
import net.piemaster.jario.components.SemiPassable;
import net.piemaster.jario.components.Shell;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Timer;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;
import net.piemaster.jario.components.Weapon;

import org.lwjgl.input.Keyboard;

import com.artemis.Entity;
import com.artemis.World;

public class EntityFactory
{
	public static Entity createPlayer(World world, float x, float y)
	{
		Entity player = world.createEntity();
		player.setGroup(EntityType.PLAYER.toString());
		player.setTag("PLAYER");
		player.addComponent(new Transform(x, y, true));
		player.addComponent(new Velocity());
		player.addComponent(new Acceleration());
		player.addComponent(new Physical());
		player.addComponent(new InputMovement(Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_SPACE));
		player.addComponent(new SpatialForm("PlayerImage"));
		player.addComponent(new CollisionMesh(x, y, 0, 0));
		player.addComponent(new Collisions());
		player.addComponent(new Health(1));
		player.addComponent(new Player());
		player.addComponent(new Score());
		player.addComponent(new Globals());
		player.addComponent(new Timer());
		player.addComponent(new Jumping(0.9f));
		player.addComponent(new Respawn(2000, x, y));
		player.addComponent(new Audible());

		return player;
	}

	public static Entity createTerrain(World world, float x, float y, int width, int height)
	{
		Entity block = world.createEntity();
		block.setGroup(EntityType.TERRAIN.toString());
		block.addComponent(new Transform(x, y));
		block.addComponent(new SpatialForm("Terrain", width, height));
		block.addComponent(new CollisionMesh(x, y, width, height));

		return block;
	}

	public static Entity createBlock(World world, float x, float y, int width, int height)
	{
		Entity block = world.createEntity();
		block.setGroup(EntityType.TERRAIN.toString());
		block.addComponent(new Transform(x, y));
		block.addComponent(new SpatialForm("Block"));
		block.addComponent(new CollisionMesh(x, y, 0, 0));
		block.addComponent(new Collisions());
		block.addComponent(new Breakable());
		block.addComponent(new Audible());

		return block;
	}

	public static Entity createPlatform(World world, float x, float y, int width, int height)
	{
		Entity block = world.createEntity();
		block.setGroup(EntityType.TERRAIN.toString());
		block.addComponent(new Transform(x, y));
		block.addComponent(new SpatialForm("Platform", width, height));
		block.addComponent(new CollisionMesh(x, y, width, height));
		block.addComponent(new SemiPassable(false, true, true, true));
		block.addComponent(new Collisions());

		return block;
	}

	public static Entity createItemBox(World world, float x, float y)
	{
		return createItemBox(world, x, y, ItemType.COIN, 1, 0);
	}

	public static Entity createItemBox(World world, float x, float y, ItemType type, int number,
			int duration)
	{
		Entity block = world.createEntity();
		block.setGroup(EntityType.ITEMBOX.toString());
		block.addComponent(new Transform(x, y));
		block.addComponent(new SpatialForm("ItemBox"));
		block.addComponent(new CollisionMesh(x, y, 0, 0));
		block.addComponent(new Collisions());
		block.addComponent(new ItemDispenser(type, number, duration));
		block.addComponent(new Audible());

		return block;
	}

	public static Entity createGoomba(World world, float x, float y)
	{
		float goombaAccel = -0.001f;

		Entity goomba = world.createEntity();
		goomba.setGroup(EntityType.ENEMY.toString());
		goomba.addComponent(new Transform(x, y));
		goomba.addComponent(new Velocity(0, 0));
		goomba.addComponent(new Acceleration(goombaAccel, 0, true));
		goomba.addComponent(new Physical(true, false));
		goomba.addComponent(new SpatialForm("Goomba"));
		goomba.addComponent(new CollisionMesh(x, y, 0, 0));
		goomba.addComponent(new Collisions());
		goomba.addComponent(new Health(1));
		goomba.addComponent(new Globals());
		goomba.addComponent(new Enemy());
		goomba.addComponent(new Audible());

		return goomba;
	}

	public static Entity createParakoopa(World world, float x, float y)
	{
		float koopaAccel = -0.001f;

		Entity para = world.createEntity();
		para.setGroup(EntityType.ENEMY.toString());
		para.addComponent(new Transform(x, y));
		para.addComponent(new Velocity(0, 0));
		para.addComponent(new Acceleration(koopaAccel, 0, true));
		para.addComponent(new Physical(true, true));
		para.addComponent(new SpatialForm("Parakoopa", "PARA"));
		para.addComponent(new CollisionMesh(x, y, 0, 0));
		para.addComponent(new Collisions());
		para.addComponent(new Health(2));
		para.addComponent(new Globals());
		para.addComponent(new Jumping(0.5f));
		para.addComponent(new Enemy());
		para.addComponent(new Parakoopa());
		para.addComponent(new Audible());

		return para;
	}

	public static Entity createKoopa(World world, float x, float y)
	{
		float koopaAccel = -0.001f;

		Entity para = world.createEntity();
		para.setGroup(EntityType.ENEMY.toString());
		para.addComponent(new Transform(x, y));
		para.addComponent(new Velocity(0, 0));
		para.addComponent(new Acceleration(koopaAccel, 0, true));
		para.addComponent(new Physical(true, true));
		para.addComponent(new SpatialForm("Parakoopa", ""));
		para.addComponent(new CollisionMesh(x, y, 0, 0));
		para.addComponent(new Collisions());
		para.addComponent(new Health(1));
		para.addComponent(new Globals());
		para.addComponent(new Enemy());
		para.addComponent(new Parakoopa());
		para.addComponent(new Audible());

		return para;
	}

	public static Entity createShell(World world, float x, float y)
	{
		Entity para = world.createEntity();
		para.setGroup(EntityType.WEAPON.toString());
		para.addComponent(new Transform(x, y));
		para.addComponent(new Velocity(0, 0));
		para.addComponent(new Acceleration());
		para.addComponent(new Physical(false, false, false, true, false, true, false));
		para.addComponent(new SpatialForm("Shell"));
		para.addComponent(new CollisionMesh(x, y, 0, 0));
		para.addComponent(new Collisions());
		para.addComponent(new Globals());
		para.addComponent(new Weapon());
		para.addComponent(new Shell());
		para.addComponent(new Audible());

		return para;
	}

	public static Entity createMushroom(World world, float x, float y)
	{
		Entity shroom = world.createEntity();
		shroom.setGroup(EntityType.ITEM.toString());
		shroom.addComponent(new Transform(x, y));
		shroom.addComponent(new Velocity());
		shroom.addComponent(new Acceleration());
		shroom.addComponent(new Physical(false, false, false, true, false, false, false));
		shroom.addComponent(new SpatialForm("Mushroom"));
		shroom.addComponent(new CollisionMesh(x, y, 0, 0));
		shroom.addComponent(new Collisions());
		shroom.addComponent(new Globals());
		shroom.addComponent(new Item(ItemType.MUSHROOM));

		return shroom;
	}

	public static Entity createFlower(World world, float x, float y)
	{
		Entity shroom = world.createEntity();
		shroom.setGroup(EntityType.ITEM.toString());
		shroom.addComponent(new Transform(x, y));
		shroom.addComponent(new Velocity());
		shroom.addComponent(new Acceleration());
		shroom.addComponent(new Physical(false, false, false, true, false, false, false));
		shroom.addComponent(new SpatialForm("Flower"));
		shroom.addComponent(new CollisionMesh(x, y, 0, 0));
		shroom.addComponent(new Collisions());
		shroom.addComponent(new Globals());
		shroom.addComponent(new Item(ItemType.FLOWER));

		return shroom;
	}

	public static Entity createStar(World world, float x, float y)
	{
		Entity star = world.createEntity();
		star.setGroup(EntityType.ITEM.toString());
		star.addComponent(new Transform(x, y));
		star.addComponent(new Velocity());
		star.addComponent(new Acceleration());
		star.addComponent(new Physical(false, false, false, true, true, false, false));
		star.addComponent(new Jumping(0.5f));
		star.addComponent(new SpatialForm("Star"));
		star.addComponent(new CollisionMesh(x, y, 0, 0));
		star.addComponent(new Collisions());
		star.addComponent(new Globals());
		star.getComponent(Globals.class).setGravity(0.0017f);
		star.addComponent(new Item(ItemType.STAR));
		
		return star;
	}

	public static Entity createCoin(World world, float x, float y)
	{
		Entity shroom = world.createEntity();
		shroom.setGroup(EntityType.ITEM.toString());
		shroom.addComponent(new Transform(x, y));
		shroom.addComponent(new Velocity());
		shroom.addComponent(new Acceleration());
		shroom.addComponent(new Physical(false, false, false, true, false, false, false));
		shroom.addComponent(new SpatialForm("Coin"));
		shroom.addComponent(new CollisionMesh(x, y, 0, 0));
		shroom.addComponent(new Collisions());
		shroom.addComponent(new Globals());
		shroom.addComponent(new Coin());
		shroom.addComponent(new Item(ItemType.COIN));

		return shroom;
	}

	public static Entity createFireball(World world, float x, float y)
	{
		float fireballSpeed = 0.5f;

		Entity fireball = world.createEntity();
		fireball.setGroup(EntityType.WEAPON.toString());
		fireball.addComponent(new Transform(x, y));
		fireball.addComponent(new Velocity(fireballSpeed, 0));
		fireball.addComponent(new Acceleration());
		fireball.addComponent(new Physical(false, false, false, false, true, true, false));
		fireball.addComponent(new Jumping(0.5f));
		fireball.addComponent(new SpatialForm("Fireball"));
		fireball.addComponent(new CollisionMesh(x, y, 0, 0));
		fireball.addComponent(new Collisions());
		fireball.addComponent(new Globals());
		fireball.addComponent(new Weapon());
		fireball.addComponent(new Fireball());
		fireball.addComponent(new Expires(1000));

		return fireball;
	}

	public static Entity createStartPoint(World world, float x, float y)
	{
		Entity start = world.createEntity();
		start.setTag("START_POINT");
		start.addComponent(new Transform(x, y));
		start.addComponent(new Meta());

		return start;
	}

	public static Entity createEndPoint(World world, float x, float y)
	{
		Entity end = world.createEntity();
		end.setTag("END_POINT");
		end.addComponent(new Transform(x, y));
		end.addComponent(new Meta());

		return end;
	}
}
