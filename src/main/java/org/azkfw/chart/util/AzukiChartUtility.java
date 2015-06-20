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

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.azkfw.chart.AzukiChart;
import org.azkfw.util.StringUtility;

/**
 * このクラスは、グラフのユーティリティクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class AzukiChartUtility {
	
	/** デフォルト画像 横幅 */
	private static final float DEFAULT_IMAGE_WIDTH = 800.f;
	/** デフォルト画像 縦幅 */
	private static final float DEFAULT_IMAGE_HEIGHT = 800.f;
	/** 画像 BMP */
	private static final String IMAGE_FORMAT_BMP = "bmp";
	/** 画像 JPEG */
	private static final String IMAGE_FORMAT_JPEG = "jpeg";
	/** 画像 PNG */
	private static final String IMAGE_FORMAT_PNG = "png";
	

	/**
	 * グラフを描画したフレームを表示する。
	 * 
	 * @param chart グラフ情報
	 * @return 結果
	 */
	public static boolean showChartAsFrame(final AzukiChart chart) {
		return showChartAsFrame(chart, null, DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT, null);
	}

	/**
	 * グラフを描画したフレームを表示する。
	 * 
	 * @param chart グラフ情報
	 * @param title タイトル
	 * @return 結果
	 */
	public static boolean showChartAsFrame(final AzukiChart chart, final String title) {
		return showChartAsFrame(chart, title, DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT, null);
	}

	/**
	 * グラフを描画したフレームを表示する。
	 * 
	 * @param chart グラフ情報
	 * @param width グラフサイズ（横幅）
	 * @param height グラフサイズ（縦幅）
	 * @return 結果
	 */
	public static boolean showChartAsFrame(final AzukiChart chart, final float width, final float height) {
		return showChartAsFrame(chart, null, width, height, null);
	}

	/**
	 * グラフを描画したフレームを表示する。
	 * 
	 * @param chart グラフ情報
	 * @param title タイトル
	 * @param width グラフサイズ（横幅）
	 * @param height グラフサイズ（縦幅）
	 * @return 結果
	 */
	public static boolean showChartAsFrame(final AzukiChart chart, final String title, final float width, final float height) {
		return showChartAsFrame(chart, title, width, height, null);
	}

	/**
	 * グラフを画像ファイル(BMP形式)に保存する。
	 * 
	 * @param file 画像ファイル
	 * @param chart グラフ情報
	 * @param width グラフサイズ（横幅）
	 * @param height グラフサイズ(縦幅)
	 * @return 結果
	 * @throws IOException IO操作に起因する問題が発生した場合
	 */
	public static boolean saveChartAsBMP(final File file, final AzukiChart chart, final float width, final float height) throws IOException {
		return saveChart(file, chart, width, height, IMAGE_FORMAT_BMP);
	}

	/**
	 * グラフを画像ファイル(Jpeg形式)に保存する。
	 * 
	 * @param file 画像ファイル
	 * @param chart グラフ情報
	 * @param width グラフサイズ（横幅）
	 * @param height グラフサイズ(縦幅)
	 * @return 結果
	 * @throws IOException IO操作に起因する問題が発生した場合
	 */
	public static boolean saveChartAsJpeg(final File file, final AzukiChart chart, final float width, final float height) throws IOException {
		return saveChart(file, chart, width, height, IMAGE_FORMAT_JPEG);
	}

	/**
	 * グラフを画像ファイル(PNG形式)に保存する。
	 * 
	 * @param file 画像ファイル
	 * @param chart グラフ情報
	 * @param width グラフサイズ（横幅）
	 * @param height グラフサイズ(縦幅)
	 * @return 結果
	 * @throws IOException IO操作に起因する問題が発生した場合
	 */
	public static boolean saveChartAsPNG(final File file, final AzukiChart chart, final float width, final float height) throws IOException {
		return saveChart(file, chart, width, height, IMAGE_FORMAT_PNG);
	}

	/**
	 * グラフを画像ファイルに保存する。
	 * 
	 * @param file 画像ファイル
	 * @param chart グラフ情報
	 * @param width グラフサイズ（横幅）
	 * @param height グラフサイズ(縦幅)
	 * @param format 画像形式
	 * @return 結果
	 * @throws IOException IO操作に起因する問題が発生した場合
	 */
	private static boolean saveChart(final File file, final AzukiChart chart, final float width, final float height, final String format)
			throws IOException {
		boolean result = false;

		BufferedImage image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (null != chart) {
			result = chart.draw(g, 0, 0, width, height);
		}
		if (result) {
			result = ImageIO.write(image, format, file);
		}

		return result;
	}

	private static boolean showChartAsFrame(final AzukiChart chart, final String title, final float width, final float height, final Frame parent) {
		boolean result = false;

		BufferedImage image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (null != chart) {
			result = chart.draw(g, 0, 0, width, height);
		}
		if (result) {
			ChartFrame frame = new ChartFrame(image, title);
			frame.setVisible(true);
		}

		return result;
	}

	private static class ChartFrame extends JFrame {

		/** serialVersionUID */
		private static final long serialVersionUID = 8502657848187570358L;

		private Image image;
		private ImagePanel panel;

		public ChartFrame(final Image aImage, final String aTitle) {
			setLayout(null);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			if (StringUtility.isNotEmpty(aTitle)) {
				setTitle(aTitle);
			}

			image = aImage;

			panel = new ImagePanel(image);
			panel.setLocation(0, 0);
			add(panel);

			addWindowListener(new WindowAdapter() {
				@Override
				public void windowOpened(final WindowEvent event) {
					Insets insets = getInsets();
					int width = image.getWidth(null) + (insets.left + insets.right);
					int height = image.getHeight(null) + (insets.top + insets.bottom);
					Dimension dim = new Dimension(width, height);
					setSize(width, height);
					setMinimumSize(dim);
				}
			});
			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(final ComponentEvent event) {
					Insets insets = getInsets();
					int width = getWidth() - (insets.left + insets.right);
					int height = getHeight() - (insets.top + insets.bottom);
					panel.setSize(width, height);
				}
			});
		}

		private static class ImagePanel extends JPanel {

			/** serialVersionUID */
			private static final long serialVersionUID = 4718980942400003983L;

			private Image image;

			public ImagePanel(final Image aImage) {
				image = aImage;
			}

			@Override
			public void paintComponent(final Graphics g) {
				int width = getWidth();
				int height = getHeight();
				int x = (width - image.getWidth(this)) / 2;
				int y = (height - image.getHeight(this)) / 2;

				Graphics2D g2D = (Graphics2D) g;
				g2D.drawImage(image, x, y, this);
			}
		}
	}
}
