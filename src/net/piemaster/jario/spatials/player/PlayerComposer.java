package net.piemaster.jario.spatials.player;

import net.piemaster.jario.components.Invulnerable;
import net.piemaster.jario.spatials.effects.AlphaStrobe;
import net.piemaster.jario.spatials.effects.NullEffect;
import net.piemaster.jario.spatials.effects.SpatialEffect;
import net.piemaster.jario.spatials.generic.EffectSpatial;
import net.piemaster.jario.spatials.generic.EffectSpatialComposer;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

public class PlayerComposer extends EffectSpatialComposer
{
	private boolean invulnDisplayed = false;
	private ComponentMapper<Invulnerable> invulnMapper;
	
	private SpatialEffect invulnStrobe = new AlphaStrobe(100, 0.3f, 1f);
	
	public PlayerComposer(World world, Entity e)
	{
		super(world, e);
		
		registerState("", new PlayerBase(world, owner));
		registerState("BIG", new PlayerBig(world, owner));
		registerState("FLOWER", new PlayerFlower(world, owner));
	}
	
	@Override
	public void initalize()
	{
		super.initalize();
		invulnMapper = new ComponentMapper<Invulnerable>(Invulnerable.class, world);
	}

	@Override
	protected void refreshCurrentSpatial()
	{
		setCurrentSpatial(getSpatial(form.getCurrentState()));
		
		if((invulnMapper.get(owner) != null) != invulnDisplayed)
		{
			if(!invulnDisplayed)
			{
				for(EffectSpatial es : getEffectStates())
				{
					es.addEffect(invulnStrobe);
				}
			}
			else
			{
				for(EffectSpatial es : getEffectStates())
				{
					es.removeEffect(invulnStrobe);
					es.applyEffect(new NullEffect());
				}
			}
			invulnDisplayed = !invulnDisplayed;
		}
	}
}
