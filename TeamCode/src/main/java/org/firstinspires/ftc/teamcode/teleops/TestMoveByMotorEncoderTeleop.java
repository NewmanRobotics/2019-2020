package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;

@TeleOp(name = "Move By Motor Encoder Test", group = "Development")
public class TestMoveByMotorEncoderTeleop extends LinearOpMode {
    private ArmLiftHardware robot = new ArmLiftHardware();
    private long EACH = 20;

    private double taskSpeed = 0.0;
    private double taskDistance = 0.0;
    private boolean taskStarted = false;

    private void message(String status) {
        telemetry.addData("Status", status);
        telemetry.addData("Task Speed", taskSpeed);
        telemetry.addData("Task Distance", taskDistance);
        telemetry.addData("Task Started", taskStarted);
        telemetry.update();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.armLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.armLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (!taskStarted) {
            double speed = gamepad1.left_stick_y * 0.025;
            double distance = gamepad1.right_stick_y * 10;

            taskSpeed += speed;
            taskDistance += distance;

            taskStarted = gamepad1.b;

            message("Waiting for params...");

            robot.waitForTick(EACH);
        }

        while (taskStarted) {
            robot.left.setPower(taskSpeed);
            robot.right.setPower(taskSpeed);

            telemetry.addData("Left Position", robot.left.getCurrentPosition());
            telemetry.addData("Right Position", robot.right.getCurrentPosition());

            message("Running Task...");

            robot.waitForTick(EACH);
        }
    }
}
