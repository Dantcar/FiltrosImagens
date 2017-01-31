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
public class MainBox {
    
    public static void main(String[] args){
        
        try{
            
            int kernelSize = 9; //ao aumentar o valor a imagem fica mais embassada
            
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            
            Mat source = Highgui.imread("D://teste_gray.jpg", Highgui.CV_LOAD_IMAGE_GRAYSCALE);
            
            Mat destination = new Mat(source.rows(), source.cols(), source.type());
            
            Mat kernel = Mat.ones(kernelSize, kernelSize, CvType.CV_32F);
            
            for(int i=0; i<kernel.rows(); i++){
                
                for(int j=0; j<kernel.cols(); j++){
                    
                    double[] m = kernel.get(j, j);
                    
                    for(int k=0;k<m.length; k++){
                        m[k] = m[k] / (kernelSize * kernelSize);
                    }
                    kernel.put(i, j, m);
                }
            }
            
            
            Imgproc.filter2D(source, destination, -1, kernel);
            
            Highgui.imwrite("D://Box.jpg", destination);
            
            
            
            
        }catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
}//class MainBox
