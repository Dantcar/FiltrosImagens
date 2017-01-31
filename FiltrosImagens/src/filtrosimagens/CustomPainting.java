package filtrosimagens;


import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Décio
 */
public class CustomPainting {
    public static void main(String[] args) throws IOException {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
               final JCanvas canvas = new JCanvas();
               JFrame frame = new JFrame();
              frame.setSize(new Dimension(500, 500));
              frame.add(canvas);
              frame.setVisible(true);
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

              Timer timer = new Timer(200, canvas);
              timer.start();
              new Thread()
              {
                  public void run()
                  {
                      try {
                          canvas.buffImg = ImageIO.read(new URL("http://images6.fanpop.com/image/photos/33400000/Cute-Panda-beautiful-pictures-33434826-500-500.jpg"));
                          JCanvas.imageLoading = false;
                      } catch (IOException ex) {
                          Logger.getLogger(CustomPainting.class.getName()).log(Level.SEVERE, null, ex);
                      }
                  }
              }.start();
            }
        });
    } 
}
