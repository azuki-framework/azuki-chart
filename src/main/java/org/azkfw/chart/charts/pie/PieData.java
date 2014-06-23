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

	/**
	 * タイトル
	 */
	private String title;

	/**
	 * 値
	 */
	private double value;

	/**
	 * コンストラクタ
	 */
	public PieData() {
		title = null;
		value = 0.0;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle
	 * @param aValue
	 */
	public PieData(final String aTitle, final double aValue) {
		title = aTitle;
		value = aValue;
	}

	/**
	 * タイトルを設定する。
	 * 
	 * @param aTitle タイトル
	 */
	public void setTitle(final String aTitle) {
		title = aTitle;
	}

	/**
	 * タイトルを取得する。
	 * 
	 * @return タイトル
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 値を設定する。
	 * 
	 * @param aValue 値
	 */
	public void setValue(final double aValue) {
		value = aValue;
	}

	/**
	 * 値を取得する。
	 * 
	 * @return 値
	 */
	public double getValue() {
		return value;
	}
}
