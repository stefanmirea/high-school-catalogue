package grafic;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import liceu.*;
import javax.swing.table.*;

public class ListenerAdaugaElev implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        JButton adauga = (JButton)e.getSource();
        Fereastra f = (Fereastra)SwingUtilities.getWindowAncestor(adauga);
        PanouClaseElevi panouClase = (PanouClaseElevi)(adauga.getParent().
                getParent().getParent());
        Clasa clasa = panouClase.getClasa();
        Vector<String> eleviRamasi = new Vector<String>();
        Centralizator centralizator = Centralizator.getInstance();
        for (Iterator<Elev> i = centralizator.iteratorElevi(); i.hasNext(); )
        {
            Elev elev = i.next();
            if (!clasa.contineElev(elev))
                eleviRamasi.add(elev.getNume() + " " + elev.getPrenume() +
                        " (" + elev.getNumeUtilizator() + ")");
        }
        String s = (String)JOptionPane.showInputDialog(f, "Alegeţi un elev:",
                "Selectare elev", JOptionPane.PLAIN_MESSAGE, null,
                eleviRamasi.toArray(), null);
        if (s != null)
        {
            String numeUtilizator = s.substring(s.indexOf('(') + 1,
                    s.indexOf(')'));
            Elev elev = null;
            for (Iterator<Elev> i = centralizator.iteratorElevi();
                    i.hasNext(); )
            {
                elev = i.next();
                if (elev.getNumeUtilizator().equals(numeUtilizator))
                    break;
            }
            ((Secretar)f.getUtilizatorLogat()).editeazaClasa(clasa, elev, 1);
            DefaultTableModel model = (DefaultTableModel)panouClase.getTabel().
                    getModel();
            Vector<String> rand = new Vector<String>();
            rand.add("" + (model.getRowCount() + 1));
            rand.add(elev.getNume());
            rand.add(elev.getPrenume());
            rand.add(elev.getNumeUtilizator());
            rand.add(elev.getCNP());
            rand.add(elev.getDataNasterii());
            model.addRow(rand);
            model.fireTableDataChanged();
        }
    }
}
