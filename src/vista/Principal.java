package vista;

import controlador.archivos;
import controlador.configuraciones;
import controlador.dbXml;
import controlador.imgPanel;
import controlador.pdf;
import controlador.sqlite;
import controlador.utilImg;
import controlador.zip;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.config;
import modelo.libro;

public class Principal extends javax.swing.JFrame {

    private int num;
    private libro libr;
    private ArrayList<ArrayList> listaImage;
    private ArrayList<ArrayList> listaTemp;
    private String rutaBookDB;
    private String rutaSalida;
    private int alto;
    private int ancho;
    private ArrayList<String> listaThumb;
    private Dimension dm;
    private String thumbName;
    private boolean selecArchivo;
    private int porcentaje;
    private boolean borrarTemp;
    private barraCrearEpub bce;
    private String sep;
    private String rutaDBXML;

    public Principal() {
        initComponents();
        this.setLocationRelativeTo(null);
        sep = System.getProperty("file.separator");
        libr = new libro();
        listaImage = new ArrayList<>();
        listaTemp = new ArrayList<>();
        listaThumb = new ArrayList<>();
        rutaBookDB = "";
        alto = this.getHeight();
        ancho = this.getWidth();
        selecArchivo = false;
        mostrarBtnActionPerformed(null);
        cargarConfiguraciones();
        desOcult();
    }

    public class Imagen extends javax.swing.JPanel {

        ImageIcon Img;

        public Imagen() {
            this.setSize(dm); //se selecciona el tamaño del panel
            //Se selecciona la imagen que tenemos en el paquete de la ruta del programa
            Img = new ImageIcon(thumbName);
        }

        //Se crea un método cuyo parámetro debe ser un objeto Graphics
        public void paint(Graphics grafico) {

            //se dibuja la imagen que tenemos en el paquete Images dentro de un panel
            grafico.drawImage(Img.getImage(), 0, 0, dm.width, dm.height, null);

            setOpaque(false);
            super.paintComponent(grafico);
        }
    }

    public void desOcult() {
        if (manwhaBox.isSelected()) {
            recBox.setSelected(false);
            divBox.setSelected(false);
            divBox.setEnabled(false);
            recBox.setEnabled(false);
        } else {
            divBox.setEnabled(true);
            recBox.setEnabled(true);
        }
        if (divExclu.isSelected() || divEspe.isSelected()) {
            divText.setEnabled(true);
            divLabel.setEnabled(true);
        } else {
            divText.setEnabled(false);
            divLabel.setEnabled(false);
        }
        if (divBox.isSelected()) {
            divTodas.setEnabled(true);
            divExclu.setEnabled(true);
            divEspe.setEnabled(true);
            divIntBox.setEnabled(true);
        } else {
            divTodas.setEnabled(false);
            divExclu.setEnabled(false);
            divEspe.setEnabled(false);
            divText.setEnabled(false);
            divLabel.setEnabled(false);
            divIntBox.setEnabled(false);
        }
        if (divIntBox.isSelected()) {
            divTodas.setEnabled(false);
            divExclu.setEnabled(false);
            divEspe.setEnabled(false);
            divText.setEnabled(false);
            divLabel.setEnabled(false);
        }
        if (recExclu.isSelected() || recEspe.isSelected()) {
            recText.setEnabled(true);
            recLabel.setEnabled(true);
        } else {
            recText.setEnabled(false);
            recLabel.setEnabled(false);
        }
        if (recBox.isSelected()) {
            recTodas.setEnabled(true);
            recExclu.setEnabled(true);
            recEspe.setEnabled(true);
            recArr.setEnabled(true);
            recAba.setEnabled(true);
            recIzq.setEnabled(true);
            recDer.setEnabled(true);
        } else {
            recTodas.setEnabled(false);
            recExclu.setEnabled(false);
            recEspe.setEnabled(false);
            recText.setEnabled(false);
            recLabel.setEnabled(false);
            recArr.setEnabled(false);
            recAba.setEnabled(false);
            recIzq.setEnabled(false);
            recDer.setEnabled(false);
        }
        if (recBox.isSelected() && divBox.isSelected()) {
            simeBox.setEnabled(true);
        } else {
            simeBox.setEnabled(false);
        }
        if (recBox.isSelected() || divBox.isSelected() || manwhaBox.isSelected()) {
            visPrevBtn.setEnabled(true);
            visPrevPagBtn.setEnabled(true);
        } else {
            visPrevBtn.setEnabled(false);
            visPrevPagBtn.setEnabled(false);
        }
    }

