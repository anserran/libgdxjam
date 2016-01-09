package com.anserran.lis.assets.loaders;

import com.anserran.lis.assets.loaders.SkeletonLoader.SkeletonAssetParameter;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.SkeletonBinary;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;

public class SkeletonLoader extends
        AsynchronousAssetLoader<SkeletonData, SkeletonAssetParameter> {

    private String name;

    private boolean json;

    private SkeletonData skeletonData;

    public SkeletonLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName,
                                                  FileHandle file, SkeletonAssetParameter parameter) {
        Array<AssetDescriptor> dependencies = new Array<AssetDescriptor>();
        name = file.path();
        json = name.endsWith(".json");
        name = name.substring(0, name.length() - 5);

        dependencies.add(new AssetDescriptor(name + ".atlas",
                TextureAtlas.class));

        return dependencies;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName,
                          FileHandle file, SkeletonAssetParameter parameter) {
        TextureAtlas atlas = manager
                .get(name + ".atlas", TextureAtlas.class);
        if (json) {
            skeletonData = new SkeletonJson(atlas)
                    .readSkeletonData(file);
        } else {
            skeletonData = new SkeletonBinary(atlas).readSkeletonData(file);
        }
    }

    @Override
    public SkeletonData loadSync(AssetManager manager, String fileName,
                                 FileHandle file, SkeletonAssetParameter parameter) {
        return skeletonData;
    }

    public static class SkeletonAssetParameter extends
            AssetLoaderParameters<SkeletonData> {
    }
}

