package net.piemaster.jario;

import net.piemaster.artemoids.states.GameplayState;
import net.piemaster.artemoids.states.MainMenuState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Jario extends StateBasedGame
{
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;

	public Jario()
	{
		super("Jario");

		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE));
		this.enterState(MAINMENUSTATE);
	}

	public static void main(String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new Jario());

		app.setDisplayMode(800, 600, false);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException
	{

		this.getState(MAINMENUSTATE).init(gameContainer, this);
		this.getState(GAMEPLAYSTATE).init(gameContainer, this);
	}
}