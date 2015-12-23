package com.anserran.lis.loader;

import com.anserran.lis.components.Collider;
import com.anserran.lis.components.Position;
import com.anserran.lis.loader.LevelLoader.EntityData;
import com.anserran.lis.loader.LevelLoader.LevelData;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LevelLoaderTest {

    private LevelLoader loader;

    @BeforeClass
    public static void setUpClass() {
        Gdx.files = new LwjglFiles();
    }

    @Before
    public void setUp() {
        loader = new LevelLoader(new PooledEngine());
    }

    @Test
    public void testLevelLoader() {
        LevelData levelData = loader.load("test");
        assertEquals(levelData.width, 16);
        assertEquals(levelData.height, 11);
        assertEquals(levelData.entities.size, 1);
        EntityData entityData = levelData.entities.get(0);
        assertEquals(entityData.components.size, 2);
        Position position = (Position) entityData.components.get(0);
        assertEquals(position.x, 0, 0.1f);
        assertEquals(position.y, 2, 0.1f);
        Collider collider = (Collider) entityData.components.get(1);
        assertEquals(collider.size.x, 1, 0.1f);
        assertEquals(collider.size.y, 6, 0.1f);
        assertFalse(collider.circle);

    }
}
