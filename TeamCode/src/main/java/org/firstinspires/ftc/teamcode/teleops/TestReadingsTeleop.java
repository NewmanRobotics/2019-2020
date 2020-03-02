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

package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.hardware.AutonomousArmLiftHardware;


/**
 * Created by Galvin on 2020-01-11
 */
@TeleOp(name = "(development) Get Readings", group = "Development")
public class TestReadingsTeleop extends OpMode {
    public AutonomousArmLiftHardware robot = new AutonomousArmLiftHardware();
//    public ModernRoboticsI2cRangeSensor distanceSensor;
    @Override
    public void init() {
        robot.init(hardwareMap);
//        distanceSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "RangeSensor");

        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    @Override
    public void loop() {
        telemetry.addData("Pos   L", robot.left.getCurrentPosition());
        telemetry.addData("Pos   R", robot.right.getCurrentPosition());
//        telemetry.addData("Ultrasonic Reading", distanceSensor.cmUltrasonic());

        telemetry.addData("Gyro Calibrating", robot.gyro.isCalibrating());
        telemetry.addData("Gyro Heading", robot.gyro.getHeading());
        telemetry.addData("Gyro Heading Mode", robot.gyro.getHeadingMode());
        telemetry.addData("Gyro Angular Velocity", robot.gyro.getAngularVelocity(AngleUnit.DEGREES));
        telemetry.addData("Gyro Raw Readings", String.format("X %d : Y %d : Z %d", robot.gyro.rawX(), robot.gyro.rawY(), robot.gyro.rawZ()));
        telemetry.update();
    }
}
