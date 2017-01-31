package filtrosimagens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Décio
 */
public class MainPyramids {

    public static void main(String[] args) {

        try {

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.out.println("Versão OPENCV: " + Core.VERSION);

             //pyramids UP
            
            Mat source = Highgui.imread("D:\\teste.png",
                    Highgui.CV_LOAD_IMAGE_COLOR);

            Mat destinationUp = new Mat(source.rows() * 2,
                    source.cols() * 2, source.type());

            destinationUp = source;

            Imgproc.pyrUp(source, destinationUp,
                    new Size(source.cols() * 2, source.rows() * 2), Imgproc.BORDER_DEFAULT);

            Highgui.imwrite("D://pyrUp.jpg", destinationUp);
            
            //pyramids DOWN

            source = Highgui.imread("D://teste.png", Highgui.CV_LOAD_IMAGE_COLOR);
            
            Mat destinationDown = new Mat(source.rows()/2, source.cols()/2, source.type());
            
            destinationDown = source;
            
            Imgproc.pyrDown(source, destinationDown, new Size(source.cols()/2, source.rows()/2));
            
            Highgui.imwrite("pyrDown.jpg", destinationDown);
            
            
            
            
            

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

}//final class MainPyramids
