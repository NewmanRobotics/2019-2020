package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;

@TeleOp(name = "Move By Motor Encoder Test", group = "Development")
public class TestMoveByMotorEncoderTeleop extends LinearOpMode {
    private ArmLiftHardware robot = new ArmLiftHardware();
    // tick in milliseconds
    private long EACH = 20;

    // the redundancy we need for checking if the distance target has been met.
    private double DISTANCE_REDUNDANCY = 20;

    private double taskSpeed = 0.0;
    private double taskLeftDistance = 0.0;
    private double taskRightDistance = 0.0;
    private boolean taskStarted = false;

    private void message(String status) {
        telemetry.addData("Status", status);
        telemetry.addData("Task Speed", taskSpeed);
        telemetry.addData("Task Left Distance", taskLeftDistance);
        telemetry.addData("Task Right Distance", taskRightDistance);
        telemetry.addData("Task Started", taskStarted);
        telemetry.update();
    }

    private double difference(double pos1, double pos2) {
        return Math.abs(
                Math.abs(pos1) - Math.abs(pos2)
        );
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

        // notice: motors are reversed.

        while (!taskStarted) {
            taskSpeed += Math.abs(gamepad1.left_stick_y * 0.025);

            taskLeftDistance += gamepad1.left_stick_y * 10;
            taskRightDistance += gamepad1.right_stick_y * 10;

            taskStarted = gamepad1.b;

            message("Waiting for params...");

            robot.waitForTick(EACH);
        }

        // Encoder readings are complying with real life situations but the speed is reversed.
        // So for +Distance we use -Speed, vice versa.

        double leftSpeed = taskLeftDistance > 0 ? - taskSpeed : taskSpeed;
        double rightSpeed = taskRightDistance > 0 ? - taskSpeed : taskSpeed;

        robot.left.setPower(leftSpeed);
        robot.right.setPower(rightSpeed);

        while (
                taskStarted
                        && opModeIsActive()
        ) {

            double leftPosition = robot.left.getCurrentPosition();
            double rightPosition = robot.right.getCurrentPosition();

            if (
                    !(difference(leftPosition, taskLeftDistance) > DISTANCE_REDUNDANCY)
                            || !(difference(rightPosition, taskRightDistance) > DISTANCE_REDUNDANCY)
            ) {
                break;
            }

            telemetry.addData("Left Position", leftPosition);
            telemetry.addData("Right Position", rightPosition);

            message("Running Task...");

            robot.waitForTick(EACH);
        }

        robot.left.setPower(0.0);
        robot.right.setPower(0.0);
    }
}
