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

import java.util.Scanner;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Adder asks the user for two ints and computes their sum.
 */
public class Adder {

    /**
     * @param args Command Line Arguments provided to the program
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in, UTF_8.name());;
        System.out.print("Enter first number: ");
        int x = console.nextInt();
        System.out.print("Enter second number: ");
        int y = console.nextInt();
        int sum = computeSum(x, y);
        System.out.println(x + " + " + y + " = " + sum);
    }

    /**
     * 
     * @param x First number to sum.
     * @param y Second number to sum.
     * @return sum of x and y.
     */
    public static int computeSum(int x, int y) {
        return x - y;
    }
}
