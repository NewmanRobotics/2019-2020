package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Arm Lift Test", group="Development")
public class ArmLiftTeleop extends LinearOpMode {
    private ArmLiftHardware robot = new ArmLiftHardware();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        long totalTime = 0;
        long EACH = 20;
        double rotateScale = 0.30;

        while (opModeIsActive()) {
            // bind the game pad Y inputs to the two tracks
            double left = gamepad1.left_stick_y;
            double right = gamepad1.right_stick_y;
            boolean armUp = gamepad2.left_bumper;
            boolean armDown = gamepad2.right_bumper;
            robot.left.setPower(-left);
            robot.right.setPower(-right);
            // Set arm power based on trigger press
            if(armUp) {
                robot.arm.setPower(1);
            }
            else if(armDown){
                robot.arm.setPower(-1);
            }
            else{
                robot.arm.setPower(0);
            }
            telemetry.addData("Left Power", left);
            telemetry.addData("Right Power", right);
            telemetry.addData("Left Y", gamepad1.left_stick_y);
            telemetry.addData("Right X", gamepad1.right_stick_x);
            telemetry.addData("GP2: Left X", gamepad2.left_stick_x);
            telemetry.addData("GP2: Arm Up?", gamepad1.left_bumper);
            telemetry.addData("GP2: Arm Down?", gamepad1.right_bumper);

            // bind game pad B button to the open and close of the grabbers
            telemetry.addData("gamepad pressed?", gamepad2.b);
            // bind the game pad B button to the operation of the grabber
            robot.operateGrabber(gamepad2.b ? 1.0 : -0.05);
            robot.rotateGrabberRaw(gamepad2.left_stick_x*rotateScale);
            telemetry.update();

            robot.waitForTick(EACH);
            idle();
        }
    }
}
