package com.anserran.lis.components;

import com.badlogic.ashley.core.Component;

public class Rotation implements Component, Value {

    public float value;

    @Override
    public void addValue(float v) {
        value += v;
    }
}
