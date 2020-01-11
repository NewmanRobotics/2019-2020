/*
 * Copyright (c) 2020 The Newman School Robotics
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.AutonomousHardware;
import org.firstinspires.ftc.teamcode.locationDescriptor.Field;
import org.firstinspires.ftc.teamcode.stateProvider.Location;
import org.firstinspires.ftc.teamcode.stateProvider.VuforiaLocationProvider;

/**
 * Created by Galvin on 2020-01-10
 */
@Autonomous(name = "Build Bot Autonomous", group = "Autonomous")
public class BuildBotAutonomous extends LinearOpMode {
    // get hardware bindings
    public AutonomousHardware robot = new AutonomousHardware();

    // initialize location provider
    public VuforiaLocationProvider vuforiaLocationProvider = new VuforiaLocationProvider();

    public Field field = new Field();

    public Location lastLocation;

    @Override
    public void runOpMode() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap);

        robot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // 0.3: 45 deg open
        robot.foundationGrabberLeft.setPosition(0.3);
        robot.foundationGrabberRight.setPosition(0.7);

        waitForStart();

        telemetry.addLine("loop");
        telemetry.update();

        telemetry.addLine("running targetPos 100");
        telemetry.update();

        robot.left.setTargetPosition(100);
        robot.right.setTargetPosition(100);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        telemetry.addLine("running targetPos 1000");
        telemetry.update();

        robot.left.setTargetPosition(1000);
        robot.right.setTargetPosition(1000);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        telemetry.addLine("Program terminated.");
        telemetry.update();
    }
}
