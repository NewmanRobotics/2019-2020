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

package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class MinerBotHardware extends BotHardware {
    DcMotor armExtender;
    DcMotor armLifter;
    CRServo grabber;
    Servo swivel;

    public static final long TIME_NEED = 250;

    static final double ORIGIN = -0.05; // based on Galvin's measurement ;)

    public void initGrabbers(HardwareMap hardwareMap){
        // ArmExtender is in port #2
        armExtender = hardwareMap.dcMotor.get("ArmExtender");

        // ArmLifter is in port #3
        armLifter = hardwareMap.dcMotor.get("ArmLifter");

        // The Talon Grabber servo is in port #4
        grabber = hardwareMap.crservo.get("TalonGrabber");

        // The Talon Swivel servo is in port #5
        swivel = hardwareMap.servo.get("TalonSwivel");

        // The grabber is at port #6
        // set to 0.0 will break the motor
        grabber.setPower(ORIGIN);

//        swivel.scaleRange(0.1, 1.0);

        armLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void _setGrabberPower(double to) {
        grabber.setPower(to);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                grabber.setPower(ORIGIN);
            }
        }, TIME_NEED, TimeUnit.MILLISECONDS);
    }

    public void openBlockGrabber() {
        _setGrabberPower(1.0);
    }

    public void closeBlockGrabber() {
        _setGrabberPower(-1.0);
    }

    void operateGrabber(double power) {
        grabber.setPower(power);
    }

    void rotateGrabberByDifference(double angle) {
        // -0.284 -> center
        // double LEFT_CAP_GAMEPAD = -1;
         double LEFT_CAP_POSITION = 0;
        // angle = Math.max(angle, LEFT_CAP_GAMEPAD);
        // angle = Range.scale(angle, LEFT_CAP_GAMEPAD, 1.0, LEFT_CAP_POSITION, 1.0);
        // angle = Range.scale(angle, -1.0, 1.0, 0.0, 1.0);
        double attemptPosition = swivel.getPosition() + angle * 0.005;
        if (attemptPosition > 1) {
            swivel.setPosition(1);
        }
        else if (attemptPosition < LEFT_CAP_POSITION){
            swivel.setPosition(LEFT_CAP_POSITION);
        }
        else {
            swivel.setPosition(attemptPosition);
        }

//        swivel.setPosition(angle);
    }

    void go() {

    }
}
