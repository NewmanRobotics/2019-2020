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

package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.AutonomousArmLiftHardware;

/**
 * Created by Galvin on 2020-01-10
 */
@Autonomous(name = "0 USE ME: Autonomous (building side, move foundation + park robot + extend ruler on line)", group = "Autonomous")
public class BotAutonomous extends OpMode {
    public AutonomousArmLiftHardware robot = new AutonomousArmLiftHardware();

    private static final double POWER = 0.05;
    private static final double THRESHOLD = 120.0;
    private double last = 1000;
    private boolean robotIsStopped = false;
    private ElapsedTime runtime = new ElapsedTime();
    private ModernRoboticsI2cRangeSensor distanceSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "RangeSensor");

    @Override
    public void init() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap);
        telemetry.addData("Status", "New Version");    //
        telemetry.update();

        robot.left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void start() {
        telemetry.addData("[Sub-Thread 1]", "thread started: lift arm");
        Thread thread1 = new Thread() {
            public void run() {
                robot.armLifter.setPower(0.8);
                robot.waitForTick(300);
                robot.armLifter.setPower(0.0);
                telemetry.addData("[Sub-Thread 1]", "thread finished: lift arm");
            }
        };
        thread1.start();

        telemetry.addData("[Sub-Thread 2]", "thread started: cam");
        Thread thread2 = new Thread() {
            public void run() {
                robot.cam.setPower(0.2);
                robot.waitForTick(1800);
                robot.cam.setPower(robot.CAM_ZERO);
                telemetry.addData("[Sub-Thread 2]", "thread finished: cam");
            }
        };
        thread2.start();

        telemetry.addData("[Sub-Thread]", "Launch thread for cam operation");
        Thread thread = new Thread() {
            public void run() {
                double power = 0.2;

                robot.cam.setPower(
                        power
                );
                robot.waitForTick(1800);
                robot.cam.setPower(-0.055);
                telemetry.addData("[Sub-Thread]", "Cam Operation finished.");
            }
        };
        thread.start();

//        robot.move(POWER, -55 - 5, -55 - 5, 5, telemetry);
        robot.move(- POWER);

        while ((distanceSensor.cmUltrasonic() < THRESHOLD || distanceSensor.cmUltrasonic() > 240 || Math.abs(last - distanceSensor.cmUltrasonic()) < 10) && !robotIsStopped) {
//            last = distanceSensor.cmUltrasonic();
//            if (!devList.contains(last)) devList.add(last);
            telemetry.addData("Move mode", "by Ultrasonic Sensor");
            telemetry.addData("Ultrasonic Reading", distanceSensor.cmUltrasonic());
            telemetry.addData("Threshold", THRESHOLD);
            telemetry.addData("Runtime", runtime.milliseconds());
            telemetry.update();
            robot.waitForTick(10);
        }
        robot.left.setPower(0.0);
        robot.right.setPower(0.0);

//        while (opModeIsActive()) {
//            telemetry.addData("Distance History", devList.toString());
//        }
    }

    @Override
    public void loop() {
        // we do nothing.
    }

    @Override
    public void stop() {
        robotIsStopped = true;
    }
}
