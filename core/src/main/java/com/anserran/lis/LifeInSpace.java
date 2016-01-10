package com.anserran.lis;

import com.anserran.lis.assets.Assets;
import com.anserran.lis.assets.loaders.SkeletonLoader.SkeletonAssetParameter;
import com.anserran.lis.components.Collider;
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
import com.anserran.lis.systems.render.DebugRenderer;
import com.anserran.lis.systems.render.LoadRenderSystem;
import com.anserran.lis.systems.render.StageSystem;
import com.anserran.lis.ui.GameGrid;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.spine.SkeletonData;

public class LifeInSpace extends ApplicationAdapter {

    private PooledEngine engine;
    private DebugRenderer renderSystem;
    private LevelLoader loader;
    private ImmutableArray<Entity> tagged;
    private Data data;
    private Assets assets;
    private Stage stage;

    public int updates = 1;
    private GameGrid grid;
    private boolean debug;

    public Data getData() {
        return data;
    }

    public Assets getAssets() {
        return assets;
    }

    @Override
    public void create() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        engine = new PooledEngine();
        data = new Data(engine);

        assets = new Assets();
        stage = new Stage(new ScreenViewport());

        Utils.engine = engine;

        engine.addSystem(new VelocitySystem());
        engine.addSystem(new AngularVelocitySystem());
        engine.addSystem(new CollisionSystem(this));
        engine.addSystem(new InterpolationSystem());
        engine.addSystem(new CommandsSystem());

        grid = new GameGrid();
        stage.getRoot().addActor(grid);
        engine.addSystem(new LoadRenderSystem(assets, grid));
        engine.addSystem(new StageSystem());
        this.loader = new LevelLoader(engine);

        renderSystem = new DebugRenderer();
        renderSystem.setEntities(engine.getEntitiesFor(Family.all(Collider.class).get()));

        tagged = engine.getEntitiesFor(Family.all(Tags.class).get());

        Gdx.input.setInputProcessor(new KeyboardInputProcessor(this));
        loadLevel("1");

        SkeletonAssetParameter parameter = new SkeletonAssetParameter();
        parameter.atlasName = C.PATH_ATLAS;
        assets.load(C.PATH_SKELETONS + "astronaut.skel", SkeletonData.class, parameter);
        assets.finishLoading();
    }

    @Override
    public void render() {
        float delta = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 33f);
        for (int i = 0; i < updates; i++) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act(delta);
            engine.update(delta);
        }
        stage.draw();
        if (debug) {
            renderSystem.render();
        }
    }

    @Override
    public void resize(int width, int height) {
        renderSystem.resize(width, height);
        grid.setBounds(0, 0, width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        assets.dispose();
        renderSystem.dispose();
    }

    public void loadLevel(String levelId) {
        engine.removeAllEntities();
        LevelData levelData = loader.load(levelId);
        renderSystem.setGridSize(levelData.width, levelData.height);
        grid.setGridSize(levelData.width, levelData.height);
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

    public void restartLevel() {
        loadLevel(level + "");
    }

    public void levelCompleted() {
        level++;
        loadLevel(level + "");
    }

    public void toggleDebug() {
        debug = !debug;
    }
}
