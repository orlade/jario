package net.piemaster.jario.systems.delayed;

import com.artemis.Entity;

public interface ListenerSystem
{
	public void process(Entity e, String trigger);
}
