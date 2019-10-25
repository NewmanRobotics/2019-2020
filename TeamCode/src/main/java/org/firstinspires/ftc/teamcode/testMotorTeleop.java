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

@TeleOp(name="Grabber Motor Test", group="Development")
//@Disabled
public class testMotorTeleop extends LinearOpMode {

    private tankBotHardware robot = new tankBotHardware(hardwareMap);

    // the grabber was initially opened
    private Boolean grabberOpened = true;
    private Boolean buttonPressedAtLastLoop = false;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        while (opModeIsActive()) {
            // when pressing the button and button has not been pressed at the last loop then
            // switch the grabber state
            if (gamepad1.b && !buttonPressedAtLastLoop) {
                if (grabberOpened) {
                    // close the grabber
                    robot.setGrabberPositionsIfChanged(1.0);
                    grabberOpened = false;
                } else {
                    // open the grabber
                    robot.setGrabberPositionsIfChanged(0.0);
                    grabberOpened = true;
                }
            }

            // record the button pressed state in current loop
            buttonPressedAtLastLoop = gamepad1.b;

            telemetry.addLine("Pressed 'b' on game pad to switch grabber state");

            telemetry.addData("grabberOpened", grabberOpened);
            telemetry.addData("buttonPressedAtLastLoop", buttonPressedAtLastLoop);

            telemetry.addData("Left Grabber @", String.format("#%s: Pos %s", robot.grabberLeft.getPortNumber(), robot.grabberLeft.getPosition()));
            telemetry.addData("Right Grabber @", String.format("#%s: Pos %s", robot.grabberRight.getPortNumber(), robot.grabberRight.getPosition()));

            telemetry.update();

            robot.waitForTick(10);
            idle();
        }
    }

}