package filtrosimagens;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Décio
 */
public class PutFiltro {
//private String[] foto = {};

    private static String[] foto = carregaDiretorio("C:\\Users\\Décio\\Documents\\__POO2016\\");
    private static String[] foto1 = {"C:\\Users\\Décio\\Documents\\__POO2016\\foto.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\dac.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\foto.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\dac.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\foto.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\dac.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\foto.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\dac.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\foto.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\dac.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\foto.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\dac.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\foto.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\dac.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\foto.jpg",
        "C:\\Users\\Décio\\Documents\\__POO2016\\foto.jpg",};

    public PutFiltro() {

        try {

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        } catch (Exception e) {
            System.out.println("class PutFiltro - Exception: " + e.getMessage());
        }

    }

    public static String[] carregaDiretorio(String path) {

        /*
         String dirname = "/java";
    File f1 = new File(dirname);
    FilenameFilter only = new OnlyExt("html");
    String s[] = f1.list(only);

    for (int i = 0; i < s.length; i++) {
      System.out.println(s[i]);
    }
         */
        
        //String dirname = "";
        File f1 = new File(path);
        FilenameFilter only = new OnlyExt("gif");
        String s[] = f1.list(only);
       
        return s;
    }

    /**
     * método 0 - mainBorda função colocar uma borda na imagem
     *
     * @param source
     * @return
     */
    public static Mat mainBorda(Mat source, int vl) {
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        try {

            //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            /*
             Mat source = Highgui.imread("D:\\teste.png",
             Highgui.CV_LOAD_IMAGE_COLOR);
             */
            int top, bottom, left, right;

            int borderType;

            //initialize arguments for the filter border
            top = (int) (0.05 * source.rows());
            bottom = (int) (0.05 * source.rows());

            left = (int) (0.05 * source.cols());
            right = (int) (0.05 * source.cols());

            destination = source;

            switch (vl%9) {
                case 1:
                    Imgproc.copyMakeBorder(source, destination, top, bottom, left, right, Imgproc.BORDER_WRAP); //borda com a imagem
                    break;
                case 2:
                    Imgproc.copyMakeBorder(source, destination, top, bottom, left, right, Imgproc.BORDER_CONSTANT); //borda preta
                    break;
                case 3:
                    Imgproc.copyMakeBorder(source, destination, top, bottom, left, right, Imgproc.BORDER_DEFAULT); //borda transparente
                    break;
                case 4:
                    Imgproc.copyMakeBorder(source, destination, top, bottom, left, right, Imgproc.BORDER_ISOLATED); // borda escura??
                    break;
                case 5:
                    Imgproc.copyMakeBorder(source, destination, top, bottom, left, right, Imgproc.BORDER_REFLECT); //borda com reflexo da própria imagem
                    break;
                case 6:
                    Imgproc.copyMakeBorder(source, destination, top, bottom, left, right, Imgproc.BORDER_REFLECT101);
                    break;
                case 7:
                    Imgproc.copyMakeBorder(source, destination, top, bottom, left, right, Imgproc.BORDER_REFLECT_101);
                    break;
                case 8:
                    Imgproc.copyMakeBorder(source, destination, top, bottom, left, right, Imgproc.BORDER_REPLICATE); //borda efeito movimento
                    break;
                case 9:
                    Imgproc.copyMakeBorder(source, destination, top, bottom, left, right, Imgproc.BORDER_TRANSPARENT); //borda não funciona
                    break;
                default:
                    Imgproc.copyMakeBorder(source, destination, top, bottom, left, right, Imgproc.BORDER_WRAP); //borda com a imagem
                    break;
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return destination;
    }//final métodomainBorda

    /**
     * método 1 - mainGrayScale função transformar a imagem em tons de cinzas
     *
     * @param source
     * @return
     */
    public static Mat mainGray2(Mat source) {
        //BufferedImage image = TelaTakeFoto.createBufferedImage(source);

        BufferedImage image = mat2Img(source);

        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        // Mat destination;
        //destination = source;
        try {
            int width = image.getWidth();
            int heigth = image.getHeight();
            for (int i = 0; i < heigth; i++) {
                for (int j = 0; j < width; j++) {

                    Color c = new Color(image.getRGB(j, i));

                    int red = (int) c.getRed();

                    int green = (int) c.getGreen();

                    int blue = (int) c.getBlue();

                    Color newColor = new Color((red + green + blue) / 3);

                    image.setRGB(j, i, newColor.getRGB());
                }
            }
            destination = img2Mat(image);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return destination;

    }//final Mat mainGray2

    /**
     * Método img2Mat : BufferedImage to Mat
     *
     * @param in
     * @return
     */
    public static Mat img2Mat(BufferedImage in) {

        Mat out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC4);

        byte[] data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];

        int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());

        for (int i = 0; i < dataBuff.length; i++) {

            data[i * 3] = (byte) ((dataBuff[i]));

            data[i * 3 + 1] = (byte) ((dataBuff[i]));

            data[i * 3 + 2] = (byte) ((dataBuff[i]));

        }

        out.put(0, 0, data);
        return out;
    }//Final método img2Mat

