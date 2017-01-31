package filtrosimagens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Décio
 */
public class GrayScale {
    
    BufferedImage image;
    
    int width;
    
    int heigth;
    
    public GrayScale() {
        
        
        
        try {
            File input = new File("d:\\teste.png");
            image = ImageIO.read(input);
            width = image.getWidth();
            heigth = image.getHeight();
            
            for(int i=0; i<heigth; i++){
                for(int j=0; j<width; j++){
                    
                    Color c = new Color(image.getRGB(j, i));
                    
                    int red = (int) (c.getRed() * 0.299);
                    
                    int green = (int) (c.getGreen() * 0.587);
                    
                    int blue = (int) (c.getBlue() * 0.114);
                    
                    Color newColor = new Color(red+green+blue, red+green+blue, red+green+blue);
                    
                    image.setRGB(j, i, newColor.getRGB());
                     
                }
            }
            
            File output = new File("d:\\grayscale.jpg");
            ImageIO.write(image, "jpg", output);
            
        } catch (IOException ex) {
            Logger.getLogger(GrayScale.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception: " + ex);
        }
        
       
        
        
    }
    
     public static void main (String args[]){
            GrayScale obj = new GrayScale();
        }
    
}//final class GrayScale
