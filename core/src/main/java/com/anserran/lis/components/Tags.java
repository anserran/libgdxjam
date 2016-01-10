package com.anserran.lis.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Tags extends Array<String> implements Component, Poolable {

	@Override
	public void reset() {
		clear();
	}
}
