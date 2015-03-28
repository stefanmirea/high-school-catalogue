package grafic;

import java.awt.event.*;
import javax.swing.*;
import liceu.*;
import java.awt.*;
import java.util.*;
import java.text.*;

class ListenerAbsentaOK implements ActionListener
{
    private SituatieMaterieBaza smb;
    private int semestru;
    
    public ListenerAbsentaOK(SituatieMaterieBaza smb, int semestru)
    {
        this.smb = smb;
        this.semestru = semestru;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        PanouTextButon panou;
        panou = (PanouTextButon)((JButton)e.getSource()).getParent();
        boolean dataCorecta = true;
        String sirData = panou.getText();
        Date data;
        try
        {
            DateFormat formatData = new SimpleDateFormat("dd.MM.yyyy");
            formatData.setLenient(false);
            data = formatData.parse(sirData);
        }
        catch (ParseException exceptie)
        {
            JOptionPane.showMessageDialog(panou.getTopLevelAncestor(),
                    "Dată incorectă. Introduceţi data în format ZZ.LL.AAAA.",
                    "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for (Iterator<SituatieMaterieBaza.Absenta> it =
                smb.iteratorAbsente(semestru); it.hasNext(); )
            if (it.next().getData().equals(data))
            {
                JOptionPane.showMessageDialog(panou.getTopLevelAncestor(),
                    "Nu puteţi adăuga două absenţe cu aceeaşi dată.",
                    "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
        ((Profesor)((Fereastra)panou.getTopLevelAncestor()).
                getUtilizatorLogat()).adaugaAbsenta(smb, semestru, data);

        JPanel panouAbs = (JPanel)panou.getParent();
        panouAbs.remove(panou);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(4, 4, 4, 4);
        c.gridy = 1;
        JPanel butoane = new JPanel(new GridLayout(2, 1, 4, 4));
        butoane.setBackground(Color.white);
        JButton adaugaAbs = new JButton("Adăugaţi");
        adaugaAbs.addActionListener(new ListenerAdaugaAbsenta(smb, semestru));
        butoane.add(adaugaAbs);
        butoane.add(new JButton("Modificaţi"));
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(4, 4, 4, 4);
        panouAbs.add(butoane, c);
            
        Component[] componente = panouAbs.getComponents();
        for (int i = 0; i < componente.length; ++i)
            if (componente[i].getClass().getName().toString().
                        equals("javax.swing.JLabel"))
            {
                JLabel listaAbs = ((JLabel)componente[i]);
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
                    s = s + (zi < 10 ? "0" : "") + zi + (luna < 10 ? ".0" : ".")
                            + luna;
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
                listaAbs.setText(s + "</div></html>");
            }
        
        Component panouProf = panouAbs.getParent();
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
        
        int linieTabel = tabel.getSelectedRow();
        int coloanaTabel = 3 + clasaSelectata.getNumarMaterii() + index;
        tabel.setValueAt("" + (Integer.parseInt((String)tabel.getValueAt(
                linieTabel, coloanaTabel)) + 1), linieTabel, coloanaTabel);
        coloanaTabel = 3 + 2 * clasaSelectata.getNumarMaterii();
        tabel.setValueAt("" + (Integer.parseInt((String)tabel.getValueAt(
                linieTabel, coloanaTabel)) + 1), linieTabel, coloanaTabel);
            
        panouAbs.revalidate();
        panouAbs.repaint();
    }
}

public class ListenerAdaugaAbsenta implements ActionListener
{
    private SituatieMaterieBaza smb;
    private int semestru;

    public ListenerAdaugaAbsenta(SituatieMaterieBaza smb, int semestru)
    {
        this.smb = smb;
        this.semestru = semestru;
    }
    
    public String dataCurenta()
    {
        Calendar calendar = Calendar.getInstance();
        DateFormat formatData = new SimpleDateFormat("dd.MM.yyyy");
        return formatData.format(calendar.getTime());
    }
    
    public void actionPerformed(ActionEvent e)
    {
        JButton buton = (JButton)e.getSource();
        JPanel butoane = (JPanel)buton.getParent();
        JPanel panou = (JPanel)butoane.getParent();
        panou.remove(butoane);
        
        // panou cu campul text si butonul OK
        PanouTextButon panouForm = new PanouTextButon();
        panouForm.setLayout(new GridLayout(1, 2, 4, 4));
        panouForm.setBackground(Color.white);
        
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(4, 4, 4, 4);
        c.gridy = 1;
        panou.add(panouForm, c);
        
        final JTextField campData = new JTextField(dataCurenta());
        panouForm.add(campData);
        JButton butonOK = new JButton("OK");
        panouForm.add(butonOK);
        
        butonOK.addActionListener(new ListenerAbsentaOK(smb, semestru));
        
        panou.revalidate();
        panou.repaint();
    }
}
