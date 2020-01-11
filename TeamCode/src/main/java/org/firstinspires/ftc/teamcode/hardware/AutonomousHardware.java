/*
 * Copyright (c) 2020 The Newman School Robotics
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

package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.locationDescriptor.Coord;
import org.firstinspires.ftc.teamcode.stateProvider.Location;

/**
 * Created by Galvin on 2020-01-05
 */
public class AutonomousHardware extends ArmLiftHardware {
    public enum Power {
        STOP(0.0),
        SLOW(0.25),
        MEDIUM(0.5),
        FAST(0.75),
        EXTREME(1.0);

        public double value;

        Power(double i) {
            this.value = i;
        }
    }

    public enum Direction {
        CLOCKWISE,
        ANTI_CLOCKWISE
    }

    public void forward(Power power) {
        left.setPower(power.value);
        right.setPower(power.value);
    }

    public void rotate(Direction direction, Power power) {
        double leftPower, rightPower;
        if (direction.equals(Direction.CLOCKWISE)) {
            leftPower = power.value;
            rightPower = - power.value;
        } else {
            leftPower = - power.value;
            rightPower = power.value;
        }
        left.setPower(leftPower);
        right.setPower(rightPower);
    }

    public void rotate(Direction direction, double angle) {

    }

    public void approach(Location from, Coord to) {
        Coord diff = new Coord(
                from.positionX - to.x,
                from.positionY - to.y
        );
        // TODO: implement approach method
    }

    public void stop() {
        left.setPower(Power.STOP.value);
        right.setPower(Power.STOP.value);
    }
}
