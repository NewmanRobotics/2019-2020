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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;

@TeleOp(name = "Use Me= Arm Lift Bot", group = "Production")
@Disabled
public class ArmLiftTeleop extends OpMode {
    public ArmLiftHardware robot = new ArmLiftHardware();
    public long EACH = 20;
    public double DRIVE_FACTOR = 0.7;
    public double ARM_FACTOR = 0.2;
    public double rotateScale = 0.30;

    @Override
    public void init() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap);
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

        robot.left.setPower(left);
        robot.right.setPower(right);

        telemetry.addData("Left Power", left);
        telemetry.addData("Right Power", right);

        /*
         * gamepad2:
         *   - [left Y] the height of the arm
         *   - [left & right bumper] sets the position of the servo being smaller than before
         *   - [left & right trigger] sets the position of the servo being larger than before
         *   - [button B] open/close the foundation grabber
         */
        double height = - Range.scale(gamepad2.left_stick_y, -1.0, 1.0, -0.3, 0.3);

        if (height > 0) {
            height = height * 2;
        }

        robot.armLifter.setPower(height);

        // servo rotation amount per tick
        double AMOUNT = 0.005;

        if (gamepad2.left_bumper) {
            robot.operateGrabber(ArmLiftHardware.GrabberSide.LEFT, -AMOUNT);
        }
        if (gamepad2.right_bumper) {
            robot.operateGrabber(ArmLiftHardware.GrabberSide.RIGHT, -AMOUNT);
        }
        robot.operateGrabber(ArmLiftHardware.GrabberSide.LEFT, gamepad2.left_trigger);
        robot.operateGrabber(ArmLiftHardware.GrabberSide.RIGHT, gamepad2.right_trigger);

        robot.foundationGrabberLeft.setPosition(gamepad2.b ? - 1.0 : 1.0);
        robot.foundationGrabberRight.setPosition(gamepad2.b ? 1.0 : -1.0);

        telemetry.addData("Arm Lifter (power delta)", height);
        telemetry.addData("Left Grabber (exact pos)", robot.leftGrabber.getPosition());
        telemetry.addData("Right Grabber (exact pos)", robot.rightGrabber.getPosition());
        telemetry.addData("Foundation Grabber Left (exact pos)", robot.foundationGrabberLeft.getPosition());
        telemetry.addData("Foundation Grabber Right (exact pos)", robot.foundationGrabberRight.getPosition());


//            boolean armUp = gamepad2.left_bumper;
//            boolean armDown = gamepad2.right_bumper;
//
//            // Set arm power based on trigger press
//            if (armUp) {
//                robot.armLifter.setPower(1);
//            } else if (armDown) {
//                robot.armLifter.setPower(-1);
//            } else {
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
