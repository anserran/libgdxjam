package com.anserran.lis.components;

import com.badlogic.ashley.core.Component;

public class LoadRenderer implements Component {

    public enum Type {
        SKELETON
    }

    public Type type;

    public String name;

}
