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
package org.azkfw.chart.core.plot;

import org.azkfw.chart.core.dataset.MatrixDataset;
import org.azkfw.chart.design.MatrixChartDesign;
import org.azkfw.chart.design.legend.LegendStyle;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Rect;

/**
 * このクラスは、グラフプロット機能を実装するための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/09
 * @author Kawakicchi
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractMatrixPlot<DATASET extends MatrixDataset, DESIGN extends MatrixChartDesign> extends AbstractPlot<DATASET, DESIGN>
		implements Plot {

	/**
	 * コンストラクタ
	 */
	public AbstractMatrixPlot() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass クラス
	 */
	public AbstractMatrixPlot(final Class<?> aClass) {
		super(aClass);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass クラス
	 * @param aDataset データセット
	 */
	public AbstractMatrixPlot(final Class<?> aClass, final DATASET aDataset) {
		super(aClass, aDataset);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 */
	public AbstractMatrixPlot(final String aName) {
		super(aName);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 * @param aDataset データセット
	 */
	public AbstractMatrixPlot(final String aName, final DATASET aDataset) {
		super(aName, aDataset);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aDataset データセット
	 */
	public AbstractMatrixPlot(final DATASET aDataset) {
		super(aDataset);
	}

	/**
	 * 凡例のフィット処理を行なう。
	 * <p>
	 * チャートRectで適切な位置に凡例を配置するように設定する。
	 * </p>
	 * 
	 * @param g Graphics
	 * @param aStyle 凡例スタイル
	 * @param rtChart チャートRect（更新される）
	 * @return 凡例Rect。凡例を表示しない場合、<code>null</code>を返す。
	 */
	protected Rect fitLegend(final Graphics g, final LegendStyle aStyle, final Rect rtChart) {
		Rect rtLegend = null;

		return rtLegend;
	}

	/**
	 * タイトルの描画を行う。
	 * 
	 * @param g Graphics
	 * @param aStyle 凡例スタイル
	 * @param aRect 描画範囲
	 */
	protected void drawLegend(final Graphics g, final LegendStyle aStyle, final Rect aRect) {

	}

}
