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
package org.azkfw.chart.charts.polararea;

/**
 * このクラスは、鶏頭図のポイントクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PolarAreaSeriesPoint {

	/**
	 * 値
	 */
	private double range;

	/**
	 * コンストラクタ
	 */
	public PolarAreaSeriesPoint() {
		range = 1.0;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aRange 値
	 */
	public PolarAreaSeriesPoint(final double aRange) {
		range = aRange;
	}

	/**
	 * 値を取得する。
	 * 
	 * @return 値
	 */
	public double getRange() {
		return range;
	}

}
