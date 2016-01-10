package com.anserran.lis.systems.render;

import com.anserran.lis.Utils;
import com.anserran.lis.components.AngularVelocity;
import com.anserran.lis.components.Velocity;
import com.anserran.lis.components.commands.Interpolate;
import com.anserran.lis.systems.Mappers;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class StageSystem extends IteratingSystem implements Mappers {

	public StageSystem() {
		super(Family.one(Velocity.class, AngularVelocity.class,
				Interpolate.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		if (skeleton.has(entity)) {
			Utils.update(skeleton.get(entity), entity);
		}

		if (tiles.has(entity)) {
			Utils.update(skeleton.get(entity), entity);
		}
	}
}
