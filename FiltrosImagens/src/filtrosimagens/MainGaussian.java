package filtrosimagens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


/**
 *
 * @author Décio
 */
public class MainGaussian {
    
    public static void main(String[] args){
        
        try{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        Mat source = Highgui.imread("D://teste.png",
                Highgui.CV_LOAD_IMAGE_COLOR);
        
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        
        Imgproc.GaussianBlur(source, destination, new Size(45,45), 0);
        //Imgproc.GaussianBlur(source, destination, new Size(65,65), 0);
        //Imgproc.GaussianBlur(source, destination, new Size(25,1), 0);
        Highgui.imwrite("D://Gaussian45.jpg", destination);
            
        }catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
    
}//final class MainGaussian
