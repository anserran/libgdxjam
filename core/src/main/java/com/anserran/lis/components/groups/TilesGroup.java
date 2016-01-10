package com.anserran.lis.components.groups;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

public class TilesGroup extends Group implements Component {

    private TextureRegion textureRegion;

    private Vector2 size = new Vector2();

    public void set(TextureRegion region) {
        set(region, 1, 1);
    }

    public void set(TextureRegion region, float width, float height) {
        this.textureRegion = region;
        size.set(width, height);
    }

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                batch.draw(textureRegion, i * textureRegion.getRegionWidth(), j * textureRegion.getRegionHeight());
            }
        }
    }
}
