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
package org.azkfw.chart.core.dataset;

import org.azkfw.chart.core.dataset.matrix.MatrixData;

/**
 * このインターフェースは、マトリクスデータセット機能を表現したインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/09
 * @author Kawakicchi
 */
public interface MatrixDataset<DATA extends MatrixData> extends Dataset {

	/**
	 * 行数を取得する。
	 * 
	 * @return 行数
	 */
	public int getRowSize();

	/**
	 * 列数を取得する。
	 * 
	 * @return 列数
	 */
	public int getColSize();

	/**
	 * データを設定する。
	 * 
	 * @param aRow 行
	 * @param aCol 列
	 * @param aData データ
	 */
	public void put(final int aRow, final int aCol, final DATA aData);

	/**
	 * データを取得する。
	 * 
	 * @param aRow　行
	 * @param aCol 列
	 * @return データ
	 */
	public DATA get(final int aRow, final int aCol);

}
