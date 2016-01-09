package com.anserran.lis;

import com.anserran.lis.components.Origin;
import com.anserran.lis.components.Position;
import com.anserran.lis.components.Rotation;
import com.anserran.lis.components.commands.Interpolate;
import com.anserran.lis.systems.Mappers;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Utils implements Mappers {

    public static PooledEngine engine;

    public static Interpolate moveTo(float x, float y, float toX, float toY, float time, Interpolation interpolation) {
        Interpolate interpolate = engine.createComponent(Interpolate.class);
        interpolate.value.set(toX - x, toY - y);
        interpolate.totalTime = time;
        interpolate.interpolation = interpolation;
        interpolate.component = Position.class;
        return interpolate;
    }

    public static void update(Actor actor, Entity entity) {
        if (position.has(entity)) {
            Position p = position.get(entity);
            actor.setPosition(p.x, p.y);
        }

        if (rotation.has(entity)) {
            Rotation r = rotation.get(entity);
            actor.setRotation(r.value);
        }

        if (origin.has(entity)) {
            Origin o = origin.get(entity);
            actor.setOrigin(o.x, o.y);
        }
    }
}
