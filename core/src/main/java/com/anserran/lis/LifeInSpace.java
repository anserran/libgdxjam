package com.anserran.lis;

import com.anserran.lis.components.Tags;
import com.anserran.lis.components.commands.Commands;
import com.anserran.lis.input.KeyboardInputProcessor;
import com.anserran.lis.loader.LevelLoader;
import com.anserran.lis.loader.LevelLoader.EntityData;
import com.anserran.lis.loader.LevelLoader.LevelData;
import com.anserran.lis.systems.CollisionSystem;
import com.anserran.lis.systems.Mappers;
import com.anserran.lis.systems.animation.AngularVelocitySystem;
import com.anserran.lis.systems.animation.CommandsSystem;
import com.anserran.lis.systems.animation.InterpolationSystem;
import com.anserran.lis.systems.animation.VelocitySystem;
import com.anserran.lis.systems.render.CollidersRenderSystem;
import com.anserran.lis.systems.render.RenderSystem;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ObjectMap.Entry;

public class LifeInSpace extends ApplicationAdapter {

	private PooledEngine engine;
	private RenderSystem renderSystem;
	private LevelLoader loader;
	private ImmutableArray<Entity> tagged;
	private Data data;

	public int updates = 1;

	public Data getData() {
		return data;
	}

	@Override
	public void create() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		engine = new PooledEngine();
		data = new Data(engine);

		Utils.engine = engine;

		engine.addSystem(new VelocitySystem());
		engine.addSystem(new AngularVelocitySystem());
		engine.addSystem(new CollisionSystem(this));
		engine.addSystem(new InterpolationSystem());
		engine.addSystem(new CommandsSystem());
		engine.addSystem((CollidersRenderSystem) (renderSystem = new CollidersRenderSystem()));
		this.loader = new LevelLoader(engine);

		tagged = engine.getEntitiesFor(Family.all(Tags.class).get());

		Gdx.input.setInputProcessor(new KeyboardInputProcessor(this));
		loadLevel("1");
	}

	@Override
	public void render() {
		float delta = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 33f);
		for (int i = 0; i < updates; i++) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			engine.update(delta);
		}
	}

	@Override
	public void resize(int width, int height) {
		renderSystem.resize(width, height);
	}

	public void loadLevel(String levelId) {
		engine.removeAllEntities();
		LevelData levelData = loader.load(levelId);
		renderSystem.setGridSize(levelData.width, levelData.height);
		for (EntityData entityData : levelData.entities) {
			Entity entity = engine.createEntity();
			if (entityData.id != null) {
				Tags tags = engine.createComponent(Tags.class);
				tags.add(entityData.id);
				entity.add(tags);
			}
			for (Component component : entityData.components) {
				entity.add(component);
			}
			engine.addEntity(entity);
		}
	}

	public void run() {
		for (Entry<String, Commands> e : data.getCommandsMap().entries()) {
			for (Entity entity : tagged) {
				Tags tags = Mappers.tags.get(entity);
				if (tags.contains(e.key, false)) {
					entity.add(e.value);
					break;
				}
			}
		}
		data.getCommandsMap().clear();
	}

	public int level = 1;

	public void restartLevel(){
		loadLevel(level + "");
	}

	public void levelCompleted() {
		level++;
		loadLevel(level + "");
	}
}
