package grafic;

import java.awt.*;
import javax.swing.*;
import liceu.*;
import java.awt.event.*;
import java.util.*;

public class ListenerCalculeazaMedii implements ActionListener
{
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
        Secretar secretar = (Secretar)f.getUtilizatorLogat();
        Centralizator centralizator = Centralizator.getInstance();
        Vector<String> esecuri = new Vector<String>();
        boolean succes = true;
        for (Iterator<Elev> i = centralizator.iteratorElevi(); i.hasNext(); )
        {
            Elev elev = i.next();
            if (secretar.calculeazaMediaGenerala(elev) == false)
            {
                esecuri.add(elev.getNume() + " " + elev.getPrenume());
                succes = false;
            }
        }
        if (succes)
            JOptionPane.showMessageDialog(f, "Mediile au fost calculate cu"
                    + " succes.");
        else
        {
            JPanel panou = new JPanel();
            panou.setLayout(new BoxLayout(panou, BoxLayout.PAGE_AXIS));
            JLabel label1 = new JLabel("Următorilor elevi nu li s-a putut " +
                    "calcula media generală:");
            label1.setAlignmentX(Component.LEFT_ALIGNMENT);
            panou.add(label1);
            JScrollPane sp = new JScrollPane(new JList(esecuri));
            sp.setAlignmentX(Component.LEFT_ALIGNMENT);
            panou.add(sp);
            JLabel label2 = new JLabel("Motiv: situaţiile nu sunt încheiate la"+
                    " toate materiile.");
            label2.setAlignmentX(Component.LEFT_ALIGNMENT);
            panou.add(label2);
            JOptionPane.showMessageDialog(null, panou, "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
