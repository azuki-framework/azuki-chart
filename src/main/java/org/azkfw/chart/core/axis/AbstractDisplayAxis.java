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
package org.azkfw.chart.core.axis;

import org.azkfw.chart.displayformat.DisplayFormat;

/**
 * このクラスは、グラフ軸情報を実装するための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/03
 * @author Kawakicchi
 */
public abstract class AbstractDisplayAxis extends AbstractAxis {

	/** 表示形式 */
	private DisplayFormat displayFormat;

	/** ラベル */
	private String labelTitle;

	/**
	 * コンストラクタ
	 */
	public AbstractDisplayAxis() {
		labelTitle = null;
		displayFormat = null;
	}

	/**
	 * ラベルを設定する。
	 * 
	 * @param aLabel ラベル
	 */
	public final void setLabelTitle(final String aTitle) {
		labelTitle = aTitle;
	}

	/**
	 * ラベルを取得する。
	 * 
	 * @return ラベル
	 */
	public final String getLabelTitle() {
		return labelTitle;
	}

	/**
	 * 表示形式を設定する。
	 * 
	 * @param aDisplayFormat 表示形式
	 */
	public final void setDisplayFormat(final DisplayFormat aDisplayFormat) {
		displayFormat = aDisplayFormat;
	}

	/**
	 * 表示形式を取得する。
	 * 
	 * @return 表示形式
	 */
	public final DisplayFormat getDisplayFormat() {
		return displayFormat;
	}
}
