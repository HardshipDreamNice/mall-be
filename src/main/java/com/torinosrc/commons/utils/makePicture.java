package com.torinosrc.commons.utils;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class makePicture {

    public static void ImgYin(String ImgUrl,String codeUrl,String outPictureName,String picType){

        File _file = new File(ImgUrl);
        Image src = null;
        try {
            src = ImageIO.read(_file);
        } catch (IOException e) {
            throw new TorinoSrcServiceException("图片合成时,传入的图片路径异常");
        }
        int wideth=src.getWidth(null);
        int height=src.getHeight(null);
        BufferedImage image=new BufferedImage(wideth,height,BufferedImage.TYPE_INT_RGB);
        Graphics g=image.createGraphics();
        //图片
        g.drawImage(src,0,0,wideth,height,null);


        //二维码
        File cfile = new File(codeUrl);
        Image csrc = null;
        try {
            csrc = ImageIO.read(cfile);
        } catch (IOException e) {
            throw new TorinoSrcServiceException("图片合成时,传入的二维码路径异常");
        }
        int cwideth = csrc.getWidth(null);
        int cheight = csrc.getHeight(null);
        g.drawImage(csrc, 670, 1120, 330, 330, null);


        image.flush();
        g.dispose();
        //写进磁盘
        File file=new File(outPictureName);
        try {
            ImageIO.write(image, picType, file);
        } catch (IOException e) {
            throw new TorinoSrcServiceException("图片合成完,存放的磁盘路径异常");
        }
    }

    /**
     * 拼团用的图片合成
     * @param ImgUrl
     * @param codeUrl
     * @param productUrl
     * @param outPictureName
     * @param picType
     * @param time
     */
    public static void ImgYinPinTuan(String ImgUrl,String codeUrl,String productUrl,String outPictureName,String picType,String time){

        File _file = new File(ImgUrl);
        Image src = null;
        try {
            src = ImageIO.read(_file);
        } catch (IOException e) {
            throw new TorinoSrcServiceException("图片合成时,传入的图片路径异常");
        }
        int wideth=src.getWidth(null);
        int height=src.getHeight(null);
        BufferedImage image=new BufferedImage(wideth,height,BufferedImage.TYPE_INT_RGB);
        Graphics g=image.createGraphics();
        //图片
        g.drawImage(src,0,0,wideth,height,null);


        //二维码
        File cfile = new File(codeUrl);
        Image csrc = null;
        try {
            csrc = ImageIO.read(cfile);
        } catch (IOException e) {
            throw new TorinoSrcServiceException("图片合成时,传入的二维码路径异常");
        }
        int cwideth = csrc.getWidth(null);
        int cheight = csrc.getHeight(null);
        g.drawImage(csrc, 275, 1660, 300, 300, null);


        //商品
        File pfile = new File(productUrl);
        Image psrc = null;
        try {
            psrc = ImageIO.read(pfile);
        } catch (IOException e) {
            throw new TorinoSrcServiceException("图片合成时,传入的商品路径异常");
        }
        int pwideth = psrc.getWidth(null);
        int pheight = psrc.getHeight(null);
        g.drawImage(psrc, 80, 355, 621, 531, null);

//        String ss="此二维码有效期至2018年12月08日";
        String ss=time;
        g.setFont(new Font("黑体",Font.BOLD,60));//设置字体
        g.setColor(Color.white);//设置颜色
        g.drawString(ss,140,2100);

        image.flush();
        g.dispose();
        //写进磁盘
        File file=new File(outPictureName);
        try {
            ImageIO.write(image, picType, file);
        } catch (IOException e) {
            throw new TorinoSrcServiceException("图片合成完,存放的磁盘路径异常");
        }
    }

}
