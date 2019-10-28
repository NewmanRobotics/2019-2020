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

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Graham Cooke on 9/24/2018.
 * Modified by Alex G. for the 2019-2020 season
 */

class tankBotHardware {

    DcMotor right;
    DcMotor left;
    Servo grabberLeft;
    Servo grabberRight;

    private ElapsedTime period = new ElapsedTime();
    private double servoLastSetAs = Double.MIN_VALUE;

    tankBotHardware(HardwareMap hardwareMap){
        right = hardwareMap.dcMotor.get("Right");
        left = hardwareMap.dcMotor.get("Left");

        // The left servo is at port #4
        grabberLeft = hardwareMap.servo.get("GrabberLeft");

        // The right servo is at port #5
        grabberRight = hardwareMap.servo.get("GrabberRight");

        // One side of the motors has been placed reversely, means we need to reverse it again ;P
        left.setDirection(DcMotor.Direction.REVERSE);
        grabberRight.setDirection(Servo.Direction.REVERSE);

        left.setPower(0);
        right.setPower(0);

        // scale the servo output, mapping all further setPosition values to range [0.6, 1.0]
        grabberLeft.scaleRange(0.6, 1.0);
        grabberRight.scaleRange(0.6, 1.0);

        // set the left and right grabber to their initial position - open
        _setGrabberPositions(0.0);
    }

    private void _setGrabberPositions(double value) {
        grabberLeft.setPosition(value);
        grabberRight.setPosition(value);
    }

    /**
     * set grabber positions only if {@code value} is changed
     *
     * @param value to open the grabber (\_/) set position to 0.0, to close set position to 1.0
     */
    private void _setGrabberPositionsIfChanged(double value) {
        // first condition: if is first run, set last set as and do setPosition
        // second condition: if the value differs from the last one, do setPosition
        if (servoLastSetAs == Double.MIN_VALUE || servoLastSetAs != value) {
            servoLastSetAs = value;
            _setGrabberPositions(value);
        }
    }

    /**
     * open the grabber
     */
    void openGrabber() {
        _setGrabberPositionsIfChanged(0.0);
    }

    /**
     * close the grabber
     */
    void closeGrabber() {
        _setGrabberPositionsIfChanged(1.0);
    }

    void waitForTick(long periodMs) throws InterruptedException {

        long remaining = periodMs - (long) period.milliseconds();

        if (remaining > 0){
            Thread.sleep(remaining);

            period.reset();
        }

    }

}
