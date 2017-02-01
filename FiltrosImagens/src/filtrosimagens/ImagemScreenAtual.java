package filtrosimagens;


import java.awt.AWTException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferByte;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Décio
 */
public class ImagemScreenAtual extends Component implements ActionListener {

    //  private javax.swing.JFileChooser jFileChooserFoto; //caminho completo para o elemento JFileChooser
    //  private javax.swing.JButton cmdChooserPicture; //caminho completo para o elemento JButton
    String descs[] = {
        "Original",
        "Convolve : LowPass",
        "Convolve : Sharpen",
        "Blur",
        "LookupOp",};

    
    String comandos[] = {
        "Escolher Fotografia",
        "Sair do Programa",};

    int opIndex;
    private BufferedImage bi, biFiltered;

    public static String pathProjeto, iconPetfast;
    
    private static int openFrameCount = 0; //teste
    private static final int xOffset = 30, yOffset = 30; //teste

    int w, h;

    //Blurring
    public static final float ninth = 1.0f / 9.0f;
    public static final float[] BLURKERNEL = { //
        ninth, ninth, ninth,
        ninth, ninth, ninth,
        ninth, ninth, ninth,
    };
   
    
    
    public static final float[] SHARPEN3X3 = { //sharpening filter kernel
        0.f, -1.f, 0.f,
        -1.f, 5.f, -1.f,
        0.f, -1.f, 0.f

    };

    public static final float[] BLUR3X3 = { //low-pass filter kernel
        0.1f, 0.1f, 0.1f,
        0.1f, 0.2f, 0.1f,
        0.1f, 0.1f, 0.1f
    };

