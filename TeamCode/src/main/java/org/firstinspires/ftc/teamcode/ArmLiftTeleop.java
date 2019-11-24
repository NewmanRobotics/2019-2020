package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Arm Lift Bot", group = "Production")
public class ArmLiftTeleop extends OpMode {
    private ArmLiftHardware robot = new ArmLiftHardware();
    private long EACH = 20;
    private double DRIVE_FACTOR = 0.6;
    private double ARM_FACTOR = 0.2;
    private double rotateScale = 0.30;

    @Override
    public void init() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        // bind the game pad Y inputs to the two tracks
        /*
         * gamepad1 is for the player who will control the direction of the robot
         * gamepad2 is for the player who will control the grabber and the lift of the robot
         *
         * bindings:
         */

        /*
         * gamepad1:
         *   - [left & right stick Y] Robot driver with Tank driving style
         */
        double left = gamepad1.left_stick_y * DRIVE_FACTOR;
        double right = gamepad1.right_stick_y * DRIVE_FACTOR;

        robot.left.setPower(left);
        robot.right.setPower(right);

        telemetry.addData("Left Power", left);
        telemetry.addData("Right Power", right);

        /*
         * gamepad2:
         *   - [left Y] the height of the arm
         *   - [right Y] the extension of the arm
         *   - [left & right bumper] the rotation of the grabber
         *   - [button B] open/close the grabber
         */
        double height = - Range.scale(gamepad2.left_stick_y, -1.0, 1.0, -0.3, 0.3);
        double extension = gamepad2.right_stick_y;
        double rotation = gamepad2.right_stick_x;
        double grabber = gamepad2.b ? 1.0 : ArmLiftHardware.ORIGIN;

//        telemetry.addData("grabber bf op", robot.swivel.getPosition());
        robot.operateGrabber(grabber);
        robot.armLifter.setPower(height);
        robot.armExtender.setPower(extension);
        robot.rotateGrabberByDifference(rotation);
//        telemetry.addData("grabber af op", robot.swivel.getPosition());
//        robot.swivel.setPosition(rotation);

        telemetry.addData("Arm Height", height);
        telemetry.addData("Arm Extender", extension);
        telemetry.addData("Grabber Rotation", rotation);
        telemetry.addData("Grabber Operation", gamepad2.b);


//            boolean armUp = gamepad2.left_bumper;
//            boolean armDown = gamepad2.right_bumper;
//
//            // Set arm power based on trigger press
//            if (armUp) {
//                robot.armLifter.setPower(1);
//            } else if (armDown) {
//                robot.armLifter.setPower(-1);
//            } else {
//                robot.armLifter.setPower(0);
//            }
//            robot.armExtender.setPower();

//            telemetry.addData("Left Y", gamepad1.left_stick_y);
//            telemetry.addData("Right X", gamepad1.right_stick_x);
//            telemetry.addData("GP2: Left X", gamepad2.left_stick_x);
//            telemetry.addData("GP2: Arm Up?", gamepad1.left_bumper);
//            telemetry.addData("GP2: Arm Down?", gamepad1.right_bumper);

        // bind game pad B button to the open and close of the grabbers

        // bind the game pad B button to the operation of the grabber

        telemetry.update();

        try {
            robot.waitForTick(EACH);
        } catch (InterruptedException e) {
            telemetry.addLine(String.format("Interrupted Exception caused in method loop(): %s", e.toString()));
        }
    }

    @Override
    public void stop() {
        telemetry.addLine("Program terminated.");
    }
}
