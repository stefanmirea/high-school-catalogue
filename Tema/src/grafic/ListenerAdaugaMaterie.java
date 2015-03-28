package grafic;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import liceu.*;

public class ListenerAdaugaMaterie implements ActionListener
{
    private ArrayList<Materie> materii;
    private JList lista;
    private JButton adauga; // ca sa se poata dezactiva
    
    public ListenerAdaugaMaterie(JList lista, JButton adauga)
    {
        this.lista = lista;
        this.adauga = adauga;
    }
    
    public void setMaterii(ArrayList<Materie> materii)
    {
        this.materii = materii;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        Set<Materie> toateMateriile = Centralizator.getInstance().
                getDictionar().keySet();
        int dim = toateMateriile.size() - materii.size();
        String deAdaugat[] = new String[dim];
        int nr = 0;
        for (Iterator<Materie> i = toateMateriile.iterator(); i.hasNext(); )
        {
            Materie materie = i.next();
            if (!materii.contains(materie))
                deAdaugat[nr++] = materie.getNume();
        }
        Fereastra f = (Fereastra)SwingUtilities.
                getWindowAncestor((Component)e.getSource());
        String s = (String)JOptionPane.showInputDialog(f,
                "Alegeţi o materie:",
                "Selectare materie", JOptionPane.PLAIN_MESSAGE, null, deAdaugat,
                null);
        if (s != null)
        {
            DefaultListModel model = (DefaultListModel)lista.getModel();
            model.addElement(s);
            for (Iterator<Materie> i = toateMateriile.iterator(); i.hasNext(); )
            {
                Materie materie = i.next();
                if (materie.getNume().equals(s))
                {
                    materii.add(materie);
                    break;
                }
            }
            if (dim == 1)
                adauga.setEnabled(false);
        }
    }
}
