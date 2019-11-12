package controlador;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class zip {

    private ArrayList<String> nomImage;
    private String rutaSalida;
    private String sep;

    public zip(String rutaSalida) {
        this.rutaSalida=rutaSalida;
        sep=System.getProperty("file.separator");
    }

    public void compress(String nombre, ArrayList<String> archivos) {
        try ( ZipArchiveOutputStream out = new ZipArchiveOutputStream(new File(rutaSalida+sep+"epub"+sep + nombre + ".epub"))) {
            for (String archivo : archivos) {
                log.escribirLog("Añadiendo: " + archivo, 0);
                addToArchiveCompression(out, new File(archivo));
            }
        } catch (IOException ex) {
            log.escribirLogSalida(ex.toString());
        }
    }

    public void compress(String nombre) {
        try ( ZipArchiveOutputStream out = new ZipArchiveOutputStream(new File(rutaSalida+sep+"epub"+sep + nombre + ".epub"))) {
            File[] archivos = new File(rutaSalida+sep+"temp"+sep + nombre).listFiles();
            for (File archivo : archivos) {
                log.escribirLog("Añadiendo: " + archivo.getName(), 0);
                addToArchiveCompression(out, archivo);
            }
        } catch (IOException ex) {
            log.escribirLogSalida(ex.toString());
        }
    }

    public void unzip(String archivo, String nombre) {

        nomImage = new ArrayList<>();
        utilImg uImg = new utilImg();

        File dirMF = new File(rutaSalida+"/temp/" + nombre + "/META-INF");
        dirMF.mkdirs();
        File dirDestino = new File(rutaSalida+"/temp/" + nombre + "/content");
        dirDestino.mkdirs();

        try {

            ZipArchiveInputStream zipFile = new ZipArchiveInputStream(new FileInputStream(new File(archivo)));
            ZipArchiveEntry entry;

            BufferedOutputStream dest;
            //FileInputStream fis = new FileInputStream(archivo);

            while ((entry = (ZipArchiveEntry) zipFile.getNextEntry()) != null) {
                System.out.println("Extrayendo: " + entry);
                int count;
                byte data[] = new byte[1024];
                //Obtenemos la ruta del archivo, con los directorios en los que se encuentra
                String rutaarchivo = entry.getName();
                int index;
                do {
                    //obtenemos la posicion del / para saber hasta que parte de la ruta estan los directorios
                    index = rutaarchivo.indexOf("/");
                    //quitamos los directorios de la ruta, y solo dejamos el nombre
                    rutaarchivo = rutaarchivo.substring(index + 1);
                } while (index != -1);
                //el proceso se repite hasta que no hayan mas subdirectorios
                if (!entry.isDirectory() && (rutaarchivo.contains(".png") || rutaarchivo.contains(".jpg") || rutaarchivo.contains(".jpeg"))) {
                    FileOutputStream fos = new FileOutputStream(dirDestino.getAbsolutePath() + "/" + rutaarchivo);
                    dest = new BufferedOutputStream(fos, 1024);
                    while ((count = zipFile.read(data, 0, 1024)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                    // aqui transforma de png a jpg
                    if (rutaarchivo.contains(".png")) {
                        uImg.pngToJpg(dirDestino.getAbsolutePath() + "/" + rutaarchivo, dirDestino.getAbsolutePath() + "/" + rutaarchivo.replace(".png", ".jpg"));
                        rutaarchivo = rutaarchivo.replace(".png", ".jpg");
                        //num++;
                    }
                    nomImage.add(rutaarchivo);
                }
            }
            zipFile.close();
            System.out.println("Extracción de " + nombre + " completada");

        } catch (Exception e) {
        }
    }

    public ArrayList<String> getNomImage() {
        Collections.sort(nomImage);
        return nomImage;
    }

    private void addToArchiveCompression(ZipArchiveOutputStream out, File file) {
        try {
            if (file.isFile()) {
                String name;
                if (file.getAbsolutePath().contains("content")) {
                    name = "content/" + file.getName();
                } else if (file.getAbsolutePath().contains("META-INF")) {
                    name = "META-INF/" + file.getName();
                } else {
                    name = file.getName();
                }
                ZipArchiveEntry entry = (ZipArchiveEntry) out.createArchiveEntry(file, name);
                out.putArchiveEntry(entry);

                FileInputStream in = new FileInputStream(file);
                byte[] b = new byte[1024];
                int count = 0;
                while ((count = in.read(b)) > 0) {
                    out.write(b, 0, count);
                }
                out.closeArchiveEntry();

            } else if (file.isDirectory()) {
                File[] children = file.listFiles();
                if (children != null) {
                    for (File child : children) {
                        addToArchiveCompression(out, child);
                    }
                }
            } else {
                System.out.println(file.getName() + " is not supported");
            }

        } catch (Exception e) {
        }
    }

    public void comprimirLinux(String nombre) {
        ejecutarTerminal("cd "+rutaSalida+"/temp/" + nombre + "\";zip -r \"" + nombre + "\".epub mimetype content META-INF");
        mover(rutaSalida+"/temp/" + nombre + "/" + nombre + ".epub", rutaSalida+"/epub/" + nombre + ".epub");
    }

    public void comprimirWindows(String nombre) {
        //ejecutarCmd("pwd");
        ejecutarCmd("cd "+rutaSalida+"/\temp/\"" + nombre + "\" && C:\\\"Program Files\"\\7-Zip\\7z.exe A \"" + nombre + "\".zip");
        mover(rutaSalida+"/temp/" + nombre + "/" + nombre + ".zip", rutaSalida+"/epub/" + nombre + ".epub");
    }

    public static void mover(String origen, String destino) {
        System.out.println("moviendo " + origen + " a " + destino);
        File fichero = new File(origen);

        File fichero2 = new File(destino);

        boolean success = fichero.renameTo(fichero2);
        if (!success) {
            System.err.println("Error intentando mover el fichero");
        }
    }

    public void ejecutarTerminal(String comando) {
        try {
            String[] command = {"sh", "-c", comando};
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
        }
    }

    public void ejecutarCmd(String comando) {
        try {
            Process p = Runtime.getRuntime().exec(comando);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
        }
    }

    public void unirListas(ArrayList<String> archivos, ArrayList<String> imagenes) {
        imagenes.forEach((imagen) -> {
            archivos.add("content/" + imagen);
        });
        Collections.sort(archivos);
    }

    public void crearDirEpub() {
        File dirEpub = new File(rutaSalida+"/epub/");
        dirEpub.mkdirs();
    }
}
