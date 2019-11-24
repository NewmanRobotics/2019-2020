package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

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

//        swivel.scaleRange(0.1, 1.0);

        armLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

    void rotateGrabberByDifference(double angle) {
        // -0.284 -> center
        // double LEFT_CAP_GAMEPAD = -1;
         double LEFT_CAP_POSITION = 0;
        // angle = Math.max(angle, LEFT_CAP_GAMEPAD);
        // angle = Range.scale(angle, LEFT_CAP_GAMEPAD, 1.0, LEFT_CAP_POSITION, 1.0);
        // angle = Range.scale(angle, -1.0, 1.0, 0.0, 1.0);
        double attemptPosition = swivel.getPosition() + angle * 0.005;
        if (attemptPosition > 1) {
            swivel.setPosition(1);
        }
        else if (attemptPosition < LEFT_CAP_POSITION){
            swivel.setPosition(LEFT_CAP_POSITION);
        }
        else {
            swivel.setPosition(attemptPosition);
        }

//        swivel.setPosition(angle);
    }
}
