package filtrosimagens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Décio
 */
public class Pixel {

    BufferedImage image;

    int width;
    int heigth;

    public Pixel() {

        try {
            System.out.println("Estou aqui");
            File input = new File("d:\\1.png");

            image = ImageIO.read(input);

            width = image.getWidth();
            heigth = image.getHeight();

            int count = 0;

            for (int i = 0; i < heigth; i++) {
                for (int j = 0; j < width; j++) {

                    count++;

                    Color c = new Color(image.getRGB(j, i));

                    System.out.println("S.No: " + count
                            + " Red " + c.getRed()
                            + " Green " + c.getGreen()
                            + " Blue " + c.getBlue()
                    );

                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Pixel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception :" + ex.getMessage());
        }

    }

    public static void main(String args[]) {
        Pixel obj = new Pixel();
    }//final classe main

}//final class Pixel
