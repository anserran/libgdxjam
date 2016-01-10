package com.anserran.lis.systems;

import com.anserran.lis.LifeInSpace;
import com.anserran.lis.Utils;
import com.anserran.lis.components.Behaviour.Type;
import com.anserran.lis.components.Collider;
import com.anserran.lis.components.Origin;
import com.anserran.lis.components.Position;
import com.anserran.lis.components.Velocity;
import com.anserran.lis.components.commands.Interpolate;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CollisionSystem extends EntitySystem implements Mappers {

	private static DataPool dataPool = new DataPool();

	private LifeInSpace controller;

	private ImmutableArray<Entity> entities;

	private ObjectMap<String, CollisionData> startedCollissions = new ObjectMap<String, CollisionData>();

	private Array<String> endedCollisions = new Array<String>();

	public CollisionSystem(LifeInSpace controller) {
		this.controller = controller;
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Collider.class,
				Position.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for (Entity entity : entities) {
			Position p = position.get(entity);
			Collider c = collider.get(entity);

			c.pos.set(p.x, p.y);
			if (c.circle && origin.has(entity)) {
				Origin o = origin.get(entity);
				c.pos.add(o.x, o.y);
			}
		}

		for (String key : startedCollissions.keys()) {
			endedCollisions.add(key);
		}

		for (int i = 0; i < entities.size(); i++) {
			Entity e1 = entities.get(i);
			Collider c1 = collider.get(e1);
			for (int j = i + 1; j < entities.size(); j++) {
				Entity e2 = entities.get(j);
				Collider c2 = collider.get(e2);
				if (c1.dynamic || c2.dynamic) {
					if (c1.intersects(c2)) {
						String collisionId = e1.toString().compareTo(
								e2.toString()) > 0 ? e1.toString()
								+ e2.toString() : e2.toString() + e1.toString();
						if (!endedCollisions.removeValue(collisionId, false)) {
							startedCollissions.put(collisionId,
									dataPool.get(e1, e2));
							collisionStarted(e1, e2);
						}
					}
				}
			}
		}

		for (String endedCollision : endedCollisions) {
			CollisionData data = startedCollissions.remove(endedCollision);
			if (data != null) {
				dataPool.free(data);
				collisionEnded(data.e1, data.e2);
			} else {
				System.err.println("No collision found");
			}
		}
		endedCollisions.clear();
	}

	private void collisionStarted(Entity e1, Entity e2) {
		Type type1 = behaviour.has(e1) ? behaviour.get(e1).type : Type.WALL;
		Type type2 = behaviour.has(e2) ? behaviour.get(e2).type : Type.WALL;
		if (type1.ordinal() < type2.ordinal()) {
			collisionStarted(e1, type1, e2, type2);
		} else {
			collisionStarted(e2, type2, e1, type1);
		}
		System.out.println("Collision started");
	}

	private void collisionStarted(Entity e1, Type type1, Entity e2, Type type2) {
		switch (type1) {
		case ASTRONAUT:
			switch (type2) {
			case ASTRONAUT:
			case WALL:
				if (velocity.has(e1)) {
					e1.remove(Velocity.class);
				}
				if (interpolate.has(e1)) {
					e1.remove(Interpolate.class);
				}

				Position p = position.get(e1);
				float toX = (float) Math.floor(p.x + 0.5f);
				float toY = (float) Math.floor(p.y + 0.5f);
				e1.add(Utils.moveTo(p.x, p.y, toX, toY, 1.5f,
						Interpolation.exp5Out));
				break;
			case EXIT:
				controller.levelCompleted();
				break;
			case WIRES:
				controller.die(e1);
				break;
			}
		}
	}

	private void collisionEnded(Entity e1, Entity e2) {
		System.out.println("Collision ended");
	}

	public static class CollisionData implements Poolable {

		public Entity e1;

		public Entity e2;

		@Override
		public void reset() {
			e1 = null;
			e2 = null;
		}
	}

	public static class DataPool extends Pool<CollisionData> {

		public CollisionData get(Entity e1, Entity e2) {
			CollisionData collisionData = obtain();
			collisionData.e1 = e1;
			collisionData.e2 = e2;
			return collisionData;
		}

		@Override
		protected CollisionData newObject() {
			return new CollisionData();
		}
	}
}
