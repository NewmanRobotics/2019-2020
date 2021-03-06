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

import org.firstinspires.ftc.teamcode.stateProvider.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Galvin on 2019-11-18
 */
public class Field {
	HashMap<String, Shape> element = new HashMap<String, Shape>();
	public Field() {
		//[x,y]
		//For squares
		//[[Most Left Coord],[Most Right Coord]]
		//For triangle
		//[[Most Left Coord],[Most Right Coord],[Edge Coord]]

		element.put("DEPOT_RED", new Rect(
				new Coord(-68.5,-45.75),
				new Coord(-45.75,-68.5)
		));
		element.put("DEPOT_BLUE", new Rect(
				new Coord(45.75, -68.5),
				new Coord(-68.5, 45.75))
		);
		element.put("BUILDING_SITE_RED", new Triangle(
				new Coord(45.75, 68.5),
				new Coord(68.5, 45.75),
				new Coord(68.5,68.5)
		));
		element.put("BUILDING_SITE_BLUE", new Triangle(
				new Coord(-45.75, 68.5),
				new Coord(-68.5, 45.75),
				new Coord(-68.5,68.5)
		));
		element.put("SIDE_RED", new Rect(
				new Coord(-68.5,-45.75),
				new Coord(-45.75,-68.5)
		));
	}

	public ArrayList<String> getCurrentFieldElement(Location location) {
		ArrayList<String> elements = new ArrayList<>();
		for (Map.Entry<String, Shape> entry : element.entrySet()) {
			if (entry.getValue().in(
					new Coord(
							location.positionX,
							location.positionY
					)
			)) {
				elements.add(entry.getKey());
			}
		}
		return elements;
	}
}
