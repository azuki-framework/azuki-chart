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
 * このクラスは、数値型の表示形式を実装するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/20
 * @author kawakicchi
 */
public class NumericDisplayFormat implements DisplayFormat {

	/** 小数点スケール */
	private int decimalScale;

	/**
	 * コンストラクタ
	 */
	public NumericDisplayFormat() {
		decimalScale = 0;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aScale 小数点桁数
	 */
	public NumericDisplayFormat(final int aDecimalScale) {
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
		String format = "%." + decimalScale + "f";
		return String.format(format, aValue);
	}
}
