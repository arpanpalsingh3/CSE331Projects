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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * HolaWorldTest is a simple test of the HolaWorld class that you
 * are to fix.  This test just makes sure that the program
 * does not crash and that the correct greeting is printed.
 */
public class HolaWorldTest {

    /**
     * Tests that HolaWorld does not crash
     */
    @Test
    public void testCrash() {
        /* Any exception thrown will be caught by JUnit. */
        HolaWorld.main(new String[0]);
    }

    /**
     * Tests that the HolaWorld greeting is correct.
     */
    @Test
    public void testGreeting() {
        HolaWorld world = new HolaWorld();
        assertEquals(HolaWorld.SPANISH_GREETING, world.getGreeting());
    }

    /**
     * Tests that the output of HolaWorld.main() is correct.
     */
    @Test
    public void testMainOutput() {
        
        // Redirect System.out to an OutputStream
        PrintStream sysoutStream = System.out;
        ByteArrayOutputStream mainOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(mainOutput));
        
        HolaWorld.main(new String[0]);
        System.setOut(sysoutStream);
        Scanner scan = new Scanner(mainOutput.toString());
        assertTrue(scan.hasNextLine());
        assertEquals(scan.nextLine(), HelloWorld.GREETING);
        assertTrue(scan.hasNextLine());
        assertEquals(scan.nextLine(), HolaWorld.SPANISH_GREETING);

        assertFalse(scan.hasNextLine());
    }
}
