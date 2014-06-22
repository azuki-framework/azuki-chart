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
package org.azkfw.chart.charts.pie;

/**
 * このクラスは、円グラフのプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PieData {

	private String title;
	private double value;

	public PieData() {
		title = null;
		value = 0.0;
	}

	public PieData(final String aTitle, final double aValue) {
		title = aTitle;
		value = aValue;
	}

	public void setTitle(final String aTitle) {
		title = aTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setValue(final double aValue) {
		value = aValue;
	}

	public double getValue() {
		return value;
	}
}
