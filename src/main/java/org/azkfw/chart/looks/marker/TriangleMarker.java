/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.azkfw.chart.looks.marker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Size;

/**
 * このクラスは、三角形のマーカー機能を実装したクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/26
 * @author Kawakicchi
 */
public class TriangleMarker extends AbstractMarker {

	/** size */
	private float size;
	/** stroke color */
	private Color strokeColor;
	/** stroke */
	private Stroke stroke;
	/** fill color */
	private Color fillColor;

	public TriangleMarker(final float aSize, final Color aColor) {
		super();
		size = aSize;
		strokeColor = upColor(aColor);
		stroke = new BasicStroke(2.f);
		fillColor = aColor;
	}

	@Override
	public Size getSize() {
		return new Size(size, size);
	}

	@Override
	public void draw(final Graphics g, final float aX, final float aY) {
		float[] xps = new float[4];
		float[] yps = new float[4];

		xps[0] = aX + (size / 2);
		yps[0] = aY;
		xps[1] = aX;
		yps[1] = aY + size;
		xps[2] = aX + size;
		yps[2] = aY + size;
		xps[3] = xps[0];
		yps[3] = yps[0];

		if (null != fillColor) {
			g.setColor(fillColor);
			g.fillPolygon(xps, yps, 4);
		}
		if (null != stroke && null != strokeColor) {
			g.setColor(strokeColor);
			g.setStroke(stroke);
			g.drawPolyline(xps, yps, 4);
		}
	}

}
