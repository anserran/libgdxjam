package com.anserran.lis.systems.render;

import com.anserran.lis.components.Collider;
import com.anserran.lis.components.Rotation;
import com.anserran.lis.systems.Mappers;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class DebugRenderer implements Mappers {

	private ShapeRenderer shapeRenderer;

	private OrthographicCamera camera;

	private Vector2 gridSize = new Vector2();

	private Vector2 viewportSize = new Vector2();

	private Matrix4 transform = new Matrix4();

	private static final Vector2 v = new Vector2();

	private ImmutableArray<Entity> entities;

	public DebugRenderer() {
		this.shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
	}

	public void setEntities(ImmutableArray<Entity> entities) {
		this.entities = entities;
	}

	public void setGridSize(int width, int height) {
		gridSize.set(width, height);
		updateCamera();
	}

	public void resize(int width, int height) {
		viewportSize.set(width, height);
		updateCamera();
	}

	private void updateCamera() {
		camera.setToOrtho(false, viewportSize.x, viewportSize.y);
		float cellSize = Math.min(viewportSize.x / gridSize.x, viewportSize.y
				/ gridSize.y);
		transform.idt();
		transform.translate((viewportSize.x - cellSize * gridSize.x) / 2.0f,
				(viewportSize.y - cellSize * gridSize.y) / 2.0f, 0);
		transform.scl(cellSize, cellSize, 1);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setTransformMatrix(transform);
	}

	public void render() {
		shapeRenderer.setColor(0.05f, 0.05f, 0.05f, 1.0f);
		shapeRenderer.begin(ShapeType.Line);
		for (int i = 0; i <= gridSize.x; i++) {
			shapeRenderer.line(i, 0, 0, i, gridSize.y, 0);
		}

		for (int j = 0; j <= gridSize.y; j++) {
			shapeRenderer.line(0, j, 0, gridSize.x, j, 0);
		}
		shapeRenderer.setColor(Color.GREEN);
		for (Entity entity : entities) {
			renderEntity(entity);
		}
		shapeRenderer.end();
	}

	protected void renderEntity(Entity entity) {
		Collider c = collider.get(entity);
		if (c != null) {
			if (c.circle) {
				shapeRenderer.circle(c.pos.x, c.pos.y, c.size.x, 10);
				if (rotation.has(entity)) {
					Rotation r = rotation.get(entity);
					v.set(c.size.x * 2, 0).rotate(r.value);
					shapeRenderer.line(c.pos.x, c.pos.y, 0, c.pos.x + v.x,
							c.pos.y + v.y, 0);
				}
			} else {
				shapeRenderer.rect(c.pos.x, c.pos.y, c.size.x, c.size.y);
			}
		}
	}

	public void dispose() {
		shapeRenderer.dispose();
	}
}
