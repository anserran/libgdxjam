package com.anserran.lis.systems.animation;

import com.anserran.lis.components.AngularVelocity;
import com.anserran.lis.components.Rotation;
import com.anserran.lis.systems.Mappers;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class AngularVelocitySystem extends IteratingSystem implements Mappers {
    public AngularVelocitySystem() {
        super(Family.all(AngularVelocity.class, Rotation.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Rotation r = rotation.get(entity);
        AngularVelocity v = angularVelocity.get(entity);
        r.value += v.value * deltaTime;
    }
}
