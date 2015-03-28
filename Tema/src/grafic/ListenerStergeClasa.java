package grafic;

import java.awt.event.*;
import javax.swing.*;
import liceu.*;
import java.util.*;

public class ListenerStergeClasa implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        JButton adauga = (JButton)e.getSource();
        PanouListaClase panouClase;
        panouClase = (PanouListaClase)adauga.getParent().getParent();
        String id = (String)panouClase.getLista().getSelectedValue();
        Centralizator centralizator = Centralizator.getInstance();
        Clasa clasa = null;
        for (Iterator<Clasa> i = centralizator.iteratorClase(); i.hasNext(); )
        {
            clasa = i.next();
            if (clasa.getId().equals(id))
                break;
        }
        PanouClaseElevi panouClaseElevi;
        panouClaseElevi = (PanouClaseElevi)panouClase.getParent();
        panouClaseElevi.getListaClase().remove(clasa);
        DefaultListModel model = (DefaultListModel)panouClase.getLista().
                getModel();
        model.removeElement(id);
        
        Fereastra f = (Fereastra)SwingUtilities.getWindowAncestor(adauga);
        ((Secretar)f.getUtilizatorLogat()).stergeClasa(clasa);
    }
}