    /**
     * Método mat2Img BufferedImage to Mat
     *
     * @param in
     * @return
     */
    public static BufferedImage mat2Img(Mat in) {
        int h = in.height();
        int w = in.width();

        BufferedImage out;
        byte[] data = new byte[w * h * (int) in.elemSize()];
        //int totalBytes = (int) (in.total() * in.elemSize());
        //byte data[] = new byte[totalBytes];

        int type;
        in.get(0, 0, data);

        if (in.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        out = new BufferedImage(w, h, type);

        out.getRaster().setDataElements(0, 0, w, h, data);
        return out;
    } //final mat2Img

    public static Mat imgMat(BufferedImage in) {
        Mat out;
        byte[] data;
        int r, g, b;

        if (in.getType() == BufferedImage.TYPE_INT_RGB) {
            out = new Mat(240, 320, CvType.CV_8UC3);
            data = new byte[320 * 240 * (int) out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, 320, 240, null, 0, 320);
            for (int i = 0; i < dataBuff.length; i++) {
                data[i * 3] = (byte) ((dataBuff[i] >> 16) & 0xFF);
                data[i * 3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                data[i * 3 + 2] = (byte) ((dataBuff[i] >> 0) & 0xFF);
            }
        } else {
            out = new Mat(240, 320, CvType.CV_8UC1);
            data = new byte[320 * 240 * (int) out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, 320, 240, null, 0, 320);
            for (int i = 0; i < dataBuff.length; i++) {
                r = (byte) ((dataBuff[i] >> 16) & 0xFF);
                g = (byte) ((dataBuff[i] >> 8) & 0xFF);
                b = (byte) ((dataBuff[i] >> 0) & 0xFF);
                data[i] = (byte) ((0.21 * r) + (0.71 * g) + (0.07 * b)); //luminosity
            }
        }
        out.put(0, 0, data);
        return out;
    }//final Mat imgMat

    /**
     *
     * @param source
     * @return
     */
    public static Mat mainGray(Mat source) {
        Mat destination = source;

        try {

            // destination = new Mat(source.rows(), source.cols(), source.type());
            Imgproc.cvtColor(destination, source, Imgproc.COLOR_BGR2GRAY);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return destination;
    }//final mainGray

    /**
     *
     * @param source
     * @param vl
     * @return
     */
    public static Mat mainGaussian(Mat source, int vl) {
        Mat destination = source;

        try {

            destination = new Mat(source.rows(), source.cols(), source.type());

            Imgproc.GaussianBlur(source, destination, new Size(45, 45), vl);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return destination;
    }//final Mat mainGaussian

    /**
     *
     * @param source
     * @return
     */
    public static Mat mainThresholding(Mat source) {
        Mat destination = source;

        try {
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_TOZERO);
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_BINARY);
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_BINARY_INV);
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_TOZERO_INV);
            Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_TRUNC);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return destination;
    }//final Mat mainThresholding

