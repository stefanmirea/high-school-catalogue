package grafic;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import liceu.*;

public class ListenerStergeMaterie implements ActionListener
{
    private ArrayList<Materie> materii;
    private JList lista;
    private PanouEditeazaMaterii pem;
    private Profesor profesorSelectat;
    private Secretar secretarLogat;
    
    public ListenerStergeMaterie(JList lista)
    {
        this.lista = lista;
    }
    
    public void setDate(ArrayList<Materie> materii, Profesor profesorSelectat,
            Secretar secretarLogat, PanouEditeazaMaterii pem)
    {
        this.profesorSelectat = profesorSelectat;
        this.materii = materii;
        this.secretarLogat = secretarLogat;
        this.pem = pem;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        Materie materie = materii.get(lista.getSelectedIndex());
        for (Iterator<Clasa> i = pem.iteratorClase(); i.hasNext(); )
        {
            Clasa clasa = i.next();
            HashMap<Materie, HashMap<Clasa, Profesor>> d =
                    Centralizator.getInstance().getDictionar();
            if (d.get(materie).get(clasa) == profesorSelectat)
                secretarLogat.editeazaMaterieProfesor(profesorSelectat, materie,
                        clasa, false);
        }
        
        DefaultListModel model = (DefaultListModel)lista.getModel();
        int index = lista.getSelectedIndex();
        if (index == -1)
            return;
        model.remove(index);
        materii.remove(index);
        
        pem.getPanouChecks().activeaza();
        pem.getPanouListaMaterii().getSterge().setEnabled(false);
    }
}
