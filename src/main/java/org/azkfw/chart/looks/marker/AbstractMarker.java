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

import java.awt.Color;

/**
 * このインターフェースは、グラフポイントマーカー機能を表現したインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/26
 * @author Kawakicchi
 */
public abstract class AbstractMarker implements Marker {

	/**
	 * コンストラクタ
	 */
	public AbstractMarker() {

	}

	protected final Color upColor(final Color aColor) {
		return addColor(aColor, 100);
	}

	protected final Color downColor(final Color aColor) {
		return addColor(aColor, -100);
	}

	protected final Color addColor(final Color aColor, final int add) {
		int red = aColor.getRed() + add;
		int green = aColor.getGreen() + add;
		int blue = aColor.getBlue() + add;
		if (0 > red)
			red = 0;
		if (0 > green)
			green = 0;
		if (0 > blue)
			blue = 0;
		if (255 < red)
			red = 255;
		if (255 < green)
			green = 255;
		if (255 < blue)
			blue = 255;
		return new Color(red, green, blue, aColor.getAlpha());
	}
}
