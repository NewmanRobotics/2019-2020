package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;

@TeleOp(name = "Foundation Grabber Test", group = "Development")
public class TestGrabberTeleop extends OpMode {
    private ArmLiftHardware robot = new ArmLiftHardware();

    @Override
    public void init() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        double left = gamepad1.left_stick_y;
        double right = gamepad1.right_stick_y;

        left = Range.scale(left, -1.0, 1.0, 0.0, 1.0);
        right = Range.scale(right, -1.0, 1.0, 0.0, 1.0);

        robot.foundationGrabberLeft.setPosition(left);
        robot.foundationGrabberRight.setPosition(right);

        telemetry.addData("Left FG Position", robot.foundationGrabberLeft.getPosition());
        telemetry.addData("Right FG Position", robot.foundationGrabberLeft.getPosition());

        telemetry.update();

        robot.waitForTick(10);
    }

    @Override
    public void stop() {
        telemetry.addLine("Program terminated.");
    }
}
