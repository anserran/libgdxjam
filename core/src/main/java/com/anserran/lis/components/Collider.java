package com.anserran.lis.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collider implements Component {

    private static final Vector2 p1 = new Vector2();
    private static final Vector2 p2 = new Vector2();

    private static final Circle c1 = new Circle();
    private static final Circle c2 = new Circle();

    private static final Rectangle rect1 = new Rectangle();
    private static final Rectangle rect2 = new Rectangle();

    public boolean circle = false;

    public Vector2 pos = new Vector2();

    public Vector2 size = new Vector2();

    public Collider() {

    }

    public Collider(float x, float y, float width, float height) {
        pos.set(x, y);
        size.set(width, height);
        this.circle = false;
    }

    public Collider(float x, float y, float radius) {
        pos.set(x, y);
        setRadius(radius);
    }

    public void setRadius(float radius) {
        circle = true;
        size.x = radius;
        size.y = radius;
    }

    public boolean intersects(Collider collider) {
        if (this.circle && collider.circle) {
            c1.set(pos, size.x);
            c2.set(collider.pos, collider.size.x);
            return c1.overlaps(c2);
        } else if (!this.circle && !collider.circle) {
            rect1.set(pos.x, pos.y, size.x, size.y);
            rect2.set(collider.pos.x, collider.pos.y, collider.size.x, collider.size.y);
            return rect1.overlaps(rect2);
        } else {
            return this.circle ? intersectCircleRectangle(this, collider) : intersectCircleRectangle(collider, this);
        }
    }

    private boolean intersectCircleRectangle(Collider circle, Collider rectangle) {
        float left = rectangle.pos.x;
        float right = left + rectangle.size.x;
        float bottom = rectangle.pos.y;
        float top = rectangle.pos.y + rectangle.size.y;
        float squareRadius = circle.size.x * circle.size.x;
        return rect1.set(rectangle.pos.x, rectangle.pos.y, rectangle.size.x, rectangle.size.y).contains(circle.pos) ||
                Intersector.intersectSegmentCircle(p1.set(left, bottom), p2.set(right, bottom), circle.pos, squareRadius) ||
                Intersector.intersectSegmentCircle(p2, p1.set(right, top), circle.pos, squareRadius) ||
                Intersector.intersectSegmentCircle(p1, p2.set(left, top), circle.pos, squareRadius) ||
                Intersector.intersectSegmentCircle(p2, p1.set(left, bottom), circle.pos, squareRadius);
    }
}
