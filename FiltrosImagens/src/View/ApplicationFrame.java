/*
 * Curso Java Graphics2d
 * Praia Grande - SP
 * 14-01-2017
 */
package View;

//import java.awt.*;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//import java.awt.event.*;

/**
 *
 * @author Décio Antonio Carvalho Twitter - @D4ntcar
 */
public class ApplicationFrame
        extends Frame {

    public ApplicationFrame() {
        this("ApplicationFrame v1.0");

    }

    public ApplicationFrame(String title) {
        super(title);
        createUI();
    }

    public void createUI() {
        setSize(500, 400);
        center();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }

        });
    }

    public void center() {
        int openFrameCount = 0; //teste
        int xOffset = 30, yOffset = 30; //teste
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();

        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;

        setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
        setLocation(new Point((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.width) / 2));
        setLocation(x, y);
    }

}//final class ApplicationFrame
