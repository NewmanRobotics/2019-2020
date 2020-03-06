package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;
import org.firstinspires.ftc.teamcode.hardware.TeleopArmLiftHardware;

@TeleOp(name = "Distance Sensor Test", group = "Development")
public class TestDistanceSensorTeleop extends OpMode {
    private ArmLiftHardware robot = new TeleopArmLiftHardware();

    private ModernRoboticsI2cRangeSensor distanceSensor;

    @Override
    public void init() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap, telemetry);
        distanceSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "RangeSensor");
    }

    @Override
    public void loop() {

        telemetry.addData("Ultrasonic Sensor status", distanceSensor.status());
        telemetry.addData("Ultrasonic Position ult-raw", distanceSensor.rawUltrasonic());
        telemetry.addData("Ultrasonic Position opt-raw", distanceSensor.rawOptical());
        telemetry.addData("Ultrasonic Position ult-cm", distanceSensor.cmUltrasonic());
        telemetry.addData("Ultrasonic Position opt-cm", distanceSensor.cmOptical());

        telemetry.update();

        robot.waitForTick(10);
    }

    @Override
    public void stop() {
        telemetry.addLine("Program terminated.");
    }
}
