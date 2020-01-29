package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRRangeSensor;
import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;

@TeleOp(name = "Distance Sensor Test", group = "Development")
public class TestDistanceSensorTeleop extends OpMode {
    private ArmLiftHardware robot = new ArmLiftHardware();

    private ModernRoboticsI2cRangeSensor distanceSensor;

    @Override
    public void init() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap);
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
