package filtrosimagens;

/**
 * Utilitarios Diversas funções utilitarias para Java Início: 22/09/2016
 * 
*
 */


import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Arquivo filtro para utilizar no método accptAllFileFilterUsed de FileChooser
 * FileChooser.addChoosableFileFilter(new TextFilter());
 *
 * @version
 * @author Dantcar
 * @since
 */
class TextFilterOut extends FileFilter {

    public TextFilterOut() {

    
    
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String s = f.getName();
        int i = s.lastIndexOf('.');

        //filtra para abrir somente arquivos jpg
        if (i > 0 && i < s.length() - 1) {
            if (s.substring(i + 1).toLowerCase().equals("jpg")) {
                return true;
            }
        }

       
        //filtra para abrir somente arquivos jpg
        if (i > 0 && i < s.length() - 1) {
            if (s.substring(i + 1).toLowerCase().equals("png")) {
                return true;
            }
        }

        //filtra para abrir somente arquivos gif
        if (i > 0 && i < s.length() - 1) {
            if (s.substring(i + 1).toLowerCase().equals("gif")) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        return "Aceita somente arquivos de imagem: jpg, png ou gif.";
    }

}