    /**
     * Método construtor ImagemScreenAtual
     */
    public ImagemScreenAtual() {

        JFrame f = new JFrame("Image Example");

        ImagemScreenAtual si = new ImagemScreenAtual();
        f.add("Center", si);

        //
        JComboBox choices = new JComboBox(si.getDescriptions());
        choices.setActionCommand("SetFilter");
        choices.addActionListener(si);

        //  
        JComboBox formats = new JComboBox(si.getFormats());
        formats.setActionCommand("Formats");
        formats.addActionListener(si);

        //
        JComboBox opcao = new JComboBox(si.getComando());
        opcao.setActionCommand("Escolher");
        opcao.addActionListener(si);

        //
        JPanel panel = new JPanel();
        panel.add(opcao);
        panel.add(choices);
        panel.add(new JLabel("Salvar como"));
        panel.add(formats);

        f.add("South", panel);
        f.pack();
        f.setVisible(true);

        f.setTitle("Faça sua Fotografia de Cadastro");
        pathProjeto = System.getProperty("user.dir") + "//";
        iconPetfast = pathProjeto + "src//Icones//petfastIcone.png";

        System.out.println(pathProjeto);
        System.out.println(iconPetfast);

        f.setIconImage(Toolkit.getDefaultToolkit().getImage(iconPetfast));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();

        setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
        setLocation(new Point((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.width) / 2));

        //File imagem = new File("imagem");
        final JFileChooser escolhaArquivo = new JFileChooser();
        try {
            int returnVal = escolhaArquivo.showOpenDialog(null);

            if (returnVal == escolhaArquivo.APPROVE_OPTION) {
                File imagem = escolhaArquivo.getSelectedFile();

                //bi = ImageIO.read(new File("f:\\1.png"));
                bi = ImageIO.read(imagem);
                w = bi.getWidth(null);
                h = bi.getHeight(null);
            }

            if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage bi2
                        = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics big = bi2.getGraphics();
                big.drawImage(bi, 0, 0, null);
                biFiltered = bi = bi2;
            }
        } catch (IOException ex) {
            Logger.getLogger(ImagemScreenAtual.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void imagemScreen(File imagem) {
        try {
            //bi = ImageIO.read(new File("f:\\1.png"));
            bi = ImageIO.read(imagem);
            w = bi.getWidth(null);
            h = bi.getHeight(null);
            if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage bi2
                        = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics big = bi2.getGraphics();
                big.drawImage(bi, 0, 0, null);
                biFiltered = bi = bi2;
                repaint();
            }

        } catch (IOException ex) {
            Logger.getLogger(ImagemScreenAtual.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }

    String[] getDescriptions() {
        return descs;
    }

    void setOpIndex(int i) {
        opIndex = i;

    }

    public void paint(Graphics g) {
        filterImage();
        g.drawImage(biFiltered, 0, 0, null);
    }

    public String[] getComando() {
        return comandos;
    }

    public String[] getFormats() {
        String[] formats = ImageIO.getWriterFormatNames();
        TreeSet<String> formatSet = new TreeSet<String>();

        for (String s : formats) {
            formatSet.add(s.toLowerCase());
        }

        return formatSet.toArray(new String[0]);
    }

    /**
     * Método Principal
     * @param args
     * @throws InterruptedException
     * @throws AWTException
     * @throws IOException 
     */
    public static void main(String[] args) throws InterruptedException,
            AWTException, IOException {
        
        pathProjeto = System.getProperty("user.dir") + "//";
        
        ImagemScreenAtual ISA = new ImagemScreenAtual();
        

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // load native library of opencv

       

//Liberamos o contexto.
    }

    public static byte[] GetCurrentScreenImage() throws AWTException,
            IOException {
        Robot robot = new Robot();
        Dimension d = new Dimension();
        d.height = 10;
        d.width = 10;
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(d));
        BufferedImage bgrScreenshot = new BufferedImage(screenShot.getWidth(),
                screenShot.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        boolean done = bgrScreenshot.getGraphics().drawImage(screenShot, 0, 0, null);
        return ((DataBufferByte) bgrScreenshot.getRaster().getDataBuffer())
                .getData();
    }

    public static BufferedImage createBufferedImage(Mat mat) {
        BufferedImage image = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        mat.get(0, 0, data);
        return image;
    }

    static {
        System.loadLibrary("opencv_java2413");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JComboBox cb = (JComboBox) e.getSource();

        if (cb.getActionCommand().equals("SetFilter")) {
            
            setOpIndex(cb.getSelectedIndex());
            repaint();
        }
        else if (cb.getActionCommand().equals("Formats")) { //Combo Box tipo Imagem
            /**
             * Save the filtered image in the sected format. The selected item
             * will be the name of the format to use
             */
            String format = (String) cb.getSelectedItem();

            /**
             * Use the format name to initialise the file suffix. Format names
             * typically corres´pond to suffixes
             */
            File saveFile = new File("savedimage." + format);
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(saveFile);

            int rval = chooser.showSaveDialog(cb);

            if (rval == JFileChooser.APPROVE_OPTION) {
                saveFile = chooser.getSelectedFile();
                try {
                    ImageIO.write(biFiltered, format, saveFile);
                } catch (IOException ex) {
                    System.out.println("Error: " + ex);
                    Logger.getLogger(ImagemScreenAtual.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

       
        } //final ComboBox Tipo Imagem
        else if (cb.getActionCommand().equals("Escolher")) { //ComboBox Escolher
            /**
             * Escolha de Sair do programa
             */
            String comando = (String) cb.getSelectedItem();

            if (comando.equalsIgnoreCase("Sair do Programa")) {
                System.out.println("Tudo Bem!");
                System.exit(0);
            } else if (comando.equalsIgnoreCase("Escolher Fotografia")) {

                final JFileChooser escolhaArquivo;// = new JFileChooser();

            }
        }//final ComboBox Escolher

    }

    int lastOp;

    public void filterImage() {
        BufferedImageOp op = null;

        if (opIndex == lastOp) {
            return;
        }
        lastOp = opIndex;

        switch (opIndex) {

            case 0:
                biFiltered = bi; //* original */
                return;
            case 1: /* low pass filter */

            case 2: /* sharpen */

                float[] data = (opIndex == 1) ? BLUR3X3 : SHARPEN3X3;
                op = new ConvolveOp(new Kernel(3, 3, data),
                        ConvolveOp.EDGE_NO_OP,
                        null);

                break;
            
            case 3: /* Blur */
                
                float[] data_b = BLURKERNEL;
                op = new ConvolveOp(new Kernel(3, 3, data_b),
                        ConvolveOp.EDGE_NO_OP,
                null);
                break;
                
                
                
            case 4: /* lookup */

                byte lut[] = new byte[256];
                for (int j = 0; j < 256; j++) {
                    lut[j] = (byte) (256 - j);
                }

                ByteLookupTable blut = new ByteLookupTable(0, lut);
                op = new LookupOp(blut, null);
                break;

        }
        /*
         Rather than directly drawing the filtered image to the
         destination, filter it into a new iage first, then that
         filtered image is ready for writing out or painting.
        
         */
        biFiltered = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        op.filter(bi, biFiltered);

    }

}
