package filtrosimagens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Décio
 */
public class MainThresholding {
    
    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
       
        try{
        
            Mat source = Highgui.imread("D://teste.png",
                    Highgui.CV_LOAD_IMAGE_COLOR);
            
            Mat destination = new
                Mat(source.rows(), source.cols(), source.type());
            
            destination = source;
            
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_TOZERO);
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_BINARY);
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_BINARY_INV);
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_TOZERO_INV);
            Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_TRUNC);
            
            Highgui.imwrite("D://ThreshZero.jpg", destination);
             
        }catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
        
    }
    
}//final MainThresholding
