package com.anserran.lis.systems;

import com.anserran.lis.components.Renderer;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderSystem extends IteratingSystem implements Mappers {

    private SpriteBatch batch;

    private OrthographicCamera cam;

    public RenderSystem(SpriteBatch spriteBatch) {
        super(Family.all(Renderer.class).get());
        this.batch = spriteBatch;
        cam = new OrthographicCamera();
        resize();
    }

    public void resize(){
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, 0);
    }

    @Override
    public void update(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Renderer r = renderer.get(entity);
        r.draw(batch);
    }
}
