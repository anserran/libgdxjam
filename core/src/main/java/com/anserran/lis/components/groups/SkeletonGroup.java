package com.anserran.lis.components.groups;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonRenderer;

public class SkeletonGroup extends Group implements Component {

    private SkeletonRenderer skeletonRenderer = new SkeletonRenderer();
    private com.esotericsoftware.spine.Skeleton skeleton;
    private AnimationState state;

    public void setData(SkeletonData skeletonData){
        this.skeleton = new com.esotericsoftware.spine.Skeleton(skeletonData);
        state = new AnimationState(new AnimationStateData(skeletonData));
    }

    @Override
    public void act(float delta){
        super.act(delta);
        state.update(delta);
        state.apply(skeleton);
    }

    @Override
    public void drawChildren(Batch batch, float alpha) {
        skeleton.updateWorldTransform();
        skeletonRenderer.draw(batch, skeleton);
    }
}
