package net.piemaster.jario.states;

import net.piemaster.jario.EntityFactory;
import net.piemaster.jario.Jario;
import net.piemaster.jario.components.Respawn;
import net.piemaster.jario.components.Transform;
import net.piemaster.jario.systems.BoundarySystem;
import net.piemaster.jario.systems.CameraSystem;
import net.piemaster.jario.systems.CollisionMeshSystem;
import net.piemaster.jario.systems.CollisionSystem;
import net.piemaster.jario.systems.EnemyHealthSystem;
import net.piemaster.jario.systems.ExpirationSystem;
import net.piemaster.jario.systems.MovementSystem;
import net.piemaster.jario.systems.PlayerControlSystem;
import net.piemaster.jario.systems.PlayerLifeSystem;
import net.piemaster.jario.systems.RespawnSystem;
import net.piemaster.jario.systems.rendering.CollisionMeshRenderSystem;
import net.piemaster.jario.systems.rendering.HudRenderSystem;
import net.piemaster.jario.systems.rendering.RenderSystem;
import net.piemaster.jario.systems.rendering.TerrainRenderSystem;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;

public class GameplayState extends BasicGameState
{
	int stateID = -1;

	private World world;
	private GameContainer container;
	private StateBasedGame sbg;

	private EntitySystem controlSystem;
	private EntitySystem movementSystem;
	private EntitySystem meshSystem;

	private EntitySystem collisionSystem;
	private EntitySystem expirationSystem;
	private EntitySystem respawnSystem;
	private EntitySystem playerLifeSystem;
	private EntitySystem enemyHealthSystem;

	private EntitySystem renderSystem;
	private EntitySystem hudRenderSystem;
	private EntitySystem terrainRenderSystem;
	private EntitySystem meshRenderSystem;

	private EntitySystem boundarySystem;
	private EntitySystem cameraSystem;
	
	private boolean debug = true;

	public GameplayState(int stateID)
	{
		this.stateID = stateID;
	}

	@Override
	public int getID()
	{
		return stateID;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		this.container = gc;
		this.sbg = sbg;

		world = new World();

		SystemManager systemManager = world.getSystemManager();
		controlSystem = systemManager.setSystem(new PlayerControlSystem(gc));

		movementSystem = systemManager.setSystem(new MovementSystem(gc));
		meshSystem = systemManager.setSystem(new CollisionMeshSystem());

		collisionSystem = systemManager.setSystem(new CollisionSystem());
		expirationSystem = systemManager.setSystem(new ExpirationSystem());
		respawnSystem = systemManager.setSystem(new RespawnSystem());
		playerLifeSystem = systemManager.setSystem(new PlayerLifeSystem());
		enemyHealthSystem = systemManager.setSystem(new EnemyHealthSystem());

		boundarySystem = systemManager.setSystem(new BoundarySystem(0, 0, 1600, 600));
		cameraSystem = systemManager.setSystem(new CameraSystem(gc));
		
		renderSystem = systemManager.setSystem(new RenderSystem(gc));
		hudRenderSystem = systemManager.setSystem(new HudRenderSystem(gc));
		terrainRenderSystem = systemManager.setSystem(new TerrainRenderSystem(gc));
		meshRenderSystem = systemManager.setSystem(new CollisionMeshRenderSystem(gc));

		systemManager.initializeAll();

		initPlayer();
		initBlocks();
		initEnemies();
	}

	private void initPlayer()
	{
		Entity player = EntityFactory.createPlayer(world);

		player.getComponent(Transform.class).setLocation(container.getWidth() / 2,
				container.getHeight() / 2);
		player.getComponent(Respawn.class).setRespawnLocation(container.getWidth() / 2,
				container.getHeight() / 2);

		player.refresh();
	}

	private void initBlocks()
	{
		Entity block = EntityFactory.createBlock(world,
				container.getWidth() / 2 - 100,
				container.getHeight() / 2 + 110,
				200, 50);
		block.refresh();

		block = EntityFactory.createBlock(world,
				container.getWidth() / 2 + 150,
				container.getHeight() / 2 + 80,
				300, 75);
		block.refresh();

		block = EntityFactory.createBlock(world,
				container.getWidth() / 2 - 100,
				container.getHeight() / 2 + 80,
				20, 30);
		block.refresh();
	}

	private void initEnemies()
	{
		Entity goomba = EntityFactory.createGoomba(world, 800, 80);
		goomba.refresh();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		world.loopStart();

		world.setDelta(delta);

		// Handle input
		controlSystem.process();

		// Handle movement
		movementSystem.process();
		meshSystem.process();

		collisionSystem.process();
		expirationSystem.process();
		playerLifeSystem.process();
		enemyHealthSystem.process();
		respawnSystem.process();

		// Maintain limits
		boundarySystem.process();
		cameraSystem.process();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		terrainRenderSystem.process();
		renderSystem.process();

		if(debug)
		{
			meshRenderSystem.process();
		}
		
		hudRenderSystem.process();
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException
	{
		super.enter(container, game);

		container.getInput().addMouseListener(this);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException
	{
		super.leave(container, game);

		container.getInput().removeMouseListener(this);
	}

	@Override
	public void keyPressed(int key, char c)
	{
		super.keyPressed(key, c);

		if (key == Keyboard.KEY_ESCAPE)
		{
			sbg.enterState(Jario.MAINMENUSTATE);
		}
	}
}