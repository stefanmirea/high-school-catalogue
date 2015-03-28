package grafic;

import java.awt.event.*;
import javax.swing.*;
import liceu.*;
import javax.swing.table.*;

public class ListenerStergeElev implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        JButton adauga = (JButton)e.getSource();
        Fereastra f = (Fereastra)SwingUtilities.getWindowAncestor(adauga);
        PanouClaseElevi panouClase = (PanouClaseElevi)(adauga.getParent().
                getParent().getParent());
        Clasa clasa = panouClase.getClasa();
        JTable tabel = (JTable)panouClase.getTabel();
        int poz = tabel.getSelectedRow();
        String numeUt = (String)(tabel.getValueAt(poz, 3));
        Centralizator centralizator = Centralizator.getInstance();
        Elev elev = (Elev)(centralizator.getUtilizatorByNumeUtilizator(numeUt));
        ((Secretar)f.getUtilizatorLogat()).editeazaClasa(clasa, elev, 0);
        DefaultTableModel model = (DefaultTableModel)tabel.getModel();
        model.removeRow(poz);
        model.fireTableDataChanged();
    }
}
