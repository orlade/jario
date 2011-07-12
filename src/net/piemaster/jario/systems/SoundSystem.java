package net.piemaster.jario.systems;

import net.piemaster.jario.components.Audible;
import net.piemaster.jario.loader.SoundLoader;

import org.newdawn.slick.Sound;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class SoundSystem extends EntityProcessingSystem
{
	public static final Sound POP_SOUND = SoundLoader.loadSound("sounds/pop.wav");
	public static final Sound PUNCH_SOUND = SoundLoader.loadSound("sounds/punch.ogg");
	public static final Sound POWERUP_SOUND = SoundLoader.loadSound("sounds/powerup.ogg");
	public static final Sound NINTENDO_SOUND = SoundLoader.loadSound("sounds/nintendo.ogg");
	public static final Sound JUMP_SOUND = SoundLoader.loadSound("sounds/jump.wav");
	public static final Sound CRUSH_SOUND = SoundLoader.loadSound("sounds/crush.wav");
	public static final Sound SLIDE_POP_SOUND = SoundLoader.loadSound("sounds/slide_pop.wav");
	public static final Sound LASER_SOUND = SoundLoader.loadSound("sounds/laser.wav");
	public static final Sound ITEM_WOOP_SOUND = SoundLoader.loadSound("sounds/item_woop.ogg");
	public static final Sound FAIL_SOUND = SoundLoader.loadSound("sounds/fail.ogg");
	public static final Sound WINNER_SOUND = SoundLoader.loadSound("sounds/winner.ogg");
	
	private static ComponentMapper<Audible> audibleMapper;

	public SoundSystem()
	{
		super(Audible.class);
	}
	
	@Override
	protected void initialize()
	{
		super.initialize();
		
		audibleMapper = new ComponentMapper<Audible>(Audible.class, world);
	}

	@Override
	protected void process(Entity e)
	{
		Audible audible = audibleMapper.get(e);
		for(Sound s : audible.getSounds())
		{
			s.play();
		}
		audible.clearSounds();
	}
	

	public static void pushSound(Sound sound, Entity e)
	{
		Audible audible = audibleMapper.get(e);
		if(audible != null)
		{
			audible.pushSound(sound);
		}
	}
	
	
	@Override
	protected boolean checkProcessing()
	{
		return true;
	}
}
