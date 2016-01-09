package com.anserran.lis.assets;

import com.anserran.lis.assets.loaders.SkeletonLoader;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.esotericsoftware.spine.SkeletonData;

public class Assets extends AssetManager {

    public Assets() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        setLoader(SkeletonData.class, new SkeletonLoader(resolver));
    }
}
