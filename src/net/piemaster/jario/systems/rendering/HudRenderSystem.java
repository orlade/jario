package net.piemaster.jario.systems.rendering;

import net.piemaster.jario.components.Player;
import net.piemaster.jario.components.Score;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class HudRenderSystem extends EntityProcessingSystem
{
	private GameContainer container;
	private Graphics g;
	private ComponentMapper<Score> scoreMapper;

	@SuppressWarnings("unchecked")
	public HudRenderSystem(GameContainer container)
	{
		super(Score.class, Player.class);
		this.container = container;
		this.g = container.getGraphics();
	}

	@Override
	public void initialize()
	{
		scoreMapper = new ComponentMapper<Score>(Score.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Score score = scoreMapper.get(e);
		g.setColor(Color.white);
		g.drawString("Score: " + score.getScore(), 20, container.getHeight() - 40);
	}

}
