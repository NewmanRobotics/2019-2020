package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;
import org.firstinspires.ftc.teamcode.hardware.TeleopArmLiftHardware;

@TeleOp(name = "Cam Position Test", group = "Development")
public class TestCamTeleop extends LinearOpMode {
    private ArmLiftHardware robot = new TeleopArmLiftHardware();
    private double camPosition = 0;
    private int EACH = 2;
    private double OFFSET = -0.055;

    @Override
    public void runOpMode() throws InterruptedException {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap);

        waitForStart();

        double power;
//        double input = gamepad1.left_stick_y;
//        if (input < 0) {
//            power = Range.scale(input, -1, 0, -1, OFFSET);
//        } else if (input > 0) {
//            power = Range.scale(input, 0, 1, OFFSET, 1);
//        } else {
//            power = OFFSET;
//        }

        power = 0.2;

        robot.cam.setPower(
                power
        );
        robot.waitForTick(1800);
        robot.cam.setPower(OFFSET);

        telemetry.addLine("Program terminated.");
        telemetry.update();
    }
}
