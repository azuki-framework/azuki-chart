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
package org.azkfw.chart.charts.bar;

import java.util.ArrayList;
import java.util.List;

import org.azkfw.chart.dataset.AbstractDataset;

/**
 * このクラスは、棒グラフのデータセット情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/25
 * @author Kawakicchi
 */
public class BarDataset extends AbstractDataset {

	/** シリーズリスト */
	private List<BarSeries> seriesList;

	/**
	 * コンストラクタ
	 */
	public BarDataset() {
		super();
		seriesList = new ArrayList<BarSeries>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 */
	public BarDataset(final String aTitle) {
		super(aTitle);
		seriesList = new ArrayList<BarSeries>();
	}

	/**
	 * シリーズを追加する。
	 * 
	 * @param aSeries シリーズ
	 */
	public void addSeries(final BarSeries aSeries) {
		seriesList.add(aSeries);
	}

	/**
	 * シリーズリストを取得する。
	 * 
	 * @return シリーズリスト
	 */
	public List<BarSeries> getSeriesList() {
		return seriesList;
	}
}
