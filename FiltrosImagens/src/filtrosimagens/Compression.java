package filtrosimagens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;



/**
 *
 * @author Décio
 */
public class Compression {
    
    public static void main(String[] args){
        
        try {
            File input = new File("D:\\teste.png");
            BufferedImage image = ImageIO.read(input);
            
            File compressedImageFile = new File("D:\\compress.jpg");
            OutputStream os = new FileOutputStream(compressedImageFile);
            
            Iterator<ImageWriter>writers =
            ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = (ImageWriter) writers.next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(os);
            
            writer.setOutput(ios);
            
            ImageWriteParam param = writer.getDefaultWriteParam();
            
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            
            param.setCompressionQuality(0.6f);
            
            writer.write(null, new IIOImage(image, null, null), param);
            
            ios.close();
            os.close();
            
            writer.dispose();
            
        } catch (IOException ex) {
            Logger.getLogger(Compression.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception: " + ex);
        }
        
        
    }
    
}//final class Compression
