package com.anserran.lis.ui;

import com.anserran.lis.C;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class GameGrid extends WidgetGroup {

    private Vector2 gridSize = new Vector2();

    private Group container = new Group();

    public GameGrid(){
        setFillParent(true);
        addActor(container);
    }

    public void setGridSize(int width, int height){
        gridSize.set(width, height);
        updateScale();
    }

    public void add(Actor actor){
        container.addActor(actor);
    }

    private void updateScale(){
        float cellSize = Math.min(getWidth() / gridSize.x, getHeight() / gridSize.y);
        container.setPosition((getWidth() - cellSize * gridSize.x) / 2.0f, (getHeight() - cellSize * gridSize.y) / 2.0f);
        container.setScale(cellSize / C.SPRITE_SIZE);
    }

    public void layout(){
        updateScale();
    }
}
