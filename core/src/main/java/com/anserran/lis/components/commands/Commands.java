package com.anserran.lis.components.commands;

import com.anserran.lis.Data.Command;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Commands extends Array<Command> implements Component, Poolable {

    @Override
    public void reset() {
        clear();
    }
}
