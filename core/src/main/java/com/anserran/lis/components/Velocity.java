package com.anserran.lis.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Velocity extends Vector2 implements Component, Poolable {
	@Override
	public void reset() {
		set(0, 0);
	}
}
