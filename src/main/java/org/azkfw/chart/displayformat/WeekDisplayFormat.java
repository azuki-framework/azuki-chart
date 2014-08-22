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
 * このクラスは、１週の表示形式を実装するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/03
 * @author kawakicchi
 */
public class WeekDisplayFormat implements DisplayFormat {

	/**
	 * コンストラクタ
	 */
	public WeekDisplayFormat() {
	}

	@Override
	public String toString(final double aValue) {
		int week = (int) aValue;
		switch (week) {
		case 0: // Sunday
			return "Sun";
		case 1: // Monday
			return "Mon";
		case 2: // Tuesday
			return "Tue";
		case 3: // Wednesday
			return "Wed";
		case 4: // Thursday
			return "Thu";
		case 5: // Friday
			return "Fri";
		case 6: // Saturday
			return "Sat";
		case 7: // Sunday
			return "Sun";
		}
		return null;
	}
}
