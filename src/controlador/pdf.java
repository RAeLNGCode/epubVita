package controlador;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class pdf {

    private ArrayList<String> nomImage;
    private int pageCounter;
    private int dpi;
    private File dirDestino;
    private String archivo;
    private String sep;

    /* Convierte el documento PDF a imagen con un tamaño segun calidad*/
    public void prepararPdf(String archivo, String nombre, String rutaSalida, int calidad) {
        sep=System.getProperty("file.separator");
        pageCounter = 0;
        this.archivo=archivo;
        switch (calidad) {
            case 0:
                dpi = 80;
                break;
            case 2:
                dpi = 140;
                break;
            case 3:
                dpi = 180;
                break;
            default:
                dpi = 100;
                break;
        }
        nomImage = new ArrayList<>();
            File dirMF = new File(rutaSalida + sep+"temp"+sep + nombre + sep+"META-INF");
            dirMF.mkdirs();
            dirDestino = new File(rutaSalida + sep+"temp"+sep + nombre + sep+"content");
            dirDestino.mkdirs();
            
        }

    public int cantidadPag(String archivo) {
        PDDocument document;
        int paginas = 0;
        try {
            document = PDDocument.load(new File(archivo));
            for (PDPage g:document.getPages()) {
                paginas++;
            }
            document.close();
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paginas;
    }

    public void convertirSig() {
        PDDocument document;
        try {
            document = PDDocument.load(new File(archivo));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, dpi, ImageType.RGB);
            document.close();
            //en este caso 100 se refiere al tamaño en dpi
            // el sufijo del nombre es usado como formato de archivo
            ImageIOUtil.writeImage(bim, dirDestino.getAbsolutePath() + sep+"img_" + (pageCounter) + ".jpg", dpi);
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        nomImage.add("img_" + (pageCounter++) + ".jpg");
    }

    public ArrayList<String> getNomImage() {
        return nomImage;
    }
}
