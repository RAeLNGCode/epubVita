package controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
import org.w3c.dom.Text;

public class archivos {

    private libro libr;
    private String ruta;
    private String rutaSalida;
    private ArrayList<String> imagenes;
    private ArrayList<String> paginas;
    private ArrayList<String> archivos;
    private String idiomaCorto;
    private String idiomaLargo;
    private String sep;

    public archivos(libro libr, String ruta, String rutaSalida) {
        this.libr = libr;
        imagenes = new ArrayList<>();
        paginas = new ArrayList<>();
        archivos = new ArrayList<>();
        this.ruta = ruta;
        this.rutaSalida = rutaSalida;
        idiomaLargo = "es-CL";
        idiomaCorto = "es";
        sep=System.getProperty("file.separator");
    }

    public void crearNavigation() {

        log.escribirLog(rutaSalida + sep+"temp" +sep+ ruta + sep+"content"+sep+"navigation.ncx", 0);

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // elemento raiz
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true);

            Element rootElement = doc.createElement("ncx");

            Attr attr = doc.createAttribute("xmlns");
            attr.setValue("http://www.daisy.org/z3986/2005/ncx/");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("version");
            attr.setValue("2005-1");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("xml:lang");
            attr.setValue(libr.getIdioma());
            rootElement.setAttributeNode(attr);

            doc.appendChild(rootElement);

            // head
            Element head = doc.createElement("head");
            rootElement.appendChild(head);

            // meta
            Element meta = doc.createElement("meta");

            attr = doc.createAttribute("content");
            attr.setValue(libr.getCodigo());
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("name");
            attr.setValue("dtb:uid");
            meta.setAttributeNode(attr);
            head.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("content");
            attr.setValue("1");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("name");
            attr.setValue("dtb:depth");
            meta.setAttributeNode(attr);
            head.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("content");
            attr.setValue("calibre (3.9.0)");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("name");
            attr.setValue("dtb:generator");
            meta.setAttributeNode(attr);
            head.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("content");
            attr.setValue("0");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("name");
            attr.setValue("dtb:totalPageCount");
            meta.setAttributeNode(attr);
            head.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("content");
            attr.setValue("0");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("name");
            attr.setValue("dtb:maxPageNumber");
            meta.setAttributeNode(attr);
            head.appendChild(meta);

            // docTitle
            Element docTitle = doc.createElement("docTitle");
            rootElement.appendChild(docTitle);

            // text
            Element textNode = doc.createElement("text");
            Text textNodeValue = doc.createTextNode(libr.getNombre() + " " + libr.getNumero());
            textNode.appendChild(textNodeValue);
            docTitle.appendChild(textNode);

            // navMap
            Element navMap = doc.createElement("navMap");
            rootElement.appendChild(navMap);

            // escribimos el contenido en un archivo .xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(rutaSalida + sep+"temp" +sep+ ruta + sep+"content"+sep+"navigation.ncx"));
            /*Si se quiere mostrar por la consola sin crear archivos,
            comentar linea arriba y descomentar la de abajo,
            aplicable a todos los archivos*/
            //StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

