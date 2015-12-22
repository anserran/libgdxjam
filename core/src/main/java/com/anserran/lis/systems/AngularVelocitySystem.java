package com.anserran.lis.systems;

import com.anserran.lis.components.AngularVelocity;
import com.anserran.lis.components.Renderer;
import com.anserran.lis.components.Velocity;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class AngularVelocitySystem extends IteratingSystem implements Mappers {
    public AngularVelocitySystem() {
        super(Family.all(AngularVelocity.class, Renderer.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Renderer r = renderer.get(entity);
        AngularVelocity v = angularVelocity.get(entity);
        r.rotate(v.v * deltaTime);
    }
}
