package com.example.lixc.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

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
    public static void proportionalScaling(float f) throws IOException {
        Thumbnails.of(fromPicture).scale(f).toFile(toPic);
    }


    public static void main(String[] args) throws Exception {
//        defineScaling(500, 300);
//        proportionalScaling(0.2f);

        //图片尺寸不变，压缩图片文件大小
        //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
//        Thumbnails.of(fromPicture).scale(1f).outputQuality(0.25f).toFile(toPic);

        Thumbnails.of(fromPicture).scale(1)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(waterPic), 0.5f)
                .outputQuality(0.8f).toFile(toPic);
    }

}
