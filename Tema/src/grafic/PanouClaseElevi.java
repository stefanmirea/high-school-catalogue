package grafic;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import liceu.*;

public class PanouClaseElevi extends JPanel
{
    private PanouListaClase panouClase;
    private PanouListaElevi panouElevi;
    private ArrayList<Clasa> clase;
    private Clasa clasaSelectata;
    
    public PanouClaseElevi()
    {
        setLayout(new GridLayout(1, 2, 5, 0));
        panouClase = new PanouListaClase();
        add(panouClase);
        panouElevi = new PanouListaElevi();
        add(panouElevi);
        DefaultListModel modelLista = new DefaultListModel();
        clase = new ArrayList<Clasa>();
        Centralizator centralizator = Centralizator.getInstance();
        for (Iterator<Clasa> i = centralizator.iteratorClase(); i.hasNext(); )
        {
            Clasa clasa = i.next();
            clase.add(clasa);
            modelLista.addElement(clasa.getId());
        }
        panouClase.adaugaModel(modelLista);
    }
    
    public ArrayList<Clasa> getListaClase()
    {
        return clase;
    }
    
    public JTable getTabel()
    {
        return panouElevi.getTabel();
    }
    
    public JButton getAdaugaElevi()
    {
        return panouElevi.getAdauga();
    }
    
    public Clasa getClasa()
    {
        return clasaSelectata;
    }
    
    public void setClasa(Clasa clasaSelectata)
    {
        this.clasaSelectata = clasaSelectata;
    }
}
