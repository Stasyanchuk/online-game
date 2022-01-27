package ru.burdakov.game.panzers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Panzer {

    private final float size = 128;
    private final float halfSize = size / 2;

    private final Vector2 position = new Vector2();
    private final Vector2 angle = new Vector2();
    private final Texture texture;
    private final TextureRegion textureRegion;

    public Panzer(float x, float y) {
       this(x, y, "my-panzer.png");
    }

    public Panzer(float x, float y, String textureName){
        this.texture = new Texture(textureName);
        this.textureRegion = new TextureRegion(texture);
        this.position.set(x, y);
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
                angle.angleDeg() - 90);
    }

    public void dispose() {
        texture.dispose();
    }

    public void moveTo(Vector2 newPosition) {
        position.add(newPosition);
    }

    public void rotateTo(Vector2 mousePos) {
        angle.set(mousePos).sub(position.x + halfSize, position.y + halfSize);
    }

    public Vector2 getPosition(){
        return position;
    }
}
