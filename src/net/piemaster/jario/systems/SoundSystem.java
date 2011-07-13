package net.piemaster.jario.systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public static final Sound QUIET_CLICK_SOUND = SoundLoader.loadSound("sounds/quiet_click.wav");
	public static final Sound FAIL_SOUND = SoundLoader.loadSound("sounds/fail.ogg");
	public static final Sound WINNER_SOUND = SoundLoader.loadSound("sounds/winner.ogg");
	public static final Sound STAR_MUSIC = SoundLoader.loadSound("sounds/star_music.ogg");
	public static final Sound BLOCK_BREAK_SOUND = SoundLoader.loadSound("sounds/block_break.ogg");
	
	private static ComponentMapper<Audible> audibleMapper;
	private static Map<Integer, List<String>> stopLoopMap;

	public SoundSystem()
	{
		super(Audible.class);
	}
	
	@Override
	protected void initialize()
	{
		super.initialize();
		
		audibleMapper = new ComponentMapper<Audible>(Audible.class, world);
		stopLoopMap = new HashMap<Integer, List<String>>();
	}

	@Override
	protected void process(Entity e)
	{
		Audible audible = audibleMapper.get(e);
		for(Sound s : audible.getSounds())
		{
			s.play();
		}
		for(Sound s : audible.getLoops())
		{
			if(!s.playing())
			{
				s.loop();
			}
		}
		if(stopLoopMap.containsKey(e.getId()))
		{
			List<String> stopKeys = stopLoopMap.get(e.getId());
			for(String key : stopKeys)
			{
				audible.stopLoop(key);
			}
			stopLoopMap.remove(e.getId());
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

	public static void pushLoop(String key, Sound sound, Entity e)
	{
		Audible audible = audibleMapper.get(e);
		if(audible != null)
		{
			audible.pushLoop(key, sound);
		}
	}
	
	public static void stopLoop(Integer entId, String key)
	{
		if(!stopLoopMap.containsKey(entId))
		{
			stopLoopMap.put(entId, new ArrayList<String>());
		}
		stopLoopMap.get(entId).add(key);
	}
	
	
	@Override
	protected boolean checkProcessing()
	{
		return true;
	}
}
