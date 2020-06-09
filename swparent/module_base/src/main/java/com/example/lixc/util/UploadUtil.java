package com.example.lixc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 文件上传处理类
 */
public class UploadUtil {
    private static final Logger logger = LoggerFactory.getLogger(UploadUtil.class);
    static String iconPath = "D:\\IdeaProjects\\lixc\\slowWarm\\other\\SlowWormPainterProject\\SlowWormPainterProject\\img\\p.png";
    static String destPath = "C:\\Users\\86180\\Desktop\\test.png";
    static int x = 500;
    static int y = 500;
    static int degree = 0;
    static float alpha = 0.5f;

    /**
     * 使用默认设置上传图片
     *
     * @param file file对象
     * @throws IOException
     */
    public static void uploadFileWithDefault(MultipartFile file) throws IOException {
        markImageWithIcon(file.getInputStream(), iconPath, destPath, x, y, degree, alpha);
    }

    /**
     * 通过文件路径的方式 上传带有水印的图片
     *
     * @param filePath
     * @throws FileNotFoundException
     */
    public static void uploadFileBySrc(String filePath) throws FileNotFoundException {
        markImageWithIcon(new FileInputStream(filePath), iconPath, destPath, x, y, degree, alpha);
    }


    public static void main(String[] args) throws FileNotFoundException {
        uploadFileBySrc("D:\\IdeaProjects\\lixc\\slowWarm\\other\\SlowWormPainterProject\\SlowWormPainterProject\\img\\banner.png");
    }

    /**
     * 为图片增加水印
     *
     * @param inputStream 文件输入流
     * @param destPath    图片保存路径
     * @param iconPath    水印图片
     * @param x           x 坐标
     * @param y           y 坐标
     * @param degree      旋转角度
     * @param alpha       透明度
     */
    public static void markImageWithIcon(InputStream inputStream, String iconPath, String destPath, int x, int y, int degree, float alpha) {
        long start = System.currentTimeMillis();
        logger.info("为图片增加水印开始....." + start);
        OutputStream os = null;
        try {
            //获取文件的输入流
            //将输入流转换为图片对象
            Image srcImage = ImageIO.read(inputStream);
            BufferedImage bufferedImage = new BufferedImage(
                    srcImage.getWidth(null), srcImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
            //得到画笔对象
            Graphics2D g = bufferedImage.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImage.getScaledInstance(srcImage.getWidth(null), srcImage
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 设置水印旋转
            g.rotate(Math.toRadians(degree),
                    (double) bufferedImage.getWidth() / 2, (double) bufferedImage
                            .getHeight() / 2);
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            // 得到Image对象。
            Image img = imgIcon.getImage();
            //透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 表示水印图片的位置
            int x1 = (int) (srcImage.getWidth(null) * 0.8);
            int y1 = (int) (srcImage.getHeight(null) * 0.6);
            System.out.println(x1);
            System.out.println(y1);
            g.drawImage(img, x1, y1, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            os = new FileOutputStream(destPath);
            // 生成图片
            ImageIO.write(bufferedImage, "JPG", os);
            logger.info("为图片增加水印结束....耗时：" + (System.currentTimeMillis() - start) / 1000);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
