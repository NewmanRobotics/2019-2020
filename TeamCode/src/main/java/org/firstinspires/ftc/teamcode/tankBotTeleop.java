package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


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

@TeleOp(name="Tank Bot", group="Staging")
//@Disabled
public class tankBotTeleop extends LinearOpMode {

    tankBotHardware robot = new tankBotHardware(hardwareMap);

    @Override
    public void runOpMode() throws InterruptedException {

        float left, right;
        boolean grabbed = true;
        boolean pressed = false;

        waitForStart();

        while (opModeIsActive()) {
            left = gamepad1.left_stick_y;
            right = gamepad1.right_stick_y;

            robot.left.setPower(left);
            robot.right.setPower(right);

            telemetry.addData("(bool) 'grabbed'", grabbed);
            telemetry.addData("gamepad pressed", gamepad1.b);
            if (gamepad1.b && !pressed) {
                pressed = true;
                if (!grabbed){
                    robot.grabberLeft.setPosition(1);
                    robot.grabberRight.setPosition(0);
                    grabbed = true;
                }
                else{
                    robot.grabberLeft.setPosition(0);
                    robot.grabberRight.setPosition(1);
                    grabbed=false;
                }
            } else if (!gamepad1.b && pressed) {
                pressed = false;
            } else if (!gamepad1.b && !pressed) {
                robot.grabberLeft.setPosition(gamepad1.left_stick_x);
                robot.grabberRight.setPosition(gamepad1.right_stick_x);
            }


            telemetry.addData("Left X", gamepad1.left_stick_x);
            telemetry.addData("Left Y", gamepad1.left_stick_y);
            telemetry.addData("Right X", gamepad1.right_stick_x);
            telemetry.addData("Right Y", gamepad1.right_stick_y);


            telemetry.addData("Left Power", left);
            telemetry.addData("Right Power", right);

            telemetry.addData("Left Grabber @", String.format("#%s: Pos %s", robot.grabberLeft.getPortNumber(), robot.grabberLeft.getPosition()));
            telemetry.addData("Right Grabber @", String.format("#%s: Pos %s", robot.grabberRight.getPortNumber(), robot.grabberRight.getPosition()));

            telemetry.update();

            robot.waitForTick(20);
            idle();
        }
    }
}