package ch.cpnv.timbreuse.mathTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

public class TestDate {

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testConstructorIntMonthTooSmall() {
        new Date(1, 0, 1);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testConstructorIntMonthTooBig() {
        new Date(1, 13, 1);
    }

    @Test
    public void testDay() {
        for (int d = 1; d <= 31; ++d)
            assertEquals(d, (new Date(d, 1, 2014)).day());
    }

    @Test
    public void testYear() {
        Random rng = new Random();
        for (int i = 0; i < 100; ++i) {
            int y = rng.nextInt();
            assertEquals(y, (new Date(12, 3, y)).year());
        }
    }

	@Test
	public void testRelative() {
        assertEquals(new Date(1
        		, 3, 2000), new Date(29, 2, 2000).relative(1));
        assertEquals(new Date(1, 3, 2100), new Date(28, 2, 2100).relative(1));
        assertEquals(new Date(1, 10, 1973), new Date(25, 11, 2013).relative(-14665));
        assertEquals(new Date(1, 10, 1973), new Date(1, 10, 1973).relative(0));
	}

	@SuppressWarnings("deprecation")
    @Test
	public void testToJavaDate() {
	    assertEquals(new java.util.Date(114, 1, 17), new Date(17, 2, 2014).toJavaDate());
	}

	// equals is extensively tested by all the assertEquals calls, we do not test it further here.

	@Test
	public void testToString() {
	    assertEquals("2014-2-17", new Date(17, 2, 2014).toString());
	}

	/*@Test
	public void testCompareTo() {
	    assertTrue(new Date(1, Month.JANUARY, 2014).compareTo(new Date(31, Month.DECEMBER, 2013)) > 0);
        assertTrue(new Date(1, Month.JANUARY, 2014).compareTo(new Date(31, Month.DECEMBER, 2014)) < 0);
        assertTrue(new Date(1, Month.JANUARY, 2014).compareTo(new Date(1, Month.JANUARY, 2014)) == 0);
	}*/
}
