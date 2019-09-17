package com.torinosrc.commons.utils.image;


import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class ImageUtils {

    private static final Logger LOG = LoggerFactory
            .getLogger(ImageUtils.class);

    /**
     * 在基础图片上，合并图片和文字
     * @param baseImagePath 背景图的全路径，包括文件名
     * @param outPutImagePath 合成后的图片的存放路径，如 /www/root/mall_160/public/
     * @param outPutImageFileName 合成后的图片的文件名
     * @param compoundTextInfoList 要合成的文字的集合
     * @param compoundImageInfoList 要合成的图片的集合
     * @return
     */
    public static String compoundPirtureAndFont(String baseImagePath, String outPutImagePath, String outPutImageFileName, List<CompoundTextInfo> compoundTextInfoList, List<CompoundImageInfo> compoundImageInfoList) {

        File file = new File(outPutImagePath);
        if (!file.exists()) {
            file.mkdir();
        }

        try (OutputStream imgOutputStream = new FileOutputStream(outPutImagePath + outPutImageFileName);) {

            LOG.info("ImageUtils baseImagePath: " + baseImagePath);

            // 创建主图片的文件流
            BufferedImage baseBuffImg;
            // 如果是读服务器上的图片，使用 new File() 这种方式会报错
            if (baseImagePath.indexOf("http") == -1) {
                baseBuffImg = ImageIO.read(new File(baseImagePath));
            } else {
                baseBuffImg = ImageIO.read(new URL(baseImagePath).openStream());
            }
            // 得到主图片的画笔对象
            Graphics baseImgGraphics = baseBuffImg.getGraphics();
            // 创建要附加的图象
            ImageIcon imgIcon;
            for (CompoundImageInfo compoundImageInfo : compoundImageInfoList) {

                LOG.info("ImageUtils compoundImageInfo.getImagePath(): " + compoundImageInfo.getImagePath());

                BufferedImage newBuffImg;
                if (!compoundImageInfo.getImagePath().contains("http")) {
                    File newImageFile = new File(compoundImageInfo.getImagePath());
                    newBuffImg = ImageIO.read(newImageFile);
                } else {
                    newBuffImg = ImageIO.read(new URL(compoundImageInfo.getImagePath()).openStream());
                }
                imgIcon = new ImageIcon(newBuffImg);
                // 得到要附加的 Image 对象
                Image img = imgIcon.getImage();
                // 获取要合并到基础图片上的图片的宽高
                int imgWidth = newBuffImg.getWidth();
                int imgHeight = newBuffImg.getHeight();
                // 将附加图片绘到背景图片上
                // 1. 2.  width 和 height 传了其中一个过来，则按原图比例计算另一个值的大小
                // 3. 如果 width 和 height 都不为空，则按照传过来的值设置图片大小
                // 4. 如果 width 和 height 都为空，则按原图大小绘制
                if (compoundImageInfo.getWidth() == null && compoundImageInfo.getHeight() != null) {
                    int imgAutoWidth = compoundImageInfo.getHeight() * imgWidth / imgHeight;
                    baseImgGraphics.drawImage(img, compoundImageInfo.getX(), compoundImageInfo.getY(), imgAutoWidth, compoundImageInfo.getHeight(), null);
                } else if (compoundImageInfo.getWidth() != null && compoundImageInfo.getHeight() == null) {
                    int imgAutoHeight = compoundImageInfo.getWidth() * imgHeight / imgWidth;
                    baseImgGraphics.drawImage(img, compoundImageInfo.getX(), compoundImageInfo.getY(), compoundImageInfo.getWidth(), imgAutoHeight, null);
                } else if (compoundImageInfo.getWidth() != null && compoundImageInfo.getHeight() != null) {
                    baseImgGraphics.drawImage(img, compoundImageInfo.getX(), compoundImageInfo.getY(), compoundImageInfo.getWidth(), compoundImageInfo.getHeight(), null);
                } else {
                    baseImgGraphics.drawImage(img, compoundImageInfo.getX(), compoundImageInfo.getY(), null);
                }
                // 设置颜色（默认黑色，暂不支持自定义）
                baseImgGraphics.setColor(Color.BLACK);
            }

            // 创建要附加的文字
            // 默认黑体，字体暂不支持自定义；字体大小、颜色可自定义
            Font font;
            for (CompoundTextInfo compoundTextInfo : compoundTextInfoList) {
                font = new Font("黑体", Font.PLAIN, compoundTextInfo.getFontSize());
                baseImgGraphics.setColor(compoundTextInfo.getColor());
                baseImgGraphics.setFont(font);
                baseImgGraphics.drawString(compoundTextInfo.getText(), compoundTextInfo.getX(), compoundTextInfo.getY());
            }

            // 销毁绘图工具资源
            baseImgGraphics.dispose();

            // 输出图片
            ImageIO.write(baseBuffImg, "jpg", imgOutputStream);

            return outPutImageFileName;
        } catch (IOException e) {
            LOG.info("ImageUtils 合并图片和文字失败: " + e);
            throw new TorinoSrcServiceException("合并图片和文字失败");
        }
    }
}
