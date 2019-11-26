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

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.locationDescriptor.Field;
import org.firstinspires.ftc.teamcode.stateProvider.Location;
import org.firstinspires.ftc.teamcode.stateProvider.VuforiaLocationProvider;

/**
 * Created by Galvin on 2019-10-31
 */
@Autonomous(name = "Autonomous", group = "Autonomous")
public class BotAutonomous extends OpMode {
    // get hardware bindings
    private ArmLiftHardware robot = new ArmLiftHardware();

    // initialize location provider
    private VuforiaLocationProvider vuforiaLocationProvider = new VuforiaLocationProvider();

    // initialize the stone recognizer
//    private StoneRecognizer stoneRecognizer = new StoneRecognizer();

    private Field field = new Field();

    enum MinerBotStatus {
        NOT_INITIALIZED(0),
        INITIALIZED(1),
        FINDING_SKYSTONE(2),
        FOUND_SKYSTONE_AND_APPROACHING(3),
        DELIVERING_SKYSTONE(4);

        private Integer level;

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

    private MinerBotStatus status = MinerBotStatus.NOT_INITIALIZED;

//    enum BuildingBotStatus {
//        NOT_INITIALIZED,
//        INITIALIZED,
//    }
//

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

    public void start() {
        vuforiaLocationProvider.activate();
//        stoneRecognizer.activate();
        status = MinerBotStatus.INITIALIZED;
    }

    @Override
    public void loop() {
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

        if (status.isNotAdvancedThan(MinerBotStatus.FOUND_SKYSTONE_AND_APPROACHING)) {
            status = MinerBotStatus.FINDING_SKYSTONE;

        } else if (status.is(MinerBotStatus.FOUND_SKYSTONE_AND_APPROACHING)) {
            // approachStone();
        } else if (status.is(MinerBotStatus.DELIVERING_SKYSTONE)) {
            // deliverStone();
        } else {
            telemetry.addLine(String.format("unknown status %s", status));
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
