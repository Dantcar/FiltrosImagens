package filtrosimagens;


import java.awt.AWTException;
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
 * @author Decio
 */
public class CaptureFullScreen {

    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            String format = "jpg";
            String fileName = "C:\\Users\\Décio\\Documents\\__POO2016\\CapturaTela\\FulScreenShot."+format;
            
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFulImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFulImage, format, new File(fileName));
            
            System.out.println("A tela foi salva!");
            
            

        } catch (AWTException | IOException ex) {
            System.err.println(ex);
            Logger.getLogger(CaptureFullScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
