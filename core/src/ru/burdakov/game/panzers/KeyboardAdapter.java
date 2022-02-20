package ru.burdakov.game.panzers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class KeyboardAdapter extends InputAdapter {

    private Vector2 direction = new Vector2();
    private Vector2 mousePos = new Vector2();
    private Vector2 angle = new Vector2();

    private final InputState inputState;

    public KeyboardAdapter(InputState inputState) {
        this.inputState = inputState;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) inputState.setLeftPressed(true);
        if (keycode == Input.Keys.D) inputState.setRightPressed(true);
        if (keycode == Input.Keys.W) inputState.setUpPressed(true);
        if (keycode == Input.Keys.S) inputState.setDownPressed(true);

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A) inputState.setLeftPressed(false);
        if (keycode == Input.Keys.D) inputState.setRightPressed(false);
        if (keycode == Input.Keys.W) inputState.setUpPressed(false);
        if (keycode == Input.Keys.S) inputState.setDownPressed(false);

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePos.set(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
//    todo letsCode сделал по другому, но у меня будет так :D
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mousePos.set(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        inputState.setFirePressed(true);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        inputState.setFirePressed(false);
        return true;
    }

    public Vector2 getDirection() {
        direction.set(0, 0);

        if (inputState.isLeftPressed()) direction.add(-5, 0);
        if (inputState.isRightPressed()) direction.add(5, 0);
        if (inputState.isUpPressed()) direction.add(0, 5);
        if (inputState.isDownPressed()) direction.add(0, -5);

        return direction;
    }

    public Vector2 getMousePos() {
        return mousePos;
    }

    public InputState updateAndGetInputState(Vector2 playerOrigin) {
        angle.set(mousePos).sub(playerOrigin);
        inputState.setAngle(angle.angleDeg() - 90);
        return inputState;
    }
}
