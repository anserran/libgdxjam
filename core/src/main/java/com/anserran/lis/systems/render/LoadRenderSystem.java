package com.anserran.lis.systems.render;

import com.anserran.lis.C;
import com.anserran.lis.Utils;
import com.anserran.lis.assets.Assets;
import com.anserran.lis.components.LoadRenderer;
import com.anserran.lis.components.groups.SkeletonGroup;
import com.anserran.lis.systems.Mappers;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.esotericsoftware.spine.SkeletonData;

public class LoadRenderSystem extends IteratingSystem implements Mappers {

    private PooledEngine engine;

    private Assets assets;

    private Group root;

    public LoadRenderSystem(Assets assets, Group root) {
        super(Family.all(LoadRenderer.class).get());
        this.assets = assets;
        this.root = root;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = (PooledEngine) engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        LoadRenderer l = loadRenderer.get(entity);
        switch (l.type) {
            case SKELETON:
                SkeletonGroup skeleton = createGroup(entity, SkeletonGroup.class);
                skeleton.setData(assets.get(C.PATH_SKELETONS + l.name + C.EXTENSION_SKELETONS, SkeletonData.class));
                break;
        }
        entity.remove(LoadRenderer.class);
    }

    private <T extends Component> T createGroup(Entity entity, Class<T> clazz) {
        T group = engine.createComponent(clazz);
        entity.add(group);
        root.addActor((Actor) group);
        Utils.update((Actor) group, entity);
        return group;
    }
}
