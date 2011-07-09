package net.piemaster.jario.systems.rendering;

import net.piemaster.jario.components.CollisionMesh;
import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Score;
import net.piemaster.jario.components.SpatialForm;
import net.piemaster.jario.components.Transform;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class DebugHudRenderSystem extends EntityProcessingSystem
{
	private GameContainer container;
	private Graphics g;
	private ComponentMapper<CollisionMesh> meshMapper;
	private ComponentMapper<Transform> transformMapper;

	@SuppressWarnings("unchecked")
	public DebugHudRenderSystem(GameContainer container)
	{
		super(Score.class, Player.class);
		this.container = container;
		this.g = container.getGraphics();
	}

	@Override
	public void initialize()
	{
		meshMapper = new ComponentMapper<CollisionMesh>(CollisionMesh.class,
				world.getEntityManager());
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Transform t = transformMapper.get(world.getTagManager().getEntity("PLAYER"));
		SpatialForm s = world.getTagManager().getEntity("PLAYER").getComponent(SpatialForm.class);
		CollisionMesh mesh = meshMapper.get(world.getTagManager().getEntity("PLAYER"));

		g.setColor(Color.white);
		g.drawString(
				"     (x, y) = (" + String.format("%.4f", t.getX()) + ", "
						+ String.format("%.4f", t.getY()) + ")", container.getWidth() - 300, 20);
		g.drawString(
				"     (w, h) = (" + String.format("%.4f", s.getWidth()) + ", "
						+ String.format("%.4f", s.getHeight()) + ")", container.getWidth() - 300,
				35);
		g.drawString(
				"Mesh (x, y) = (" + String.format("%.4f", mesh.getX()) + ", "
						+ String.format("%.4f", mesh.getY()) + ")", container.getWidth() - 300, 50);
	}

}