    public void aplicarOpcionesAvan() {
        if (manwhaBox.isSelected()) {
            //opciones manwha inteligente, aun no han sido creadas
            for (ArrayList arrayList : listaImage) {
                libr.genCodigo();
                manwhaAutomatic(libr.getNombre() + " " + libr.getNumero(), arrayList);
                libr.aumentarNumero();
            }
            libr.setNumero(num);
            listaImage = listaTemp;
            listaTemp = new ArrayList<>();
        } else {
            if (divBox.isSelected()) {
                //dividir paginas a la mitad
                if (divIntBox.isSelected()) {
                    //que divida solo paginas muy anchas
                    for (ArrayList arrayList : listaImage) {
                        libr.genCodigo();
                        divicionInteligente(libr.getNombre() + " " + libr.getNumero(), arrayList);
                        libr.aumentarNumero();
                    }
                    libr.setNumero(num);
                    listaImage = listaTemp;
                    listaTemp = new ArrayList<>();
                } else if (divTodas.isSelected()) {
                } else if (divExclu.isSelected()) {
                } else if (divEspe.isSelected()) {
                }
                //que de lo anterior se guarden los numeros de paginas que seran divididas
            }
            if (recBox.isSelected()) {
                if (divTodas.isSelected()) {
                    for (ArrayList arrayList : listaImage) {
                        libr.genCodigo();
                        recortarImg(libr.getNombre() + " " + libr.getNumero(), arrayList);
                        libr.aumentarNumero();
                    }
                    libr.setNumero(num);
                    listaImage = listaTemp;
                    listaTemp = new ArrayList<>();
                } else if (divExclu.isSelected()) {
                } else if (divEspe.isSelected()) {
                }
                if (simeBox.isSelected()) {
                    //aqui calculo, pagina inicial de simetria,
                    //segun si la primera pagina fue recortada o cuales fueron recortadas
                    //si se especifica o se excluyen se calcula segun si la primera dividida es par o impar
                }
            }
        }
    }

    public void renombrarImage() {
        utilImg uImg = new utilImg();
        for (ArrayList imagenes : listaImage) {
            uImg.restart();
            uImg.renameImg(rutaSalida + sep + "temp" + sep + libr.getNombre() + " " + libr.getNumero() + sep + "content", imagenes);
            libr.aumentarNumero();
            listaTemp.add(uImg.getListImg());
        }
        libr.setNumero(num);
        listaImage = listaTemp;
        listaTemp = new ArrayList<>();
    }

    public void manwhaAutomatic(String nombre, ArrayList<String> imagenes) {
        utilImg utilimg = new utilImg();
        String imagen1 = "";
        for (String imagen : imagenes) {
            if (!imagen1.equals("")) {
                utilimg.unirManwha(rutaSalida + sep + "temp" + sep + nombre + sep + "content", imagen1, imagen);
                imagen1 = "";
            } else if (utilimg.esMuyLarga(rutaSalida + sep + "temp" + sep + nombre + sep + "content", imagen)) {
                utilimg.dividirManwha(rutaSalida + sep + "temp" + sep + nombre + sep + "content", imagen);
            } else if (utilimg.esMuyCorta(rutaSalida + sep + "temp" + sep + nombre + sep + "content", imagen)) {
                imagen1 = imagen;
            } else {
                utilimg.add(imagen);
            }
        }
        listaTemp.add(utilimg.getListImg());
    }

    public void recortarImg(String nombre, ArrayList<String> imagenes) {
        utilImg utilimg = new utilImg();
        for (String imagen : imagenes) {
            utilimg.recortar((int) recIzq.getValue(), (int) recDer.getValue(), (int) recArr.getValue(), (int) recAba.getValue(), rutaSalida + "/temp/" + nombre + "/content", imagen);
        }
        listaTemp.add(utilimg.getListImg());
    }

    public void divicionInteligente(String nombre, ArrayList<String> imagenes) {
        utilImg utilimg = new utilImg();
        for (String imagen : imagenes) {
            if (utilimg.esDoble(rutaSalida + sep + "temp" + sep + nombre + sep + "content", imagen)) {
                utilimg.dividir(rutaSalida + sep + "temp" + sep + nombre + sep + "content", imagen, libr.getDireccionLectura());
            } else {
                utilimg.add(imagen);
            }
        }
        listaTemp.add(utilimg.getListImg());
    }

