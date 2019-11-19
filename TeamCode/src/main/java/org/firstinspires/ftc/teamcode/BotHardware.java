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
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class BotHardware {
    DcMotor right;
    DcMotor left;

    private ElapsedTime period = new ElapsedTime();

    /**
     * construct a new bot hardware
     * @param hardwareMap received from OpMode
     */
    void init(HardwareMap hardwareMap) {
        left = hardwareMap.dcMotor.get("Left");
        right = hardwareMap.dcMotor.get("Right");

        left.setPower(0);
        right.setPower(0);

        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        initGrabbers(hardwareMap);

        // One side of the motors has been placed reversely, means we need to reverse it again ;P
        left.setDirection(DcMotor.Direction.REVERSE);
    }

    /**
     * initialize the grabber servo objects
     * @param hardwareMap received from OpMode
     */
    abstract void initGrabbers(HardwareMap hardwareMap);

    /**
     * open the grabber - make it ready for grabbing the block
     */
    public abstract void openGrabber();

    /**
     * close the grabber - make it grab the block
     */
    public abstract void closeGrabber();

    /**
     * let the robot wait for a fixed duration; in majority of times we will use
     * this as a debounce function
     * @param periodInMillisecond the duration of the robot should wait; in milliseconds
     * @throws InterruptedException times up!
     */
    void waitForTick(long periodInMillisecond) throws InterruptedException {

        long remaining = periodInMillisecond - (long) period.milliseconds();

        if (remaining > 0){
            Thread.sleep(remaining);

            period.reset();
        }

    }
}
