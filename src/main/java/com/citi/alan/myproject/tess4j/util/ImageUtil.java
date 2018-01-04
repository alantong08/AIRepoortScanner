package com.citi.alan.myproject.tess4j.util;

import java.io.File;
import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.sourceforge.tess4j.TesseractException;

@Component
public class ImageUtil {
    
    @Value("${original.screenshot.path}")
    private String resultPath;
    
    @Value("${upload.file.path}")
    private String uploadFilePath;
    
    public String  processImageThreshold(String absoluteFilePath){
        long lStartTime = System.currentTimeMillis();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Imgcodecs.imread(absoluteFilePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat dst = new Mat();
        Imgproc.adaptiveThreshold(image, dst, 200, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 25, 10);
        
        String newFileName = "grayscall_"+absoluteFilePath.substring(absoluteFilePath.lastIndexOf(File.separator)+1);
        String newFile = uploadFilePath+newFileName;
        Imgcodecs.imwrite(newFile , dst);
        long lEndTime = System.currentTimeMillis();
        long output = (lEndTime - lStartTime);
        System.out.println("processImageThreshold : Elapsed time in millseconds: " + output);
        return newFile;
    }

    public static void main(String[] args) throws IOException, TesseractException {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String path = "C://upload//android_elian1.png";
        String result = "C://upload//elian_result.png";
        // 读取原图像
        Mat image = Imgcodecs.imread(path, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat dst = new Mat();
        Imgproc.adaptiveThreshold(image, dst, 200, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 25, 10);
        Imgcodecs.imwrite(result, dst);
        
        TessercatUtil tessercatUtil = new TessercatUtil();
       // tessercatUtil.parseImage(new File(result));
    }

}
