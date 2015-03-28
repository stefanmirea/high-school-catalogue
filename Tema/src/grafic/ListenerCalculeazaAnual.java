package grafic;

import java.awt.*;
import javax.swing.*;
import liceu.*;
import java.awt.event.*;
import java.util.*;

public class ListenerCalculeazaAnual implements ActionListener
{
    private SituatieMaterieBaza smb;

    public ListenerCalculeazaAnual(SituatieMaterieBaza smb)
    {
        this.smb = smb;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        JButton buton = (JButton)e.getSource();
        JPanel panou = (JPanel)buton.getParent();
        
        ((Profesor)((Fereastra)panou.getTopLevelAncestor()).
                    getUtilizatorLogat()).calculeazaMedieAnuala(smb);
        if (smb.getMedieAnuala() == -1)
        {
            JOptionPane.showMessageDialog(panou.getTopLevelAncestor(), "Mai "
                    + "întâi încheiaţi situaţia pe ambele semestre.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        panou.remove(buton);
        JLabel medieSem = new JLabel("" + smb.getMedieAnuala());
        panou.add(medieSem);
        
        Component panouProf = panou.getParent();
        while (!(panouProf instanceof PanouProf))
            panouProf = panouProf.getParent();
        ((PanouProf)panouProf).valueChanged(null);
        
        JTable tabel = ((PanouProf)panouProf).getTabel();
        Clasa clasaSelectata = ((PanouProf)panouProf).getClasaSelectata();
        int index = 0;
        for (Iterator<Materie> i = clasaSelectata.iteratorMaterii();
                i.hasNext(); ++index)
            if (i.next().equals(smb.getMaterie()))
                break;
        tabel.setValueAt("" + smb.getMedieAnuala(), tabel.getSelectedRow(), 3 +
                index);
        
        panou.revalidate();
        panou.repaint();
    }
}
