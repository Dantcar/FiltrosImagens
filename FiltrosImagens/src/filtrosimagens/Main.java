package filtrosimagens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Décio
 */
public class Main {

    static int width;

    static int height;

    static double alpha = 2;

    static double beta = 50;

    public static void main(String[] args) {

        try {

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            Mat source = Highgui.imread("d:\\grayscale.jpg",
                    Highgui.CV_LOAD_IMAGE_GRAYSCALE);

            Mat destination = new Mat(source.rows(), source.cols(), source.type());

            Imgproc.equalizeHist(source, destination);
            
            Highgui.imwrite("D:\\contraste.jpg", destination);

        }catch (Exception e){
            System.out.println("Exception: "+ e.getMessage());
        }

    }
}
