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
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

/**
 * RandomHelloTest is a simple test of the RandomHello class that is
 * to be written by the students. This test just makes sure that the
 * program does not crash and that it prints at least 5 different
 * greetings.
 */
public class RandomHelloTest {

    /** Number of times to run the greeting test until we're quite sure we'd have seen all the greetings */
    private int TIMES_TO_TEST = 1000;

    /** Required number of greetings */
    private int REQUIRED_NUMBER_OF_GREETINGS = 5;

    /**
     * Tests that RandomHello does not crash.
     */
    @Test
    public void testCrash() {
        /* If RandomHello.main() throws an exception, it will
         * propagate outside testCrash() and JUnit will flag
         * an error. */
        RandomHello.main(new String[0]);
    }


    /**
     * Tests that the greetings are indeed random and that there are
     * at least 5 different ones.
     */
    @Test
    public void testGreetings() {
        RandomHello world = new RandomHello();
        Set<String> set = new HashSet<String>();

        for (int i=0; i< TIMES_TO_TEST; i++) {
            String greeting = world.getGreeting();
            if (!set.contains(greeting)) {
                set.add(greeting);
            }
        }
        assertEquals("Wrong number of greetings in RandomHello",
                     REQUIRED_NUMBER_OF_GREETINGS, set.size());
    }


}
