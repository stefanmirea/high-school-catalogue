package grafic;

import liceu.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class PanouProf extends javax.swing.JPanel implements
        ListSelectionListener
{
    private Profesor profesor;
    private Clasa clasaSelectata;
    private Elev elevSelectat;
    private HashMap<Materie, SituatieMaterieBaza> hashAfisat;
    private Vector<String> comboVect;
    private DefaultComboBoxModel comboModel;
    private final Color gri = new Color(220, 220, 220);
    
    public PanouProf()
    {
        initComponents();
    }
    
    public void golestePanouSituatie()
    {
        elevSelectat = null;
        Component[] componente = jPanel1.getComponents();
        for (int i = 0; i < componente.length; ++i)
            jPanel1.remove(componente[i]);
        jPanel1.add(new JLabel("<html>Selectaţi un elev pentru<br/>a-i edita " +
                "situaţia şcolară.</html>"));
        revalidate();
        repaint();
    }
    
    public PanouProf(Profesor profesor)
    {
        initComponents();
        
        jPanel1.setLayout(new GridBagLayout());
        JLabel lungime = new JLabel();
        lungime.setPreferredSize(new Dimension(220, 20));
        jPanel1.add(lungime);
        this.profesor = profesor;
        comboVect = new Vector<String>();
        
        ArrayList<Clasa> clase;
        clase = Centralizator.getInstance().getClase(profesor);
        for (Iterator<Clasa> i = clase.iterator(); i.hasNext(); )
            comboVect.add(i.next().getId());
        Collections.sort(comboVect);
        comboVect.insertElementAt("Selectaţi o clasă", 0);
        
        comboModel = new DefaultComboBoxModel(comboVect);
        jComboBox1.setModel(comboModel);
        
        // listenerul pentru schimbarea selectiei
        jTable1.getSelectionModel().addListSelectionListener(this);
    }
    
    public JTable getTabel()
    {
        return jTable1;
    }
    
    public Clasa getClasaSelectata()
    {
        return clasaSelectata;
    }
    
    // schimbare selectie tabel
    public void valueChanged(ListSelectionEvent event)
    {
        int rand = jTable1.getSelectedRow();
        if (rand >= 0)
        {
            int numarCurent = Integer.parseInt(
                    (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            elevSelectat = clasaSelectata.getElev(numarCurent - 1);
                    
            // Tocmai s-a selectat elevul elev. Selectez un subhash al
            // hashului care ii corespunde lui in catalog
            HashMap<Materie, SituatieMaterieBaza> toateMateriile;
            HashMap<Materie, SituatieMaterieBaza> materiiPredate;
            materiiPredate = new HashMap<Materie, SituatieMaterieBaza>();
            toateMateriile = clasaSelectata.getCatalog().getDictionar()
                    .get(elevSelectat);
            for (Iterator<Materie> i = profesor.iteratorMaterii(); i.hasNext();)
            {
                Materie materie = i.next();
                if (toateMateriile.containsKey(materie))
                    materiiPredate.put(materie, toateMateriile.get(materie));
            }
            profesor.afiseazaSituatia(materiiPredate, jPanel1);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jSplitPane1.setResizeWeight(0.5);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 183, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel1);

        jSplitPane1.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (jComboBox1.getSelectedItem().equals("Selectaţi o clasă"))
            return;

        Object selectat = comboModel.getSelectedItem();
        if (comboVect.elementAt(0).equals("Selectaţi o clasă"))
        {
            comboVect.remove(0);
            comboModel = new DefaultComboBoxModel(comboVect);
            comboModel.setSelectedItem(selectat);
            jComboBox1.setModel(comboModel);
            jPanel1.setBackground(gri);
        }
        
        clasaSelectata = Centralizator.getInstance().getClasaById(
                (String)selectat);
        profesor.listeazaElevi(clasaSelectata, jTable1);
        
        golestePanouSituatie();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}