package com.anserran.lis.systems.animation;

import com.anserran.lis.components.commands.Interpolate;
import com.anserran.lis.components.Value;
import com.anserran.lis.systems.Mappers;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

public class InterpolationSystem extends IteratingSystem implements Mappers {

    private PooledEngine engine;

    public InterpolationSystem() {
        super(Family.all(Interpolate.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = (PooledEngine) engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Interpolate i = interpolate.get(entity);
        Component component = null;
        for (Component c : entity.getComponents()) {
            if (c.getClass() == i.component) {
                component = c;
                break;
            }
        }

        if (component == null) {
            component = engine.createComponent(i.component);
            entity.add(component);
        }

        float a = i.time / i.totalTime;
        float x = i.interpolation.apply(0, i.value.x, a);
        float y = i.interpolation.apply(0, i.value.y, a);
        i.time = Math.min(i.time + deltaTime, i.totalTime);
        a = i.time / i.totalTime;
        float dx = i.interpolation.apply(0, i.value.x, a) - x;
        float dy = i.interpolation.apply(0, i.value.y, a) - y;

        if (component instanceof Vector2) {
            ((Vector2) component).add(dx, dy);
        } else if (component instanceof Value) {
            ((Value) component).addValue(dx);
        }

        if (i.time == i.totalTime) {
            entity.remove(Interpolate.class);
        }

    }
}
