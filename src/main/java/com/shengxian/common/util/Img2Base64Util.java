package com.shengxian.common.util;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

/**
 * Description: 将图片转换为Base64<br>
 *              将base64编码字符串解码成img图片
 * @Author: yang
 * @Date: 2018-10-10
 * @Version: 1.0
 */
public class Img2Base64Util {


    public static void main(String[] args) {
        String imgFile = "E:\\49.jpg";//待处理的图片
        String imgSrt = getImgSrt(imgFile);
        System.out.println(imgSrt.length());
        System.out.println(imgSrt);
        String imgFilePath = "E:\\49.jpg";
        generateImage(imgSrt,imgFilePath);
    }


    /**
     * 将图片转换成Base64编码
     * @param imgFile 待处理图片
     * @return
     */
    public static String getImgSrt(String imgFile){
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;

        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()] ;
            in.read(data);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }

    public static boolean generateImage(String imgStr,String imgFilePath){

        if (imgStr == null){//图像数据为空
            return false;
        }
        try {
            //Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for(int i=0;i<b.length;++i){
                if(b[i]<0){//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }catch (Exception  e){
            return false;
        }

    }
}
