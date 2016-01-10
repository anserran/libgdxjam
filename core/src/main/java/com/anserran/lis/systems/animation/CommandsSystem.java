package com.anserran.lis.systems.animation;

import com.anserran.lis.C;
import com.anserran.lis.Data.Command;
import com.anserran.lis.components.Rotation;
import com.anserran.lis.components.Velocity;
import com.anserran.lis.components.commands.Commands;
import com.anserran.lis.components.commands.Interpolate;
import com.anserran.lis.systems.Mappers;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

public class CommandsSystem extends IteratingSystem implements Mappers {

	private PooledEngine engine;

	private static final Vector2 v = new Vector2();

	public CommandsSystem() {
		super(Family.all(Commands.class).get());
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.engine = (PooledEngine) engine;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		if (!velocity.has(entity) && !interpolate.has(entity)) {
			Commands c = commands.get(entity);
			Command command = c.removeIndex(0);
			switch (command) {
			case THRUST:
				Interpolate interpolate = engine
						.createComponent(Interpolate.class);
				v.set(C.THRUST_SPEED, 0);
				if (rotation.has(entity)) {
					v.rotate(rotation.get(entity).value);
				}
				interpolate.set(Velocity.class, v.x, v.y, C.THRUST_TIME,
						C.THRUST_INTERPOLATION);
				entity.add(interpolate);
				break;
			case ROTATE_CW:
			case ROTATE_CCW:
				interpolate = engine.createComponent(Interpolate.class);
				interpolate.set(Rotation.class,
						command == Command.ROTATE_CCW ? 90 : -90,
						C.THRUST_TIME, C.THRUST_INTERPOLATION);
				entity.add(interpolate);
				break;

			}

			if (c.size == 0) {
				entity.remove(Commands.class);
			}
		}
	}
}
