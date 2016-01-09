package com.anserran.lis;

import com.badlogic.gdx.math.Interpolation;

public interface C {
    float THRUST_SPEED = 5;
    float THRUST_TIME = 2.1f;
    Interpolation THRUST_INTERPOLATION = Interpolation.pow3In;

    String PATH_SKELETONS = "skeletons/";
    String EXTENSION_SKELETONS = ".skel";
}
