package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class ArmLiftHardware extends tankBotHardware {
    DcMotor arm;
    private CRServo grabber;
    private Servo swivel;


    private static final long TIME_NEED = 250;

    static final double ORIGIN = -0.05; // based on Galvin's measurement ;)

    void initGrabbers(HardwareMap hardwareMap){
        // Arm motor is in port #3
        arm = hardwareMap.dcMotor.get("Arm");
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
        if (swivel.getPosition()+angle > 1){
           swivel.setPosition(1);
        }
        else if (swivel.getPosition()+angle < 0){
            swivel.setPosition(0);
        }
        else{
            swivel.setPosition(swivel.getPosition()+angle);
        }

    }

}
