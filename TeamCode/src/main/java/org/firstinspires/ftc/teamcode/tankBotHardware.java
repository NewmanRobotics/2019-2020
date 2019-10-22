package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Graham Cooke on 9/24/2018.
 * Modified by Alex G. for the 2019-2020 season
 */

public class tankBotHardware {

    public DcMotor right = null;
    public DcMotor left = null;

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public tankBotHardware(){
    }

    public void init(HardwareMap ahwMap){

        hwMap = ahwMap;

        right = hwMap.dcMotor.get("Right");
        left = hwMap.dcMotor.get("Left");

        left.setDirection(DcMotorSimple.Direction.REVERSE);

        right.setPower(0);
        left.setPower(0);

    }

    public void waitForTick(long periodMs) throws InterruptedException {

        long remaining = periodMs - (long) period.milliseconds();

        if (remaining > 0){
            Thread.sleep(remaining);

            period.reset();
        }

    }

}
