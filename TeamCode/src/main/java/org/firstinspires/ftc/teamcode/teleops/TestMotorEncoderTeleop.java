package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;
import org.firstinspires.ftc.teamcode.hardware.TeleopArmLiftHardware;

@TeleOp(name = "Motor Encoder Test", group = "Development")
public class TestMotorEncoderTeleop extends OpMode {
    private ArmLiftHardware robot = new TeleopArmLiftHardware();
    private long EACH = 20;
    private double DRIVE_FACTOR = 0.3;
    private double ARM_FACTOR = 0.2;
    private double rotateScale = 0.30;
    boolean buttonPressedAtLastLoop = false;
    boolean toggleSpeed = false;

    @Override
    public void init() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap, telemetry);

        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.armLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.armLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        double left = gamepad1.left_stick_y * DRIVE_FACTOR;
        double right = gamepad1.right_stick_y * DRIVE_FACTOR;
        robot.left.setPower(left);
        robot.right.setPower(right);
        telemetry.addData("Left Power", left);
        telemetry.addData("Left Position", robot.left.getCurrentPosition());
        telemetry.addData("Right Power", right);
        telemetry.addData("Right Position", robot.right.getCurrentPosition());
        telemetry.addData("ArmLift Position", robot.armLifter.getCurrentPosition());


//            boolean armUp = gamepad2.left_bumper;
//            boolean armDown = gamepad2.right_bumper;
//
//            // Set arm power based on trigger press
//            if (armUp) {
//                robot.armLifter.setPower(1);
//            } else if (armDown) {
//                robot.armLifter.setPower(-1);
//            } else {w2
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

        robot.waitForTick(EACH);
    }

    @Override
    public void stop() {
        telemetry.addLine("Program terminated.");
    }
}
