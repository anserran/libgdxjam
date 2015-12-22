package com.anserran.lis.systems;

import com.anserran.lis.components.AngularVelocity;
import com.anserran.lis.components.Collider;
import com.anserran.lis.components.Renderer;
import com.anserran.lis.components.Velocity;
import com.badlogic.ashley.core.ComponentMapper;

public interface Mappers {
    ComponentMapper<Renderer> renderer = ComponentMapper.getFor(Renderer.class);
    ComponentMapper<Velocity> velocity = ComponentMapper.getFor(Velocity.class);
    ComponentMapper<AngularVelocity> angularVelocity = ComponentMapper.getFor(AngularVelocity.class);
    ComponentMapper<Collider> collider = ComponentMapper.getFor(Collider.class);
}