            log.escribirLog(" ... Archivo guardado!", 0);
            archivos.add("content"+sep+"navigation.ncx");

        } catch (ParserConfigurationException | TransformerException pce) {
        }

    }

    public void crearNavigation2() {

        log.escribirLog(rutaSalida + sep+"temp"+sep + ruta + sep+"content"+sep+"navigation.xhtml", 0);

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // elemento raiz
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true);

            Element rootElement = doc.createElement("html");

            Attr attr = doc.createAttribute("xmlns:epub");
            attr.setValue("http://www.idpf.org/2007/ops");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("xmlns");
            attr.setValue("http://www.w3.org/1999/xhtml");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("xml:lang");
            attr.setValue(libr.getIdioma());
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("lang");
            attr.setValue(libr.getIdioma());
            rootElement.setAttributeNode(attr);

            doc.appendChild(rootElement);

            // head
            Element head = doc.createElement("head");
            rootElement.appendChild(head);

            // title
            Element title = doc.createElement("title");
            Text titleNodeValue = doc.createTextNode(libr.getNombre() + " " + libr.getNumero());
            title.appendChild(titleNodeValue);
            head.appendChild(title);

            // body
            Element body = doc.createElement("body");
            rootElement.appendChild(body);

            // nav
            Element nav = doc.createElement("nav");
            rootElement.appendChild(nav);

            // h1
            Element h1 = doc.createElement("h1");
            Text h1NodeValue = doc.createTextNode(libr.getNombre() + " " + libr.getNumero());
            h1.appendChild(h1NodeValue);
            nav.appendChild(h1);

            // ol
            Element ol = doc.createElement("ol");
            nav.appendChild(ol);

            // escribimos el contenido en un archivo .xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(rutaSalida + sep+"temp"+sep + ruta + sep+"content"+sep+"navigation.xhtml"));
            
            transformer.transform(source, result);

            log.escribirLog(" ... Archivo guardado!", 0);
            archivos.add("content"+sep+"navigation.xhtml");

        } catch (ParserConfigurationException | TransformerException pce) {
        }

    }

    public void crearPage(String num, String imagen) {

        log.escribirLog(rutaSalida + sep+"temp"+sep + ruta + sep+"content"+sep+"page_" + num + ".xhtml", 0);

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // elemento raiz
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true);

            Element rootElement = doc.createElement("html");

            Attr attr = doc.createAttribute("xmlns:xlink");
            /*Esta parte en realidad debería ser "http://www.w3.org/1999/xlink"
            pero si se coloca así, da error producto de tener el mismo atributo
            con el mismo valor en svg y reader, y ningun lector de epub lo leeria*/
            attr.setValue("http://www.w3.org/1999/xlink/");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("lang");
            attr.setValue(idiomaLargo);
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("xml:lang");
            attr.setValue(idiomaLargo);
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("xmlns");
            attr.setValue("http://www.w3.org/1999/xhtml");
            rootElement.setAttributeNode(attr);

            doc.appendChild(rootElement);

            // head
            Element head = doc.createElement("head");
            rootElement.appendChild(head);

            // title
            Element title = doc.createElement("title");
            Text titleNodeValue = doc.createTextNode("page_" + num);
            title.appendChild(titleNodeValue);
            head.appendChild(title);

            // meta
            Element meta = doc.createElement("meta");
            attr = doc.createAttribute("charset");
            attr.setValue("UTF-8");
            meta.setAttributeNode(attr);
            head.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("name");
            attr.setValue("viewport");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("content");
            attr.setValue("width=960, height=1160");
            meta.setAttributeNode(attr);
            head.appendChild(meta);

            // body
            Element body = doc.createElement("body");
            rootElement.appendChild(body);

            // div
            Element div = doc.createElement("div");
            body.appendChild(div);

            // svg
            Element svg = doc.createElement("svg");
            attr = doc.createAttribute("xmlns:xlink");
            attr.setValue("http://www.w3.org/1999/xlink");
            svg.setAttributeNode(attr);
            attr = doc.createAttribute("version");
            attr.setValue("1.1");
            svg.setAttributeNode(attr);
            attr = doc.createAttribute("width");
            attr.setValue("100%");
            svg.setAttributeNode(attr);
            attr = doc.createAttribute("height");
            attr.setValue("100%");
            svg.setAttributeNode(attr);
            attr = doc.createAttribute("viewBox");
            attr.setValue("0 0 960 1160");
            svg.setAttributeNode(attr);
            attr = doc.createAttribute("xmlns");
            attr.setValue("http://www.w3.org/2000/svg");
            svg.setAttributeNode(attr);
            div.appendChild(svg);

            // image
            Element image = doc.createElement("image");
            attr = doc.createAttribute("width");
            attr.setValue("960");
            image.setAttributeNode(attr);
            attr = doc.createAttribute("height");
            attr.setValue("1160");
            image.setAttributeNode(attr);
            attr = doc.createAttribute("xlink:href");
            attr.setValue(imagen);
            image.setAttributeNode(attr);
            svg.appendChild(image);

            // escribimos el contenido en un archivo .xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(rutaSalida + sep+"temp"+sep + ruta + sep+"content"+sep+"page_" + num + ".xhtml"));
            
            transformer.transform(source, result);

            log.escribirLog(" ... Archivo guardado!", 0);

            imagenes.add(imagen);
            paginas.add("page_" + num + ".xhtml");
            archivos.add("content"+sep+"page_" + num + ".xhtml");

        } catch (ParserConfigurationException | TransformerException pce) {
        }

    }

    public void crearContent() {

        log.escribirLog(rutaSalida + sep+"temp"+sep + ruta + sep+"content"+sep+"content.opf", 0);

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // elemento raiz
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true);

            Element rootElement = doc.createElement("package");

            Attr attr = doc.createAttribute("xmlns:dc");
            attr.setValue("http://purl.org/dc/elements/1.1/");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("xmlns:dcterms");
            attr.setValue("http://purl.org/dc/terms/");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("xmlns:opf");
            attr.setValue("http://www.idpf.org/2007/opf");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("xmlns:xsi");
            attr.setValue("http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("xmlns");
            attr.setValue("http://www.idpf.org/2007/opf");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("prefix");
            attr.setValue("prism: http://prismstandard.org/namespaces/basic/2.1/ calibre: https://calibre-ebook.com prs: http://xmlns.sony.net/e-book/prs/ bisac: http://www.bisg.org/what-we-do-0-136-bisac-subject-headings-list-major-subjects.php");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("unique-identifier");
            attr.setValue("uid");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("version");
            attr.setValue("3.0");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("xml:lang");
            attr.setValue(idiomaCorto);
            rootElement.setAttributeNode(attr);

            doc.appendChild(rootElement);

            // metadata
            Element metadata = doc.createElement("metadata");
            rootElement.appendChild(metadata);

            // dc y meta
            Element dc = doc.createElement("dc:identifier");
            attr = doc.createAttribute("id");
            attr.setValue("uid");
            dc.setAttributeNode(attr);
            Text titleNodeValue = doc.createTextNode(libr.getCodigo());
            dc.appendChild(titleNodeValue);
            metadata.appendChild(dc);

            Element meta = doc.createElement("meta");
            attr = doc.createAttribute("refines");
            attr.setValue("#uid");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("property");
            attr.setValue("identifier-type");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("scheme");
            attr.setValue("xsd:string");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode("sonybookid");
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            dc = doc.createElement("dc:title");
            attr = doc.createAttribute("id");
            attr.setValue("mainTitle");
            dc.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode(libr.getNombre() + " " + libr.getNumero());
            dc.appendChild(titleNodeValue);
            metadata.appendChild(dc);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("refines");
            attr.setValue("#mainTitle");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("property");
            attr.setValue("title-type");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode("main");
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("refines");
            attr.setValue("#mainTitle");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("property");
            attr.setValue("file-as");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode(libr.getNombre() + " " + libr.getNumero());
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            dc = doc.createElement("dc:publisher");
            titleNodeValue = doc.createTextNode(libr.getPublicador());
            dc.appendChild(titleNodeValue);
            metadata.appendChild(dc);

            dc = doc.createElement("dc:language");
            titleNodeValue = doc.createTextNode(libr.getIdioma());
            dc.appendChild(titleNodeValue);
            metadata.appendChild(dc);

            dc = doc.createElement("dc:description");
            titleNodeValue = doc.createTextNode(libr.getDescripcion());
            dc.appendChild(titleNodeValue);
            metadata.appendChild(dc);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("property");
            attr.setValue("dcterms:modified");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("id");
            attr.setValue("dcterms-modified");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode("2012-09-25T01:08:05Z");
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("property");
            attr.setValue("dcterms:issued");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("id");
            attr.setValue("dcterms-issued");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode("2012-09-18");
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("property");
            attr.setValue("prism:publicationName");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("id");
            attr.setValue("prism-publicationName");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode(libr.getNombre() + " " + libr.getNumero());
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("refines");
            attr.setValue("#prism:publicationName");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("property");
            attr.setValue("file-as");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode(libr.getNombre() + " " + libr.getNumero());
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("property");
            attr.setValue("prism:volume");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("id");
            attr.setValue("prism-volume");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode("1");
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("property");
            attr.setValue("prism:number");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("id");
            attr.setValue("prism-number");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode(String.valueOf(libr.getNumero()));
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("property");
            attr.setValue("rendition:layout");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode("pre-paginated");
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            dc = doc.createElement("dc:creator");
            attr = doc.createAttribute("id");
            attr.setValue("dccredit_0");
            dc.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode(libr.getCreador());
            dc.appendChild(titleNodeValue);
            metadata.appendChild(dc);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("property");
            attr.setValue("dcterms:creator");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("id");
            attr.setValue("credit_0");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode(libr.getCreador());
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("refines");
            attr.setValue("#credit_0");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("property");
            attr.setValue("role");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("id");
            attr.setValue("role_0");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("scheme");
            attr.setValue("onix:codelist17");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode("A09");
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("refines");
            attr.setValue("#credit_0");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("property");
            attr.setValue("file-as");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode(libr.getCreador());
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("refines");
            attr.setValue("#dccredit_0");
            meta.setAttributeNode(attr);
            attr = doc.createAttribute("property");
            attr.setValue("file-as");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode(libr.getCreador());
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            meta = doc.createElement("meta");
            attr = doc.createAttribute("property");
            attr.setValue("prs:datatype");
            meta.setAttributeNode(attr);
            titleNodeValue = doc.createTextNode("comic");
            meta.appendChild(titleNodeValue);
            metadata.appendChild(meta);

            // manifest
            Element manifest = doc.createElement("manifest");
            rootElement.appendChild(manifest);

            // item
            Element item = doc.createElement("item");
            attr = doc.createAttribute("href");
            attr.setValue("navigation.ncx");
            item.setAttributeNode(attr);
            attr = doc.createAttribute("media-type");
            attr.setValue("application/x-dtbncx+xml");
            item.setAttributeNode(attr);
            attr = doc.createAttribute("id");
            attr.setValue("ncx");
            item.setAttributeNode(attr);
            manifest.appendChild(item);

            item = doc.createElement("item");
            attr = doc.createAttribute("href");
            attr.setValue("navigation.xhtml");
            item.setAttributeNode(attr);
            attr = doc.createAttribute("properties");
            attr.setValue("nav");
            item.setAttributeNode(attr);
            attr = doc.createAttribute("media-type");
            attr.setValue("application/xhtml+xml");
            item.setAttributeNode(attr);
            attr = doc.createAttribute("id");
            attr.setValue("navXhtml");
            item.setAttributeNode(attr);
            manifest.appendChild(item);

            // aqui se agrega cada pagina
            int num = 0;
            String id = "id";
            for (String pagina : paginas) {
                item = doc.createElement("item");
                attr = doc.createAttribute("href");
                attr.setValue(pagina);
                item.setAttributeNode(attr);
                attr = doc.createAttribute("id");
                attr.setValue(id);
                item.setAttributeNode(attr);
                attr = doc.createAttribute("media-type");
                attr.setValue("application/xhtml+xml");
                item.setAttributeNode(attr);

                // aqui se revisa si es la primera pagina y se coloca como pagina de titulo
                if (num == 0) {
                    attr = doc.createAttribute("properties");
                    attr.setValue("calibre:title-page");
                    item.setAttributeNode(attr);
                }

                manifest.appendChild(item);

                // aqui se incrementa el id. id,id1,id2,id3,idN
                num++;
                id = "id" + num;
            }

            boolean primera = true;
            // aqui se hace lo mismo para las imagenes
            for (String imagen : imagenes) {
                item = doc.createElement("item");
                attr = doc.createAttribute("href");
                attr.setValue(imagen);
                item.setAttributeNode(attr);
                attr = doc.createAttribute("id");
                attr.setValue(id);
                item.setAttributeNode(attr);
                attr = doc.createAttribute("media-type");
                attr.setValue("image/jpeg");
                item.setAttributeNode(attr);

                // aqui se revisa si es la primera imagen y se coloca como portada
                if (primera) {
                    attr = doc.createAttribute("properties");
                    attr.setValue("cover-image");
                    item.setAttributeNode(attr);
                    primera = false;
                }

                manifest.appendChild(item);

                // aqui se incrementa el id. id,id1,id2,id3,idN
                num++;
                id = "id" + num;
            }

            // spine
            Element spine = doc.createElement("spine");
            attr = doc.createAttribute("toc");
            attr.setValue("ncx");
            spine.setAttributeNode(attr);
            attr = doc.createAttribute("page-progression-direction");
            attr.setValue(libr.getDireccionLectura());
            spine.setAttributeNode(attr);
            rootElement.appendChild(spine);

            // aqui se agrega cada pagina al spine, resetea el id y declara itemref
            id = "id";
            Element itemref;
            for (int i = 0; i < paginas.size(); i++) {
                itemref = doc.createElement("itemref");
                attr = doc.createAttribute("idref");
                attr.setValue(id);
                itemref.setAttributeNode(attr);
                spine.appendChild(itemref);
                id = "id" + (i + 1);
            }

            // escribimos el contenido en un archivo .xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(rutaSalida + sep+"temp"+sep + ruta + sep+"content"+sep+"content.opf"));
            
            transformer.transform(source, result);

            log.escribirLog(" ... Archivo guardado!", 0);
            archivos.add("content"+sep+"content.opf");

        } catch (ParserConfigurationException | TransformerException pce) {
        }

    }

    public void crearContainer() {

        log.escribirLog(rutaSalida +sep+ "temp"+sep + ruta + sep+"META-INF"+sep+"container.xml", 0);

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // elemento raiz
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true);

            Element rootElement = doc.createElement("container");

            Attr attr = doc.createAttribute("version");
            attr.setValue("1.0");
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("xmlns");
            attr.setValue("urn:oasis:names:tc:opendocument:xmlns:container");
            rootElement.setAttributeNode(attr);

            doc.appendChild(rootElement);

            // rootfiles
            Element rootfiles = doc.createElement("rootfiles");
            rootElement.appendChild(rootfiles);

            // title
            Element rootfile = doc.createElement("rootfile");
            attr = doc.createAttribute("media-type");
            attr.setValue("application/oebps-package+xml");
            rootfile.setAttributeNode(attr);
            attr = doc.createAttribute("full-path");
            attr.setValue("content/content.opf");
            rootfile.setAttributeNode(attr);
            rootfiles.appendChild(rootfile);

            // escribimos el contenido en un archivo .xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(rutaSalida +sep+ "temp"+sep + ruta + sep+"META-INF"+sep+"container.xml"));
            
            transformer.transform(source, result);

            log.escribirLog(" ... Archivo guardado!", 0);
            archivos.add("META-INF"+sep+"container.xml");

        } catch (ParserConfigurationException | TransformerException pce) {
        }

    }

    public void crearCaliBook() {
        try {
            log.escribirLog(rutaSalida + sep+"temp"+sep + ruta + sep+"META-INF"+sep+"calibre_bookmarks.txt", 0);
            File archivo = new File(rutaSalida + sep+"temp"+sep + ruta + sep+"META-INF"+sep+"calibre_bookmarks.txt");
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("calibre_current_page_bookmark*|!|?|*0*|!|?|*0.0");
            bw.flush();
            bw.close();
            log.escribirLog(" ... Archivo guardado!", 0);
            archivos.add("META-INF"+sep+"calibre_bookmarks.txt");
        } catch (IOException e) {
        }
    }

    public void crearMime() {
        try {
            log.escribirLog(rutaSalida +sep+ "temp"+sep + ruta + sep+"mimetype", 0);
            File archivo = new File(rutaSalida +sep+ "temp"+sep + ruta + sep+"mimetype");
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("application/epub+zip");
            bw.flush();
            bw.close();
            log.escribirLog(" ... Archivo guardado!", 0);
            archivos.add("mimetype");
        } catch (IOException e) {
        }
    }

    public ArrayList<String> getArchivos() {
        return archivos;
    }

    public static void borrarTemp(String ruta) {
        File rutaF = new File(ruta);
        File[] archivos = rutaF.listFiles();
        for (File archivo : archivos) {
            if (archivo.isDirectory()) {
                borrarTemp(archivo.getAbsolutePath());
                log.escribirLog("Borrando: " + archivo.getAbsolutePath(), 0);
            } else {
                log.escribirLog("Borrando: " + archivo.getAbsolutePath(), 0);
                archivo.delete();
            }
        }
        rutaF.delete();
    }
}
