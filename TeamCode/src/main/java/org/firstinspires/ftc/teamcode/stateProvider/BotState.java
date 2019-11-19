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
 * BotState gives a fancy way to get and set the bot's position, giving more hints on what
 * we've got from the StateProviders
 */
public class BotState {
    final float positionX;
    final float positionY;
    final float positionZ;
    final float rotationRoll;
    final float rotationPitch;
    final float rotationHeading;

    BotState(
            float positionX,
            float positionY,
            float positionZ,
            float rotationRoll,
            float rotationPitch,
            float rotationHeading
    ) {

        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.rotationRoll = rotationRoll;
        this.rotationPitch = rotationPitch;
        this.rotationHeading = rotationHeading;
    }

    public String toString() {
        return String.format("Position: x=%.3f | y=%.3f | z=%.3f\n    Rotation: r=%.3f | p=%.3f | h=%.3f",
                this.positionX,
                this.positionY,
                this.positionZ,
                this.rotationRoll,
                this.rotationPitch,
                this.rotationHeading
                );
    }
}
