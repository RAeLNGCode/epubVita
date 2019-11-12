package controlador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class utilImg {

    private int contador;
    private ArrayList<String> listImg;
    private String sep;

    public utilImg() {
        this.contador = 0;
        listImg = new ArrayList<>();
        sep=System.getProperty("file.separator");
    }

    public void restart() {
        this.contador = 0;
        listImg = new ArrayList<>();
    }

    public ArrayList<String> getListImg() {
        return listImg;
    }

    public void add(String archivo) {
        listImg.add(archivo);
    }

    public void copiarThumbnails(String ruta, ArrayList<String> archivos) {
        int num = 0;
        for (String archivo : archivos) {
            File origen = new File(ruta + sep + archivo);
            String nombreThumb = "img_" + num + "_Thumb.jpg";
            num++;
            File destino = new File(System.getProperty("java.io.tmpdir")+System.getProperty("file.separator")+nombreThumb);
            try {
                Files.copy(Paths.get(origen.getAbsolutePath()), Paths.get(destino.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(utilImg.class.getName()).log(Level.SEVERE, null, ex);
            }
            listImg.add(nombreThumb);
            if (num > 5) {
                break;
            }
        }
    }

    public void renameImg(String ruta, ArrayList<String> archivos) {
        int num = 0;
        for (String archivo : archivos) {
            File origen = new File(ruta + sep + archivo);
            String archivo2 = "img_epub_" + num + ".jpg";
            num++;
            File destino = new File(ruta + sep + archivo2);
            boolean success = origen.renameTo(destino);
            if (!success) {
                System.err.println("Error intentando mover el fichero");
            }
            listImg.add(archivo2);
        }
    }

    public boolean esDoble(String ruta, String filename) {
        boolean doble = false;
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(new File(ruta + sep + filename));
            int width = bimg.getWidth();
            int height = bimg.getHeight();
            doble = width >= height;
        } catch (IOException ex) {
            Logger.getLogger(utilImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doble;
    }

    public boolean esMuyLarga(String ruta, String filename) {
        boolean larga = false;
        BufferedImage bimg = null;
        try {
            log.escribirLog(ruta + sep + filename,0);
            bimg = ImageIO.read(new File(ruta + sep + filename));
            int width = bimg.getWidth();
            int height = bimg.getHeight();
            larga = 1.8 < (height / width);
        } catch (IOException ex) {
            Logger.getLogger(utilImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return larga;
    }

    public boolean esMuyCorta(String ruta, String filename) {
        boolean corta = false;
        BufferedImage bimg = null;
        try {
            log.escribirLog(ruta + sep + filename,0);
            bimg = ImageIO.read(new File(ruta + sep + filename));
            int width = bimg.getWidth();
            int height = bimg.getHeight();
            corta = 0.8 < (width / height);
        } catch (IOException ex) {
            Logger.getLogger(utilImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return corta;
    }

    //El Reader no lee imagenes PNG dentro de une EPUB
    //por lo que se hace necesario cambiar a otro formato
    public void pngToJpg(String ruta, String ruta2) {
        BufferedImage bufferedImage;

        try {
            File fichero = new File(ruta);
            //Leer imagen
            bufferedImage = ImageIO.read(fichero);

            //Se crea una base RGB con el mismo alto y ancho
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            //Se escribe la imagen obtenida a la base y se le inserta un fondo blanco
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            //Escribir archivo jpg
            ImageIO.write(newBufferedImage, "jpg", new File(ruta2));
            fichero.delete();
            log.escribirLog("Formato cambiado",0);

        } catch (IOException e) {
        }
    }

    public void dividirManwha(String ruta, String archivo) {

        File file = new File(ruta + sep + archivo);
        FileInputStream fis;
        String imgDiv;
        try {
            fis = new FileInputStream(file);

            BufferedImage image = ImageIO.read(fis); // Leer archivo de imagen
            int filas = (int) ((image.getHeight() / image.getWidth()) / 1.5);

            if (filas == 1) {
                filas = 2;
            }

            int rows = filas; // Se colocan numeros segun como se quiere dividir cada imagen
            int cols = 1; // En este caso se divide cada imagen en 2 con un corte vertical, por asi decirlo
            int chunks = rows * cols;

            int chunkWidth = image.getWidth() / cols; // Se calculan los nuevos tamaños, segun las divisiones que se haran y ancho (y alto) iniciales
            int chunkHeight = image.getHeight() / rows;
            int count = 0;
            BufferedImage imgs[] = new BufferedImage[chunks]; // Se hace un array de las imagenes recortadas
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {

                    imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                    Graphics2D gr = imgs[count++].createGraphics();
                    gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                    gr.dispose();
                }
            }
            log.escribirLog("Division correcta",0);

            // Se escriben la imagenes recortadas
            for (int i = 0; i < imgs.length; i++, contador++) {
                imgDiv = "img_" + contador + "-div.jpg";
                ImageIO.write(imgs[i], "jpg", new File(ruta + sep + imgDiv));
                listImg.add(imgDiv);
            }
            file.delete();
            log.escribirLog("Imagenes divididas",0);
        } catch (IOException ex) {
            Logger.getLogger(utilImg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unirManwha(String ruta, String archivo1, String archivo2) {

        File file1 = new File(ruta + sep+ archivo1);
        FileInputStream fis1;
        File file2 = new File(ruta + sep + archivo2);
        FileInputStream fis2;

        String imgUni;
        try {
            fis1 = new FileInputStream(file1);
            BufferedImage image1 = ImageIO.read(fis1); // Leer archivo1 de imagen
            fis2 = new FileInputStream(file2);
            BufferedImage image2 = ImageIO.read(fis2); // Leer archivo2 de imagen

            int ancho;
            if (image1.getWidth() > image2.getWidth()) {
                //En caso de que la imagen1 es mas ancha,
                //entonces se convierte en el ancho total de la imagen
                ancho = image1.getWidth();
            } else {
                //En caso contrario, la imagen2 es mas ancha o son iguales
                //por lo que se tomara la imagen2 como referencia para ancho total
                ancho = image2.getWidth();
            }
            int alto = image1.getHeight() + image2.getHeight();

            BufferedImage img = new BufferedImage(ancho, alto, image1.getType()); // Se hace un array de las imagenes recortadas
            Graphics2D gr = img.createGraphics();
            gr.drawImage(image1, 0, 0, image1.getWidth(), image1.getHeight(), 0, 0, image1.getWidth(), image1.getHeight(), null);
            gr.drawImage(image2, 0, image1.getHeight(), ancho, alto, 0, 0, image2.getWidth(), image2.getHeight(), null);
            gr.dispose();
            log.escribirLog("Union correcta",0);

            // Se escriben la imagenes recortadas
            imgUni = "img_" + contador + "-unida.jpg";
            ImageIO.write(img, "jpg", new File(ruta + sep + imgUni));
            contador++;
            if (2.2 < (alto / ancho)) {
                dividirManwha(ruta, imgUni);
            } else {
                listImg.add(imgUni);
            }
            file1.delete();
            file2.delete();
            log.escribirLog("Imagenes unidas",0);
        } catch (IOException ex) {
            Logger.getLogger(utilImg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dividir(String ruta, String archivo, String direccion) {

        File file = new File(ruta + sep + archivo);
        FileInputStream fis;
        String imgDiv;
        try {
            fis = new FileInputStream(file);

            BufferedImage image = ImageIO.read(fis); // Leer archivo de imagen

            int rows = 1; // Se colocan numeros segun como se quiere dividir cada imagen
            int cols = 2; // En este caso se divide cada imagen en 2 con un corte vertical, por asi decirlo
            int chunks = rows * cols;

            int chunkWidth = image.getWidth() / cols; // Se calculan los nuevos tamaños, segun las divisiones que se haran y ancho (y alto) iniciales
            int chunkHeight = image.getHeight() / rows;
            int count = 0;
            BufferedImage imgs[] = new BufferedImage[chunks]; // Se hace un array de las imagenes recortadas
            if (direccion.equals("ltr")) {
                for (int y = 0; y < cols; y++) {

                    imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                    Graphics2D gr = imgs[count++].createGraphics();
                    gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, 0, chunkWidth * y + chunkWidth, chunkHeight, null);
                    gr.dispose();
                }
            } else {
                for (int y = cols - 1; y >= 0; y--) {

                    imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                    Graphics2D gr = imgs[count++].createGraphics();
                    gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, 0, chunkWidth * y + chunkWidth, chunkHeight, null);
                    gr.dispose();
                }
            }
            log.escribirLog("Splitting done",0);

            // Se escriben la imagenes recortadas
            for (int i = 0; i < imgs.length; i++, contador++) {
                imgDiv = "img_" + contador + "-div.jpg";
                ImageIO.write(imgs[i], "jpg", new File(ruta + sep + imgDiv));
                listImg.add(imgDiv);
            }
            file.delete();
            log.escribirLog("Imagenes divididas",0);
        } catch (IOException ex) {
            Logger.getLogger(utilImg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recortar(int Izquierda, int Derecha, int Arriba, int Abajo, String ruta, String archivo) {

        File file = new File(ruta + sep + archivo);
        FileInputStream fis;
        String imgRec;
        try {
            fis = new FileInputStream(file);

            BufferedImage image = ImageIO.read(fis); // Leer archivo de imagen

            int posIz = image.getWidth() * Izquierda / 100;
            int posDe = image.getWidth() * (100 - Derecha) / 100;
            int posAr = image.getHeight() * Arriba / 100;
            int posAb = image.getHeight() * (100 - Abajo) / 100;
            int chunkWidth = posDe - posIz; // Se calculan los nuevos tamaños, segun las divisiones que se haran y ancho (y alto) iniciales
            int chunkHeight = posAb - posAr;

            //log.escribirLog(posIz + ", " + posDe + ", " + posAr + ", " + posAb + " tamaño:" + chunkWidth + "X" + chunkHeight,0);

            BufferedImage img = new BufferedImage(chunkWidth, chunkHeight, image.getType()); // Se hace un array de las imagenes recortadas

            Graphics2D gr = img.createGraphics();
            gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, posIz, posAr, posDe, posAb, null);
            gr.dispose();

            imgRec = "img_" + contador + "-rec.jpg";
            // Se escribe la imagen recortada
            ImageIO.write(img, "jpg", new File(ruta + sep + imgRec));
            listImg.add(imgRec);
            contador++;
            file.delete();
            log.escribirLog("Imagene recortada",0);
        } catch (IOException ex) {
            Logger.getLogger(utilImg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Dimension thumbnail(int tamano, String ruta, String archivo, String thumbName) {
        int ancho = 0;
        int alto = 0;
        try {
            BufferedImage image = ImageIO.read(new File(ruta + sep + archivo));
            ancho = image.getWidth();
            alto = image.getHeight();
            if (ancho > alto) {
                alto = alto / (ancho / tamano);
                ancho = tamano;
            } else {
                ancho = ancho / (alto / tamano);
                alto = tamano;
            }
            BufferedImage img = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
            img.createGraphics().drawImage(image.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(img, "jpg", new File(ruta + "/" + thumbName));
        } catch (IOException ex) {
            Logger.getLogger(utilImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Dimension(ancho, alto);
    }
}
