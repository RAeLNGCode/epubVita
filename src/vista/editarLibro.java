package vista;

import controlador.sqlite;
import modelo.libro;

public class editarLibro extends javax.swing.JDialog {

    private libro lib;
    sqlite conector;
    
    public editarLibro(java.awt.Frame parent, boolean modal, sqlite conector,libro lib) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        this.lib=lib;
        this.conector=conector;
        cargarLibro();
    }
    
    private void cargarLibro(){
        codigoText.setText(lib.getCodigo());
        nombreText.setText(lib.getNombre());
        numeroSpin.setValue(lib.getNumero());
        autorText.setText(lib.getCreador());
        descripText.setText(lib.getDescripcion());
        rutaCB.setSelectedItem(lib.getRuta());
        if(lib.isLeido()){
            leidoCB.setSelectedIndex(0);
        }else{
            leidoCB.setSelectedIndex(1);
        }
        if(lib.getRuta().contains("ux0:/book/bookModelData/guest/")){
            rutaCB.setSelectedIndex(0);
        }else{
            rutaCB.setSelectedIndex(1);
        }
    }

    private void guardarLibro() {
        lib.setNombre(nombreText.getText());
        lib.setNumero((int)numeroSpin.getValue());
        lib.setCreador(autorText.getText());
        lib.setDescripcion(descripText.getText());
        lib.setLeido(leidoCB.getSelectedIndex()==0);
        
        String nombreArchivo = lib.getRuta();
        int index;
        do {
            index = nombreArchivo.indexOf("/");
            nombreArchivo = nombreArchivo.substring(index + 1);
        } while (index != -1);
        String rutaNueva = "";
        switch (rutaCB.getSelectedIndex()) {
            case 0:
                rutaNueva = "ux0:/book/bookModelData/guest/" + nombreArchivo;
                break;
            case 1:
                rutaNueva = "ur0:/book/" + nombreArchivo;
                break;
        }
        lib.setRuta(rutaNueva);
        
        conector.modificarLibro(lib);
        controlador.log.escribirLog("Modificado " + lib.getCodigo(), 0);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        codigoText = new javax.swing.JLabel();
        autorText = new javax.swing.JTextField();
        descripText = new javax.swing.JTextField();
        rutaCB = new javax.swing.JComboBox<>();
        leidoCB = new javax.swing.JComboBox<>();
        guardarBtn = new javax.swing.JButton();
        cancelarBtn = new javax.swing.JButton();
        nombreText = new javax.swing.JTextField();
        numeroSpin = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Codigo:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Numero:");

        jLabel4.setText("Autor:");

        jLabel5.setText("Descripción:");

        jLabel6.setText("Ruta:");

        jLabel7.setText("Leido:");

        rutaCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ux0:/book/bookModelData/guest/", "ur0:/book/ (experimental, no recomendada)" }));

        leidoCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));

        guardarBtn.setText("Guardar");
        guardarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarBtnActionPerformed(evt);
            }
        });

        cancelarBtn.setText("Cancelar");
        cancelarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarBtnActionPerformed(evt);
            }
        });

        numeroSpin.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(21, 21, 21)
                        .addComponent(codigoText, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelarBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(guardarBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(autorText)
                            .addComponent(descripText)
                            .addComponent(rutaCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(leidoCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nombreText)
                            .addComponent(numeroSpin))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(codigoText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(nombreText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(numeroSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(autorText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(descripText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rutaCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(leidoCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guardarBtn)
                    .addComponent(cancelarBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelarBtnActionPerformed

    private void guardarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarBtnActionPerformed
        guardarLibro();
        this.dispose();
    }//GEN-LAST:event_guardarBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField autorText;
    private javax.swing.JButton cancelarBtn;
    private javax.swing.JLabel codigoText;
    private javax.swing.JTextField descripText;
    private javax.swing.JButton guardarBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JComboBox<String> leidoCB;
    private javax.swing.JTextField nombreText;
    private javax.swing.JSpinner numeroSpin;
    private javax.swing.JComboBox<String> rutaCB;
    // End of variables declaration//GEN-END:variables
}
