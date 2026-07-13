/*
 * Copyright (c) 2025 huipei.x
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
package cn.xphsc.web.common.lang.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Captchas类用于处理验证码相关的功能
 * 该类提供了生成、验证和管理验证码的方法
 * @since 2.0.4
 */
public class Captchas {

    private  static  String   RANDOM_CHARS="ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
    /**
     * 生成图形随机验证码
     */
    public static BufferedImage generateCaptchaImage(int length) {
        String captcha = generateRandomCaptcha(length);
        BufferedImage image = createCaptchaImage(captcha);
        return image;
    }

    /**
     * 生成柔和图形随机验证码
     */
    public static BufferedImage generateSoftCaptchaImage(int length) {
        String captcha = generateRandomCaptcha(length);
        BufferedImage image = createSoftCaptchaImage(captcha);
        return image;
    }

    /**
     * 生成随机验证码字符串
     * @param length 验证码长度
     * @return 随机验证码字符串
     */
    public static  String generateRandomCaptcha(int length) {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < length; i++) {
            captcha.append(RANDOM_CHARS.charAt(random.nextInt(RANDOM_CHARS.length())));
        }
        return captcha.toString();
    }


    public static BufferedImage createSoftCaptchaImage(String text) {
        int width = 120;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // ----- 1. 柔和背景（浅蓝渐变） -----
        Color start = new Color(236, 242, 255);
        Color end = new Color(219, 233, 255);
        GradientPaint gradient = new GradientPaint(0, 0, start, 0, height, end);
        g.setPaint(gradient);
        g.fillRect(0, 0, width, height);
        // ----- 2. 柔和噪点（不脏不乱） -----
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            g.setColor(new Color(180 + random.nextInt(40), 190 + random.nextInt(40), 200 + random.nextInt(40)));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.fillOval(x, y, 2, 2);
        }
        // ----- 3. 柔和弧形干扰线（专业、整洁） -----
        for (int i = 0; i < 2; i++) {
            g.setColor(new Color(150, 180, 220));
            drawSmoothCurve(g, width, height);
        }
        // ----- 4. 字体：微软雅黑，更符合中国审美 -----
        g.setFont(new Font("微软雅黑", Font.BOLD, 32));
        char[] chars = text.toCharArray();
        int x = 15;
        for (int i = 0; i < chars.length; i++) {
            // 随机柔和颜色
            g.setColor(new Color(20 + random.nextInt(80), 40 + random.nextInt(80), 120 + random.nextInt(80)));

            // 轻微旋转，更优雅
            double angle = (random.nextInt(6) - 3) * Math.PI / 180;

            g.rotate(angle, x + 10, 30);
            g.drawString(String.valueOf(chars[i]), x, 35);
            g.rotate(-angle, x + 10, 30);

            x += 28;
        }
        g.dispose();
        return image;
    }



    private static void drawSmoothCurve(Graphics2D g, int width, int height) {
        Random random = new Random();

        int x1 = 0;
        int y1 = random.nextInt(height / 2);
        int x2 = width;
        int y2 = height / 2 + random.nextInt(height / 2);

        int ctrl1X = width / 3;
        int ctrl1Y = random.nextInt(height);
        int ctrl2X = width * 2 / 3;
        int ctrl2Y = random.nextInt(height);

        // 使用 Java2D 曲线路径
        java.awt.geom.Path2D path = new java.awt.geom.Path2D.Double();
        path.moveTo(x1, y1);
        path.curveTo(ctrl1X, ctrl1Y, ctrl2X, ctrl2Y, x2, y2);

        g.setStroke(new BasicStroke(1.5f));
        g.draw(path);
    }

    public static BufferedImage createCaptchaImage(String captchaText) {
        int width = 120;
        int height = 40;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 设置背景色 - 浅色背景
        g.setColor(getRandomColor(230, 255));
        g.fillRect(0, 0, width, height);

        // 添加噪点 - 提高视觉效果
        Random random = new Random();
        g.setColor(getRandomColor(160, 200));
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(5);
            int yl = random.nextInt(5);
            g.drawOval(x, y, xl, yl);
        }

        // 添加干扰线 - 曲线形式更美观
        g.setColor(getRandomColor(100, 160));
        for (int i = 0; i < 3; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            // 使用贝塞尔曲线绘制更自然的干扰线
            drawBezierCurve(g, x1, y1, x2, y2);
        }

        // 绘制验证码文本 - 多色彩和变换
        Font[] fonts = {
                new Font("微软雅黑", Font.BOLD, 22),
                new Font("宋体", Font.ITALIC, 20),
                new Font("楷体", Font.BOLD, 21)
        };

        char[] chars = captchaText.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            g.setFont(fonts[random.nextInt(fonts.length)]);
            g.setColor(getRandomColor(20, 100));

            // 文字旋转和偏移
            double theta = random.nextInt(40) * Math.PI / 180 - Math.PI / 9;
            g.rotate(theta, 20 + i * 20, 20);
            g.drawString(String.valueOf(chars[i]), 20 + i * 20, 25);
            g.rotate(-theta, 20 + i * 20, 20);
        }

        g.dispose();
        return image;
    }

    /**
     * 获取随机颜色
     */
    private static  Color getRandomColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 绘制贝塞尔曲线干扰线
     */
    private static void drawBezierCurve(Graphics2D g, int x1, int y1, int x2, int y2) {
        Random random = new Random();
        int cx1 = x1 + (x2 - x1) / 3 + random.nextInt(20) - 10;
        int cy1 = y1 + random.nextInt(20) - 10;
        int cx2 = x1 + 2 * (x2 - x1) / 3 + random.nextInt(20) - 10;
        int cy2 = y2 + random.nextInt(20) - 10;

        for (int i = 0; i < 2; i++) {
            g.drawLine(x1 + i, y1, cx1 + i, cy1);
            g.drawLine(cx1 + i, cy1, cx2 + i, cy2);
            g.drawLine(cx2 + i, cy2, x2 + i, y2);
        }
    }


}
