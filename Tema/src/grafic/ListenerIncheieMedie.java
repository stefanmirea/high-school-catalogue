package grafic;

import java.awt.*;
import javax.swing.*;
import liceu.*;
import java.awt.event.*;

public class ListenerIncheieMedie implements ActionListener
{
    private SituatieMaterieBaza smb;
    private int semestru;

    public ListenerIncheieMedie(SituatieMaterieBaza smb, int semestru)
    {
        this.smb = smb;
        this.semestru = semestru;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        JButton buton = (JButton)e.getSource();
        JPanel panou = (JPanel)buton.getParent();
        
        ((Profesor)((Fereastra)panou.getTopLevelAncestor()).
                    getUtilizatorLogat()).incheieMedie(smb, semestru);
        if (smb.getMedieSem(semestru) == -1)
        {
            if (smb.getNumarNote(semestru) < 2)
                JOptionPane.showMessageDialog(panou.getTopLevelAncestor(),
                    "Trebuie să existe cel puţin două note pentru ca media să" +
                    " poată fi încheiată.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(panou.getTopLevelAncestor(),
                    "Treceţi mai întâi nota de la teză.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        panou.remove(buton);
        JLabel medieSem = new JLabel("" + smb.getMedieSem(semestru));
        panou.add(medieSem);
        
        Component panouProf = panou.getParent();
        while (!(panouProf instanceof PanouProf))
            panouProf = panouProf.getParent();
        ((PanouProf)panouProf).valueChanged(null);
    }
}
