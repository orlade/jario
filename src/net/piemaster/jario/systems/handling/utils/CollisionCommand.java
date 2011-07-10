package net.piemaster.jario.systems.handling.utils;

import net.piemaster.jario.systems.CollisionSystem.EdgeType;

import com.artemis.Entity;

public interface CollisionCommand
{
	public void handle(Entity source, Entity target, EdgeType edge);
}
