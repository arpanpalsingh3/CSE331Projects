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

/**
 * HolaWorld is like HelloWorld except it can say hello in Spanish!
 */
public class HolaWorld extends HelloWorld{

    /** Greeting in Spanish */
    public static final String SPANISH_GREETING = "Hola Mundo!";

    /**
     * Shows what happens when the getGreeting() method
     * of both HelloWorld and HolaWorld are invoked
     * @param argv Command Line Arguments provided to the program
     */
    public static void main(String[] argv) {

        // Create the Hello World objects.
        HelloWorld myFirstHW = new HelloWorld();
        HolaWorld world = new HolaWorld();

        // Print out greetings
        System.out.println(myFirstHW.getGreeting());
        System.out.println(world.getGreeting());
    }
    /**
     * @return Returns a greeting (in Spanish).
     */
    @Override

    public String getGreeting() {
        return SPANISH_GREETING;
    }


}
