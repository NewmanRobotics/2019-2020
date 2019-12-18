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

package org.firstinspires.ftc.teamcode.locationDescriptor;

import java.util.ArrayList;

/**
 * Created by Galvin on 2019-12-05
 */
public class Path {
    public ArrayList<Coord> waypoints;
    public int currentIndex = -1;
    enum PathRunState {
        WAITING(0),
        READY(1),
        RUNNING(2),
        PAUSED(3),
        DONE(4);

        private int level;

        PathRunState(int level) {
            this.level = level;
        }
    }
    public PathRunState state = PathRunState.WAITING;
    public Path(ArrayList<Coord> waypoints) {
        this.waypoints = waypoints;
    }
    public void add(Coord waypoint) throws IllegalArgumentException {
        if (this.state == PathRunState.WAITING) {
            this.waypoints.add(waypoint);
        } else {
            throw new IllegalArgumentException("Path has already started");
        }
    }
    public void start() {
        this.state = PathRunState.READY;
    }

}
