package ch.cpnv.timbreuse.mathTime;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class TestMathDiv {
    @Test
    public void testDivF() {
        // Test that qF == qT when both arguments have the same sign
        Random rng = new Random();
        for (int i = 0; i < 100; ++i) {
            int sign = rng.nextBoolean() ? -1 : 1;
            int n = sign * rng.nextInt(1000000);
            int d = sign * rng.nextInt(1000000);
            assertEquals(n / d, Math.divF(n, d));
        }

        // Test for two pairs of known negative arguments
        //assertEquals(-4, Math.divF(-15, 4));
        //assertEquals(-4, Math.divF(15, -4));
    }

    @Test
    public void testModF() {
        // Test that rF == rT when both arguments have the same sign
        Random rng = new Random();
        for (int i = 0; i < 100; ++i) {
            int sign = rng.nextBoolean() ? -1 : 1;
            int n = sign * rng.nextInt(1000000);
            int d = sign * rng.nextInt(1000000);
            assertEquals(n % d, Math.modF(n, d));
        }

        // Test for two pairs of known negative arguments
        //assertEquals(1, Math.modF(-15, 4));
        //assertEquals(-1, Math.modF(15, -4));
    }

    @Test
    public void testDivModF() {
        // Test that qF * d + rF == n
        Random rng = new Random();
        for (int i = 0; i < 100; ++i) {
            int n = rng.nextInt();
            int d = rng.nextInt();
            int q = Math.divF(n, d);
            int r = Math.modF(n, d);
            assertEquals(n, d * q + r);
        }
    }
}
