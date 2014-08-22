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
package org.azkfw.chart.dataset;

import java.util.ArrayList;
import java.util.List;

import org.azkfw.chart.matrix.MatrixData;

/**
 * このクラスは、マトリクスデータセット機能の実装を行うための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/09
 * @author Kawakicchi
 */
public abstract class AbstractMatrixDataset<DATA extends MatrixData> extends AbstractDataset implements MatrixDataset<DATA> {

	private List<List<DATA>> datas;

	private int maxCol;
	private int maxRow;

	/**
	 * コンストラクタ
	 */
	public AbstractMatrixDataset() {
		super();
		datas = new ArrayList<List<DATA>>();
		maxCol = 0;
		maxRow = 0;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 */
	public AbstractMatrixDataset(final String aTitle) {
		super(aTitle);
		datas = new ArrayList<List<DATA>>();
		maxCol = 0;
		maxRow = 0;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aRow 初期確保行数
	 * @param aCol 初期確保列数
	 */
	public AbstractMatrixDataset(final int aRow, final int aCol) {
		super();
		datas = new ArrayList<List<DATA>>();
		addsize(aRow - 1, aCol - 1);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aRow 初期確保行数
	 * @param aCol 初期確保列数
	 */
	public AbstractMatrixDataset(final String aTitle, final int aRow, final int aCol) {
		super(aTitle);
		datas = new ArrayList<List<DATA>>();
		addsize(aRow - 1, aCol - 1);
	}

	@Override
	public final int getRowSize() {
		return maxRow;
	}

	@Override
	public final int getColSize() {
		return maxCol;
	}

	@Override
	public final void put(final int aRow, final int aCol, final DATA aData) {
		if (aRow >= maxRow || aCol >= maxCol) {
			addsize(aRow, aCol);
		}
		datas.get(aRow).set(aCol, aData);
	}

	@Override
	public final DATA get(final int aRow, final int aCol) {
		return datas.get(aRow).get(aCol);
	}

	private void addsize(final int aRow, final int aCol) {
		if (aCol >= maxCol) {
			for (int row = 0; row < datas.size(); row++) {
				List<DATA> lst = datas.get(row);
				for (int i = 0; i < aCol - maxCol + 1; i++) {
					lst.add(null);
				}
			}
			maxCol = aCol + 1;
		}
		if (aRow >= maxRow) {
			for (int i = 0; i < aRow - maxRow + 1; i++) {
				List<DATA> lst = new ArrayList<>();
				for (int col = 0; col < maxCol; col++) {
					lst.add(null);
				}
				datas.add(lst);
			}
			maxRow = aRow + 1;
		}
	}
}
