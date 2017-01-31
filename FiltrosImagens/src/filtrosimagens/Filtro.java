package filtrosimagens;


import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import java.awt.image.ImageProducer;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Décio
 */
 //Image im = makeColorTransparent(new ImageIcon(screenShot).getImage(), Color.BLACK);
public class Filtro {

    static boolean sFlagServer = true;

    //método de aplicação do filtro escala de cinza
    //recebe como parâmetro uma imagem
    //método de aplicação do filtro escala de cinza
    //recebe como parâmetro uma imagem
    public static BufferedImage escalaDeCinza(BufferedImage imagem) {
        //pegar largura e altura da imagem
        int width = imagem.getWidth();
        int height = imagem.getHeight();

        int media = 0;
        //laço para varrer a matriz de pixels da imagem
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {//rgb recebe o valor RGB do pixel em questão                
                int rgb = imagem.getRGB(i, j);
                int r = (int) ((rgb & 0x00FF0000) >>> 16); //R
                int g = (int) ((rgb & 0x0000FF00) >>> 8);  //G
                int b = (int) (rgb & 0x000000FF);       //B

                //media dos valores do RGB
                //será o valor do pixel na nova imagem
                media = (r + g + b) / 3;

                //criar uma instância de Color
                Color color = new Color(media, media, media);
                //setar o valor do pixel com a nova cor
                imagem.setRGB(i, j, color.getRGB());
            }
        }
        return imagem;
    }

    public static BufferedImage negativo(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);//a cor inversa é dado por 255 menos o valor da cor                 
                int r = 255 - (int) ((rgb & 0x00FF0000) >>> 16);
                int g = 255 - (int) ((rgb & 0x0000FF00) >>> 8);
                int b = 255 - (int) (rgb & 0x000000FF);
                Color color = new Color(r, g, b);
                image.setRGB(i, j, color.getRGB());
            }
        }
        return image;
    }

    public static BufferedImage threshold(BufferedImage image, int limiar) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int r = (int) ((rgb & 0x00FF0000) >>> 16);
                int g = (int) ((rgb & 0x0000FF00) >>> 8);
                int b = (int) (rgb & 0x000000FF);
                int media = (r + g + b) / 3;
                Color white = new Color(255, 255, 255);
                Color black = new Color(0, 0, 0);
                //como explicado no artigo, no threshold definimos um limiar,
                //que é um valor "divisor de águas"
                //pixels com valor ABAIXO do limiar viram pixels PRETOS,
                //pixels com valor ACIMA do limiar viram pixels BRANCOS
                if (media < limiar) {
                    image.setRGB(i, j, black.getRGB());
                } else {
                    image.setRGB(i, j, white.getRGB());
                }
            }
        }
        return image;
    }

    /**
     *
     * @param inImage
     * @return
     */
    public static BufferedImage color2sepia(BufferedImage inImage) {

        int width = inImage.getWidth();
        int height = inImage.getHeight();
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(inImage.getRGB(j, i));
                int red = (int) (c.getRed());
                int green = (int) (c.getGreen());
                int blue = (int) (c.getBlue());
                Color newColor = new Color(
                        (red * .393f) + (green * .769f) + (blue * .189f),
                        (red * .349f) + (green * .686f) + (blue * .168f),
                        (red * .272f) + (green * .534f) + (blue * .131f)
                );
                outImage.setRGB(j, i, newColor.getRGB());
            }
        }
        return (outImage);
    }//final BufferedImage color2sepia

    /**
     * Sepia
     *
     * @param source
     * @return
     */
    public static BufferedImage mainSepia(BufferedImage source) {

        //get width and height of the image
        int width = source.getWidth();
        int height = source.getHeight();

        Color colorImagen = null;

        //convert to sepia
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = source.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                //calculate tr, tg, tb
                int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);

                /*
                 //check condition
                 if (tr > 255) {
                 r = 255;
                 } else {
                 r = tr;
                 }

                 if (tg > 255) {
                 g = 255;
                 } else {
                 g = tg;
                 }

                 if (tb > 255) {
                 b = 255;
                 } else {
                 b = tb;
                 }

                 */
                colorImagen = new Color((tr > 255) ? 255 : tr, (tg > 255) ? 255 : tg, (tb > 255) ? 255 : tb); //substitui o código acima
                //set new RGB value
                // p = (a << 24) | (r << 16) | (g << 8) | b;

                //source.setRGB(x, y, p);
                source.setRGB(x, y, colorImagen.getRGB());
            }
        }
        // destination = 
        //write image
        return source;

    }//final BufferedImage mainSepia3

    /**
     *
     * @param im
     * @param color
     * @return
     */
    public static Image makeColorTransparent(Image im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            public int markerRGB = color.getRGB() | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                Color c = new Color(rgb);

                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                //if(red<140 && green<140 && blue<140)
                {
                    int alpha = Math.max(Math.max(red, green), Math.max(green, blue)) * 3;
                    c = new Color(red, green, blue, alpha > 255 ? 255 : alpha);
                }

                return c.getRGB();
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }//final Image makeColorTransparent

    /**
     *
     * @param image
     * @return
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded 
        image = new ImageIcon(image).getImage();

        BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

    /**
     *
     * @param i
     * @return
     */
    public static BufferedImage getTransparentIcon(BufferedImage i) {
        if (i == null) {
            return null;
        }
        final int t = i.getRGB(0, i.getHeight() - 1) & 0x00FFFFFF;
        ImageFilter filter = new RGBImageFilter() {
            public int filterRGB(int x, int y, int rgb) {
                if ((rgb & 0x00FFFFFF) == t) {
                    return t;
                }
                return rgb;
            }
        };

        ImageProducer ip = new FilteredImageSource(i.getSource(), filter);
        return toBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));

    }//final BufferedImage getTransparentIcon

    /**
     * Método thresholdInv
     *
     * @param teste
     * @param i
     * @return
     */
    static BufferedImage thresholdInv(BufferedImage image, int limiar) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int r = (int) ((rgb & 0x00FF0000) >>> 16);
                int g = (int) ((rgb & 0x0000FF00) >>> 8);
                int b = (int) (rgb & 0x000000FF);
                int media = (r + g + b) / 3;
                Color white = new Color(255, 255, 0);
                Color black = new Color(0, 255, 0);
                //como explicado no artigo, no threshold definimos um limiar,
                //que é um valor "divisor de águas"
                //pixels com valor ABAIXO do limiar viram pixels PRETOS,
                //pixels com valor ACIMA do limiar viram pixels BRANCOS
                if (media < limiar) {
                    image.setRGB(i, j, black.getRGB());
                } else {
                    image.setRGB(i, j, white.getRGB());
                }
            }
        }
        return image;

    }

    public static BufferedImage screenShot(BufferedImage teste) {
        BufferedImage screnShot = teste;
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy hh mm ss a");
        Robot robot;
        try {
            robot = new Robot();
            screnShot
                    = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException ex) {
            Logger.getLogger(Filtro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (screnShot);
    }

    /**
     * Metodo filtro para
     *
     * @param image
     * @param limiar
     * @return
     */
    static BufferedImage mainSegmentoCor(BufferedImage image, int limiar) {

        BufferedImage bi = null;
        int width = image.getWidth();
        int height = image.getHeight();
        bi = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        Color colorImagen = null, colorAnt = null, colorSig = null;

        int tmp0, tmp1, tmp2, tolerancia = limiar; //original 30

        for (int i = 0; i < width; i++) {

            for (int j = 1; j < (height - 1); j++) {

                colorImagen = new Color(image.getRGB(i, j));
                tmp1 = (colorImagen.getRed() + colorImagen.getGreen() + colorImagen.getBlue()) / 3;

                colorAnt = new Color(image.getRGB(i, j - 1));
                tmp0 = (colorAnt.getRed() + colorAnt.getGreen() + colorAnt.getBlue()) / 3;

                colorSig = new Color(image.getRGB(i, j + 1));
                tmp2 = (colorSig.getRed() + colorSig.getGreen() + colorSig.getBlue()) / 3;

                if ((Math.abs(tmp1 - tmp0) > tolerancia) && (Math.abs(tmp2 - tmp1) < tolerancia)) {
                    colorImagen = new Color(0, 220, 220);

                } else {
                    colorImagen = new Color(255, 255, 0);
                }

                image.setRGB(i, j, colorImagen.getRGB());
            }
        }
        return image;

    }//final método mainSegmentoCor

    static BufferedImage mainRotation(BufferedImage image, int limiar) {
        limiar = limiar * 2;
        BufferedImage bi = null;
        int width = image.getWidth();
        int height = image.getHeight();
        bi = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.PI / -(limiar), image.getWidth() / 2, image.getHeight() / 2);
        System.out.println("Angulo: " + tx);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
        Color colorImagen = null, colorAnt = null, colorSig = null;
        image = op.filter(image, bi);

        return bi;

    }//final método mainRotation

    /**
     *
     * @param input
     * @param angle
     * @return
     */
    public static BufferedImage rotate(BufferedImage input, double angle) {

        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage output = new BufferedImage(width, height, input.getType());

        double a0 = Math.cos(angle * Math.PI / 180);
        double b0 = Math.sin(angle * Math.PI / 180);
        double a1 = -b0, b1 = a0;

        int rx, ry;

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {

                rx = (int) Math.round(a0 * x + a1 * 1);
                ry = (int) Math.round(b0 * x + b1 * y);

                if (rx >= 0 && rx < width && ry >= 0 && ry < height) {
                    output.setRGB(rx, ry, input.getRGB(x, y));
                }

            }
        }

        return output;
    }//final método rotate

    /**
     *
     * @param input
     * @param angle
     * @return
     */
    public static BufferedImage rotate1(BufferedImage input, double angle) {
        AffineTransform rotation
                = AffineTransform.getRotateInstance(-Math.PI / angle);
        BufferedImageOp rotateOp
                = new AffineTransformOp(rotation, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = rotateOp.filter(input, null);

        BufferedImage output = rotatedImage;

        return output;
    }//final método rotate

    /**
     *
     * @param input
     * @param angle
     * @return
     */
    public static BufferedImage rotate2(BufferedImage input, double angle) {
        AffineTransform rotation
                = AffineTransform.getRotateInstance(angle);
        // Rectangle2D box = GeomTools.
        BufferedImageOp rotateOp
                = new AffineTransformOp(rotation, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = rotateOp.filter(input, null);

        BufferedImage output = rotatedImage;

        return output;
    }//final método rotate

    /**
     *
     * @param image
     * @param transformation
     * @return
     */
    public static Rectangle2D getBoundingBox(
            BufferedImage image, AffineTransform transformation) {

        //Apply transformation to image corners
        int xmax = image.getWidth() - 1;
        int ymax = image.getHeight() - 1;

        Point2D.Double[] corners = new Point2D.Double[4];
        corners[0] = new Point2D.Double(0, 0);
        corners[1] = new Point2D.Double(xmax, 0);
        corners[2] = new Point2D.Double(xmax, ymax);
        corners[3] = new Point2D.Double(0, ymax);

        //Calcutate bouding box of transformed corner points
        Rectangle2D boundingBox = new Rectangle2D.Double();

        for (int i = 0; i < 4; ++i) {
            boundingBox.add(corners[i]);
        }
        return boundingBox;
    }//final método getBoundingBox

    /**
     * Servidor imagens
     *
     * @param args
     */
    public BufferedImage mainServer(BufferedImage source) {

        int width = source.getWidth();
        int height = source.getHeight();
        BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        ServerSocket server = null;

        Socket socket;

        try {

            if (sFlagServer) {
                server = new ServerSocket(4000);
                sFlagServer = false;
            }
            System.out.println("Server Waiting for image");

            socket = server.accept();

            System.out.println("Client connected. ");

            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);

            int len = dis.readInt();

            System.out.println("Image Size: " + len / 1024 + "KB");

            byte[] data = new byte[len];

            dis.readFully(data);

            dis.close();

            InputStream ian = new ByteArrayInputStream(data);

            bImage = ImageIO.read(ian);
            source = bImage;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception: " + ex.getMessage());
        }

        Client cl = new Client();
        return bImage;
    }

    public void mainClient(String args[]) {
        //mainClient("D:\\TwitterD4ntcar.png","Imagem from Client"); //comando
        Socket soc = null;
        BufferedImage img = null;
        String filename = "D:\\TwitterD4ntcar.png";
        String message = "Imagem from Client";

        try {

            //int split = 4;
            if (args.length > 0) {
                filename = args[0];
            }

            if (args.length > 1) {
                message = args[1];
            }

            /*
             if (args.length > 2) {
             split = Integer.parseInt(args[2]);
             }
             */
            soc = new Socket("localhost", 4000);
            System.out.println("Reading image from disk. ");
            img = ImageIO.read(new File("digital_image_processing.jpg"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(img, "jpg", baos);

            baos.flush();

            byte[] bytes = baos.toByteArray();

            System.out.println("Sending image to server. ");

            OutputStream out = soc.getOutputStream();

            DataOutputStream dos = new DataOutputStream(out);

            dos.writeInt(bytes.length);
            dos.write(bytes, 0, bytes.length);

            System.out.println("Image sent to server. ");

            dos.close();
            out.close();
            soc.close();
        } catch (Exception e) {
            System.out.println("Método Client Exception: " + e.getMessage());

        }

    }// final método mainClient

    /**
     * Filtro 3 cores básicas vermelho, verde, azul
     *
     * @param img
     * @param cor
     * @return
     */
    public static BufferedImage filter(BufferedImage img, int cor) {
        BufferedImage ans = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int choice = (cor % 4) + 1;
        System.out.println(choice);
        int graylvl;

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                switch (choice) {
                    case 1:
                        graylvl = (img.getRGB(x, y) & 0xff);
                        ans.setRGB(x, y, graylvl);
                        break;
                    case 2:
                        graylvl = (img.getRGB(x, y) & 0xff00);
                        ans.setRGB(x, y, graylvl);
                        break;
                    case 3:
                        graylvl = (img.getRGB(x, y) & 0xff0000);
                        ans.setRGB(x, y, graylvl);
                        break;
                    case 4:
                        graylvl =  (img.getRGB(x, y) & 0xffff00);
                                

                        ans.setRGB(x, y, graylvl);
                        break;
                }
            }
        }
        return ans;
    }//final método filter

    /**
     *
     * @param img
     * @param dm
     * @return
     */
    public static BufferedImage orderedDither(BufferedImage img, int[][] dm) {
        BufferedImage ans = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        int i, j;
        int n = dm.length;
        int intensityLevel;

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                intensityLevel = (int) ((img.getRGB(x, y) & 0xff) * ((n * n) / 256));
                i = x % n;
                j = y % n;

                if (intensityLevel > dm[i][j]) {
                    ans.setRGB(x, y, 0xffffff);
                } else {
                    ans.setRGB(x, y, 0x000000);
                }
            }
        }
        return ans;

    }//final método orderedDither

    public static BufferedImage rodarImage(BufferedImage img) {
        int fil = img.getWidth();
        int col = img.getHeight();

        BufferedImage matNueva = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_BGR);

        int contFil = 0;
        int contCol = 0;

        for (int i = 0; i<fil; i++) {
            contCol = 0;
            for (int j = 0; j<col; j++) {
                
                int rgb = img.getRGB(i, j);
                int r = (int) ((rgb & 0x00FF0000) >>> 16); //R
                int g = (int) ((rgb & 0x0000FF00) >>> 8);  //G
                int b = (int) (rgb & 0x000000FF);       //B
                Color cor = new Color(g,0,g);
                //    Color cor = new Color(img.getRGB(i, j));
                    /**
                     *  
                     * int rgb = imagem.getRGB(i, j);
                int r = (int) ((rgb & 0x00FF0000) >>> 16); //R
                int g = (int) ((rgb & 0x0000FF00) >>> 8);  //G
                int b = (int) (rgb & 0x000000FF);       //B

                //media dos valores do RGB
                //será o valor do pixel na nova imagem
                media = (r + g + b) / 3;

                //criar uma instância de Color
                Color color = new Color(media, media, media);
                //setar o valor do pixel com a nova cor
                imagem.setRGB(i, j, color.getRGB());
                     */
                    
                    
                    //int iCor = (int)((cor.getRed() + cor.getGreen()) + cor.getGreen())/4;
                    matNueva.setRGB(i, j, cor.getRGB());
                  //  contCol++;
                
               //  System.out.println(contCol +","+contFil);
            }

           // contFil++;
           
            //System.out.println(contFil);
        }
        return matNueva;
    }//rotarCuadradod90Grados

    public int[][] rotarCuadrado90Grados(int[][] matOriginal) {
        int fil = matOriginal.length;
        int col = matOriginal[0].length;
        int contFil = 0;
        int contCol = 0;
        int[][] matNueva = new int[fil][col];
        for (int i = fil; i >= 0; i--) {
            contCol = 0;
            for (int j = col; j >= 0; j--) {
                matNueva[contFil][contCol] = matOriginal[i][j];
                contCol++;
            }
            contFil++;
        }
        return matNueva;
    }//rotarCuadradod90Grados

}//Final class Filtro
