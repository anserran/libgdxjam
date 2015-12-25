package com.anserran.lis;

import com.anserran.lis.components.Position;
import com.anserran.lis.components.commands.Interpolate;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Interpolation;

public class Utils {

	public static PooledEngine engine;

	public static Interpolate moveTo(float x, float y, float toX, float toY, float time, Interpolation interpolation){
		Interpolate interpolate = engine.createComponent(Interpolate.class);
		interpolate.value.set(toX - x, toY - y);
		interpolate.totalTime = time;
		interpolate.interpolation = interpolation;
		interpolate.component = Position.class;
		return interpolate;
	}
}
