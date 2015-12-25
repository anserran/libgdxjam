package com.anserran.lis.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Rotation implements Component, Value, Poolable {

	public float value;

	@Override
	public void addValue(float v) {
		value += v;
	}

	@Override
	public void reset() {
		value = 0;
	}
}
