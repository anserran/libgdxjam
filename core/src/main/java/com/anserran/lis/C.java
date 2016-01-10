package com.anserran.lis;

import com.badlogic.gdx.math.Interpolation;

public interface C {
	float SPRITE_SCALE = 0.4f;
	int SPRITE_SIZE = 480;
	float SPRITE_SIZE_SCALED = SPRITE_SIZE * 0.4f;

	float THRUST_SPEED = 3;
	float THRUST_TIME = 3.1f;
	Interpolation THRUST_INTERPOLATION = Interpolation.pow3In;

	String PATH_SKELETONS = "skeletons/";
	String EXTENSION_SKELETONS = ".skel";
	String PATH_ATLAS = "scale" + SPRITE_SCALE + "/atlas.atlas";

}
