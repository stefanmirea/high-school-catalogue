package grafic;

import java.awt.event.*;
import liceu.*;
import javax.swing.*;
import java.util.*;

public class ListenerDateElev implements ActionListener
{
    private Elev elevLogat;
    private int optiuneAscultata;
    
    public ListenerDateElev(Elev elevLogat, int optiuneAscultata)
    {
        this.elevLogat = elevLogat;
        this.optiuneAscultata = optiuneAscultata;
    }
    
    // afisare date personale in workspace
    public void actionPerformed(ActionEvent e)
    {
        Fereastra f = null;
        if (e.getSource() instanceof JMenuItem)
        {
            JMenuItem itemMeniu = (JMenuItem)e.getSource();  
            JPopupMenu meniuPopup = (JPopupMenu)itemMeniu.getParent();  
            JComponent inv = (JComponent)meniuPopup.getInvoker();
            f = (Fereastra)inv.getTopLevelAncestor();
        }
        if (optiuneAscultata == 0)
            elevLogat.afiseazaDatePersonale(f);
        else
        {
            // verific daca elevul se afla intr-o clasa
            boolean gasit = false;
            for (Iterator<Clasa> i = Centralizator.getInstance().
                    iteratorClase(); i.hasNext(); )
            {
                Clasa clasa = i.next();
                for (Iterator<Elev> j = clasa.iteratorElevi(); j.hasNext(); )
                    if (j.next() == elevLogat)
                    {
                        gasit = true;
                        break;
                    }
                if (gasit)
                    break;
            }
            if (gasit == false)
                JOptionPane.showMessageDialog(f, "Momentan nu sunteţi înregi" +
                        "strat în nicio clasă. Luaţi legătura cu secretariat" +
                        "ul.", "Elev neînregistrat",
                        JOptionPane.WARNING_MESSAGE);
            else
                elevLogat.afiseazaSituatiaScolara(f);
        }
    }
}
