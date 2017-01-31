/*
 * Curso Java Graphics2d
 * Praia Grande - SP
 * 14-01-2017
 */
package View;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import java.applet.Applet.*;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.internal.util.xml.impl.Input;

/**
 *
 * @author Décio
 */
public class ShowOff extends Component {

    public static void main(String[] args) {
        try {
            String filename = "D:\\TwitterD4ntcar.png";
            String message = "Java2D";
            int split = 4;

           
            if (args.length > 0) {
                filename = args[0];
            }

            if (args.length > 1) {
                message = args[1];
            }

            if (args.length > 2) {
                split = Integer.parseInt(args[2]);
            }

            ApplicationFrame f = new ApplicationFrame("ShowOff v1.0");

            f.setLayout(new BorderLayout());
            ShowOff showOff = new ShowOff(filename, message, split);

            f.add(showOff, BorderLayout.CENTER);
            f.getSize(f.getPreferredSize());
            f.center();
            f.setResizable(false);
            f.setVisible(true);

        } catch (Exception e) {
            System.out.println("Exception: método main ShowOff : " + e.getMessage());
           // System.exit(0);

        }
    }// final método main

    private BufferedImage mImage;
    private Font mFont;
    private String mMessage;
    private int mSplit;
    private TextLayout mLayout;

    /**
     * método construtor ShowOff
     *
     * @param filename
     * @param message
     * @param split
     */
    private ShowOff(String filename, String message, int split) {
        //get the specified image.
        InputStream in = getClass().getResourceAsStream(filename);

        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);

        try {
            mImage = decoder.decodeAsBufferedImage();
            in.close();

            // Create a font.
            mFont = new Font("Serif", Font.PLAIN, 116);

            //Save the message and split.
            mMessage = message;
            mSplit = split;

            //Set our size to match the image's size.
            setSize((int) mImage.getWidth(), (int) mImage.getHeight());

        } catch (IOException ex) {
            Logger.getLogger(ShowOff.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Exception: método construtor ShowOff : " + ex.getMessage());
        } catch (ImageFormatException ex) {
            Logger.getLogger(ShowOff.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Exception: método construtor ShowOff : " + ex.getMessage());
        }

    }//final showOff construtor

    /**
     *
     * @param g
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Turn on antialiasing.
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2);
        drawImageMosaic(g2);
        drawText(g2);

    }

    /**
     *
     * @param g2
     */
    protected void drawBackground(Graphics2D g2) {
        //Draw circles of different colors.
        int side = 45;
        int width = getSize().width;
        int height = getSize().height;

        Color[] colors = {Color.yellow, Color.cyan, Color.orange,
            Color.pink, Color.magenta, Color.lightGray};

        for (int y = 0; y < height; y += side) {
            for (int x = 0; x < width; x += side) {
                Ellipse2D ellipse = new Ellipse2D.Float(x, y, side, side);
                int index = (x + y) / side % colors.length;
                g2.setPaint(colors[index]);
                g2.fill(ellipse);
            }
        }

    }//final metodo drawBackground /**

    /**
     *
     * @param g2
     */
    protected void drawImageMosaic(Graphics2D g2) {
        //Break the image up into tiles. Draw each
        // tile with its own transparency, allowing
        // the background to show through to varying
        // degrees.

        int side = 36;
        int width = mImage.getWidth();
        int height = mImage.getHeight();

        for (int y = 0; y < height; y += side) {
            for (int x = 0; x < width; x += side) {
                //Calculate an appropriate transparency value.
                float xBias = (float) x / (float) width;
                float yBias = (float) y / (float) height;
                float alpha = 1.0f - Math.abs(xBias - yBias);

                g2.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, alpha));

                // Draw the subimage.
                int w = Math.min(side, width - x);
                int h = Math.min(side, height - y);

                BufferedImage tile = mImage.getSubimage(x, y, w, h);
                g2.drawImage(tile, x, y, null);
            }
        }
        //Reset the composite.
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }//final método drawImageMosaic

    /**
     *
     * @param g2
     */
    protected void drawText(Graphics2D g2) {
        //Find the bounds of the entire string.
        FontRenderContext frc = g2.getFontRenderContext();
        mLayout = new TextLayout(mMessage, mFont, frc);
        //Find the dimensions of this component.
        int width = mImage.getWidth();
        int height = mImage.getHeight();
        //Place the first full string, horizontally centered.
        // at the bottom of the component.

        Rectangle2D bounds = mLayout.getBounds();
        double x = (width - bounds.getWidth()) / 2;
        double y = height - bounds.getHeight();
        drawString(g2, x, y, 0);

        //Now draw a second version, anchored to the right side
        // of the component and rotated by - PI /2.
        drawString(g2, width - bounds.getHeight(), y, -Math.PI / 2);

    }// final método drawText

    /**
     *
     * @param g2
     * @param x
     * @param y
     * @param theta
     */
    protected void drawString(Graphics2D g2, double x, double y, double theta) {
        // Transform to the requested location.
        g2.translate(x, y);
        // Rotate by the requested angle.
        g2.rotate(theta);
        //Draw the first part of the string.
        String first = mMessage.substring(0, mSplit);
        float width = drawBoxedString(g2, first, Color.white, Color.red, 0);
        // Draw the second part of the string.
        String second = mMessage.substring(mSplit);
        drawBoxedString(g2, second, Color.blue, Color.white, width);
        // Undo the transformations.
        g2.rotate(-theta);
        g2.translate(-x, -y);

    }// final drawString

    private float drawBoxedString(Graphics2D g2, String s, Color c1, Color c2, double x) {
        //Calculate the width of the string.
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout subLayout = new TextLayout(s, mFont, frc);
        float advance = subLayout.getAdvance();
        //Fill the background rectangle with a gradient.
        GradientPaint gradient = new GradientPaint((float) x, 0, c1, (float) (x + advance), 0, c2);

        g2.setPaint(gradient);

        Rectangle2D bounds = mLayout.getBounds();
        Rectangle2D back = new Rectangle2D.Double(x, 0, advance, bounds.getHeight());
        g2.fill(back);

        // Draw the string over the gradient rectangle.
        g2.setPaint(Color.white);
        g2.setFont(mFont);
        g2.drawString(s, (float) x, (float) -bounds.getY());

        return advance;
    }//final método drawBoxedString

}//final class ShowOff
