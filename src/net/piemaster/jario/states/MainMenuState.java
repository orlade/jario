package net.piemaster.jario.states;

import net.piemaster.jario.Jario;

import org.lwjgl.input.Keyboard;
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

	private GameContainer gc;
	private StateBasedGame sbg;

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
		backgroundImage = new Image("assets/images/menu_background.jpg");
		playImage = new Image("assets/images/menu_button_play.png");
		exitImage = new Image("assets/images/menu_button_exit.png");

		this.gc = gc;
		this.sbg = sbg;
		
		playX = 50;
		playY = 200;
		exitX = gc.getWidth() - 150;
		exitY = gc.getHeight() - 60;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException
	{
		super.enter(container, game);
		
		container.getInput().resetInputTransform();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		backgroundImage.draw(0, 0);
		playImage.draw(playX, playY);
		exitImage.draw(exitX, exitY);
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
		super.mouseClicked(button, x, y, clickCount);

		if (x >= playX && x <= playX + playImage.getWidth() && 
				y >= playY	&& y <= playY + playImage.getHeight())
		{
			playGame();
		}
		else if (x >= exitX && x <= exitX + exitImage.getWidth() && 
				y >= exitY	&& y <= exitY + exitImage.getHeight())
		{
			exitGame();
		}
	}
	
	@Override
	public void keyPressed(int key, char c)
	{
		super.keyPressed(key, c);
		
		if(key == Keyboard.KEY_RETURN)
		{
			playGame();
		}
		else if(key == Keyboard.KEY_ESCAPE)
		{
			exitGame();
		}
	}

	protected void playGame()
	{
		sbg.enterState(Jario.GAMEPLAYSTATE);
	}

	protected void exitGame()
	{
		gc.exit();
	}
}