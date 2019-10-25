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
