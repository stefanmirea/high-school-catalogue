package grafic;

import java.awt.event.*;
import javax.swing.*;
import liceu.*;
import java.awt.*;
import java.util.*;
import java.text.*;

class ListenerSalveaza implements ActionListener
{
    private SituatieMaterieBaza smb;
    private int semestru;
    
    public ListenerSalveaza(SituatieMaterieBaza smb, int semestru)
    {
        this.smb = smb;
        this.semestru = semestru;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        ArrayList<JComboBox> listaComb;
        listaComb = ((ButonSalvareAbsente)e.getSource()).getCombos();

        Profesor prof = (Profesor)((Fereastra)((JButton)e.getSource()).
                getTopLevelAncestor()).getUtilizatorLogat();
        int nr = 0;
        for (Iterator<JComboBox> it = listaComb.iterator(); it.hasNext(); ++nr)
            prof.modificaAbsenta(smb, semestru, nr,
                    it.next().getSelectedIndex());
        
        JPanel panou = (JPanel)((JButton)e.getSource()).getParent();
        Component[] componente = panou.getComponents();
        for (int i = 0; i < componente.length; ++i)
            if (componente[i].getClass().getName().toString().
                    equals("javax.swing.JPanel") || componente[i].getClass()
                    .getName().toString().equals("grafic.ButonSalvareAbsente"))
                panou.remove(componente[i]);

        GridBagConstraints c = new GridBagConstraints();
        String s = "<html><div style=\"text-align: center;\">";
        for (Iterator<SituatieMaterieBaza.Absenta> it =
                smb.iteratorAbsente(semestru); it.hasNext(); )
        {
            SituatieMaterieBaza.Absenta absenta = it.next();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(absenta.getData());
            int zi = calendar.get(Calendar.DAY_OF_MONTH);
            int luna = calendar.get(Calendar.MONTH) + 1;
            int an = calendar.get(Calendar.YEAR);
            s = s + (zi < 10 ? "0" : "") + zi + (luna < 10 ? ".0" : ".") + luna;
            switch (absenta.getStatus())
            {
                case 0:
                    s = s + " (M)";
                    break;
                case 1:
                    s = s + " (N)";
                    break;
                default:
                    s = s + " (?)";
            }
                    s = s + "<br/>";
        }
        s = s + "</div></html>";
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        panou.add(new JLabel(s), c);
        c.gridy = 1;
        JPanel butoane;
        if (smb.getNumarAbsente(semestru) > 0)
            butoane = new JPanel(new GridLayout(2, 1, 4, 4));
        else
            butoane = new JPanel(new GridBagLayout());
        butoane.setBackground(Color.white);
        JButton adaugaAbs = new JButton("Adăugaţi");
        adaugaAbs.addActionListener(new ListenerAdaugaAbsenta(smb, semestru));
        butoane.add(adaugaAbs);
        JButton modifica = new JButton("Modificaţi");
        modifica.addActionListener(new ListenerModificaAbsente(smb, semestru));
        butoane.add(modifica);
                    
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(4, 4, 4, 4);
        panou.add(butoane, c);
            
        panou.revalidate();
        panou.repaint();
    }
}

class ButonSalvareAbsente extends JButton
{
    private ArrayList<JComboBox> listaComb;
    
    public ButonSalvareAbsente(String nume, ArrayList<JComboBox> listaComb)
    {
        super(nume);
        this.listaComb = listaComb;
    }
    
    public ArrayList<JComboBox> getCombos()
    {
        return listaComb;
    }
}

public class ListenerModificaAbsente implements ActionListener
{
    private SituatieMaterieBaza smb;
    private int semestru;

    public ListenerModificaAbsente(SituatieMaterieBaza smb, int semestru)
    {
        this.smb = smb;
        this.semestru = semestru;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        JButton buton = (JButton)e.getSource();
        JPanel panou = (JPanel)buton.getParent().getParent();
        
        Component[] componente = panou.getComponents();
        for (int i = 0; i < componente.length; ++i)
            if (componente[i].getClass().getName().toString().
                    equals("javax.swing.JLabel") || componente[i].
                    getClass().getName().toString().
                    equals("javax.swing.JPanel"))
                panou.remove(componente[i]);
        
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 0.5;
        c.gridx = c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        
        // panou de absente cu combobox-uri
        ArrayList<JComboBox> listaComb = new ArrayList<JComboBox>();
        JPanel panouEditare = new JPanel(new GridBagLayout());
        panouEditare.setBackground(Color.white);
        GridBagConstraints cEditare = new GridBagConstraints();
        cEditare.gridy = 0;
        Vector<String> itemi = new Vector<String>();
        itemi.add("M");
        itemi.add("N");
        itemi.add("?");
        for (Iterator<SituatieMaterieBaza.Absenta> it =
                smb.iteratorAbsente(semestru); it.hasNext(); ++cEditare.gridy)
        {
            cEditare.gridx = 0;
            SituatieMaterieBaza.Absenta abs = it.next();
            Date data = abs.getData();
            panouEditare.add(new JLabel(new SimpleDateFormat("dd.MM ( ").
                    format(data)), cEditare);
            cEditare.gridx = 1;
            cEditare.insets = new Insets(1, 0, 1, 0);
            listaComb.add(new JComboBox(itemi));
            listaComb.get(listaComb.size() - 1).setSelectedIndex(
                    abs.getStatus());
            panouEditare.add(listaComb.get(listaComb.size() - 1), cEditare);
            cEditare.gridx = 2;
            cEditare.insets = new Insets(0, 0, 0, 0);
            panouEditare.add(new JLabel(" )"), cEditare);
        }
        panou.add(panouEditare);
        
        // buton de salvare
        JButton salveaza = new ButonSalvareAbsente("Salvaţi", listaComb);
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(4, 4, 4, 4);
        c.gridy = 1;
        salveaza.addActionListener(new ListenerSalveaza(smb, semestru));
        panou.add(salveaza, c);
        
        panou.revalidate();
        panou.repaint();
    }
}
