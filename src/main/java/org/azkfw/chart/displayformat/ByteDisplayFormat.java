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
 * このクラスは、バイトの表示形式を実装するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/03
 * @author kawakicchi
 */
public class ByteDisplayFormat implements DisplayFormat {

	/** 小数点スケール */
	private int decimalScale;

	/**
	 * コンストラクタ
	 */
	public ByteDisplayFormat() {
		decimalScale = 0;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aScale 小数点桁数
	 */
	public ByteDisplayFormat(final int aDecimalScale) {
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

		if (value >= Math.pow(10, 15)) {
			String format = "%." + decimalScale + "f PB";
			string = String.format(format, sign * value / (Math.pow(10, 15)));
		} else if (value >= Math.pow(10, 12)) {
			String format = "%." + decimalScale + "f TB";
			string = String.format(format, sign * value / (Math.pow(10, 12)));
		} else if (value >= Math.pow(10, 9)) {
			String format = "%." + decimalScale + "f GB";
			string = String.format(format, sign * value / (Math.pow(10, 9)));
		} else if (value >= Math.pow(10, 6)) {
			String format = "%." + decimalScale + "f MB";
			string = String.format(format, sign * value / (Math.pow(10, 6)));
		} else if (value >= Math.pow(10, 3)) {
			String format = "%." + decimalScale + "f kB";
			string = String.format(format, sign * value / (Math.pow(10, 3)));
		} else {
			String format = "%." + decimalScale + "f Byte";
			string = String.format(format, sign * value);
		}
		return string;
	}
}
