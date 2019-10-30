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

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TalonGrabberHardware extends BotHardware {
    private CRServo grabber;
    private Servo swivel;

    private static final long TIME_NEED = 250;

    static final double ORIGIN = -0.05; // based on Galvin's measurement ;)

    @Override
    void initGrabbers(HardwareMap hardwareMap){

        // The talon grabber servo is at port #4
        grabber = hardwareMap.crservo.get("TalonGrabber");

        // The talon swivel servo is at port #5
        swivel = hardwareMap.servo.get("Swivel");

        // set to 0.0 will break the motor
        grabber.setPower(ORIGIN);
    }

    private void _setGrabberPower(double to) {
        grabber.setPower(to);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                grabber.setPower(ORIGIN);
            }
        }, TIME_NEED, TimeUnit.MILLISECONDS);
    }

    public void openGrabber() {
        _setGrabberPower(1.0);
    }

    public void closeGrabber() {
        _setGrabberPower(-1.0);
    }

    public void operateGrabber(double power) {
        grabber.setPower(power);
    }

    public void rotateGrabberRaw(double angle) {
        angle = Range.scale(angle, -1.0, 1.0, 0.0, 1.0);
        swivel.setPosition(angle);
    }

}
