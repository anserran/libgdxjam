package com.anserran.lis.components.groups;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonRenderer;

public class SkeletonGroup extends Group implements Component {

    private SkeletonData skeletonData;
    private SkeletonRenderer skeletonRenderer = new SkeletonRenderer();
    private Skeleton skeleton;
    private AnimationState state;

    public void setData(SkeletonData skeletonData){
        this.skeletonData = skeletonData;
        this.skeleton = new Skeleton(skeletonData);
        AnimationStateData stateData = new AnimationStateData(skeletonData);
        state = new AnimationState(stateData);
        state.setAnimation(0, "float", true);
    }

    public void setAnimation(String animation){

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
