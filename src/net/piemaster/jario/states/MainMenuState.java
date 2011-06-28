package net.piemaster.jario.states;

import net.piemaster.jario.Jario;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState
{
	private int stateID = -1;

	private Image backgroundImage;
	private Image playImage;
	private Image exitImage;

	private boolean wasClick;
	private int clickX;
	private int clickY;

	private int playX;
	private int playY;
	private int exitX;
	private int exitY;

	public MainMenuState(int stateID)
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
		backgroundImage = new Image("assets/menu_background.jpg");
		playImage = new Image("assets/menu_button_play.png");
		exitImage = new Image("assets/menu_button_exit.png");

		playX = 50;
		playY = 200;
		exitX = gc.getWidth() - 150;
		exitY = gc.getHeight() - 60;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		backgroundImage.draw(0, 0);
		playImage.draw(playX, playY);
		exitImage.draw(exitX, exitY);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{

		if (wasClick)
		{
			if (clickX >= playX && clickX <= playX + playImage.getWidth() && 
					clickY >= playY	&& clickY <= playY + playImage.getHeight())
			{
				handlePlayClick(gc, sbg);
			}
			else if (clickX >= exitX && clickX <= exitX + exitImage.getWidth() && 
					clickY >= exitY	&& clickY <= exitY + exitImage.getHeight())
			{
				handleExitClick(gc, sbg);
			}
			wasClick = false;
		}
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
		super.mouseClicked(button, x, y, clickCount);

		wasClick = true;
		clickX = x;
		clickY = y;
	}

	protected void handlePlayClick(GameContainer gc, StateBasedGame sbg)
	{
		sbg.enterState(Jario.GAMEPLAYSTATE);
	}

	protected void handleExitClick(GameContainer gc, StateBasedGame sbg)
	{
		gc.exit();
	}
}