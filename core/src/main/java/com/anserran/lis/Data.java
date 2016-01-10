package com.anserran.lis;

import com.anserran.lis.components.commands.Commands;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.ObjectMap;

public class Data {

	public enum Command {
		THRUST, ROTATE_CW, ROTATE_CCW
	}

	private PooledEngine engine;

	private ObjectMap<String, Commands> commandsMap = new ObjectMap<String, Commands>();

	private String idSelected = "a";

	public Data(PooledEngine engine) {
		this.engine = engine;
	}

	public void addCommand(Command command) {
		Commands commands = commandsMap.get(idSelected);
		if (commands == null) {
			commands = engine.createComponent(Commands.class);
			commandsMap.put(idSelected, commands);
		}
		commands.add(command);
	}

	public ObjectMap<String, Commands> getCommandsMap() {
		return commandsMap;
	}
}
