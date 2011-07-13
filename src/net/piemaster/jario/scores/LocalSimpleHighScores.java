package net.piemaster.jario.scores;

import java.util.prefs.Preferences;

import org.newdawn.slick.util.Log;

public class LocalSimpleHighScores implements SimpleHighScores
{
	private static final String SCORE_KEY = "jario_highscore";

	private Preferences scorePrefs;
	private boolean prefsAvailable;

	public LocalSimpleHighScores()
	{
		/*
		 * If in an applet we don't have permision to use preferences and a SecurityException will
		 * be thrown. If this happens we set prefsAvailable to false.
		 */
		try
		{
			scorePrefs = Preferences.userNodeForPackage(SimpleHighScores.class);
			prefsAvailable = true;
		}
		catch (Exception e)
		{
			prefsAvailable = false;
			Log.warn("HIGH SCORES: Preferences file inaccessible.");
		}
	}

	@Override
	/**
	 * Submit the given score for high score consideration.
	 * @return True if the given score is the new high score, false other (or if store failed).
	 */
	public boolean submitHighScore(int score)
	{
		if (prefsAvailable && score > getHighScore())
		{
			scorePrefs.putInt(SCORE_KEY, score);
			return true;
		}
		return false;
	}

	/**
	 * Get the highest score achieved by any player.
	 */
	public int getHighScore()
	{
		return scorePrefs.getInt(SCORE_KEY, 0);
	}
}