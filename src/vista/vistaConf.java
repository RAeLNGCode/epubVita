package vista;

import controlador.configuraciones;
import java.io.File;
import javax.swing.JFileChooser;
import modelo.config;

public class vistaConf extends javax.swing.JDialog {

    configuraciones ctrlConf;
    
    public vistaConf(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        ctrlConf = new configuraciones();
        cargarConfiguraciones();
    }

    private void cargarConfiguraciones() {
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
        rutaLabel.setText(conf.getRuta());
        borrarTempCheck.setSelected(conf.isBorrarTemp());
        direccionCB.setSelectedIndex(conf.getDireccion());
        calidadCB.setSelectedIndex(conf.getCalidad());
    }
    
    private void guardarConfiguraciones(){
        config conf = new config();
        conf.setRuta(rutaLabel.getText());
        conf.setBorrarTemp(borrarTempCheck.isSelected());
        conf.setDireccion(direccionCB.getSelectedIndex());
        conf.setCalidad(calidadCB.getSelectedIndex());
        ctrlConf.guardarProperties(conf);
        this.dispose();
    }
    
    private void selectRuta(){
        javax.swing.JFileChooser filechooser = new javax.swing.JFileChooser();

        filechooser.setDialogTitle("Seleccione carpeta de salida");

        filechooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        filechooser.setAcceptAllFileFilterUsed(false);

        if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            final File file = filechooser.getSelectedFile();
            String rutaCarp = file.getAbsolutePath();
            rutaLabel.setText(rutaCarp);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rutaBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        rutaLabel = new javax.swing.JLabel();
        calidadCB = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        direccionCB = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        guardarBtn = new javax.swing.JButton();
        borrarTempCheck = new javax.swing.JCheckBox();
        abrirFinalCheck = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuraciones por defecto");
        setResizable(false);

        rutaBtn.setText("Cambiar");
        rutaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rutaBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Ruta por defecto:");

        rutaLabel.setText("Label Ruta");

        calidadCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Baja", "Media", "Alta", "Muy alta" }));

        jLabel2.setText("Calidad por defecto para los PDFs:");

        jLabel3.setText("Direccion de lectura por defecto:");

        direccionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Izquierda a derecha", "Derecha a izquierda" }));

        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("*Nota:*");

        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Izquierda a derecha=Libros, Comic, etc");

        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setText("Derecha a izquierda=Manga, Nobelas Ligeras, etc");

        guardarBtn.setText("Guardar");
        guardarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarBtnActionPerformed(evt);
            }
        });

        borrarTempCheck.setSelected(true);
        borrarTempCheck.setText("Borrar carpeta Temp resultante");

        abrirFinalCheck.setSelected(true);
        abrirFinalCheck.setText("Abrir carpeta al finalizar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rutaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rutaBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(guardarBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(calidadCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(direccionCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(borrarTempCheck)
                            .addComponent(abrirFinalCheck))
                        .addGap(0, 151, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rutaBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rutaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abrirFinalCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(borrarTempCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(direccionCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calidadCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(guardarBtn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void guardarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarBtnActionPerformed
        guardarConfiguraciones();
    }//GEN-LAST:event_guardarBtnActionPerformed

    private void rutaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rutaBtnActionPerformed
        selectRuta();
    }//GEN-LAST:event_rutaBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox abrirFinalCheck;
    private javax.swing.JCheckBox borrarTempCheck;
    private javax.swing.JComboBox<String> calidadCB;
    private javax.swing.JComboBox<String> direccionCB;
    private javax.swing.JButton guardarBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JButton rutaBtn;
    private javax.swing.JLabel rutaLabel;
    // End of variables declaration//GEN-END:variables
}
