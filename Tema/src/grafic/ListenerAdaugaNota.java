package grafic;

import java.awt.event.*;
import javax.swing.*;
import liceu.*;
import java.awt.*;
import java.util.*;

class ListenerNotaOK implements ActionListener
{
    private SituatieMaterieBaza smb;
    private int semestru;
    
    public ListenerNotaOK(SituatieMaterieBaza smb, int semestru)
    {
        this.smb = smb;
        this.semestru = semestru;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        PanouTextButon panou;
        panou = (PanouTextButon)((JButton)e.getSource()).getParent();
        int nota = 0;
        boolean problemaParsare = false;
        try
        {
            nota = Integer.parseInt(panou.getText());
        }
        catch (Exception exceptie)
        {
            problemaParsare = true;
        }
        if (problemaParsare || nota < 1 || nota > 10)
            JOptionPane.showMessageDialog(panou.getTopLevelAncestor(),
                    "Notă incorectă.", "Eroare", JOptionPane.ERROR_MESSAGE);
        else
        {
            ((Profesor)((Fereastra)panou.getTopLevelAncestor()).
                    getUtilizatorLogat()).adaugaNota(smb, semestru, nota);
            JPanel panouNote = (JPanel)panou.getParent();
            panouNote.remove(panou);
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.PAGE_END;
            c.insets = new Insets(4, 4, 4, 4);
            c.gridy = 1;
            JButton adauga = new JButton("Adăugaţi");
            adauga.addActionListener(new ListenerAdaugaNota(smb, semestru));
            panouNote.add(adauga, c);
            
            Component[] componente = panouNote.getComponents();
            for (int i = 0; i < componente.length; ++i)
                if (componente[i].getClass().getName().toString().
                        equals("javax.swing.JLabel"))
                {
                    JLabel listaNote = ((JLabel)componente[i]);
                    int numarNote = 0;
                    String s = "<html><div style=\"text-align: center;\">";
                    for (Iterator<Integer> it = smb.iteratorNote(semestru);
                        it.hasNext(); )
                        {
                            s = s + it.next() + "<br/>";
                            ++numarNote;
                        }
                    for (int contor = 0; contor < 3 - numarNote; ++contor)
                        s = s + "<br/>";
                    listaNote.setText(s + "</div></html>");
                    break;
                }
            
            panouNote.revalidate();
            panouNote.repaint();
        }
    }
}

public class ListenerAdaugaNota implements ActionListener
{
    private SituatieMaterieBaza smb;
    private int semestru;

    public ListenerAdaugaNota(SituatieMaterieBaza smb, int semestru)
    {
        this.smb = smb;
        this.semestru = semestru;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        JButton buton = (JButton)e.getSource();
        JPanel panou = (JPanel)buton.getParent();
        panou.remove(buton);
        
        // panou cu campul text si butonul OK
        PanouTextButon panouForm = new PanouTextButon();
        panouForm.setLayout(new GridLayout(1, 2, 4, 4));
        panouForm.setBackground(Color.white);
        
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(4, 4, 4, 4);
        c.gridy = 1;
        panou.add(panouForm, c);
        
        final JTextField campNota = new JTextField("< notă >");
        panouForm.add(campNota);
        JButton butonOK = new JButton("OK");
        panouForm.add(butonOK);
        
        campNota.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                if (campNota.getText().equals("< notă >"))
                    campNota.setText("");
            }
        });
        
        butonOK.addActionListener(new ListenerNotaOK(smb, semestru));
        
        panou.revalidate();
        panou.repaint();
    }
}
