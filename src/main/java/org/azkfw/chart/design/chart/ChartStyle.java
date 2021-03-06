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
package org.azkfw.chart.design.chart;

import java.awt.Color;

/**
 * このインターフェースは、チャートスタイルを定義するインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/30
 * @author Kawakicchi
 */
public interface ChartStyle {

	/**
	 * 背景色を設定する。
	 * 
	 * @param aColor 背景色
	 */
	public void setBackgroundColor(final Color aColor);

	/**
	 * 背景色を取得する。
	 * 
	 * @return 背景色
	 */
	public Color getBackgroundColor();

	/**
	 * オーバーフローを設定する。
	 * 
	 * @param aOverflow オーバーフロー
	 */
	public void setOverflow(final boolean aOverflow);

	/**
	 * オーバーフローを取得する。
	 * 
	 * @return 判断結果
	 */
	public boolean isOverflow();
}
