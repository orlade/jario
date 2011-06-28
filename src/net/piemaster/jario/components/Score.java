package net.piemaster.jario.components;

import com.artemis.Component;

public class Score extends Component
{
	private int score;
	
	public Score()
	{
	}
	
	public Score(int score)
	{
		this.score = score;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}
	
	public void incrementScore()
	{
		++this.score;
	}
	
	public void decrementScore()
	{
		--this.score;
	}
}
