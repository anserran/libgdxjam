package com.anserran.lis.systems.render;

import com.anserran.lis.C;
import com.anserran.lis.Utils;
import com.anserran.lis.assets.Assets;
import com.anserran.lis.components.LoadRenderer;
import com.anserran.lis.components.Size;
import com.anserran.lis.components.groups.SkeletonGroup;
import com.anserran.lis.components.groups.TilesGroup;
import com.anserran.lis.systems.Mappers;
import com.anserran.lis.ui.GameGrid;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.esotericsoftware.spine.SkeletonData;

public class LoadRenderSystem extends IteratingSystem implements Mappers {

	private PooledEngine engine;

	private Assets assets;

	private GameGrid root;

	public LoadRenderSystem(Assets assets, GameGrid root) {
		super(Family.all(LoadRenderer.class).get());
		this.assets = assets;
		this.root = root;
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.engine = (PooledEngine) engine;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		LoadRenderer l = loadRenderer.get(entity);
		switch (l.type) {
		case SKELETON:
			SkeletonGroup skeleton = createGroup(entity, SkeletonGroup.class);
			skeleton.setData(assets.get(C.PATH_SKELETONS + l.name
					+ C.EXTENSION_SKELETONS, SkeletonData.class));
			break;
		case TILES:
			TilesGroup tiles = createGroup(entity, TilesGroup.class);
			TextureAtlas atlas = assets.get(C.PATH_ATLAS, TextureAtlas.class);
			if (size.has(entity)) {
				Size s = size.get(entity);
				tiles.set(atlas.findRegion(l.name), s.x, s.y);
			} else {
				tiles.set(atlas.findRegion(l.name));
			}
			break;

		}
		entity.remove(LoadRenderer.class);
	}

	private <T extends Component> T createGroup(Entity entity, Class<T> clazz) {
		T group = engine.createComponent(clazz);
		entity.add(group);
		root.add((Actor) group);
		Utils.update((Actor) group, entity);
		return group;
	}
}
