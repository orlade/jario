package net.piemaster.jario.spatials.generic;

import java.util.ArrayList;
import java.util.List;

import net.piemaster.jario.spatials.effects.SpatialEffect;

import com.artemis.Entity;
import com.artemis.World;

public abstract class EffectSpatial extends Spatial
{
	private List<SpatialEffect> effects;

	public EffectSpatial(World world, Entity owner)
	{
		super(world, owner);
	}

	@Override
	public void initalize()
	{
		effects = new ArrayList<SpatialEffect>();
	}

	public void addEffect(SpatialEffect effect)
	{
		effects.add(effect);
	}

	public void removeEffect(SpatialEffect effect)
	{
		effects.remove(effect);
	}
	
	public void updateEffects(int delta)
	{
		for (SpatialEffect effect : effects)
		{
			effect.update(delta);
		}
	}

	public void applyAllEffects()
	{
		for (SpatialEffect effect : effects)
		{
			applyEffect(effect);
		}
	}

	public abstract void applyEffect(SpatialEffect effect);
}
