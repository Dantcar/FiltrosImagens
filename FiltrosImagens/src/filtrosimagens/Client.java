package filtrosimagens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Décio
 */
public class Client {

    public static void main(String args[]){
        Socket soc = null;
        BufferedImage img = null;
        
                    
        try{
            
            soc = new Socket("localhost", 4000);
            System.out.println("Reading image from disk. ");
            img = ImageIO.read(new File("digital_image_processing.jpg"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            ImageIO.write(img, "jpg", baos);
            
            baos.flush();
            
            byte[] bytes = baos.toByteArray();
            
            System.out.println("Sending image to server. ");
            
            OutputStream out = soc.getOutputStream();
            
            DataOutputStream dos = new DataOutputStream(out);
            
            dos.writeInt(bytes.length);
            dos.write(bytes, 0, bytes.length);
            
            System.out.println("Image sent to server. ");
            
            dos.close();
            out.close();
            
            
        }catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
            try {
                soc.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            

    }// final método principal main

}//final class Client
