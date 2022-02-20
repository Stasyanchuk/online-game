package ru.burdakov.game.panzers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Panzer {

    private final float size = 128;
    private final float halfSize = size / 2;

    private final Vector2 position = new Vector2();
    private final Vector2 angle = new Vector2(1, 1);
    private final Vector2 origin = new Vector2();

    private final Texture texture;
    private final TextureRegion textureRegion;

    public Panzer(float x, float y) {
       this(x, y, "my-panzer.png");
    }

    public Panzer(float x, float y, String textureName){
        this.texture = new Texture(textureName);
        this.textureRegion = new TextureRegion(texture);
        this.position.set(x, y);
        this.position.set(position).add(halfSize, halfSize);
    }

    public void render(Batch batch) {
        batch.draw(textureRegion,
                position.x,
                position.y,
                halfSize,
                halfSize,
                size,
                size,
                1,
                1,
                angle.angleDeg());
    }

    public void dispose() {
        texture.dispose();
    }

    public void moveTo(Vector2 newPosition) {
        position.add(newPosition);
        origin.set(position).add(halfSize, halfSize);
    }

    public void moveTo(float x, float y) {
        position.set(x, y);
        origin.set(position).add(halfSize, halfSize);
    }

    public void rotateTo(Vector2 mousePos) {
        angle.set(mousePos).sub(origin);
    }

    public Vector2 getPosition(){
        return position;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public void rotateTo(float angle) {
        this.angle.setAngleDeg(angle);
    }
}
