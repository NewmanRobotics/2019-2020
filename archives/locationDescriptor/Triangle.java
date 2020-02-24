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

/**
 * Created by Galvin on 2019-11-20
 */
public class Triangle implements Shape {
    public Coord a;
    public Coord b;
    public Coord c;
    public Triangle(Coord a, Coord b, Coord c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean in(Coord coord) {
        double s = a.y * c.x - a.x * c.y + (c.y - a.y) * coord.x + (a.x - c.x) * coord.y;
        double t = a.x * b.y - a.y * b.x + (a.y - b.y) * coord.x + (b.x - a.x) * coord.y;

        if ((s < 0) != (t < 0)) return false;

        double A = -b.y * c.x + a.y * (c.x - b.x) + a.x * (b.y - c.y) + b.x * c.y;

        return A < 0 ?
                (s <= 0 && s + t >= A) :
                (s >= 0 && s + t <= A);
    }
}
