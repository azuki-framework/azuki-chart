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
package org.azkfw.chart.style;

import org.azkfw.chart.looks.legend.LegendStyle;
import org.azkfw.chart.looks.title.TitleStyle;

/**
 * このクラスは、グラフデザイン機能を実装するための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public abstract class AbstractChartStyle implements ChartStyle {

	private TitleStyle titleDesign;

	private LegendStyle legendDesign;

	public AbstractChartStyle() {
		titleDesign = null;
		legendDesign = null;
	}

	@Override
	public final void setTitleDesign(final TitleStyle aDesign) {
		titleDesign = aDesign;
	}

	@Override
	public final TitleStyle getTitleDesign() {
		return titleDesign;
	}

	@Override
	public final void setLegendDesign(final LegendStyle aDesign) {
		legendDesign = aDesign;
	}

	@Override
	public final LegendStyle getLegendDesign() {
		return legendDesign;
	}
}
