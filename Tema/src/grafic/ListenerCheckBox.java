package grafic;

import java.awt.event.*;
import liceu.*;
import javax.swing.*;

public class ListenerCheckBox implements ActionListener
{
    private Profesor profesor;
    private Materie materie;
    private Clasa clasa;
    
    public ListenerCheckBox(Profesor profesor, Materie materie, Clasa clasa)
    {
        this.profesor = profesor;
        this.materie = materie;
        this.clasa = clasa;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        JCheckBox cb = (JCheckBox)e.getSource();
        Fereastra f = (Fereastra)SwingUtilities.getWindowAncestor(cb);
        ((Secretar)f.getUtilizatorLogat()).editeazaMaterieProfesor(profesor,
                materie, clasa, cb.isSelected());
    }
}
