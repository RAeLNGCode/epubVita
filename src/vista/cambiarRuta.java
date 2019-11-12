package vista;

import controlador.sqlite;
import java.util.ArrayList;
import modelo.libro;

public class cambiarRuta extends javax.swing.JDialog {

    sqlite conector;
    ArrayList<String> listaNomSer;
    ArrayList<libro> listaLibros;

    public cambiarRuta(java.awt.Frame parent, boolean modal, sqlite conector) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        this.conector = conector;
        cargarNomSer();
        listaLibros = new ArrayList<>();
    }

    private void cambiarRuta() {
        listaLibros = conector.obtenerLibros((String) nomSerBox.getSelectedItem());
        for (libro libr : listaLibros) {
            String nombreArchivo = libr.getRuta();
            int index;
            do {
                index = nombreArchivo.indexOf("/");
                nombreArchivo = nombreArchivo.substring(index + 1);
            } while (index != -1);
            String rutaNueva="";
            switch (rutaCBox.getSelectedIndex()) {
                case 0:
                    rutaNueva="ux0:/book/bookModelData/guest/"+nombreArchivo;
                    break;
                case 1:
                    rutaNueva="ur0:/book/"+nombreArchivo;
                    break;
            }
            conector.cambiarRuta(libr.getCodigo(), rutaNueva);
        }
    }

    private void cargarNomSer() {
        listaNomSer = new ArrayList<>();
        listaNomSer = conector.obtenerListaNomSer();
        nomSerBox.removeAllItems();
        for (String nomSer : listaNomSer) {
            if (!nomSer.equals("ガイドブック")) {
                nomSerBox.addItem(nomSer);
            }
        }
        nomSerBox.setSelectedIndex(-1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nomSerBox = new javax.swing.JComboBox<>();
        rutaCBox = new javax.swing.JComboBox<>();
        cambiarBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        rutaCBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ux0:/book/bookModelData/guest/", "ur0:/book/ (experimental, no recomendada)" }));

        cambiarBtn.setText("Cambiar");
        cambiarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre serie:");

        jLabel2.setText("Ruta:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cambiarBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(nomSerBox, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(rutaCBox, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rutaCBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomSerBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(cambiarBtn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cambiarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarBtnActionPerformed
        cambiarRuta();
        this.dispose();
    }//GEN-LAST:event_cambiarBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cambiarBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JComboBox<String> nomSerBox;
    private javax.swing.JComboBox<String> rutaCBox;
    // End of variables declaration//GEN-END:variables
}
