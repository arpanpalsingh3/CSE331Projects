/*
 * Copyright ©2020 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package setup;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 * BallTest is a simple glassbox test of the Ball class.
 *
 * @see setup.Ball
 */
public class BallTest {

    private static Ball b = null;

    private static final double BALL_VOLUME = 20.0;
    private static final double JUNIT_DOUBLE_DELTA = 0.0001;

    @BeforeClass 
    public static void setupBeforeTests() throws Exception {
        b = new Ball(BALL_VOLUME);
    }

    /** Test to see that Ball returns the correct volume when queried. */
    @Test
    public void testVolume() {
        assertEquals("b.getVolume()",
                     BALL_VOLUME, b.getVolume(), JUNIT_DOUBLE_DELTA);
    }

}
