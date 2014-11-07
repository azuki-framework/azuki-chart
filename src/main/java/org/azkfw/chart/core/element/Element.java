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
package org.azkfw.chart.core.element;

import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Rect;

/**
 * このインターフェースは、エレメント機能を表現したインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/07
 * @author Kawakicchi
 */
public interface Element {

	/**
	 * エレメントのフィット処理を行なう。
	 * <p>
	 * 描画Rectで適切な位置にタイトルを配置するように設定する。
	 * </p>
	 * 
	 * @param g Graphics
	 * @param rect 描画Rect（更新される）
	 * @return エレメントRect。エレメントを表示しない場合は、<code>null</code>を返す。
	 */
	public Rect deploy(final Graphics g, final Rect rect);

	/**
	 * エレメントの描画を行う。
	 * 
	 * @param g Graphics
	 * @param rect エレメント描画範囲
	 */
	public void draw(final Graphics g, final Rect rect);
}
