package com.anserran.lis;

import com.anserran.lis.systems.AngularVelocitySystem;
import com.anserran.lis.systems.CollisionSystem;
import com.anserran.lis.systems.DebugRenderSystem;
import com.anserran.lis.systems.RenderSystem;
import com.anserran.lis.systems.VelocitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LifeInSpace extends ApplicationAdapter {

    private PooledEngine engine;
    private RenderSystem renderSystem;

    private SpriteBatch batch;

    @Override
    public void create() {
        engine = new PooledEngine();
        engine.addSystem(renderSystem = new RenderSystem(batch = new SpriteBatch()));
        engine.addSystem(new VelocitySystem());
        engine.addSystem(new AngularVelocitySystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new DebugRenderSystem(batch));
    }

    @Override
    public void render() {
        float delta = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 33f);
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderSystem.resize();
    }
}
