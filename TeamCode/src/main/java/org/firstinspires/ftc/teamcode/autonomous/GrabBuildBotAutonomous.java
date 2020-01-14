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

/**
 * Created by Galvin on 2020-01-10
 */
@Autonomous(name = "Grab Build Bot Autonomous", group = "Autonomous")
public class GrabBuildBotAutonomous extends LinearOpMode {
    // get hardware bindings
    public AutonomousHardware robot = new AutonomousHardware();

    public static final double POWER = 0.75;

    public void message () {
        telemetry.addData("Power L", robot.left.getPower());
        telemetry.addData("Pos   L", robot.left.getCurrentPosition());
        telemetry.addData("Power R", robot.right.getPower());
        telemetry.addData("Pos   R", robot.right.getCurrentPosition());

        telemetry.update();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        // pass in the hardwareMap into hardware bindings and util functions
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.right.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Run Initial",  "at %7d :%7d",
                robot.left.getCurrentPosition(),
                robot.right.getCurrentPosition());
        telemetry.update();

        waitForStart();

//        robot.move(POWER, -55 - 5, -55 - 5, 5, telemetry);
//
//        robot.foundationGrabberLeft.setPosition(1.0);
//        robot.foundationGrabberRight.setPosition(1.0);

        robot.move(- POWER, -10, -10, 5, telemetry);
    }
}
