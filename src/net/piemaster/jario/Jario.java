package net.piemaster.jario;

import net.piemaster.jario.states.GameplayState;
import net.piemaster.jario.states.MainMenuState;

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

		app.setDisplayMode(800, 450, false);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException
	{
		gc.setTargetFrameRate(100);
		
//		this.getState(MAINMENUSTATE).init(gameContainer, this);
//		this.getState(GAMEPLAYSTATE).init(gameContainer, this);
	}
}