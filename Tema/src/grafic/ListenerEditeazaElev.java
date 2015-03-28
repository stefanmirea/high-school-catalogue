package grafic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import liceu.*;
import javax.swing.table.*;

public class ListenerEditeazaElev implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        JButton editeaza = (JButton)e.getSource();
        Fereastra f = (Fereastra)SwingUtilities.getWindowAncestor(editeaza);
        PanouClaseElevi panouClase = (PanouClaseElevi)(editeaza.getParent().
                getParent().getParent());
        //Clasa clasa = panouClase.getClasa();
        JTable tabel = (JTable)panouClase.getTabel();
        int poz = tabel.getSelectedRow();
        String numeUt = (String)(tabel.getValueAt(poz, 3));
        Centralizator centralizator = Centralizator.getInstance();
        Elev elev = (Elev)(centralizator.getUtilizatorByNumeUtilizator(numeUt));
        //((Secretar)f.getUtilizatorLogat()).editeazaClasa(clasa, elev, 0);
        //

        JTextField nume = new JTextField(elev.getNume(), 20);
        JTextField prenume = new JTextField(elev.getPrenume(), 20);
        JTextField cnp = new JTextField(elev.getCNP(), 20);

        JPanel campuri = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        campuri.add(new JLabel("Nume:"), c);
        ++c.gridy;
        campuri.add(nume, c);
        ++c.gridy;
        campuri.add(new JLabel("Prenume:"), c);
        ++c.gridy;
        campuri.add(prenume, c);
        ++c.gridy;
        campuri.add(new JLabel("CNP:"), c);
        ++c.gridy;
        campuri.add(cnp, c);
        
        int rasp = JOptionPane.showConfirmDialog(null, campuri, "Editare date "
                + elev.getNumeUtilizator(), JOptionPane.OK_CANCEL_OPTION);
        if (rasp == JOptionPane.OK_OPTION)
        {
            ((Secretar)f.getUtilizatorLogat()).editeazaElev(elev,
                    nume.getText(), prenume.getText(), cnp.getText());
            DefaultTableModel model = (DefaultTableModel)tabel.getModel();
            model.setValueAt(elev.getNume(), poz, 1);
            model.setValueAt(elev.getPrenume(), poz, 2);
            model.setValueAt(elev.getCNP(), poz, 4);
            model.setValueAt(elev.getDataNasterii(), poz, 5);
            model.fireTableDataChanged();
        }
    }
}
