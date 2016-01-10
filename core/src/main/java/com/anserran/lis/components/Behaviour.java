package com.anserran.lis.components;

import com.badlogic.ashley.core.Component;

public class Behaviour implements Component {

	public enum Type {
		ASTRONAUT, EXIT, WALL, WIRES
	}

	public Type type;
}