    public void obtenerDatLibro() {
        libr.setNombre(jTextNombre.getText());
        libr.setNumero((int) (jNumero.getValue()));
        num = (int) (jNumero.getValue());
        libr.genCodigo();
        libr.setCreador(jTextAutor.getText());
        libr.setPublicador(jTextPublic.getText());
        libr.setDescripcion(jTextDesc.getText());
        libr.setIdioma(jComboIdioma.getSelectedItem().toString());
        if (direccionCB.getSelectedItem().toString().equals("Izquierda a derecha")) {
            libr.setDireccionLectura("ltr");
        } else {
            libr.setDireccionLectura("rtl");
        }
        switch (rutaCBox.getSelectedIndex()) {
            case 0:
                libr.setRuta("ux0:/book/bookModelData/guest/");
                break;
            case 1:
                libr.setRuta("ur0:/book/");
                break;
        }
    }

    public void selecArchivo() {
        if (jTextNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un Nombre\nantes de seleccionar archivo(s)");
        } else if (!selecArchivo) {
            selecArchivoBtn.setEnabled(false);
            hiloBarra conteoBarra = new hiloBarra();
            conteoBarra.start();
        } else {
            JOptionPane.showMessageDialog(null, "Ya hay una selección de archivo(s)");
        }
    }

