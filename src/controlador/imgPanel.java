package controlador;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class imgPanel extends javax.swing.JPanel {

        private Dimension dm;
        private ImageIcon Img;
        
        public imgPanel(Dimension dm,String thumbName) {
            this.dm=dm;
            this.setSize(dm); //se selecciona el tamaño del panel
            //Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
            Img = new ImageIcon(System.getProperty("java.io.tmpdir")+System.getProperty("file.separator")+thumbName);
        }

        //Se crea un método cuyo parámetro debe ser un objeto Graphics
        public void paint(Graphics grafico) {
            
            //se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
            grafico.drawImage(Img.getImage(), 0, 0, dm.width, dm.height, null);
            setOpaque(false);
            super.paintComponent(grafico);
        }
    }
