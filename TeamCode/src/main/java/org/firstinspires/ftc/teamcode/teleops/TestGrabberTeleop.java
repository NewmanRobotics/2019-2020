package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;
import org.firstinspires.ftc.teamcode.hardware.TeleopArmLiftHardware;

@TeleOp(name = "Block Grabber Test", group = "Development")
public class TestGrabberTeleop extends OpMode {
    private ArmLiftHardware robot = new TeleopArmLiftHardware();

    @Override
    public void init() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        double left = gamepad1.left_stick_y;
        double right = gamepad1.right_stick_y;

//        left = Range.scale(left, -1.0, 1.0, 0.0, 1.0);
//        right = Range.scale(right, -1.0, 1.0, 0.0, 1.0);

        robot.leftGrabber.setPosition(left);
        robot.rightGrabber.setPosition(right);

        telemetry.addData("Left FG Position", robot.leftGrabber.getPosition());
        telemetry.addData("Right FG Position", robot.rightGrabber.getPosition());

        telemetry.update();

        robot.waitForTick(10);
    }

    @Override
    public void stop() {
        telemetry.addLine("Program terminated.");
    }
}
