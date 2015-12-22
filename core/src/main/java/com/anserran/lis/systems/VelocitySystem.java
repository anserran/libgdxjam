package com.anserran.lis.systems;

import com.anserran.lis.components.Renderer;
import com.anserran.lis.components.Velocity;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class VelocitySystem extends IteratingSystem implements Mappers {
    public VelocitySystem() {
        super(Family.all(Velocity.class, Renderer.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Renderer r = renderer.get(entity);
        Velocity v = velocity.get(entity);
        r.translate(v.x * deltaTime, v.y * deltaTime);
    }
}
