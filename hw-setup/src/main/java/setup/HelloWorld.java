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
 * HelloWorld is an implementation of the token
 * introductory "Hello World" program.
 *
 * HelloWorld is also the superclass for other classes in this package.
 */
public class HelloWorld {

    /** the greeting to display when this getGreeting() is invoked */
    public static final String GREETING = "Hello World!";

    /**
     * @param args Command Line Arguments provided to the program
     * @spec.effects prints the string "Hello World!" to the console
     */
    public static void main(String[] args) {
        HelloWorld myFirstHW = new HelloWorld();
        System.out.println(myFirstHW.getGreeting());
    }

    /**
     * @return Returns a greeting (in English).
     */
    public String getGreeting() {
        return GREETING;
    }

}
