/*
 * Copyright (c) 2019 The Newman School Robotics
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
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.ArmLiftHardware;
import org.firstinspires.ftc.teamcode.locationDescriptor.Field;
import org.firstinspires.ftc.teamcode.stateProvider.Location;
import org.firstinspires.ftc.teamcode.stateProvider.VuforiaLocationProvider;

/**
 * Created by Galvin on 2019-10-31
 */
@Autonomous(name = "Miner Bot Autonomous", group = "Autonomous")
public class MinerBotAutonomous extends OpMode {
    // get hardware bindings
    public ArmLiftHardware robot = new ArmLiftHardware();

    // initialize location provider
    public VuforiaLocationProvider vuforiaLocationProvider = new VuforiaLocationProvider();

    // initialize the stone recognizer
//    public StoneRecognizer stoneRecognizer = new StoneRecognizer();

    public Field field = new Field();

    enum MinerBotStatus {
        NOT_INITIALIZED(0),
        FINDING_SKYSTONE(1),
        FOUND_SKYSTONE_AND_APPROACHING(2),
        DELIVERING_SKYSTONE(3);

        public Integer level;

        MinerBotStatus(int level) {
            this.level = level;
        }

        public boolean isAdvancedThan(MinerBotStatus other) {
            return this.level > other.level;
        }

        public boolean isNotAdvancedThan(MinerBotStatus other) {
            return this.level < other.level;
        }

        public boolean is(MinerBotStatus other) {
            return this.level.equals(other.level);
        }
    }

    public MinerBotStatus status = MinerBotStatus.NOT_INITIALIZED;

    @Override
    public void init() {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap);

        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // initialize location provider
        vuforiaLocationProvider.init(hardwareMap, telemetry);

        // initialize the stone recognizer
//        stoneRecognizer.init(hardwareMap, telemetry);
    }

    // executes after pressing INIT
    public void start() {
        vuforiaLocationProvider.activate();
//        stoneRecognizer.activate();
        status = MinerBotStatus.FINDING_SKYSTONE;
    }


    // executes after pressing START; calls repeatedly.
    @Override
    public void loop() {
        // get location
        Location location = vuforiaLocationProvider.get();
        if (location != null) {
            // location detected
            telemetry.addData("loc status", "detected");
            telemetry.addData("loc detail", location.toString());
            telemetry.addData("loc field map", field.getCurrentFieldElement(
                    location
            ));
        } else {
            // location not detected
            telemetry.addData("loc status", "NOT detected");
        }
    }

    public void findSkyStone() {
    }

    @Override
    public void stop() {
        vuforiaLocationProvider.deactivate();
//        stoneRecognizer.deactivate();

        telemetry.addLine("Program terminated.");
        telemetry.update();
    }
}
