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

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Galvin on 2020-01-05
 */
public class AutonomousArmLiftHardware extends ArmLiftHardware {
    private static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: TETRIX Motor Encoder
    private static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    private static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    private static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    private static final double DISTANCE_FROM_WHEELS = 15.5;

    private ElapsedTime runtime = new ElapsedTime();

    private boolean check(double distance, int target, int current) {
        if (distance > 0) {
            return current < target;
        } else {
            return current > target;
        }
    }

    public void move(double speed,
                     double leftInches, double rightInches, int timeoutS, Telemetry telemetry, IsActiveCallback isActiveCallback) {
        int newLeftTarget;
        int newRightTarget;

        // Determine new target position, and pass to motor controller
        newLeftTarget = left.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
        newRightTarget = right.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
//        left.setTargetPosition(newLeftTarget);
//        right.setTargetPosition(newRightTarget);

        // Turn On RUN_TO_POSITION
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // reset the timeout time and start motion.
        runtime.reset();
        if (leftInches > 0) {
            left.setPower(-speed);
        } else {
            left.setPower(speed);
        }
        if (rightInches > 0) {
            right.setPower(-speed);
        } else {
            right.setPower(speed);
        }

        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
//        while ((runtime.seconds() < timeoutS) &&
//                        ((Math.abs(newLeftTarget) - left.getCurrentPosition() > 0 ) &&
//                                ( Math.abs(newRightTarget) - Math.abs(right.getCurrentPosition()) > 0))
        // TODO: finish the mathematical condition
        while ((runtime.seconds() < timeoutS) && isActiveCallback.isActive() &&
                check(rightInches, newRightTarget, -right.getCurrentPosition()) && check(leftInches, newLeftTarget, -left.getCurrentPosition())) {
            telemetry.addData("Status", "Running");
            // Display it for the driver.
            telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
            telemetry.addData("Path2", "Running at %7d :%7d",
                    left.getCurrentPosition(),
                    right.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion;
        left.setPower(0);
        right.setPower(0);

        telemetry.addData("Status", "Completed");

        // Turn off RUN_TO_POSITION
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each mov
    }

    public void move(double speed) {
        left.setPower(speed);
        right.setPower(speed);
    }

    public void move(double speed, double milliseconds, Telemetry telemetry, IsActiveCallback isActiveCallback) {
        move(speed);
        runtime.reset();
        double now = runtime.milliseconds();
        telemetry.addLine((runtime.milliseconds() - now) + "");
        telemetry.update();
        while (isActiveCallback.isActive() && Math.abs(runtime.milliseconds() - now) < milliseconds) {
            telemetry.addData("Move mode", "by Time");
            telemetry.addData("Remaining", runtime.milliseconds() - now);
            telemetry.update();
        }
    }

    public void stop() {
        left.setPower(0.0);
        right.setPower(0.0);
    }
}
