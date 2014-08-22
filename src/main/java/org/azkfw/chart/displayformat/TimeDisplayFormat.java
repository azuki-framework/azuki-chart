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
package org.azkfw.chart.displayformat;

/**
 * このクラスは、時間の表示形式を実装するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/03
 * @author kawakicchi
 */
public class TimeDisplayFormat implements DisplayFormat {

	/** 小数点スケール */
	private int decimalScale;

	/**
	 * コンストラクタ
	 */
	public TimeDisplayFormat() {
		decimalScale = 0;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aScale 小数点桁数
	 */
	public TimeDisplayFormat(final int aDecimalScale) {
		decimalScale = aDecimalScale;
	}

	/**
	 * 小数点桁数を設定する。
	 * 
	 * @param aScale 小数点桁数
	 */
	public void setDecimalScale(final int aScale) {
		decimalScale = aScale;
	}

	@Override
	public String toString(final double aValue) {
		String string = null;

		int sign = (aValue >= 0) ? 1 : -1;
		double value = Math.abs(aValue);

		if (value >= 24 * 60 * 60) {
			int v = (int) (value/(60*60)); // scale hour
			int day = (int) (v / 60);
			int hou = v % 60;
			String format = "%." + decimalScale + "f day";
			string = String.format(format, sign * ((double)day + hou / 24.f));
		} else if (value >= 60 * 60) {
			int v = (int) (value/60); // scale min
			int hou = (int) (v / 60);
			int min = v % 60;
			String format = "%." + decimalScale + "f h";
			string = String.format(format, sign * ((double)hou + min / 60.f));
		} else if (value >= 60) {
			int v = (int) (value); // scale sec
			int min = (int) (v / 60);
			int sec = v % 60;
			String format = "%." + decimalScale + "f min";
			string = String.format(format, sign * ((double)min + sec / 60.f));
		} else if (value >= 0) {
			String format = "%." + decimalScale + "f sec";
			string = String.format(format, sign * value);
		} else {
			String format = "%." + decimalScale + "f ms";
			string = String.format(format, sign * value * 1000);
		}

		return string;
	}
}
