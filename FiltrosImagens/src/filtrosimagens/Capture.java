package filtrosimagens;


import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Décio
 */
public class Capture {
    
    

    /**
     * 
     * @throws IOException 
     */
    public void robo() throws IOException {
        Calendar now = Calendar.getInstance();
        
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
            Robot robot = new Robot();
            BufferedImage screnShot = 
                    robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(screnShot, "jpg", new File("C:\\Users\\Décio\\Documents\\__POO2016\\CapturaTela\\"+formatter.format(now.getTime())+".jpg"));
            System.out.println(formatter.format(now.getTime()));
            
        } catch (AWTException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//final método robo
    
    public static void main(String[] args){
        Capture s21 = new Capture();
        while(1==1){
            try {
                s21.robo();
                Thread.sleep(10000);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
