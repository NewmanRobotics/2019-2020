package org.firstinspires.ftc.teamcode;

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

    private tankBotHardware robot = new tankBotHardware(hardwareMap);

    // the grabber was initially opened
    private Boolean grabberOpened = true;
    private Boolean buttonPressedAtLastLoop = false;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        while (opModeIsActive()) {
            // bind the game pad Y inputs to the two tracks
            double left = gamepad1.left_stick_y;
            double right = gamepad1.right_stick_y;
            robot.left.setPower(left);
            robot.right.setPower(right);
            telemetry.addData("Left Power", left);
            telemetry.addData("Right Power", right);

            // bind game pad B button to the open and close of the grabbers
            telemetry.addData("gamepad pressed", gamepad1.b);
            // when pressing the button and button has not been pressed at the last loop then
            // switch the grabber state
            if (gamepad1.b && !buttonPressedAtLastLoop) {
                if (grabberOpened) {
                    // close the grabber
                    robot.closeGrabber();
                    grabberOpened = false;
                } else {
                    // open the grabber
                    robot.openGrabber();
                    grabberOpened = true;
                }
            }
            telemetry.addData("grabberOpened", grabberOpened);
            telemetry.addData("buttonPressedAtLastLoop", buttonPressedAtLastLoop);

            // record the button pressed state in current loop
            buttonPressedAtLastLoop = gamepad1.b;

            telemetry.addData("Left X", gamepad1.left_stick_x);
            telemetry.addData("Left Y", gamepad1.left_stick_y);
            telemetry.addData("Right X", gamepad1.right_stick_x);
            telemetry.addData("Right Y", gamepad1.right_stick_y);

            telemetry.addData("Left Grabber @", String.format("#%s: Pos %s", robot.grabberLeft.getPortNumber(), robot.grabberLeft.getPosition()));
            telemetry.addData("Right Grabber @", String.format("#%s: Pos %s", robot.grabberRight.getPortNumber(), robot.grabberRight.getPosition()));

            telemetry.update();

            robot.waitForTick(20);
            idle();
        }
    }
}