    /**
     *
     * @param source
     * @return
     */
    public static Mat mainThresholdingInv(Mat source) {
        Mat destination = source;

        try {
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_TOZERO);
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_BINARY);
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_BINARY_INV);
            //Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_TOZERO_INV);
            Imgproc.threshold(source, destination, 125, 255, Imgproc.THRESH_TRUNC);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return destination;
    }//final Mat mainThresholding

    /**
     *
     * @param source
     * @param flag
     * @return
     */
    public static Mat mainPyramids(Mat source, int flag, int vl) {
        Mat destination = source;

        if (vl % 2 == 0) {
            vl = 2;
        } else {
            vl = 1;
        }

        try {

            if (flag == 0) {
                Imgproc.pyrUp(source, destination,
                        new Size(source.cols() * vl, source.rows() * vl), Imgproc.BORDER_DEFAULT);
            } else {
                Imgproc.pyrDown(source, destination, new Size(source.cols() / vl, source.rows() / vl), Imgproc.BORDER_DEFAULT);
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return destination;
    }//final mainPyramids

    /**
     *
     * @param source
     * @return
     */
    public static Mat mainDilation(Mat source, int vl) {
        Mat destination = source;
        try {

            int dilation_size = vl;
            //int erosion_size = 5;
            //int dilation_size = 8;

            Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                    new Size(2 * dilation_size + 1, 2 * dilation_size + 1));

            Imgproc.dilate(source, destination, element);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return destination;
    }// final mainDilation

    /**
     *
     * @param source
     * @return
     */
    public static Mat mainEroding(Mat source, int vl) {
        Mat destination = source;
        try {

            int erosion_size = vl;
            //int erosion_size = 4;
            //int dilation_size = 5;

            Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                    new Size(2 * erosion_size + 1, 2 * erosion_size + 1));

            Imgproc.erode(source, destination, element);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return destination;
    }//final mainEroding

    /**
     *
     * @param source
     * @param vl
     * @return
     */
    public static Mat mainContraste(Mat source, int vl) {

        double alpha = 2;
        //double alpha = (double)vl;
        double beta = 50;
        Mat destination = source;

        try {

            Imgproc.equalizeHist(source, destination);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return destination;
    }// final mainContraste

    /**
     *
     * @param source
     * @param vl
     * @return
     */
    public static Mat mainGrayDac(Mat source, int vl) {
        // Mat destination = source;
        vl = 3;
        int totalBytes = (int) (source.total() * source.elemSize());

        byte buffer[] = new byte[totalBytes];

        source.get(0, 0, buffer);

        for (int i = 0; i < totalBytes; i++) {
            if (i % vl == 0) {
                buffer[i] = 0;
            }
        }
        source.put(0, 0, buffer);
        //source = openFile("D:\\Box.jpg");

        return source;

    }//final mainGrayDac

    /**
     *
     * @param source
     * @param vl
     * @return
     */
    public static Mat mainTeste(Mat source, int vl) {
        // Mat destination = source;

        vl = 3;
        int totalBytes = (int) (source.total() * source.elemSize());
        byte buffer[] = new byte[totalBytes];
        source.get(0, 0, buffer);

        for (int i = 0; i < totalBytes; i++) {
            if (i % vl == 0) {
                buffer[i] = 0;
            }
        }
        source.put(0, 0, buffer);
        //source = openFile("D:\\TwitterD4ntcar.png");

        return source;

    }//final mainTeste/**

    /* Método para colocar uma foto no lugar da imagem da webcam
     * @param source
     * @param vl
     * @return
     */
    public static Mat mainFoto(Mat source, int vl) {
        // Mat destination = source;
       // foto = carregaDiretorio("C:\\Users\\Décio\\Documents\\__POO2016");
        
        System.out.println(foto);
        // vl = 3;
        return source = openFile(foto[vl]);

    }//final mainFoto

    /**
     *
     * @param source
     * @param vl
     * @return
     */
    public static Mat mainTeste1(Mat source, int vl) {
        // Mat destination = source;
        vl = 3;
        int totalBytes = (int) (source.total() * source.elemSize());
        byte buffer[] = new byte[totalBytes];
        source.get(0, 0, buffer);

        for (int i = 0; i < totalBytes; i++) {
            if (i % vl == 0) {
                buffer[i] = 0;
            }
        }
        source.put(0, 0, buffer);

        return source;

    }//final mainTeste1

    /**
     *
     * @param source
     * @return
     */
    public static Mat mainBox(Mat source, int vl) {

        Mat destination = source;

        try {

            int kernelSize = vl; //ao aumentar o valor a imagem fica mais embassada

            Mat kernel = Mat.ones(kernelSize, kernelSize, CvType.CV_32F);

            for (int i = 0; i < kernel.rows(); i++) {

                for (int j = 0; j < kernel.cols(); j++) {

                    double[] m = kernel.get(j, j);

                    for (int k = 0; k < m.length; k++) {
                        m[k] = m[k] / (kernelSize * kernelSize);
                    }
                    kernel.put(i, j, m);
                }
            }

            Imgproc.filter2D(source, destination, -1, kernel);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return destination;

    }//final 

    /**
     *
     * @param fileName
     * @return
     */
    public static Mat openFile(String fileName) {
        String msg = "";
        Mat newImage = Highgui.imread(fileName);
        try {
            if (newImage.dataAddr() == 0) {
                msg = "Couldn't open file " + fileName;

            }

        } catch (Exception e) {

        }
        return newImage;
    }//final Mat openFile

    /**
     *
     * @param inImage
     * @return
     */
    public static BufferedImage color2gray(BufferedImage inImage) {

        int width = inImage.getWidth();
        int height = inImage.getHeight();
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(inImage.getRGB(j, i));
                int red = (int) (c.getRed() * 0.2126);
                int green = (int) (c.getGreen() * 0.7152);
                int blue = (int) (c.getBlue() * 0.0722);
                Color newColor = new Color(red + green + blue,
                        red + green + blue, red + green + blue);
                outImage.setRGB(j, i, newColor.getRGB());
            }
        }//final BufferedImage color2gray

        return (outImage);
    }// final color2gray

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
     * método - mainSepia2 função transformar a imagem em tons de sepia
     *
     * @param source
     * @return
     */
    public static Mat mainSepia2(Mat source) {
        //BufferedImage image = TelaTakeFoto.createBufferedImage(source);
        Mat destination = source;
        BufferedImage image = mat2Img(source);
        image = color2sepia(image);
        return destination = img2Mat(image);

    }//final Mat mainSepia2

    public static Mat mainSepia3(Mat source) {
        Mat destination = source;
        BufferedImage image = mat2Img(source);
        //read image

        //get width and height of the image
        int width = image.getWidth();
        int height = image.getHeight();

        //convert to sepia
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                //calculate tr, tg, tb
                int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);

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

                //set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;

                image.setRGB(x, y, p);
            }
        }
        // destination = 
        //write image
        return destination = img2Mat(image);

    }//class ends here

    /**
     * Sepia
     *
     * @param source
     * @return
     */
    public static BufferedImage mainSepia3(BufferedImage source) {

        //get width and height of the image
        int width = source.getWidth();
        int height = source.getHeight();

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

                //set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;

                source.setRGB(x, y, p);
            }
        }
        // destination = 
        //write image
        return source;

    }//final BufferedImage mainSepia3

    /**
     * método - mainSepia2 função transformar a imagem em tons de sepia
     *
     * @param source
     * @return
     */
    public static Mat mainNegativo(Mat source) {
        //BufferedImage image = TelaTakeFoto.createBufferedImage(source);
        //Mat destination = source;
        Filtro filtro = new Filtro();
        BufferedImage image = mat2Img(source);
        image = filtro.negativo(image);
        return source = img2Mat(image);

    }//final Mat mainSepia2

    public static Mat img3Mat(BufferedImage in) {
        Mat out;
        byte[] data;
        int r, g, b;

        int x = in.getWidth();
        int y = in.getHeight();

        if (in.getType() == BufferedImage.TYPE_INT_RGB) {
            out = new Mat(y, x, CvType.CV_8UC3);
            data = new byte[x * y * (int) out.elemSize()];

            int[] dataBuff = in.getRGB(0, 0, x, y, null, 0, x);

            for (int i = 0; i < dataBuff.length; i++) {
                data[i * 3] = (byte) ((dataBuff[i] >> 16) & 0xFF);
                data[i * 3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                data[i * 3 + 2] = (byte) ((dataBuff[i] >> 0) & 0xFF);
            }
        } else {
            out = new Mat(y, x, CvType.CV_8UC1);
            data = new byte[x * y * (int) out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, x, y, null, 0, x);
            for (int i = 0; i < dataBuff.length; i++) {
                r = (byte) ((dataBuff[i] >> 16) & 0xFF);
                g = (byte) ((dataBuff[i] >> 8) & 0xFF);
                b = (byte) ((dataBuff[i] >> 0) & 0xFF);
                data[i] = (byte) ((0.21 * r) + (0.71 * g) + (0.07 * b)); //luminosity
            }
        }
        out.put(0, 0, data);
        return out;
    }//final Mat img3Mat
}
