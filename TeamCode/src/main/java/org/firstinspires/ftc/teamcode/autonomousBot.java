package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

//This file may not be nessesary
@Disabled
@Autonomous
public class autonomousBot {
    ConceptTensorFlowObjectDetection autobot = new ConceptTensorFlowObjectDetection();

    @Override
    public void runOpMode() throws InterruptedException{
        autobot.runOpMode();
    }

}
