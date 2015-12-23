package com.anserran.lis.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AngularVelocity implements Component, Value, Poolable {

    public float value;

    @Override
    public void addValue(float value) {
        this.value += value;
    }

    @Override
    public void reset() {
        value = 0;
    }
}
