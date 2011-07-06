package net.piemaster.jario;

import net.piemaster.jario.components.Acceleration;
import net.piemaster.jario.components.Collisions;
import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Enemy;
import net.piemaster.jario.components.Globals;
import net.piemaster.jario.components.Health;
import net.piemaster.jario.components.Item;
import net.piemaster.jario.components.Item.ItemType;
import net.piemaster.jario.components.ItemDispenser;
import net.piemaster.jario.components.Jumping;
import net.piemaster.jario.components.Physical;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Respawn;
import net.piemaster.jario.components.Score;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.components.Velocity;
import net.piemaster.jario.entities.EntityType;

import com.artemis.Entity;
import com.artemis.World;

public class EntityFactory
{
	public static Entity createPlayer(World world, float x, float y)
	{
		Entity player = world.createEntity();
		player.setGroup(EntityType.PLAYER.toString());
		player.setTag("PLAYER");
		player.addComponent(new Transform(x, y));
		player.addComponent(new Velocity());
		player.addComponent(new Acceleration());
		player.addComponent(new Physical());
		player.addComponent(new SpatialForm("PlayerImage"));
		player.addComponent(new CollisionMesh(x, y, 0, 0));
		player.addComponent(new Collisions());
		player.addComponent(new Health(1));
		player.addComponent(new Player());
		player.addComponent(new Score());
		player.addComponent(new Globals());
		player.addComponent(new Jumping(0.75f));
		player.addComponent(new Respawn(2000, x, y));
		
		return player;
	}

	public static Entity createBlock(World world, float x, float y, int width, int height)
	{
		Entity block = world.createEntity();
		block.setGroup(EntityType.TERRAIN.toString());
		block.addComponent(new Transform(x, y));
		block.addComponent(new SpatialForm("Block", width, height));
		block.addComponent(new CollisionMesh(x, y, width, height));
		
		return block;
	}

	public static Entity createItemBox(World world, float x, float y)
	{
		Entity block = world.createEntity();
		block.setGroup(EntityType.ITEMBOX.toString());
		block.addComponent(new Transform(x, y));
		block.addComponent(new SpatialForm("ItemBox"));
		block.addComponent(new CollisionMesh(x, y, 0, 0));
		block.addComponent(new Collisions());
		block.addComponent(new ItemDispenser(ItemType.COIN, 5, 0));
		
		return block;
	}
	
	public static Entity createGoomba(World world, float x, float y)
	{
		float goombaSpeed = -0.1f;
		
		Entity goomba = world.createEntity();
		goomba.setGroup(EntityType.ENEMY.toString());
		goomba.addComponent(new Transform(x, y));
		goomba.addComponent(new Velocity(goombaSpeed, 0));
		goomba.addComponent(new Acceleration());
		goomba.addComponent(new Physical(true, false));
		goomba.addComponent(new SpatialForm("Goomba"));
		goomba.addComponent(new CollisionMesh(x, y, 0, 0));
		goomba.addComponent(new Collisions());
		goomba.addComponent(new Health(1));
		goomba.addComponent(new Globals());
		goomba.addComponent(new Enemy());
		
		return goomba;
	}

	public static Entity createParakoopa(World world, float x, float y)
	{
		float koopaSpeed = -0.1f;
		
		Entity para = world.createEntity();
		para.setGroup(EntityType.ENEMY.toString());
		para.addComponent(new Transform(x, y));
		para.addComponent(new Velocity(koopaSpeed, 0));
		para.addComponent(new Acceleration());
		para.addComponent(new Physical(true, true));
		para.addComponent(new SpatialForm("Parakoopa"));
		para.addComponent(new CollisionMesh(x, y, 0, 0));
		para.addComponent(new Collisions());
		para.addComponent(new Health(2));
		para.addComponent(new Globals());
		para.addComponent(new Jumping(0.5f));
		para.addComponent(new Enemy());
		
		return para;
	}
	
	public static Entity createMushroom(World world, float x, float y)
	{
		Entity shroom = world.createEntity();
		shroom.setGroup(EntityType.ITEM.toString());
		shroom.addComponent(new Transform(x, y));
		shroom.addComponent(new Velocity());
		shroom.addComponent(new Acceleration());
		shroom.addComponent(new Physical(false, false, false, true, false, false));
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
		shroom.addComponent(new Physical(false, false, false, true, false, false));
		shroom.addComponent(new SpatialForm("Flower"));
		shroom.addComponent(new CollisionMesh(x, y, 0, 0));
		shroom.addComponent(new Collisions());
		shroom.addComponent(new Globals());
		shroom.addComponent(new Item(ItemType.FLOWER));
		
		return shroom;
	}
	
	public static Entity createCoin(World world, float x, float y)
	{
		Entity shroom = world.createEntity();
		shroom.setGroup(EntityType.ITEM.toString());
		shroom.addComponent(new Transform(x, y));
		shroom.addComponent(new Velocity());
		shroom.addComponent(new Acceleration());
		shroom.addComponent(new Physical(false, false, false, true, false, false));
		shroom.addComponent(new SpatialForm("Coin"));
		shroom.addComponent(new CollisionMesh(x, y, 0, 0));
		shroom.addComponent(new Collisions());
		shroom.addComponent(new Globals());
		shroom.addComponent(new Item(ItemType.COIN));
		
		return shroom;
	}

//	public static Entity createShipExplosion(World world, float x, float y)
//	{
//		Entity e = world.createEntity();
//
//		e.setGroup("EFFECTS");
//
//		e.addComponent(new Transform(x, y));
//		e.addComponent(new SpatialForm("ShipExplosion"));
//		e.addComponent(new Expires(1000));
//
//		return e;
//	}

}
