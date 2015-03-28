package grafic;

import java.awt.*;
import javax.swing.*;
import liceu.*;
import java.awt.event.*;

class ListenerTezaOK implements ActionListener
{
    private SituatieMaterieCuTeza smt;
    private int semestru;
    
    public ListenerTezaOK(SituatieMaterieCuTeza smt, int semestru)
    {
        this.smt = smt;
        this.semestru = semestru;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        PanouTextButon panou;
        panou = (PanouTextButon)((JButton)e.getSource()).getParent();
        int teza = 0;
        boolean problemaParsare = false;
        try
        {
            teza = Integer.parseInt(panou.getText());
        }
        catch (Exception exceptie)
        {
            problemaParsare = true;
        }
        if (problemaParsare || teza < 1 || teza > 10)
            JOptionPane.showMessageDialog(panou.getTopLevelAncestor(),
                    "Notă incorectă.", "Eroare", JOptionPane.ERROR_MESSAGE);
        else
        {
            smt.setNotaTeza(semestru, teza);
            JPanel panouTeza = (JPanel)panou.getParent();
            panouTeza.remove(panou);
            JLabel labelTeza = new JLabel("" + teza);
            panouTeza.add(labelTeza);
            
            panouTeza.revalidate();
            panouTeza.repaint();
        }
    }
}

public class ListenerAdaugaTeza implements ActionListener
{
    private SituatieMaterieCuTeza smt;
    private int semestru;

    public ListenerAdaugaTeza(SituatieMaterieCuTeza smt, int semestru)
    {
        this.smt = smt;
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
        c.insets = new Insets(4, 4, 4, 4);
        
        panou.add(panouForm, c);
        
        final JTextField campNota = new JTextField("< teză >");
        panouForm.add(campNota);
        JButton butonOK = new JButton("OK");
        panouForm.add(butonOK);
        
        campNota.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                if (campNota.getText().equals("< teză >"))
                    campNota.setText("");
            }
        });
        
        butonOK.addActionListener(new ListenerTezaOK(smt, semestru));
        
        panou.revalidate();
        panou.repaint();
    }
}