    public boolean selecBDB() {
        if (rutaDB.getText().isEmpty()) {
            javax.swing.JFileChooser filechooser = new javax.swing.JFileChooser();

            filechooser.setDialogTitle("Seleccione book.db");

            filechooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);

            filechooser.addChoosableFileFilter(new FileNameExtensionFilter("DB", "db"));
            filechooser.setAcceptAllFileFilterUsed(false);

            if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                final File file = filechooser.getSelectedFile();
                rutaBookDB = file.getAbsolutePath();
                rutaDB.setText(rutaBookDB);
            }
        }
        return !rutaDB.getText().isEmpty();
    }

    public boolean selecDBXML() {
        if (rutaDBXML == null) {
            javax.swing.JFileChooser filechooser = new javax.swing.JFileChooser();

            filechooser.setDialogTitle("Seleccione db.xml");

            filechooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);

            filechooser.addChoosableFileFilter(new FileNameExtensionFilter("XML", "xml"));
            filechooser.setAcceptAllFileFilterUsed(false);

            if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                final File file = filechooser.getSelectedFile();
                rutaDBXML = file.getAbsolutePath();
            }
        }
        return rutaDBXML != null;
    }

    public void obtenerThumb() {
        if (selecArchivo) {
            utilImg ui = new utilImg();
            ui.copiarThumbnails(rutaSalida + sep + "temp" + sep + libr.getNombre() + " " + (num) + sep + "content", listaImage.get(0));
            listaThumb = ui.getListImg();
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar archivo(s)\n para obtener vista previa");
        }
    }

    public void crearArchivos() {
        int cont;
        int actual = 0;
        sqlite BD;
        dbXml db = new dbXml(rutaSalida+sep);
        db.revisarDbXml();
        obtenerDatLibro();
        bce.estado(0, "Creando archivos Epub...");
        for (ArrayList arrayList : listaImage) {
            cont = 0;
            libr.genCodigo();
            archivos archi = new archivos(libr, libr.getNombre() + " " + libr.getNumero(), rutaSalida);
            archi.crearNavigation();
            archi.crearNavigation2();
            for (Object imagen : arrayList) {
                archi.crearPage(String.valueOf(cont), String.valueOf(imagen));
                cont++;
            }
            archi.crearContent();
            archi.crearContainer();
            archi.crearCaliBook();
            archi.crearMime();
            comprimir(libr.getNombre() + " " + libr.getNumero(), archi.getArchivos(), arrayList);
            //aqui metodo sql
            if (!rutaBookDB.equals("")) {
                BD = new sqlite(rutaBookDB);
                BD.guardarLibro(libr);
            } else {
                db.addLibro(libr.clonar());
            }
            libr.aumentarNumero();
            bce.estado((int) (((double) actual * 100) / (double) listaImage.size()), "Creando archivos Epub...");
            actual++;
        }
        if (!db.vacia()) {
            db.crearDbXml();
        }
        if (borrarTemp) {
            archivos.borrarTemp(rutaSalida + sep + "temp" + sep);
        }
        bce.estado(100, "Creando archivos Epub...");
    }

    public void importarDBXml() {
        dbXml db = new dbXml(rutaDBXML);
        db.leerDbXml();
        ArrayList<libro> libros = db.getLibros();
        sqlite BD = new sqlite(rutaBookDB);
        for (libro libr : libros) {
            BD.guardarLibro(libr);
        }

    }

    public void comprimir(String nombre, ArrayList<String> archis, ArrayList<String> imagenes) {
        zip comp = new zip(rutaSalida);
        comp.crearDirEpub();
        comp.compress(nombre);
    }

    private void cargarConfiguraciones() {
        configuraciones ctrlConf = new configuraciones();
        config conf;
        if (ctrlConf.existeProperties()) {
            conf = ctrlConf.getProperties();
            if (conf == null) {
                conf = new config();
                conf.setRuta(System.getProperty("user.home"));
            }
        } else {
            conf = new config();
            conf.setRuta(System.getProperty("user.home"));
        }
        rutaSalida = conf.getRuta();
        rutaLabel.setText(rutaSalida);
        direccionCB.setSelectedIndex(conf.getDireccion());
        calidadCB.setSelectedIndex(conf.getCalidad());
        borrarTemp = conf.isBorrarTemp();
    }

    private void selectRuta() {
        javax.swing.JFileChooser filechooser = new javax.swing.JFileChooser();

        filechooser.setDialogTitle("Seleccione carpeta de salida");

        filechooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        filechooser.setAcceptAllFileFilterUsed(false);

        if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            final File file = filechooser.getSelectedFile();
            rutaSalida = file.getAbsolutePath();
            rutaLabel.setText(rutaSalida);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dividir = new javax.swing.ButtonGroup();
        recortar = new javax.swing.ButtonGroup();
        menuPrincipal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        direccionCB = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboIdioma = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextDesc = new javax.swing.JTextField();
        jTextAutor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextPublic = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        BtContinuar = new javax.swing.JButton();
        rutaDB = new javax.swing.JLabel();
        jButtonBookDB = new javax.swing.JButton();
        mostrarBtn = new javax.swing.JButton();
        selecArchivoBtn = new javax.swing.JButton();
        jNumero = new javax.swing.JSpinner();
        barCarga = new javax.swing.JProgressBar();
        rutaLabel = new javax.swing.JLabel();
        rutaBtn = new javax.swing.JButton();
        calidadCB = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        menuOculto = new javax.swing.JPanel();
        divBox = new javax.swing.JCheckBox();
        divTodas = new javax.swing.JRadioButton();
        divExclu = new javax.swing.JRadioButton();
        divEspe = new javax.swing.JRadioButton();
        divText = new javax.swing.JTextField();
        divLabel = new javax.swing.JLabel();
        recBox = new javax.swing.JCheckBox();
        recArr = new javax.swing.JSpinner();
        recIzq = new javax.swing.JSpinner();
        recDer = new javax.swing.JSpinner();
        recAba = new javax.swing.JSpinner();
        recTodas = new javax.swing.JRadioButton();
        recExclu = new javax.swing.JRadioButton();
        recEspe = new javax.swing.JRadioButton();
        recText = new javax.swing.JTextField();
        recLabel = new javax.swing.JLabel();
        visPrevPagBtn = new javax.swing.JButton();
        manwhaBox = new javax.swing.JCheckBox();
        simeBox = new javax.swing.JCheckBox();
        visPrevBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        divIntBox = new javax.swing.JCheckBox();
        rutaCBox = new javax.swing.JComboBox<>();
        MenuBar = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        importDB = new javax.swing.JMenuItem();
        editDB = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();
        menuOpciones = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuInfo = new javax.swing.JMenu();
        infoEpubVita = new javax.swing.JMenuItem();
        infoLog = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("epubVita");

        jLabel1.setText("Nombre serie:");

        jLabel2.setText("Numero inicial:");

        jLabel3.setText("Direccion de lectura:");

        direccionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Izquierda a derecha", "Derecha a izquierda" }));

        jLabel4.setText("Idioma:");

        jComboIdioma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "es", "en", "ja" }));

        jLabel5.setText("Descripción:");

        jLabel6.setText("Autor:");

        jLabel7.setText("Publicador:");

        jLabel8.setFont(new java.awt.Font("Open Sans Light", 1, 18)); // NOI18N
        jLabel8.setText("Opcionales");

        BtContinuar.setText("Crear archivos");
        BtContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtContinuarActionPerformed(evt);
            }
        });

        jButtonBookDB.setText("Archivo book.db");
        jButtonBookDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBookDBActionPerformed(evt);
            }
        });

        mostrarBtn.setText("<<");
        mostrarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarBtnActionPerformed(evt);
            }
        });

        selecArchivoBtn.setText("Seleccionar Archivo(s)");
        selecArchivoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecArchivoBtnActionPerformed(evt);
            }
        });

        jNumero.setModel(new javax.swing.SpinnerNumberModel(1, null, null, 1));

        rutaBtn.setText("Ruta salida");
        rutaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rutaBtnActionPerformed(evt);
            }
        });

        calidadCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Baja", "Media", "Alta", "Muy alta" }));

        jLabel9.setText("Calidad PDFs:");

        javax.swing.GroupLayout menuPrincipalLayout = new javax.swing.GroupLayout(menuPrincipal);
        menuPrincipal.setLayout(menuPrincipalLayout);
        menuPrincipalLayout.setHorizontalGroup(
            menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPrincipalLayout.createSequentialGroup()
                        .addComponent(rutaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rutaBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPrincipalLayout.createSequentialGroup()
                        .addComponent(rutaDB, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBookDB))
                    .addGroup(menuPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(direccionCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(menuPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextNombre))
                    .addGroup(menuPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jNumero))
                    .addGroup(menuPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboIdioma, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calidadCB, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPrincipalLayout.createSequentialGroup()
                        .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextPublic)
                            .addComponent(jTextAutor)))
                    .addGroup(menuPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextDesc))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPrincipalLayout.createSequentialGroup()
                        .addComponent(barCarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(selecArchivoBtn)
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPrincipalLayout.createSequentialGroup()
                        .addComponent(BtContinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mostrarBtn)))
                .addGap(15, 15, 15))
        );
        menuPrincipalLayout.setVerticalGroup(
            menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(direccionCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonBookDB, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rutaDB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rutaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rutaBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calidadCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(selecArchivoBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barCarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addComponent(jLabel8)
                .addGap(19, 19, 19)
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextPublic, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(menuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtContinuar)
                    .addComponent(mostrarBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        divBox.setText("Dividir paginas");
        divBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                divBoxActionPerformed(evt);
            }
        });

        dividir.add(divTodas);
        divTodas.setText("Todas");
        divTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                divTodasActionPerformed(evt);
            }
        });

        dividir.add(divExclu);
        divExclu.setText("Excluir");
        divExclu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                divExcluActionPerformed(evt);
            }
        });

        dividir.add(divEspe);
        divEspe.setText("Especificas");
        divEspe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                divEspeActionPerformed(evt);
            }
        });

        divLabel.setText("Especifique:");

        recBox.setText("Recortar");
        recBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recBoxActionPerformed(evt);
            }
        });

        recArr.setModel(new javax.swing.SpinnerNumberModel(0, 0, 45, 1));

        recIzq.setModel(new javax.swing.SpinnerNumberModel(0, 0, 45, 1));

        recDer.setModel(new javax.swing.SpinnerNumberModel(0, 0, 45, 1));

        recAba.setModel(new javax.swing.SpinnerNumberModel(0, 0, 45, 1));

        recortar.add(recTodas);
        recTodas.setText("Todas");
        recTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recTodasActionPerformed(evt);
            }
        });

        recortar.add(recExclu);
        recExclu.setText("Excluir");
        recExclu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recExcluActionPerformed(evt);
            }
        });

        recortar.add(recEspe);
        recEspe.setText("Especificas");
        recEspe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recEspeActionPerformed(evt);
            }
        });

        recLabel.setText("Especifique:");

        visPrevPagBtn.setText("Vista previa paginas");
        visPrevPagBtn.setEnabled(false);
        visPrevPagBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visPrevPagBtnActionPerformed(evt);
            }
        });

        manwhaBox.setText("Manwha automatico");
        manwhaBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manwhaBoxActionPerformed(evt);
            }
        });

        simeBox.setText("simetrico");

        visPrevBtn.setText("Vista previa");
        visPrevBtn.setEnabled(false);
        visPrevBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visPrevBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        divIntBox.setText("Division inteligente");
        divIntBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                divIntBoxActionPerformed(evt);
            }
        });

        rutaCBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ux0:/book/bookModelData/guest/", "ur0:/book/ (experimental, no recomendada)" }));

        javax.swing.GroupLayout menuOcultoLayout = new javax.swing.GroupLayout(menuOculto);
        menuOculto.setLayout(menuOcultoLayout);
        menuOcultoLayout.setHorizontalGroup(
            menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuOcultoLayout.createSequentialGroup()
                .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuOcultoLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(menuOcultoLayout.createSequentialGroup()
                                .addComponent(divLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(divText, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(menuOcultoLayout.createSequentialGroup()
                                .addComponent(divTodas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(divExclu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(divEspe))
                            .addGroup(menuOcultoLayout.createSequentialGroup()
                                .addComponent(recTodas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(recExclu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(recEspe))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuOcultoLayout.createSequentialGroup()
                                .addComponent(recLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(recText, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(menuOcultoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(menuOcultoLayout.createSequentialGroup()
                                .addComponent(recBox)
                                .addGap(108, 108, 108)
                                .addComponent(simeBox))
                            .addGroup(menuOcultoLayout.createSequentialGroup()
                                .addComponent(divBox)
                                .addGap(72, 72, 72)
                                .addComponent(divIntBox)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuOcultoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuOcultoLayout.createSequentialGroup()
                        .addComponent(recIzq, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(recDer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuOcultoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(recAba, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(recArr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(105, 105, 105)))
                .addGap(71, 71, 71))
            .addGroup(menuOcultoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rutaCBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(menuOcultoLayout.createSequentialGroup()
                        .addComponent(manwhaBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(visPrevBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(visPrevPagBtn)))
                .addContainerGap())
        );
        menuOcultoLayout.setVerticalGroup(
            menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuOcultoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(divBox)
                    .addComponent(divIntBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(divTodas)
                    .addComponent(divExclu)
                    .addComponent(divEspe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(divText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(divLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recBox)
                    .addComponent(simeBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recTodas)
                    .addComponent(recExclu)
                    .addComponent(recEspe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(recArr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuOcultoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuOcultoLayout.createSequentialGroup()
                        .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(recDer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(recIzq, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67)))
                .addComponent(recAba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(visPrevBtn)
                    .addComponent(visPrevPagBtn)
                    .addComponent(manwhaBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rutaCBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuArchivo.setText("Archivo");

        importDB.setText("Importar db.xml");
        importDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importDBActionPerformed(evt);
            }
        });
        menuArchivo.add(importDB);

        editDB.setText("Editar book.db");
        editDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDBActionPerformed(evt);
            }
        });
        menuArchivo.add(editDB);

        exit.setText("Salir");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        menuArchivo.add(exit);

        MenuBar.add(menuArchivo);

        menuOpciones.setText("Opciones");

        jMenuItem1.setText("Configuraciones");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuOpciones.add(jMenuItem1);

        MenuBar.add(menuOpciones);

        menuInfo.setText("Información");

        infoEpubVita.setText("Sobre EpubVita");
        infoEpubVita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoEpubVitaActionPerformed(evt);
            }
        });
        menuInfo.add(infoEpubVita);

        infoLog.setText("Log de EpubVita");
        menuInfo.add(infoLog);

        MenuBar.add(menuInfo);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menuPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(menuOculto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuOculto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtContinuarActionPerformed
        if (selecArchivo) {
            boolean seleccion = true;
            if (rutaBookDB.equals("")) {
                int resp = JOptionPane.showConfirmDialog(this, "Estas seguro de no seleccionar el archivo: book.db?"
                        + "\nSi no lo selecionas se creara un db.xml para ingresar más tarde", "Advertencia", JOptionPane.WARNING_MESSAGE);
                seleccion = JOptionPane.OK_OPTION == resp;
            }
            if (seleccion) {
                setEnabled(false);
                bce = new barraCrearEpub(this);
                bce.setVisible(true);
                hiloVentanaBarra conteoBarra = new hiloVentanaBarra(this);
                conteoBarra.start();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar archivo(s)\n para continuar (minimo)");
        }
    }//GEN-LAST:event_BtContinuarActionPerformed

    private void jButtonBookDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBookDBActionPerformed
        selecBDB();
    }//GEN-LAST:event_jButtonBookDBActionPerformed

    private void mostrarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarBtnActionPerformed
        if (mostrarBtn.getText().equals("<<")) {
            menuOculto.setVisible(false);
            this.setSize(menuPrincipal.getWidth(), alto);
            mostrarBtn.setText(">>");
        } else {
            menuOculto.setVisible(true);
            this.setSize(ancho, alto);
            mostrarBtn.setText("<<");
        }
    }//GEN-LAST:event_mostrarBtnActionPerformed

    private void visPrevPagBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visPrevPagBtnActionPerformed
        if (listaThumb.isEmpty()) {
            obtenerThumb();
        }
        if (!listaThumb.isEmpty()) {
            new vistaPrevia(this, true, listaThumb).setVisible(true);
        }
    }//GEN-LAST:event_visPrevPagBtnActionPerformed

    private void divBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_divBoxActionPerformed
        desOcult();
    }//GEN-LAST:event_divBoxActionPerformed

    private void recBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recBoxActionPerformed
        desOcult();
    }//GEN-LAST:event_recBoxActionPerformed

    private void manwhaBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manwhaBoxActionPerformed
        desOcult();
    }//GEN-LAST:event_manwhaBoxActionPerformed

    private void divTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_divTodasActionPerformed
        desOcult();
    }//GEN-LAST:event_divTodasActionPerformed

    private void divExcluActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_divExcluActionPerformed
        desOcult();
    }//GEN-LAST:event_divExcluActionPerformed

    private void divEspeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_divEspeActionPerformed
        desOcult();
    }//GEN-LAST:event_divEspeActionPerformed

    private void recTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recTodasActionPerformed
        desOcult();
    }//GEN-LAST:event_recTodasActionPerformed

    private void recExcluActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recExcluActionPerformed
        desOcult();
    }//GEN-LAST:event_recExcluActionPerformed

    private void recEspeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recEspeActionPerformed
        desOcult();
    }//GEN-LAST:event_recEspeActionPerformed

    private void divIntBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_divIntBoxActionPerformed
        desOcult();
    }//GEN-LAST:event_divIntBoxActionPerformed

    private void selecArchivoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecArchivoBtnActionPerformed
        selecArchivo();
    }//GEN-LAST:event_selecArchivoBtnActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitActionPerformed

    private void editDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDBActionPerformed
        if (selecBDB()) {
            new modificarBook(rutaDB.getText()).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_editDBActionPerformed

    private void rutaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rutaBtnActionPerformed
        selectRuta();
    }//GEN-LAST:event_rutaBtnActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new vistaConf(this, true).setVisible(true);
        cargarConfiguraciones();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void infoEpubVitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoEpubVitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_infoEpubVitaActionPerformed

    private void importDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importDBActionPerformed
        if (selecDBXML()) {
            if (selecBDB()) {
                importarDBXml();
                JOptionPane.showMessageDialog(this, "Libros importados a book.db");
                new modificarBook(rutaDB.getText()).setVisible(true);
                this.dispose();
            }
        }
    }//GEN-LAST:event_importDBActionPerformed

    private void visPrevBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visPrevBtnActionPerformed
        if (listaThumb.isEmpty()) {
            obtenerThumb();
        }
        if (!listaThumb.isEmpty()) {
            utilImg utilimg = new utilImg();
            thumbName = "test_thumb1.jpg";
            Dimension dm = utilimg.thumbnail(150, System.getProperty("java.io.tmpdir") + sep, "img_0_Thumb.jpg", thumbName);
            imgPanel Imagen1 = new imgPanel(dm, thumbName);
            jPanel1.add(Imagen1);
            jPanel1.repaint();
        }
    }//GEN-LAST:event_visPrevBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtContinuar;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JProgressBar barCarga;
    private javax.swing.JComboBox<String> calidadCB;
    private javax.swing.JComboBox<String> direccionCB;
    private javax.swing.JCheckBox divBox;
    private javax.swing.JRadioButton divEspe;
    private javax.swing.JRadioButton divExclu;
    private javax.swing.JCheckBox divIntBox;
    private javax.swing.JLabel divLabel;
    private javax.swing.JTextField divText;
    private javax.swing.JRadioButton divTodas;
    private javax.swing.ButtonGroup dividir;
    private javax.swing.JMenuItem editDB;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenuItem importDB;
    private javax.swing.JMenuItem infoEpubVita;
    private javax.swing.JMenuItem infoLog;
    private javax.swing.JButton jButtonBookDB;
    private javax.swing.JComboBox<String> jComboIdioma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JSpinner jNumero;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextAutor;
    private javax.swing.JTextField jTextDesc;
    private javax.swing.JTextField jTextNombre;
    private javax.swing.JTextField jTextPublic;
    private javax.swing.JCheckBox manwhaBox;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuInfo;
    private javax.swing.JPanel menuOculto;
    private javax.swing.JMenu menuOpciones;
    private javax.swing.JPanel menuPrincipal;
    private javax.swing.JButton mostrarBtn;
    private javax.swing.JSpinner recAba;
    private javax.swing.JSpinner recArr;
    private javax.swing.JCheckBox recBox;
    private javax.swing.JSpinner recDer;
    private javax.swing.JRadioButton recEspe;
    private javax.swing.JRadioButton recExclu;
    private javax.swing.JSpinner recIzq;
    private javax.swing.JLabel recLabel;
    private javax.swing.JTextField recText;
    private javax.swing.JRadioButton recTodas;
    private javax.swing.ButtonGroup recortar;
    private javax.swing.JButton rutaBtn;
    private javax.swing.JComboBox<String> rutaCBox;
    private javax.swing.JLabel rutaDB;
    private javax.swing.JLabel rutaLabel;
    private javax.swing.JButton selecArchivoBtn;
    private javax.swing.JCheckBox simeBox;
    private javax.swing.JButton visPrevBtn;
    private javax.swing.JButton visPrevPagBtn;
    // End of variables declaration//GEN-END:variables
    private class hiloBarra extends Thread {

        @Override
        public void run() {
            obtenerDatLibro();

            javax.swing.JFileChooser filechooser = new javax.swing.JFileChooser();

            filechooser.setDialogTitle("Seleccione comic o pdf");

            filechooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);

            filechooser.addChoosableFileFilter(new FileNameExtensionFilter("ZIP", "zip"));
            filechooser.addChoosableFileFilter(new FileNameExtensionFilter("CBR", "cbr"));
            filechooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF", "pdf"));
            filechooser.setMultiSelectionEnabled(true);
            filechooser.setAcceptAllFileFilterUsed(false);

            if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                jTextNombre.setEnabled(false);
                jNumero.setEnabled(false);
                rutaBtn.setEnabled(false);
                BtContinuar.setEnabled(false);
                final File file[] = filechooser.getSelectedFiles();

                if (!filechooser.getFileFilter().getDescription().equals("PDF")) {
                    calidadCB.setEnabled(false);
                    zip utilZip = new zip(rutaSalida);
                    porcentaje = 0;
                    barCarga.setValue(porcentaje);
                    for (int i = 0; i < file.length; i++) {
                        utilZip.unzip(file[i].getAbsolutePath(), libr.getNombre() + " " + (i + num));
                        listaImage.add(utilZip.getNomImage());
                        porcentaje = (i * 100) / file.length;
                        barCarga.setValue(porcentaje);
                    }
                    porcentaje = 100;
                    barCarga.setValue(porcentaje);
                } else {
                    calidadCB.setEnabled(false);
                    pdf extrac = new pdf();
                    porcentaje = 0;
                    barCarga.setValue(porcentaje);
                    for (int i = 0; i < file.length; i++) {
                        porcentaje = (i * 100) / file.length;
                        barCarga.setValue(porcentaje);

                        extrac.prepararPdf(file[i].getAbsolutePath(), libr.getNombre() + " " + (i + num), rutaSalida, calidadCB.getSelectedIndex());
                        int pags = extrac.cantidadPag(file[i].getAbsolutePath());
                        for (int j = 0; j < pags; j++) {
                            extrac.convertirSig();
                            double id = i, jd = j, pagsd = pags, lengthd = file.length;
                            porcentaje = (int) (((id + ((jd + 1) / pagsd)) * 100) / lengthd);
                            barCarga.setValue(porcentaje);
                        }
                        listaImage.add(extrac.getNomImage());
                    }
                    porcentaje = 100;
                    barCarga.setValue(porcentaje);
                }
                selecArchivo = true;
                visPrevBtn.setEnabled(true);
                visPrevPagBtn.setEnabled(true);
                BtContinuar.setEnabled(true);
            }
            if (!selecArchivo) {
                selecArchivoBtn.setEnabled(true);
            }
        }
    }

    private class hiloVentanaBarra extends Thread {

        public hiloVentanaBarra(Principal ventanaP) {
        }

        @Override
        public void run() {
            if (mostrarBtn.getText().equals("<<")) {
                bce.estado("Aplicando opciones avanzadas...");
                aplicarOpcionesAvan();
            }
            bce.estado("Renombrando Archivos...");
            renombrarImage();
            bce.estado("Creando archivos Epub...");
            crearArchivos();
            bce.estado(100, "Archivos creados!");
            bce.botonVisible();
        }
    }
}
