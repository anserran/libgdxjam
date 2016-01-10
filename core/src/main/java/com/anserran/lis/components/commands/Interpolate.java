package com.anserran.lis.components.commands;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Interpolate implements Component, Poolable {

	public Interpolation interpolation = Interpolation.linear;

	public Class<? extends Component> component;

	public Vector2 value = new Vector2();

	public float totalTime;

	public float time;

	public void set(Class<? extends Component> component, float x, float time,
			Interpolation interpolation) {
		set(component, x, 0, time, interpolation);
	}

	public void set(Class<? extends Component> component, float x, float y,
			float time, Interpolation interpolation) {
		this.component = component;
		value.set(x, y);
		this.totalTime = time;
		this.interpolation = interpolation;
	}

	@Override
	public void reset() {
		interpolation = Interpolation.linear;
		component = null;
		value.set(0, 0);
		totalTime = 0;
		time = 0;
	}
}
