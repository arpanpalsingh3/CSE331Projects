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
 * This is a simple object that has a volume.
 */
// You may not make Ball implement the Comparable interface.
public class Ball {

    /** the volume of the Ball. */
    private double volume;

    /**
     * Constructor that creates a new ball object with the specified volume.
     * @param volumeInput Volume of the new object.
     */
    public Ball(double volumeInput) {
        volume = volumeInput;
    }

    /**
     * Returns the volume of the Ball.
     * @return the volume of the Ball.
     */
    public double getVolume() {
        return volume;
    }

}
