package controlador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import modelo.libro;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class dbXml {

    private String rutaDBXML;
    private ArrayList<libro> libros;

    public dbXml(String rutaDBXML) {
        this.rutaDBXML = rutaDBXML;
        libros=new ArrayList<>();
    }
    
    public boolean vacia(){
        return libros.isEmpty();
    }
    
    public void addLibro(libro libr){
        libros.add(libr);
    }
    
    public ArrayList<libro> getLibros(){
        return libros;
    }
    
    public void leerDbXml() {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(new File(rutaDBXML));

            NodeList items = doc.getElementsByTagName("libro");
            for (int ix = 0; ix < items.getLength(); ix++) {
                Element element = (Element) items.item(ix);
                libro libr=new libro();
                libr.setNombre(element.getAttribute("nombre"));
                libr.setNumero(Integer.parseInt(element.getAttribute("numero")));
                libr.genCodigo();
                libr.setDescripcion(element.getAttribute("descripcion"));
                libr.setCreador(element.getAttribute("creador"));
                libr.setPublicador(element.getAttribute("publicador"));
                libr.setDireccionLectura(element.getAttribute("direccionLectura"));
                libr.setIdioma(element.getAttribute("idioma"));
                libr.setRuta(element.getAttribute("ruta"));
                libros.add(libr);
            }

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(dbXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearDbXml() {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // elemento raiz
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true);

            Element rootElement = doc.createElement("book-db");

            doc.appendChild(rootElement);

            for (libro libro : libros) {
                //libro
                Element infoLibro = doc.createElement("libro");
                //nombre
                Attr attr = doc.createAttribute("nombre");
                attr.setValue(libro.getNombre());
                infoLibro.setAttributeNode(attr);
                //codigo
                attr = doc.createAttribute("codigo");
                attr.setValue(libro.getCodigo());
                infoLibro.setAttributeNode(attr);
                //numero
                attr = doc.createAttribute("numero");
                attr.setValue(String.valueOf(libro.getNumero()));
                infoLibro.setAttributeNode(attr);
                //descripción
                attr = doc.createAttribute("descripcion");
                attr.setValue(libro.getDescripcion());
                infoLibro.setAttributeNode(attr);
                //autor
                attr = doc.createAttribute("creador");
                attr.setValue(libro.getCreador());
                infoLibro.setAttributeNode(attr);
                //publicador
                attr = doc.createAttribute("publicador");
                attr.setValue(libro.getPublicador());
                infoLibro.setAttributeNode(attr);
                //Direccion de lectura
                attr = doc.createAttribute("direccionLectura");
                attr.setValue(libro.getDireccionLectura());
                infoLibro.setAttributeNode(attr);
                //Idioma
                attr = doc.createAttribute("idioma");
                attr.setValue(libro.getIdioma());
                infoLibro.setAttributeNode(attr);
                //Ruta
                attr = doc.createAttribute("ruta");
                attr.setValue(libro.getRuta());
                infoLibro.setAttributeNode(attr);
                
                rootElement.appendChild(infoLibro);
            }

            // escribimos el contenido en un archivo .xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(rutaDBXML +"db.xml"));
            
            transformer.transform(source, result);

            System.out.println(" ... Archivo guardado!");

        } catch (ParserConfigurationException | TransformerException pce) {
        }

    }

    public void revisarDbXml() {
        File fileXml=new File(rutaDBXML + "db.xml");
        if (fileXml.exists()){
            leerDbXml();
            //fileXml.delete();
        }
    }
}
