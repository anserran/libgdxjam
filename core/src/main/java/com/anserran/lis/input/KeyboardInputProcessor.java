package com.anserran.lis.input;

import com.anserran.lis.Data.Command;
import com.anserran.lis.LifeInSpace;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class KeyboardInputProcessor implements InputProcessor {

	private LifeInSpace controller;

	public KeyboardInputProcessor(LifeInSpace controller) {
		this.controller = controller;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.ENTER:
			controller.run();
			break;
		case Keys.SPACE:
			controller.getData().addCommand(Command.THRUST);
			break;
		case Keys.A:
			controller.getData().addCommand(Command.ROTATE_CCW);
			break;
		case Keys.S:
			controller.getData().addCommand(Command.ROTATE_CW);
			break;
		case Keys.NUM_1:
			controller.updates = 1;
			break;
		case Keys.NUM_2:
			controller.updates = 10;
			break;
		case Keys.R:
			controller.restartLevel();
			break;

		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
