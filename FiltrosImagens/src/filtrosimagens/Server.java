package filtrosimagens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.*;
import java.io.*;
import java.awt.image.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.swing.*;





/**
 *
 * @author Décio
 */
public class Server {
    
    public static void main(String args[]){
        ServerSocket server = null;
        
        Socket socket;
        
        try {
            server = new ServerSocket(4000);
            System.out.println("Server Waiting for image");
            
            socket = server.accept();
            
            System.out.println("Client connected. ");
            
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            
            int len = dis.readInt();
            
            System.out.println("Image Size: " + len/1024 + "KB");
            
            byte[] data = new byte[len];
            
            dis.readFully(data);
            
            dis.close();
            
            InputStream ian = new ByteArrayInputStream(data);
            
            BufferedImage bImage = ImageIO.read(ian);
            
            JFrame f = new JFrame("Server");
            
            ImageIcon icon = new ImageIcon(bImage);
            
            JLabel l = new JLabel();
            
            l.setIcon(icon);
            
            f.add(l);
            
            f.pack();
            
            f.setVisible(true);
            
               
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception: " + ex.getMessage());
        }
        
        
    }
    
}//final class Server
