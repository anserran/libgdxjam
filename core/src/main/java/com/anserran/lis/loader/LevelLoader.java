package com.anserran.lis.loader;

import com.anserran.lis.components.Behaviour;
import com.anserran.lis.components.Behaviour.Type;
import com.anserran.lis.components.Collider;
import com.anserran.lis.components.Origin;
import com.anserran.lis.components.Position;
import com.anserran.lis.components.LoadRenderer;
import com.anserran.lis.components.Rotation;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public class LevelLoader {

    private Json json;

    private PooledEngine engine;

    public LevelLoader(final PooledEngine engine) {
        this(new Json() {
            @Override
            protected Object newInstance(Class type) {
                if (ClassReflection.isAssignableFrom(Component.class, type)) {
                    return engine.createComponent(type);
                } else {
                    return super.newInstance(type);
                }
            }
        });
        this.engine = engine;
    }

    public LevelLoader(Json json) {
        this.json = json;
        json.addClassTag("position", Position.class);
        json.addClassTag("rotation", Rotation.class);
        json.addClassTag("collider", Collider.class);
        json.setSerializer(EntityData.class, new EntityDataLoader());
    }

    public LevelData load(String levelName) {
        return json.fromJson(LevelData.class,
                Gdx.files.internal(levelName + ".json"));
    }

    public static class LevelData {
        public int width;
        public int height;
        public Array<EntityData> entities;
    }

    public static class EntityData {
        public String id;
        public Array<Component> components;
    }

    public class EntityDataLoader extends ReadOnlySerializer<EntityData> {

        private Array<String> position = new Array<String>(new String[]{
                "wall", "astronaut", "exit"});
        private Array<String> rectangle = new Array<String>(
                new String[]{"wall", "exit"});
        private Array<String> circle = new Array<String>(new String[]{});
        private Array<String> rotation = new Array<String>(
                new String[]{"astronaut"});

        @Override
        public EntityData read(Json json, JsonValue jsonData, Class clazz) {
            EntityData entityData = new EntityData();

            if (jsonData.has("type")) {
                String type = jsonData.getString("type");
                Array<Component> components = new Array<Component>();
                if (position.contains(type, false)) {
                    Position p = newComponent(Position.class, components);
                    p.set(jsonData.getFloat("x"), jsonData.getFloat("y"));
                }

                if (rectangle.contains(type, false)) {
                    Collider collider = newComponent(Collider.class, components);
                    collider.setSize(jsonData.getFloat("w"),
                            jsonData.getFloat("h"));
                    collider.dynamic = false;
                }

                if (circle.contains(type, false)) {
                    Collider collider = newComponent(Collider.class, components);
                    collider.setRadius(jsonData.getFloat("rad"));
                }

                if (rotation.contains(type, false)) {
                    Rotation rotation = newComponent(Rotation.class, components);
                    if (jsonData.has("r")) {
                        rotation.value = jsonData.getFloat("r");
                    }
                }

                if ("astronaut".equals(type)) {
                    Collider collider = newComponent(Collider.class, components);
                    collider.setRadius(0.4f);
                    Origin origin = newComponent(Origin.class, components);
                    origin.set(0.5f, 0.5f);
                    entityData.id = "a";
                    behaviour(Type.ASTRONAUT, components);

                    LoadRenderer loadRenderer = newComponent(LoadRenderer.class, components);
                    loadRenderer.type = LoadRenderer.Type.SKELETON;
                    loadRenderer.name = "astronaut";
                }

                if ("exit".equals(type)) {
                    behaviour(Type.EXIT, components);
                }

                entityData.components = components;
            } else {
                json.readFields(entityData, jsonData);
            }
            return entityData;
        }

        private <T extends Component> T newComponent(Class<T> clazz,
                                                     Array<Component> components) {
            T component = engine.createComponent(clazz);
            components.add(component);
            return component;
        }

        private void behaviour(Type type, Array<Component> components) {
            Behaviour behaviour = newComponent(Behaviour.class, components);
            behaviour.type = type;
        }
    }

}
