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
    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double     DISTANCE_FROM_WHEELS    = 15.5;

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

    public void rotate_angle(double speed,
                             double angle,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Determine new target position, and pass to motor controller
        newLeftTarget = left.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        newRightTarget = right.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        left.setTargetPosition(newLeftTarget);
        right.setTargetPosition(newRightTarget);

        // Turn On RUN_TO_POSITION
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        left.setPower(Math.abs(speed));
        right.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
        while ((runtime.seconds() < timeoutS) && (left.isBusy() && right.isBusy())) {

            // Display it for the driver.
            telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
            telemetry.addData("Path2",  "Running at %7d :%7d",
                    left.getCurrentPosition(),
                    right.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion;
        left.setPower(0);
        right.setPower(0);

        // Turn off RUN_TO_POSITION
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
