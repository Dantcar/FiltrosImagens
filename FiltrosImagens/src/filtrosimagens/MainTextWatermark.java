package filtrosimagens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.opencv.core.Point;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

/**
 *
 * @author Décio
 */
public class MainTextWatermark {
    
    public static void main(String[] args){
        
        try{
            
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            
            Mat source = Highgui.imread("D://teste.png", Highgui.CV_LOAD_IMAGE_COLOR);
            
            Mat destination = new Mat(source.rows(), source.cols(), source.type());
            
            Core.putText(source, 
                    "Tutorialspoint.com by DAC", 
                    new Point(source.rows()/2,(source.cols()/15*11)), //Posição do texto na tela
                    Core.FONT_HERSHEY_PLAIN, new Double(1.1),new Scalar(150));
            
            Highgui.imwrite("D://Watermarked.jpg", source);
            
            
        }catch(Exception e){
            System.out.println("Exception:" + e.getMessage());
        }
        
    }
    
}//class MainTextWatermark
