package net.piemaster.jario.spatials.player;

import net.piemaster.jario.components.Health;
import net.piemaster.jario.spatials.EffectSpatial;
import net.piemaster.jario.spatials.SpatialComposer;
import net.piemaster.jario.spatials.effects.AlphaStrobe;
import net.piemaster.jario.spatials.effects.NullEffect;
import net.piemaster.jario.spatials.effects.SpatialEffect;

import org.newdawn.slick.Graphics;

import com.artemis.Entity;
import com.artemis.World;

public class PlayerComposer extends SpatialComposer
{
	private boolean invulnDisplayed = false;
	
	private SpatialEffect invulnStrobe = new AlphaStrobe(100, 0.3f, 1f);
	
	public PlayerComposer(World world, Entity e)
	{
		super(world, e);
		
		stateMap.put("", new PlayerBase(world, owner));
		stateMap.put("BIG", new PlayerBig(world, owner));
		stateMap.put("FLOWER", new PlayerFlower(world, owner));
	}
	
	@Override
	public void render(Graphics g)
	{
		refreshCurrentSpatial();
		super.render(g);
	}

	protected void refreshCurrentSpatial()
	{
		setCurrentSpatial(stateMap.get(form.getCurrentState()));
		
		if(owner.getComponent(Health.class).isInvulnerable() != invulnDisplayed)
		{
			if(!invulnDisplayed)
			{
				for(EffectSpatial es : stateMap.values())
				{
					es.addEffect(invulnStrobe);
				}
			}
			else
			{
				for(EffectSpatial es : stateMap.values())
				{
					es.removeEffect(invulnStrobe);
					es.applyEffect(new NullEffect());
				}
			}
			invulnDisplayed = !invulnDisplayed;
		}
	}
}
