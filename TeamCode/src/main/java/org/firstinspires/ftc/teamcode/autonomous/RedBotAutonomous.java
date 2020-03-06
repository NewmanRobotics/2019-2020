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
@Autonomous(name = "RedBotAutonomous", group = "Autonomous")
public class RedBotAutonomous extends LinearOpMode {
    // get hardware bindings
    public AutonomousArmLiftHardware robot = new AutonomousArmLiftHardware();

    public static final double POWER = 0.1;
    public static final double THRESHOLD = 9;

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
        robot.init(hardwareMap, telemetry);
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
        telemetry.update();

        waitForStart();

        robot.move(POWER);
        while (distanceSensor.cmUltrasonic() > (THRESHOLD) && opModeIsActive()) {
            telemetry.addData("Move mode", "by Ultrasonic Sensor");
            telemetry.addData("Ultrasonic Reading", distanceSensor.cmUltrasonic());
            telemetry.addData("Threshold", THRESHOLD);
            telemetry.addData("Runtime", runtime.milliseconds());
            telemetry.update();
        }
        telemetry.addData("Move mode", "Stopping and grabbing Platform");
        telemetry.addData("Runtime", runtime.milliseconds());
        telemetry.update();
        robot.stop();
        robot.foundationGrabberLeft.setPosition(0.0);
        robot.foundationGrabberRight.setPosition(0.0);
        robot.waitForTick(2500);

        telemetry.addData("Move mode", "Spinning 180");
        telemetry.addData("Runtime", runtime.milliseconds());
        telemetry.update();

        robot.rotate(-180, 0.05, telemetry);

        robot.waitForTick(2000);
        telemetry.addData("Move mode", "Moving to Wall");
        telemetry.addData("Runtime", runtime.milliseconds());
        telemetry.update();

        robot.move(POWER);
        robot.waitForTick(5000);
        robot.stop();
        telemetry.addData("Move mode", "Parking on Line");
        telemetry.addData("Runtime", runtime.milliseconds());
        telemetry.update();
        robot.foundationGrabberLeft.setPosition(1.0);
        robot.foundationGrabberRight.setPosition(1.0);
        robot.waitForTick(4000);
        robot.move(-POWER);
        robot.waitForTick(500);
        robot.rotate(90, 0.05, telemetry);

        robot.extender.setPower(1.0);
        robot.waitForTick(2500);
        robot.extender.setPower(0.0);
        telemetry.addData("Stopping", "Done");
        telemetry.update();


//        robot.foundationGrabberLeft.setPosition(1.0);
//        robot.foundationGrabberRight.setPosition(1.0);
//        robot.waitForTick(500);
//        robot.left.setPower(0);
//        robot.right.setPower(0.1);
//        robot.waitForTick(500);
//        robot.left.setPower(0);
//        robot.right.setPower(0);
//        robot.extender.setPower(1);
//        robot.waitForTick(2500);
//        robot.extender.setPower(0);

//        while (opModeIsActive()) {
//            telemetry.addData("Distance History", devList.toString());
//        }

    }
}
