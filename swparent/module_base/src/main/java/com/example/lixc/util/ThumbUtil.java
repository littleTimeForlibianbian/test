package com.example.lixc.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * 水印工具类
 */
public class ThumbUtil {

    public static File fromPicture = new File("C:\\Users\\86180\\Desktop\\test.png");
    public static File toPic = new File("C:\\Users\\86180\\Desktop\\test1.png");
    public static File waterPic = new File("D:\\IdeaProjects\\lixc\\slowWarm\\other\\SlowWormPainterProject\\SlowWormPainterProject\\img\\p.png");


    /**
     * 指定数值缩放
     *
     * @param x 缩放后x的值
     * @param y 缩放后y的值
     * @throws IOException
     */
    public static void defineScaling(int x, int y) throws IOException {
        Thumbnails.of(fromPicture).size(x, y).toFile(toPic);
    }

    /**
     * 等比例缩放
     */
    public static void proportionalScalingWithNoWater(File fromPicture, File toPic, float f) throws IOException {
        Thumbnails.of(fromPicture).scale(f).toFile(toPic);
    }


    public static void proportionalScalingWithWater(File fromPicture, File toPic, float f, File waterPic) throws IOException {

        Thumbnails.of(fromPicture).scale(1)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(waterPic), 0.5f)
                .outputQuality(f).toFile(toPic);
    }

}
