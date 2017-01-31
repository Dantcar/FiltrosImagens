package filtrosimagens;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Random;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Décio
 */
class JCanvas extends JPanel implements ActionListener {

    public BufferedImage buffImg;
    public Rectangle rectangle;
    Random random;
    long lastTimeChanged;
    int dirX = 1, dirY = 1;
    volatile static boolean imageLoading = true;

    public JCanvas() {
        random = new Random();
        rectangle = new Rectangle(50, 50, 150, 150);//colocar parametros no método 
        lastTimeChanged = System.currentTimeMillis();
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imageLoading) {
            showWaitForLoading(g);
            return;
        }

        g.clipRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.drawImage(buffImg, 0, 0, getWidth(), getHeight(), this);

    }

    public void showWaitForLoading(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRoundRect(getWidth() / 2 - 100, getHeight() / 2 - 15, 200, 30, 30, 30);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Loading image...", getWidth() / 2 - 45, getHeight() / 2 + 3);
        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        long endTime = System.currentTimeMillis();
        if (endTime - lastTimeChanged > 500) {
            dirX = random.nextInt(2) == 0 ? -1 : 1;
            dirY = random.nextInt(2) == 0 ? -1 : 1;
            lastTimeChanged = endTime;
        }

        if (rectangle.x < 0) {
            dirX = 1;
        } else if (rectangle.x + rectangle.width > getWidth()) {
            dirX = -1;
        }

        if (rectangle.y < 0) {
            dirY = 1;
        } else if (rectangle.y + rectangle.height > getHeight()) {
            dirY = -1;
        }

        rectangle.x = rectangle.x + dirX * 10;
        rectangle.y = rectangle.y + dirY * 10;;

        repaint();
    }
    
}
