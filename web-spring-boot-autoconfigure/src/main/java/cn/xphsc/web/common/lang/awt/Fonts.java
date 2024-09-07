/*
 * Copyright (c) 2023 huipei.x
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xphsc.web.common.lang.awt;

import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 字体工具类
 * @since 1.2.0
 */
public class Fonts {

    private Fonts() {
    }

    /**
     * 获取字体指标
     * @param font font
     * @return metrics
     */
    public static FontMetrics getMetrics(Font font) {
        return FontDesignMetrics.getMetrics(font);
    }

    /**
     * 获取字符实际长度
     * @param c    c
     * @param font font
     * @return 实际长度
     */
    public static int getStringWidth(char c, Font font) {
        FontMetrics fm = FontDesignMetrics.getMetrics(font);
        return fm.charWidth(c);
    }

    /**
     * 获取字符传实际长度
     * @param s    s
     * @param font font
     * @return 实际长度
     */
    public static int getStringWidth(String s, Font font) {
        FontMetrics fm = FontDesignMetrics.getMetrics(font);
        return fm.stringWidth(s);
    }

    /**
     * 获取字体高度
     * @param font font
     * @return 高度
     */
    public static int getHeight(Font font) {
        FontMetrics fm = FontDesignMetrics.getMetrics(font);
        return fm.getHeight();
    }

    /**
     * 获取系统支持的字体
     *
     * @return fonts
     */
    public static String[] getSupportedFonts() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }

    /**
     * 获取字体实际宽高
     * @param g2d  g2d
     * @param font font
     * @param s    s
     * @return [宽, 高]
     */
    public static int[] getWidthHeightPixel(Graphics2D g2d, Font font, String s) {
        return getWidthHeightPixel(g2d, font, s, 0, 0);
    }

    /**
     * 获取字体实际宽高
     * @param g2d  g2d
     * @param font font
     * @param s    s
     * @param x    x
     * @param y    y
     * @return [宽, 高]
     */
    public static int[] getWidthHeightPixel(Graphics2D g2d, Font font, String s, int x, int y) {
        FontRenderContext context = g2d.getFontRenderContext();
        GlyphVector gv = font.createGlyphVector(context, s);
        Rectangle bounds = gv.getPixelBounds(null, x, y);
        return new int[]{bounds.width, bounds.height};
    }

}
