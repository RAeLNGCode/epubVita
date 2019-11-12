package controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import modelo.config;
import java.util.Properties;

public class configuraciones {

    private String rutaArchivo;
    public configuraciones() {
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            rutaArchivo=System.getProperty("user.home") + "\\AppData\\Roaming\\epubVita.conf";
        }else if(System.getProperty("os.name").toLowerCase().contains("linux")){
            rutaArchivo=System.getProperty("user.home") + "/.config/epubVita.conf";
        }else{
            rutaArchivo=System.getProperty("user.home") + System.getProperty("file.separator")+".config"+ System.getProperty("file.separator")+"epubVita.conf";
        }
    }
    
    public config getProperties() {
        //se crea una instancia a la clase Properties
        Properties propiedades = new Properties();
        InputStream entrada = null;
        try {
            //se leen el archivo .properties
            entrada = new FileInputStream(rutaArchivo);
            propiedades.load(entrada);
            //si el archivo de propiedades NO esta vacio retornan las propiedes leidas
            if (!propiedades.isEmpty()) {
                config conf = new config(propiedades.getProperty("ruta"),
                        propiedades.getProperty("direccion"),
                        propiedades.getProperty("calidad"),
                        propiedades.getProperty("borrarTemp"));
                return conf;
            } else {//sino  retornara NULL
                return null;
            }
        } catch (IOException ex) {
            log.escribirLog("Error al acceder a archivo "+rutaArchivo, 0);
            log.escribirLogSalida(ex.toString());
            return null;
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();
                } catch (IOException e) {
                }
            }

        }
    }

    public boolean existeProperties() {
        File fichero = new File(rutaArchivo);
        return fichero.exists();
    }

    public void guardarProperties(config conf) {
        Properties propiedades = new Properties();
        OutputStream salida = null;

        try {
            salida = new FileOutputStream(rutaArchivo);

            // asignamos los valores a las propiedades
            propiedades.setProperty("ruta", conf.getRuta());
            propiedades.setProperty("direccion", conf.getDirecString());
            propiedades.setProperty("calidad", conf.getCaliString());
            propiedades.setProperty("borrarTemp", conf.getBorrarTemp());

            // guardamos el archivo de propiedades en la carpeta de aplicación
            propiedades.store(salida, null);

        } catch (IOException io) {
            log.escribirLog("Error al crear archivo "+rutaArchivo, 0);
            log.escribirLogSalida(io.toString());
        } finally {
            if (salida != null) {
                try {
                    salida.close();
                } catch (IOException e) {
                }
            }

        }
    }
}
