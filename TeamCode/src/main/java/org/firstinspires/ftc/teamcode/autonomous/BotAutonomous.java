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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.AutonomousArmLiftHardware;

/**
 * Created by Galvin on 2020-01-10
 */
@Autonomous(name = "0 USE ME: Autonomous (building side, move foundation + park robot + extend ruler on line)", group = "Autonomous")
public class BotAutonomous extends LinearOpMode {
    // get hardware bindings
    public AutonomousArmLiftHardware robot = new AutonomousArmLiftHardware();

    public static final double POWER = 0.05;
    public static final double THRESHOLD = 120.0;

    public void message () {
        telemetry.addData("Power L", robot.left.getPower());
        telemetry.addData("Pos   L", robot.left.getCurrentPosition());
        telemetry.addData("Power R", robot.right.getPower());
        telemetry.addData("Pos   R", robot.right.getCurrentPosition());

        telemetry.update();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime runtime = new ElapsedTime();
//        ArrayList<Double> devList = new ArrayList<Double>();
        double last = 1000;
        ModernRoboticsI2cRangeSensor distanceSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "RangeSensor");

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

        telemetry.addData("Run Initial",  "at %7d :%7d",
                robot.left.getCurrentPosition(),
                robot.right.getCurrentPosition());
        telemetry.update();

        waitForStart();

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

        while ((distanceSensor.cmUltrasonic() < THRESHOLD || distanceSensor.cmUltrasonic() > 240 || Math.abs(last - distanceSensor.cmUltrasonic()) < 10) && opModeIsActive()) {
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
}
