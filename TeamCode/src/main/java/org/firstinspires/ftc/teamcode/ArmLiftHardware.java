package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class ArmLiftHardware extends BotHardware {
    DcMotor armExtender;
    DcMotor armLifter;
    CRServo grabber;
    Servo swivel;

    private static final long TIME_NEED = 250;

    static final double ORIGIN = -0.05; // based on Galvin's measurement ;)

    void initGrabbers(HardwareMap hardwareMap){
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

    void operateGrabber(double power) {
        grabber.setPower(power);
    }

    void rotateGrabber(double angle) {
        angle = Range.scale(angle, -1.0, 1.0, 0.0, 1.0);
        double attemptAngle = swivel.getPosition() + angle;
        if (attemptAngle >= 1){
            swivel.setPosition(1);
        }
        else if (attemptAngle <= 0){
            swivel.setPosition(0);
        }
        else {
            swivel.setPosition(attemptAngle);
        }
    }

}
