package vista;

import controlador.sqlite;
import modelo.libro;

public class cambiarRutaArch extends javax.swing.JDialog {

    sqlite conector;
    libro libr;

    public cambiarRutaArch(java.awt.Frame parent, boolean modal, sqlite conector, libro libr) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        this.conector = conector;
        this.libr = libr;
        this.setTitle("Editar " + libr.getNombre() + " " + libr.getNumero());
        if(libr.getRuta().contains("ux0:/book/bookModelData/guest/")){
            rutaCBox.setSelectedIndex(0);
        }else{
            rutaCBox.setSelectedIndex(1);
        }
    }

    private void cambiarRuta() {
        String nombreArchivo = libr.getRuta();
        int index;
        do {
            index = nombreArchivo.indexOf("/");
            nombreArchivo = nombreArchivo.substring(index + 1);
        } while (index != -1);
        String rutaNueva = "";
        switch (rutaCBox.getSelectedIndex()) {
            case 0:
                rutaNueva = "ux0:/book/bookModelData/guest/" + nombreArchivo;
                break;
            case 1:
                rutaNueva = "ur0:/book/" + nombreArchivo;
                break;
        }
        conector.cambiarRuta(libr.getCodigo(), rutaNueva);
        controlador.log.escribirLog("Cambiando ruta a " + libr.getNombre() + " " + libr.getNumero(), 0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rutaCBox = new javax.swing.JComboBox<>();
        cambiarBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cancelarBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        rutaCBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ux0:/book/bookModelData/guest/", "ur0:/book/ (experimental, no recomendada)" }));

        cambiarBtn.setText("Cambiar");
        cambiarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarBtnActionPerformed(evt);
            }
        });

        jLabel2.setText("Ruta:");

        cancelarBtn.setText("Cancelar");
        cancelarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelarBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cambiarBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(rutaCBox, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(rutaCBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cambiarBtn)
                    .addComponent(cancelarBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cambiarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarBtnActionPerformed
        cambiarRuta();
        this.dispose();
    }//GEN-LAST:event_cambiarBtnActionPerformed

    private void cancelarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelarBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cambiarBtn;
    private javax.swing.JButton cancelarBtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JComboBox<String> rutaCBox;
    // End of variables declaration//GEN-END:variables
}
