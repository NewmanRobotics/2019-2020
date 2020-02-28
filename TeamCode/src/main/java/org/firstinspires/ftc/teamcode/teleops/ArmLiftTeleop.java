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

package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;

@TeleOp(name = "Arm Lift Bot", group = "Production")
public class ArmLiftTeleop extends OpMode {
    private ArmLiftHardware robot = new ArmLiftHardware();
    private long EACH = 20;
    private double DRIVE_FACTOR = 0.3;
    private double ARM_FACTOR = 0.2;
    private double rotateScale = 0.30;
    private boolean buttonPressedAtLastLoop = false;
    private boolean toggleSpeed = false;
    private boolean camDoingInit = false;

    @Override
    public void init() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap);
    }

    @Override
    public void start() {
        telemetry.addData("[Sub-Thread 1]", "thread started: lift arm");
        Thread thread1 = new Thread() {
            public void run() {
                camDoingInit = true;
                robot.armLifter.setPower(0.8);
                robot.waitForTick(300);
                robot.armLifter.setPower(0.0);
                camDoingInit = false;
                telemetry.addData("[Sub-Thread 1]", "thread finished: lift arm");
            }
        };
        thread1.start();

        telemetry.addData("[Sub-Thread 2]", "thread started: cam");
        Thread thread2 = new Thread() {
            public void run() {
                robot.cam.setPower(0.2);
                robot.waitForTick(1800);
                robot.cam.setPower(-0.055);
                telemetry.addData("[Sub-Thread 2]", "thread finished: cam");
            }
        };
        thread2.start();
    }

    @Override
    public void loop() {
        // bind the game pad Y inputs to the two tracks
        /*
         * gamepad1 is for the player who will control the direction of the robot
         * gamepad2 is for the player who will control the grabber and the lift of the robot
         *
         * bindings:
         */

        /*
         * gamepad1:
         *   - [left & right stick Y] Robot driver with Tank driving style
         */
        double left = gamepad1.left_stick_y * DRIVE_FACTOR;
        double right = gamepad1.right_stick_y * DRIVE_FACTOR;

        // when pressing the button and button has not been pressed at the last loop then
        // switch the speed adjuster
        if (gamepad1.b && !buttonPressedAtLastLoop) {
            toggleSpeed = !toggleSpeed;
        }
        telemetry.addData("SLOW MODE", !toggleSpeed ? "✓ YES" : "× NO");
        // record the button pressed state in current loop
        buttonPressedAtLastLoop = gamepad1.b;
        // if speed is toggled
        if (toggleSpeed) {
            // use direct inputs
            robot.left.setPower(gamepad1.left_stick_y);
            robot.right.setPower(gamepad1.right_stick_y);
            telemetry.addData("Left Power", gamepad1.left_stick_y);
            telemetry.addData("Right Power", gamepad1.right_stick_y);
        } else {
            // use scaled inputs
            robot.left.setPower(left);
            robot.right.setPower(right);
            telemetry.addData("Left Power", left);
            telemetry.addData("Right Power", right);
        }


        /*
         * gamepad2:
         *   - [left Y] the height of the arm
         *   - [left & right bumper] sets the position of the servo being smaller than before
         *   - [left & right trigger] sets the position of the servo being larger than before
         *   - [button B] open/close the foundation grabber
         */
        double height = - Range.scale(gamepad2.left_stick_y, -1.0, 1.0, -0.5, 0.5);

        if (height < 0) {
            height = height * 0.5;
        } else {
            height = height * 2;
        }

        if (!camDoingInit) {
            robot.armLifter.setPower(height);
        }

        // If bumper is pressed depress by AMOUNT go up
        if (gamepad2.right_bumper) {
            robot.leftGrabber.setPower(1.0);
            robot.rightGrabber.setPower(1.0);
        } else if (gamepad2.right_trigger != 0) {
            robot.leftGrabber.setPower(-1.0);
            robot.rightGrabber.setPower(-1.0);
        } else {
            robot.leftGrabber.setPower(0.0);
            robot.rightGrabber.setPower(0.0);
        }

        if (gamepad2.b) {
            robot.foundationGrabberLeft.setPower(-1.0);
            robot.foundationGrabberRight.setPower(-1.0);
        } else if (gamepad2.a) {
            robot.foundationGrabberLeft.setPower(1.0);
            robot.foundationGrabberRight.setPower(1.0);
        } else {
            robot.foundationGrabberLeft.setPower(0.0);
            robot.foundationGrabberRight.setPower(0.0);
        }

        if (gamepad2.dpad_up) {
            robot.cam.setPower(1.0);
        } else if (gamepad2.dpad_down) {
            robot.cam.setPower(-1.0);
        } else {
            robot.cam.setPower(0.0);
        }



        robot.extender.setPower(gamepad2.right_stick_y);

        telemetry.addData("Arm Lifter (power)", height);
        telemetry.addData("Left Grabber (power)", robot.leftGrabber.getPower());
        telemetry.addData("Right Grabber (power)", robot.rightGrabber.getPower());
        telemetry.addData("Foundation Grabber Left (power)", robot.foundationGrabberLeft.getPower());
        telemetry.addData("Foundation Grabber Right (power)", robot.foundationGrabberRight.getPower());


//            boolean armUp = gamepad2.left_bumper;
//            boolean armDown = gamepad2.right_bumper;
//
//            // Set arm power based on trigger press
//            if (armUp) {
//                robot.armLifter.setPower(1);
//            } else if (armDown) {
//                robot.armLifter.setPower(-1);
//            } else {w2
//                robot.armLifter.setPower(0);
//            }
//            robot.armExtender.setPower();

//            telemetry.addData("Left Y", gamepad1.left_stick_y);
//            telemetry.addData("Right X", gamepad1.right_stick_x);
//            telemetry.addData("GP2: Left X", gamepad2.left_stick_x);
//            telemetry.addData("GP2: Arm Up?", gamepad1.left_bumper);
//            telemetry.addData("GP2: Arm Down?", gamepad1.right_bumper);

        // bind game pad B button to the open and close of the grabbers

        // bind the game pad B button to the operation of the grabber

        telemetry.update();

        robot.waitForTick(EACH);
    }

    @Override
    public void stop() {
        telemetry.addLine("Program terminated.");
    }
}
