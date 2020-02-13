package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;

@TeleOp(name = "Cam Position Test", group = "Development")
public class TestCamTeleop extends LinearOpMode {
    private ArmLiftHardware robot = new ArmLiftHardware();
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
