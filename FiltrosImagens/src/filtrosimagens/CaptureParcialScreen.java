package filtrosimagens;


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
public class CaptureParcialScreen {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            String format = "jpg";
            String fileName = "C:\\Users\\Décio\\Documents\\__POO2016\\CapturaTela\\ParcialcreenShot."+format;
            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            //Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            Rectangle captureRect = new Rectangle(0, 0, screenSize.width/4, screenSize.height/4);
            BufferedImage screenFulImage = robot.createScreenCapture(captureRect);
            ImageIO.write(screenFulImage, format, new File(fileName));
            
            System.out.println("A tela foi salva parcialmente!");
            
            

        } catch (AWTException | IOException ex) {
            System.err.println(ex);
            Logger.getLogger(CaptureFullScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

    } 
}
