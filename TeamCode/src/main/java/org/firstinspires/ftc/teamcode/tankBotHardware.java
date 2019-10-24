package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Graham Cooke on 9/24/2018.
 * Modified by Alex G. for the 2019-2020 season
 */

public class tankBotHardware {

    public DcMotor right;
    public DcMotor left;
    public Servo grabberLeft;
    public Servo grabberRight;

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public tankBotHardware(){
    }

    /**
     * Initialize the hardware map
     * @param ahwMap
     */

    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;

        right = hwMap.dcMotor.get("Right");
        left = hwMap.dcMotor.get("Left");

        // The left servo is at port #4
        grabberLeft = hwMap.servo.get("GrabberLeft");

        // The left servo is at port #5
        grabberRight = hwMap.servo.get("GrabberRight");

        // One side of the motors has been placed reversely, means we need to reverse it again ;P
        left.setDirection(DcMotorSimple.Direction.REVERSE);
//        grabberRight.setDirection(Servo.Direction.REVERSE);

        left.setPower(0);
        right.setPower(0);

        grabberLeft.setPosition(1);
        grabberRight.setPosition(1);
    }

    public void waitForTick(long periodMs) throws InterruptedException {

        long remaining = periodMs - (long) period.milliseconds();

        if (remaining > 0){
            Thread.sleep(remaining);

            period.reset();
        }

    }

}
