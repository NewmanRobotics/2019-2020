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

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TalonGrabberHardware extends BotHardware {
    private Servo grabber;

    @Override
    void initGrabbers(HardwareMap hardwareMap){

        // The talon grabber servo is at port #3
        grabber = hardwareMap.servo.get("TalonGrabber");

        // set the left and right grabber to their initial position - open
        _setGrabberPosition(0.0);
    }

    /**
     * set the positions of the two grabbers
     * @param value open = 0.0, close = 1.0
     */
    private void _setGrabberPosition(double value) {
        grabber.setPosition(value);
    }

    public void openGrabber() {
        _setGrabberPosition(0.0);
    }
    public void closeGrabber() {
        _setGrabberPosition(1.0);
    }

}
