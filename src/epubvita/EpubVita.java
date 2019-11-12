package epubvita;

import controlador.configuraciones;
import controlador.log;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;
import vista.Principal;
import vista.vistaConf;

public class EpubVita {

    public static void main(String[] args) {
        log.eliminarLogs();
        
        log.escribirLog("Cargando apariencia de entorno...", 0);
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            String estilo = javax.swing.UIManager.getSystemLookAndFeelClassName();
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                javax.swing.UIManager.setLookAndFeel(estilo);
            } else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("GTK+".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println(e.getCause());
        }
        
        configuraciones ctrlConf=new configuraciones();
        if(!ctrlConf.existeProperties()){
            log.escribirLog("Cargando configuraciones y mostrando ventana de primera vez", 0);
            new vistaConf(null, true).setVisible(true);
        }
        log.escribirLog("Mostrando ventana principal", 0);
        new Principal().setVisible(true);
        
    }

}
