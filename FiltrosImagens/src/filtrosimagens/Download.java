package filtrosimagens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


/**
 *
 * @author Décio
 */
public class Download {
    
    
 public static void main (String[] args){
     
     try{
         String fileName = "digital_image_processing.jpg";
         String website=
                 "http://localhost:8080//images//teste1.jpg";
         System.out.println("Downloading File From: " + website);
         URL url = new URL(website);
         
         OutputStream outputStream;
         try (InputStream inputStream = url.openStream()) {
             outputStream = new FileOutputStream(fileName);
             byte[] buffer = new byte[2048];
             int length = 0;
             while((length = inputStream.read(buffer)) !=-1){
                 
                 System.out.println("Buffer Read of length: " + length);
                 
                 outputStream.write(buffer, 0, length);
             }
         }
          outputStream.close();
          
         
     }catch (Exception e){
        System.out.println("Exception: " + e.getMessage());
     }
 } //final método principal main  
}
