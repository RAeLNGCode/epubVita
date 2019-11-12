package vista;

import controlador.sqlite;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.libro;

public class modificarBook extends javax.swing.JFrame {

    sqlite conector;
    ArrayList<libro> listaLibros;

    public modificarBook(String ruta) {
        initComponents();
        this.setLocationRelativeTo(null);
        conector = new sqlite(ruta);
        listaLibros = new ArrayList<>();
        cargarTabla();
    }

    private void cargarTabla() {
        listaLibros = conector.obtenerLibros();
        DefaultTableModel modelo = new DefaultTableModel(new Object[][]{},
                new String[]{
                    "Id", "Codigo", "Nombre", "Numero", "Autor", "Descripcion", "Ruta", "Leido"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //esto hace que las celdas no sean editables directamente
                return false;
            }
        };
        Object[] fila = new Object[8];
        for (libro libr : listaLibros) {
            fila[0] = libr.getId();
            fila[1] = libr.getCodigo();
            fila[2] = libr.getNombre();
            fila[3] = libr.getNumero();
            fila[4] = libr.getCreador();
            fila[5] = libr.getDescripcion();
            fila[6] = libr.getRuta();
            if (libr.isLeido()) {
                fila[7] = "Si";
            } else {
                fila[7] = "No";
            }
            modelo.addRow(fila);
        }
        tablaLibro.setModel(modelo);
        TableRowSorter<TableModel> ordenTabla = new TableRowSorter<TableModel>(modelo);
        tablaLibro.setRowSorter(ordenTabla);
    }

    private void eliminarLibro() {
        DefaultTableModel model = (DefaultTableModel) tablaLibro.getModel();

        int a[] = tablaLibro.getSelectedRows();

        if (tablaLibro.getSelectedRowCount() > 0) {
            for (int i = 0; i < a.length; i++) {
                int j = a[i];
                for (libro libr : listaLibros) {
                    if (libr.getCodigo().equals((String) model.getValueAt(j, 1))) {
                        conector.eliminarLb(libr.getCodigo());
                        listaLibros.remove(libr);
                        cargarTabla();
                        controlador.log.escribirLog("Eliminado " + libr.getNombre() + " " + libr.getNumero(), 0);
                        break;
                    }
                }
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
        }
    }
    
    private void editarLibro() {
        DefaultTableModel model = (DefaultTableModel) tablaLibro.getModel();

        int a[] = tablaLibro.getSelectedRows();

        if (tablaLibro.getSelectedRowCount() > 0) {
            for (int i = 0; i < a.length; i++) {
                int j = a[i];
                for (libro libr : listaLibros) {
                    if (libr.getCodigo().equals((String) model.getValueAt(j, 1))) {
                        controlador.log.escribirLog("Editando " + libr.getNombre() + " " + libr.getNumero(), 0);
                        editarLibro edLibro=new editarLibro(this, rootPaneCheckingEnabled,conector, libr);
                        edLibro.setVisible(true);
                        cargarTabla();
                        break;
                    }
                }
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
        }
    }
    
    private void marcarNoLeido() {
        DefaultTableModel model = (DefaultTableModel) tablaLibro.getModel();

        int a[] = tablaLibro.getSelectedRows();

        if (tablaLibro.getSelectedRowCount() > 0) {
            for (int i = 0; i < a.length; i++) {
                int j = a[i];
                for (libro libr : listaLibros) {
                    if (libr.getCodigo().equals((String) model.getValueAt(j, 1))) {
                        conector.marcarNoLeido(libr.getCodigo());
                        cargarTabla();
                        controlador.log.escribirLog("Marcando como no leido " + libr.getNombre() + " " + libr.getNumero(), 0);
                        break;
                    }
                }
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
        }
    }

    private void cambiarRutaArch() {
        DefaultTableModel model = (DefaultTableModel) tablaLibro.getModel();

        int a[] = tablaLibro.getSelectedRows();

        if (tablaLibro.getSelectedRowCount() > 0) {
            for (int i = 0; i < a.length; i++) {
                int j = a[i];
                for (libro libr : listaLibros) {
                    if (libr.getCodigo().equals((String) model.getValueAt(j, 1))) {
                        new cambiarRutaArch(this, true, conector, libr).setVisible(true);
                        cargarTabla();
                        break;
                    }
                }
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaLibro = new javax.swing.JTable();
        cambiarRutaBtn = new javax.swing.JButton();
        eliminarLibroBtn = new javax.swing.JButton();
        editarBtn = new javax.swing.JButton();
        cambiarRutaArchBtn = new javax.swing.JButton();
        noLeidoBtn = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaLibro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Codigo", "Nombre", "Numero", "Autor", "Descripcion", "Ruta", "Leido"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaLibro);

        cambiarRutaBtn.setText("cambiar ruta por nombre");
        cambiarRutaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarRutaBtnActionPerformed(evt);
            }
        });

        eliminarLibroBtn.setText("Eliminar");
        eliminarLibroBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarLibroBtnActionPerformed(evt);
            }
        });

        editarBtn.setText("Editar");
        editarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarBtnActionPerformed(evt);
            }
        });

        cambiarRutaArchBtn.setText("Cambiar ruta selección");
        cambiarRutaArchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarRutaArchBtnActionPerformed(evt);
            }
        });

        noLeidoBtn.setText("No leidos");
        noLeidoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noLeidoBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cambiarRutaBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cambiarRutaArchBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(noLeidoBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editarBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarLibroBtn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cambiarRutaBtn)
                    .addComponent(eliminarLibroBtn)
                    .addComponent(editarBtn)
                    .addComponent(cambiarRutaArchBtn)
                    .addComponent(noLeidoBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cambiarRutaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarRutaBtnActionPerformed
        new cambiarRuta(this, true, conector).setVisible(true);
        cargarTabla();
    }//GEN-LAST:event_cambiarRutaBtnActionPerformed

    private void eliminarLibroBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarLibroBtnActionPerformed
        eliminarLibro();
    }//GEN-LAST:event_eliminarLibroBtnActionPerformed

    private void cambiarRutaArchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarRutaArchBtnActionPerformed
        cambiarRutaArch();
    }//GEN-LAST:event_cambiarRutaArchBtnActionPerformed

    private void noLeidoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noLeidoBtnActionPerformed
        marcarNoLeido();
    }//GEN-LAST:event_noLeidoBtnActionPerformed

    private void editarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarBtnActionPerformed
        editarLibro();
    }//GEN-LAST:event_editarBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cambiarRutaArchBtn;
    private javax.swing.JButton cambiarRutaBtn;
    private javax.swing.JButton editarBtn;
    private javax.swing.JButton eliminarLibroBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton noLeidoBtn;
    private javax.swing.JTable tablaLibro;
    // End of variables declaration//GEN-END:variables
}
