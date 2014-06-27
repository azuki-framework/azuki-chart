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
package org.azkfw.chart.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.azkfw.chart.AzukiChart;

/**
 * このクラスは、グラフのユーティリティクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class AzukiChartUtility {

	/**
	 * グラフを画像ファイル(BMP形式)に保存する。
	 * 
	 * @param aFile 画像ファイル
	 * @param aChart グラフ情報
	 * @param aWidth グラフサイズ（横幅）
	 * @param aHeight グラフサイズ(縦幅)
	 * @return 結果
	 * @throws IOException IO操作に起因する問題が発生した場合
	 */
	public static boolean saveChartAsBMP(final File aFile, final AzukiChart aChart, final float aWidth, final float aHeight) throws IOException {
		return saveChart(aFile, aChart, aWidth, aHeight, "bmp");
	}

	/**
	 * グラフを画像ファイル(Jpeg形式)に保存する。
	 * 
	 * @param aFile 画像ファイル
	 * @param aChart グラフ情報
	 * @param aWidth グラフサイズ（横幅）
	 * @param aHeight グラフサイズ(縦幅)
	 * @return 結果
	 * @throws IOException IO操作に起因する問題が発生した場合
	 */
	public static boolean saveChartAsJpeg(final File aFile, final AzukiChart aChart, final float aWidth, final float aHeight) throws IOException {
		return saveChart(aFile, aChart, aWidth, aHeight, "jpeg");
	}

	/**
	 * グラフを画像ファイル(PNG形式)に保存する。
	 * 
	 * @param aFile 画像ファイル
	 * @param aChart グラフ情報
	 * @param aWidth グラフサイズ（横幅）
	 * @param aHeight グラフサイズ(縦幅)
	 * @return 結果
	 * @throws IOException IO操作に起因する問題が発生した場合
	 */
	public static boolean saveChartAsPNG(final File aFile, final AzukiChart aChart, final float aWidth, final float aHeight) throws IOException {
		return saveChart(aFile, aChart, aWidth, aHeight, "png");
	}

	/**
	 * グラフを画像ファイルに保存する。
	 * 
	 * @param aFile 画像ファイル
	 * @param aChart グラフ情報
	 * @param aWidth グラフサイズ（横幅）
	 * @param aHeight グラフサイズ(縦幅)
	 * @param aFormat 画像形式
	 * @return 結果
	 * @throws IOException IO操作に起因する問題が発生した場合
	 */
	private static boolean saveChart(final File aFile, final AzukiChart aChart, final float aWidth, final float aHeight, final String aFormat)
			throws IOException {
		boolean result = false;

		int width = (int) aWidth;
		int height = (int) aHeight;

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (null != aChart) {
			result = aChart.draw(g, 0, 0, width, height);
		}
		if (result) {
			result = ImageIO.write(image, aFormat, aFile);
		}

		return result;
	}
}
