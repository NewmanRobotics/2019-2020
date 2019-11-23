/*
 * Copyright (c) 2019 The Newman School Robotics
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.stateProvider;

/**
 * Created by Galvin on 2019-11-16
 */
/**
 * Location gives a fancy way to get and set the bot's position, giving more hints on what
 * we've got from the StateProviders
 */
public class Location {
    public final float positionX;
    public final float positionY;
    public final float positionZ;
    public final float heading;

    Location(
            float positionX,
            float positionY,
            float positionZ,
            float heading
    ) {

        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.heading = heading;
    }

    public String toString() {
        return String.format("Position: x=%.3f | y=%.3f | z=%.3f\n    Heading: %.3f",
                this.positionX,
                this.positionY,
                this.positionZ,
                this.heading
                );
    }
}
