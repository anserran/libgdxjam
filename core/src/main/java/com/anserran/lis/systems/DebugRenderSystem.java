package com.anserran.lis.systems;

import com.anserran.lis.components.Collider;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class DebugRenderSystem extends IteratingSystem implements Mappers {

    private ShapeRenderer shapeRenderer;

    private SpriteBatch batch;

    public DebugRenderSystem(SpriteBatch spriteBatch) {
        super(Family.all(Collider.class).get());
        this.batch = spriteBatch;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update(float deltaTime) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeType.Line);
        super.update(deltaTime);
        shapeRenderer.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        shapeRenderer.setColor(Color.PINK);
        Collider c = collider.get(entity);
        if (c != null) {
            if (c.circle) {
                shapeRenderer.circle(c.pos.x, c.pos.y, c.size.x);
            } else {
                shapeRenderer.rect(c.pos.x, c.pos.y, c.size.x, c.size.y);
            }
        }
    }
}
