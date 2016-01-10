package com.anserran.lis.systems.animation;

import com.anserran.lis.components.Position;
import com.anserran.lis.components.Velocity;
import com.anserran.lis.systems.Mappers;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class VelocitySystem extends IteratingSystem implements Mappers {
	public VelocitySystem() {
		super(Family.all(Velocity.class, Position.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Position p = position.get(entity);
		Velocity v = velocity.get(entity);
		p.add(v.x * deltaTime, v.y * deltaTime);
	}
}
