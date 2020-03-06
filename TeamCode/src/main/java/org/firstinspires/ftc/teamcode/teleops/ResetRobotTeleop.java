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
import org.firstinspires.ftc.teamcode.hardware.TeleopArmLiftHardware;

@TeleOp(name = "(utility) Reset Robot", group = "Utility")
public class ResetRobotTeleop extends OpMode {
    private ArmLiftHardware robot = new TeleopArmLiftHardware();
    private long EACH = 20;
    private double DRIVE_FACTOR = 0.45;
    private boolean buttonPressedAtLastLoop = false;
    private boolean toggleSpeed = false;

    @Override
    public void init() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap, telemetry);
    }

    @Override
    public void start() {
        telemetry.addData("[Cam]", "resetting");
        new Thread() {
            public void run() {
                robot.cam.setPower(-0.2);
                robot.waitForTick(1400);
                robot.cam.setPower(robot.CAM_ZERO);
                telemetry.addData("[Cam]", "reset finished");
            }
        }.start();

        telemetry.addData("[Lift Arm]", "resetting");
        new Thread() {
            public void run() {
                robot.waitForTick(300);
                robot.armLifter.setPower(-0.8);
                robot.waitForTick(300);
                robot.armLifter.setPower(0.0);
                telemetry.addData("[Lift Arm]", "reset finished");
            }
        }.start();

        telemetry.addData("[Extender]", "resetting");
        new Thread() {
            public void run() {
                robot.extender.setPower(1.0);
                robot.waitForTick(6000);
                robot.extender.setPower(0.0);
                telemetry.addData("[Extender]", "reset finished");
            }
        }.start();

        telemetry.addData("[Grabbers]", "resetting");
        new Thread() {
            public void run() {
                robot.leftGrabber.setPosition(1.0);
                robot.rightGrabber.setPosition(1.0);
                robot.waitForTick(2000);
                robot.leftGrabber.setPosition(0.0);
                robot.rightGrabber.setPosition(0.0);
                telemetry.addData("[Grabbers]", "reset finished");
            }
        }.start();

        telemetry.addData("[FoundationGrabbers]", "resetting");
        new Thread() {
            public void run() {
                robot.foundationGrabberLeft.setPosition(1.0);
                robot.foundationGrabberRight.setPosition(1.0);
                robot.waitForTick(2000);
                robot.foundationGrabberLeft.setPosition(0.0);
                robot.foundationGrabberRight.setPosition(0.0);
                telemetry.addData("[FoundationGrabbers]", "reset finished");
            }
        }.start();

        telemetry.update();
    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {
        telemetry.addLine("Program terminated.");
    }
}
