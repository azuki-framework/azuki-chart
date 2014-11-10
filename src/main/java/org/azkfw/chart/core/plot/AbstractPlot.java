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

import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Rect;
import org.azkfw.lang.LoggingObject;

/**
 * このクラスは、プロット機能を実装するための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/07
 * @author Kawakicchi
 */
public abstract class AbstractPlot extends LoggingObject implements Plot {

	public static final float PIXEL_MAXIMUM = 100000;
	public static final float PIXEL_MINIMUM = -100000;

	/**
	 * コンストラクタ
	 */
	public AbstractPlot() {
		super(Plot.class);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass クラス
	 */
	public AbstractPlot(final Class<?> aClass) {
		super(aClass);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 */
	public AbstractPlot(final String aName) {
		super(aName);
	}

	@Override
	public final boolean draw(final Graphics g, final int x, final int y, final int width, final int height) {
		return draw(g, new Rect(x, y, width, height));
	}

	@Override
	public final boolean draw(final Graphics g, final float x, final float y, final float width, final float height) {
		return draw(g, new Rect(x, y, width, height));
	}

	@Override
	public final boolean draw(final Graphics g, final Rect aRect) {
		return doDraw(g, aRect);
	}

	/**
	 * デバッグモードか判断する。
	 * 
	 * @return 判断結果
	 */
	protected final boolean isDebugMode() {
		return false;
	}

	/**
	 * 描画を行う。
	 * 
	 * @param g Graphics
	 * @param aRect 描画範囲
	 * @return 結果
	 */
	protected abstract boolean doDraw(final Graphics g, final Rect aRect);

	/**
	 * ピクセル値の上限下限を調整する。
	 * <p>
	 * {@link AbstractPlot#PIXEL_MINIMUM} 未満の場合は、
	 * {@link AbstractPlot#PIXEL_MINIMUM}にする。 <br/>
	 * {@link AbstractPlot#PIXEL_MAXIMUM} 未満の場合は、
	 * {@link AbstractPlot#PIXEL_MAXIMUM}にする。
	 * </p>
	 * 
	 * @param aValue ピクセル値
	 * @return ピクセル値（調整後）
	 */
	protected static float pixelLimit(final float aValue) {
		if (aValue > PIXEL_MAXIMUM) {
			return PIXEL_MAXIMUM;
		} else if (aValue < PIXEL_MINIMUM) {
			return PIXEL_MINIMUM;
		}
		return aValue;
	}
}
