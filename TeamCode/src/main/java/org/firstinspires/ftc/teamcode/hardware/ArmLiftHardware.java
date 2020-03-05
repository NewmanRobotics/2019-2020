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
import com.qualcomm.robotcore.hardware.Servo;

public abstract class ArmLiftHardware extends AbstractBotHardware {
    public DcMotor armLifter;
    public DcMotor extender;
    public Servo leftGrabber;
    public Servo rightGrabber;
    public Servo foundationGrabberLeft;
    public Servo foundationGrabberRight;
    public CRServo cam;
    public double CAM_ZERO = -0.055;

    public void initGrabbers(){
        // The lifter of the arm
        armLifter = hardwareMap.dcMotor.get("ArmLifter");
        // The cam below the armLifter
        cam = hardwareMap.crservo.get("Cam");
        // The extender
        extender = hardwareMap.dcMotor.get("Extender");

        // The grabbers of the arm
        leftGrabber = hardwareMap.servo.get("GrabberLeft");
        rightGrabber = hardwareMap.servo.get("GrabberRight");

        // The foundation grabber
        foundationGrabberLeft = hardwareMap.servo.get("FoundationGrabberLeft");
        foundationGrabberRight = hardwareMap.servo.get("FoundationGrabberRight");

        leftGrabber.setDirection(Servo.Direction.REVERSE);

        foundationGrabberLeft.setDirection(Servo.Direction.REVERSE);

        armLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        initSpecificHardwares();
    }

    public abstract void initSpecificHardwares();
}
