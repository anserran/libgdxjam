package com.anserran.lis.components;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ColliderTest {

    @Test
    public void testCircleCircle() {
        Collider c1 = new Collider(0, 0, 1);
        Collider c2 = new Collider(2, 0, 1);
        Collider c3 = new Collider(15, 15, 4);
        Collider c4 = new Collider(0, 0, 1.5f);

        assertFalse(c1.intersects(c3));
        assertFalse(c3.intersects(c1));
        assertTrue(c1.intersects(c4));
        assertTrue(c4.intersects(c1));
        assertTrue(c4.intersects(c2));
        assertTrue(c2.intersects(c4));
    }

    @Test
    public void testRectangleRectangle() {
        Collider r1 = new Collider(0, 0, 1, 1);
        Collider r2 = new Collider(1, 0, 1, 1);
        Collider r3 = new Collider(2, 0, 1, 1);
        Collider r4 = new Collider(2.5f, -1.f, 3.5f, 3.f);
        Collider r5 = new Collider(3, 1, 1, 2);
        Collider r6 = new Collider(5, 0, 6, 1);
        Collider r7 = new Collider(5, -2, 6, 18);
        Collider r8 = new Collider(3f, 0, .5f, .5f);

        assertFalse(r1.intersects(r2));
        assertFalse(r2.intersects(r3));
        assertFalse(r1.intersects(r3));
        assertTrue(r3.intersects(r4));
        assertFalse(r1.intersects(r5));
        assertTrue(r4.intersects(r5));
        assertTrue(r4.intersects(r6));
        assertTrue(r4.intersects(r7));
        assertTrue(r4.intersects(r8));
    }

    @Test
    public void testRectangleCircle(){
        Collider r = new Collider(0, 0, 1, 1);
        Collider c = new Collider(3, 0, 1);

        assertFalse(r.intersects(c));
        assertFalse(c.intersects(r));

        c.pos.x = 1f;

        assertTrue(r.intersects(c));
        assertTrue(c.intersects(r));

        c.pos.set(.5f, .5f);
        c.size.x = .5f;

        assertTrue(r.intersects(c));

        c.pos.set(0, 0);
        c.size.x = 100;

        assertTrue(r.intersects(c));
    }
}
