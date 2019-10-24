package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.HashMap;
import java.util.Map;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="testMotorTeleop", group="Teleop")
//@Disabled
public class testMotorTeleop extends LinearOpMode {

    tankBotHardware robot = new tankBotHardware();

    Map<String, Double> servoCacheMap = new HashMap<>();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        waitForStart();

        double grabberCanMoveIn = 0.4;

        while (opModeIsActive()) {

            // to avoid going backwards (which it shouldn't)
            double gleft = Math.max(0.0, gamepad1.left_stick_x); // range [0.0, 1.0]
            double gright = Math.min(0.0, gamepad1.right_stick_x); // range [-1.0, 0.0]

            // map the game pad input to the range where the grabber can move
            double grabberLeftPos = Range.scale(gleft, 0.0, 1.0, 1.0 - grabberCanMoveIn, 1.0);
            double grabberRightPos = Range.scale(gright, -1.0, 0.0, 0.0, grabberCanMoveIn);

            // debounce the setPosition so that we don't burn out our motors ;)
//            this.setGrabberPositionIfChanged(robot.grabberLeft, grabberLeftPos);
//            this.setGrabberPositionIfChanged(robot.grabberRight, grabberRightPos);

            robot.grabberLeft.setPosition(grabberLeftPos);
            robot.grabberRight.setPosition(grabberRightPos);

            telemetry.addData("Left X", gamepad1.left_stick_x);
            telemetry.addData("Left Y", gamepad1.left_stick_y);
            telemetry.addData("Right X", gamepad1.right_stick_x);
            telemetry.addData("Right Y", gamepad1.right_stick_y);

            telemetry.addData("Left Grabber @", String.format("#%s: Pos %s", robot.grabberLeft.getPortNumber(), robot.grabberLeft.getPosition()));
            telemetry.addData("Right Grabber @", String.format("#%s: Pos %s", robot.grabberRight.getPortNumber(), robot.grabberRight.getPosition()));

            telemetry.update();

            robot.waitForTick(25);
            idle();
        }
    }

    public void setGrabberPositionIfChanged(Servo servo, double value) {
        String servoName = servo.getDeviceName();
        if (servoCacheMap.containsKey(servoName)) {
            if (servoCacheMap.get(servoName) != value) {
                servoCacheMap.put(servoName, value);
                servo.setPosition(value);
            }
        } else {
            servoCacheMap.put(servoName, value);
            servo.setPosition(value);
        }
    }
}