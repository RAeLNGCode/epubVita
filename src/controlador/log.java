package controlador;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class log {

    
    public static void eliminarLogs() {
        File logFile = new File(System.getProperty("java.io.tmpdir")+System.getProperty("file.separator")+"epubVita");
        if (logFile.exists()) {
            for (File archivo : logFile.listFiles()) {
                if (archivo.getName().contains("epubVitaLog")) {
                    archivo.delete();
                }
            }
        }else{
            logFile.mkdir();
        }
    }

    public static void escribirLog(String mensaje, int opcion) {
        Logger logger = Logger.getLogger("epubVitaLog");
        FileHandler fh;

        try {
            fh = new FileHandler(System.getProperty("java.io.tmpdir")+System.getProperty("file.separator")+"epubVita"+System.getProperty("file.separator")+"epubVitaLog.log", true);
            logger.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            switch (opcion) {
                case 0:
                    logger.info(mensaje);
                    break;

                case 1:
                    logger.warning(mensaje);
                    break;

                case 2:
                    logger.severe(mensaje);
                    break;

                default:
                    break;
            }
            fh.close();
        } catch (IOException | SecurityException e) {
        }
    }

    public static void escribirLogSalida(String mensaje) {
        Logger logger = Logger.getLogger("epubVitaLogSalida");
        FileHandler fh;

        try {
            fh = new FileHandler(System.getProperty("java.io.tmpdir")+System.getProperty("file.separator")+"epubVita"+System.getProperty("file.separator")+"epubVitaLogSalida.log", true);
            logger.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.warning(mensaje);
            fh.close();
        } catch (IOException | SecurityException e) {
        }
    }
    
}
