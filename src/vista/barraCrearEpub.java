
package vista;

public class barraCrearEpub extends javax.swing.JFrame {

    Principal ventanaP;
    
    public barraCrearEpub(Principal ventanaP) {
        initComponents();
        this.setLocationRelativeTo(ventanaP);
        panelFinal.setVisible(false);
        this.ventanaP=ventanaP;
    }

    public void estado(int porcentaje,String estado){
        estadoBar.setIndeterminate(false);
        estadoBar.setValue(porcentaje);
        estadoLabel.setText(estado);
    }
    
    public void estado(String estado){
        estadoBar.setIndeterminate(true);
        estadoLabel.setText(estado);
    }
    
    public void botonVisible(){
        panelFinal.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFinal = new javax.swing.JPanel();
        aceptarBtn = new javax.swing.JButton();
        panelPrincipal = new javax.swing.JPanel();
        estadoBar = new javax.swing.JProgressBar();
        estadoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        aceptarBtn.setText("Aceptar");
        aceptarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFinalLayout = new javax.swing.GroupLayout(panelFinal);
        panelFinal.setLayout(panelFinalLayout);
        panelFinalLayout.setHorizontalGroup(
            panelFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFinalLayout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addComponent(aceptarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );
        panelFinalLayout.setVerticalGroup(
            panelFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFinalLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(aceptarBtn)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        estadoLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        estadoLabel.setText("Archivos creados!");

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(estadoBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(estadoLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(estadoBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(estadoLabel)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(panelFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void aceptarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarBtnActionPerformed
        ventanaP.dispose();
        this.dispose();
    }//GEN-LAST:event_aceptarBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptarBtn;
    private javax.swing.JProgressBar estadoBar;
    private javax.swing.JLabel estadoLabel;
    private javax.swing.JPanel panelFinal;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
