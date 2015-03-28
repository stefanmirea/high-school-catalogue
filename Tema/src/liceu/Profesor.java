package liceu;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import grafic.*;

public class Profesor extends Utilizator implements IProfesor
{
    private final Color gri = new Color(220, 220, 220);
    private ArrayList<Materie> materii;
    
    public Profesor(String numeUtilizator, String parola, String nume,
            String prenume)
    {
        super(numeUtilizator, parola, nume, prenume);
        materii = new ArrayList<Materie>();
    }
    
    public void adaugaMaterie(Materie materie)
    {
        materii.add(materie);
    }
    
    public void stergeMaterie(Materie materie)
    {
        materii.remove(materie);
    }
    
    public boolean preda(Materie materie)
    {
        return materii.indexOf(materie) != -1;
    }
    
    public Iterator<Materie> iteratorMaterii()
    {
        return materii.iterator();
    }
    
    public class ComparatorNumeric implements Comparator<String>
    {
        public int compare(String s1, String s2)
        {
            if (s1.equals("necalculată"))
                return -1;
            if (s2.equals("necalculată"))
                return 1;
            float a = Float.parseFloat(s1);
            float b = Float.parseFloat(s2);
            if (a < b)
                return -1;
            else if (a == b)
                return 0;
            return 1;
        }
    }

    public void listeazaElevi(Clasa clasa, JTable tabel)
    {
        DefaultTableModel model = new DefaultTableModel()
        {
            public Class getColumnClass(int columnIndex)
            {
                return String.class;
            }
            public boolean isCellEditable(int linie, int coloana)
            {
                return false;
            }
        };
        tabel.setModel(model);
        TableRowSorter<TableModel> sorter = new
                TableRowSorter<TableModel>(model);
        tabel.setRowSorter(sorter);
        ComparatorNumeric comparator = new ComparatorNumeric();
        
        ArrayList<Vector<String>> coloane = new ArrayList<Vector<String>>();
        
        // coloane pentru nr. crt., nume, prenume, medii pe materii, absente pe
        // materii, numar total de absente si medie generala
        for (int i = 0; i < 5 + 2 * clasa.getNumarMaterii(); ++i)
            coloane.add(new Vector<String>());
        int numarCurent = 0;
        for (Iterator<Elev> i = clasa.iteratorElevi(); i.hasNext(); )
        {
            Elev elev = i.next();
            coloane.get(0).add("" + ++numarCurent);
            coloane.get(1).add(elev.getNume());
            coloane.get(2).add(elev.getPrenume());
            int index = 3, numarAbsente = 0;
            for (Iterator<Materie> j = clasa.iteratorMaterii(); j.hasNext(); )
            {
                SituatieMaterieBaza smb = clasa.getCatalog().getDictionar().
                        get(elev).get(j.next());
                if (smb.getMedieAnuala() == -1)
                    coloane.get(index).add("necalculată");
                else
                    coloane.get(index).add("" + smb.getMedieAnuala());
                int temp = smb.getNumarAbsente(1) + smb.getNumarAbsente(2);
                coloane.get(index + clasa.getNumarMaterii()).add("" + temp);
                numarAbsente += temp;
                ++index;
            }
            index += clasa.getNumarMaterii();
            coloane.get(index++).add("" + numarAbsente);
            if (elev.getMedieGenerala() == -1)
                coloane.get(index).add("necalculată");
            else
                coloane.get(index).add("" + elev.getMedieGenerala());
        }
        model.addColumn("<html><div style=\"text-align: center;\">Nr.<br/>" +
                "crt.</div></html>", coloane.get(0));
        model.addColumn("Nume", coloane.get(1));
        model.addColumn("Prenume", coloane.get(2));
        int index = 3;
        for (Iterator<Materie> i = clasa.iteratorMaterii(); i.hasNext(); )
        {
            model.addColumn("<html><div style=\"text-align: center;\">Medie" +
                    "<br/>" + numeMaterieTabel(i.next().getNume(), false) +
                    "</div></html>", coloane.get(index));
            ++index;
        }
        for (Iterator<Materie> i = clasa.iteratorMaterii(); i.hasNext(); )
        {
            model.addColumn("<html><div style=\"text-align: center;\">Absenţe" +
                    "<br/>" + numeMaterieTabel(i.next().getNume(), false) +
                    "</div></html>", coloane.get(index));
            ++index;
        }
        model.addColumn("<html><div style=\"text-align: center;\">Număr<br/>" +
                "absenţe</div></html>", coloane.get(index));
        ++index;
        model.addColumn("<html><div style=\"text-align: center;\">Medie<br/>" +
                "generală</div></html>", coloane.get(index));
        
        sorter.setComparator(0, comparator);
        for (int i = 3; i < 5 + 2 * clasa.getNumarMaterii(); ++i)
            sorter.setComparator(i, comparator);
        tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabel.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabel.getColumnModel().getColumn(2).setPreferredWidth(140);
    }
    
    public JPanel generarePanou(String text, int tipCelula,
            SituatieMaterieBaza smb, int semestru)
    {
        JPanel panou = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        panou.setBorder(BorderFactory.createLineBorder(gri));
        panou.setBackground(Color.white);
        
        switch (tipCelula)
        {
            case 0: // celula obisnuita
                panou.add(new JLabel(text));
                break;
            case 1: // celula cu note si buton de adaugare
                c.weightx = 0.5;
                c.weighty = 0.5;
                c.gridx = 0;
                c.gridy = 0;
                c.anchor = GridBagConstraints.PAGE_START;
                panou.add(new JLabel(text), c);
                
                // exista buton de adaugare de note doar daca situatia nu a
                // fost incheiata inca
                if (smb.getMedieSem(semestru) == -1)
                {
                    c.gridy = 1;
                    c.anchor = GridBagConstraints.PAGE_END;
                    c.insets = new Insets(4, 4, 4, 4);
                    JButton adaugaNota = new JButton("Adăugaţi");
                    adaugaNota.addActionListener(new ListenerAdaugaNota(smb,
                            semestru));
                    panou.add(adaugaNota, c);
                }
                break;
            case 2: // celula cu absente si butoane de adaugare si modificare
                c.weightx = 0.5;
                c.weighty = 0.5;
                c.gridx = 0;
                c.gridy = 0;
                c.anchor = GridBagConstraints.PAGE_START;
                panou.add(new JLabel(text), c);
                
                // se pot adauga / modifica absente doar daca situatia nu a fost
                // incheiata inca
                if (smb.getMedieSem(semestru) == -1)
                {
                    c.gridy = 1;
                    JPanel butoane;
                    if (smb.getNumarAbsente(semestru) > 0)
                        butoane = new JPanel(new GridLayout(2, 1, 4, 4));
                    else
                        butoane = new JPanel(new GridBagLayout());
                    butoane.setBackground(Color.white);
                    JButton adaugaAbs = new JButton("Adăugaţi");
                    adaugaAbs.addActionListener(new ListenerAdaugaAbsenta(smb,
                            semestru));
                    butoane.add(adaugaAbs);
                    
                    // daca nu exista absente, nu mai apare butonul "Modificati"
                    if (smb.getNumarAbsente(semestru) > 0)
                    {
                        JButton modifica = new JButton("Modificaţi");
                        modifica.addActionListener(new
                                ListenerModificaAbsente(smb, semestru));
                        butoane.add(modifica);
                    }
                    
                    c.anchor = GridBagConstraints.PAGE_END;
                    c.insets = new Insets(4, 4, 4, 4);
                    panou.add(butoane, c);
                }
                break;
            case 3: // celula cu buton de trecere a notei la teza
                JButton trece = new JButton("Treceţi");
                trece.addActionListener(new ListenerAdaugaTeza(
                        (SituatieMaterieCuTeza)smb, semestru));
                c.insets = new Insets(4, 4, 4, 4);
                panou.add(trece, c);
                break;
            case 4:
                JButton incheie = new JButton("Încheiaţi");
                incheie.addActionListener(new ListenerIncheieMedie(smb,
                        semestru));
                c.insets = new Insets(4, 4, 4, 4);
                panou.add(incheie, c);
                break;
            case 5:
                JButton calculeaza = new JButton("Calculaţi");
                calculeaza.addActionListener(new ListenerCalculeazaAnual(smb));
                c.insets = new Insets(4, 4, 4, 4);
                panou.add(calculeaza, c);
                break;
        }
        return panou;
    }
    
    public String numeMaterieTabel(String numeMaterieMemorie, boolean majuscula)
    {
        StringBuilder sb = new StringBuilder(numeMaterieMemorie);

        if (majuscula && sb.charAt(0) != Character.toUpperCase(sb.charAt(0)))
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    
        // ... si cu diacritice (cel putin 'a' de la sfarsit)
        if (numeMaterieMemorie.endsWith("a"))
            sb.setCharAt(numeMaterieMemorie.length() - 1, 'ă');

        return sb.toString();
    }
    
    public void afiseazaSituatia(HashMap<Materie, SituatieMaterieBaza> situatie,
            JPanel panou)
    {
        Component[] componente = panou.getComponents();
        for (int i = 0; i < componente.length; ++i)
            panou.remove(componente[i]);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        panou.add(generarePanou("", 0, null, 0), c);

        c.gridy = 1;
        c.gridheight = 2;
        panou.add(generarePanou("Semestrul 1", 0, null, 0), c);

        c.gridy = 3;
        c.gridheight = 1;
        panou.add(generarePanou("Teză", 0, null, 0), c);
        
        c.gridy = 4;
        c.gridheight = 1;
        panou.add(generarePanou("Media semestrială", 0, null, 0), c);

        c.gridy = 5;
        c.gridheight = 2;
        panou.add(generarePanou("Semestrul 2", 0, null, 0), c);

        c.gridy = 7;
        c.gridheight = 1;
        panou.add(generarePanou("Teză", 0, null, 0), c);
        
        c.gridy = 8;
        c.gridheight = 1;
        panou.add(generarePanou("Media semestrială", 0, null, 0), c);
        
        c.gridy = 9;
        c.gridheight = 1;
        panou.add(generarePanou("Media anuală", 0, null, 0), c);
        
        String s;
        int coloana = 1;
        for (Map.Entry<Materie, SituatieMaterieBaza> i : situatie.entrySet())
        {
            c.gridx = coloana;
            
            c.gridy = 0;
            c.gridheight = 1;
            c.gridwidth = 2;
            String numeMaterie = i.getKey().getNume();
            
            panou.add(generarePanou(numeMaterieTabel(numeMaterie, true), 0,
                    null, 0), c);
            c.gridwidth = 1;
            
            for (int sem = 1; sem <= 2; ++sem)
            {
                // absente
                c.gridx = coloana;
                c.gridy = (sem - 1) * 4 + 1;
                c.gridwidth = 1;
                c.gridheight = 1;
                panou.add(generarePanou("Absenţe", 0, null, 0), c);
                ++c.gridy;
                c.gridheight = 2;
                s = "<html><div style=\"text-align: center;\">";
                for (Iterator<SituatieMaterieBaza.Absenta> it =
                        i.getValue().iteratorAbsente(sem); it.hasNext(); )
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
                panou.add(generarePanou(s + "</div></html>", 2, i.getValue(),
                        sem), c);
                
                // note
                ++c.gridx;
                c.gridy = (sem - 1) * 4 + 1;
                c.gridheight = 1;
                panou.add(generarePanou("Note", 0, null, 0), c);
            
                ++c.gridy;
                c.gridheight = 1;
                int numarNote = 0;
                s = "<html><div style=\"text-align: center;\">";
                for (Iterator<Integer> j = i.getValue().iteratorNote(sem);
                        j.hasNext(); )
                {
                    s = s + j.next() + "<br/>";
                    ++numarNote;
                }
                // ma asigur ca celulele cu note au minim 4 randuri
                for (int contor = 0; contor < 3 - numarNote; ++contor)
                    s = s + "<br/>";
                panou.add(generarePanou(s + "</div></html>", 1, i.getValue(),
                        sem), c);
                
                // teza
                ++c.gridy;
                c.gridheight = 1;
                if (i.getKey().areTeza())
                {
                    SituatieMaterieCuTeza smt =
                            (SituatieMaterieCuTeza)i.getValue();
                    int notaTeza = smt.getNotaTeza(sem);
                    if (notaTeza == -1)
                        panou.add(generarePanou("", 3, smt, sem), c);
                    else
                        panou.add(generarePanou("" + smt.getNotaTeza(sem), 0,
                                null, 0), c);
                }
                else
                    panou.add(generarePanou("N/A", 0, null, 0), c);
                
                ++c.gridy;
                --c.gridx;
                c.gridheight = 1;
                c.gridwidth = 2;
                int medieSemestriala = i.getValue().getMedieSem(sem);
                if (medieSemestriala == -1)
                    panou.add(generarePanou("", 4, i.getValue(), sem), c);
                else
                    panou.add(generarePanou("" + medieSemestriala, 0, null, 0),
                            c);
            }

            ++c.gridy;
            c.gridheight = 1;
            c.gridwidth = 2;
            if (i.getValue().getMedieAnuala() == -1)
                panou.add(generarePanou("", 5, i.getValue(), 0), c);
            else
                panou.add(generarePanou("" + i.getValue().getMedieAnuala(), 0,
                        null, 0), c);
            
            coloana += 2;
        }
        
        panou.revalidate();
        panou.repaint();
    }
    
    public void ordoneazaElevi(){}

    public void adaugaNota(SituatieMaterieBaza smb, int semestru, int nota)
    {
        smb.adaugaNota(semestru, nota);
    }
         
    public void incheieMedie(SituatieMaterieBaza smb, int semestru)
    {
        smb.calculeazaMedieSem(semestru);
    }
    
    public void calculeazaMedieAnuala(SituatieMaterieBaza smb)
    {
        smb.calculeazaMedieAnuala();
    }
    
    public void adaugaAbsenta(SituatieMaterieBaza smb, int semestru, Date data)
    {
        smb.adaugaAbsenta(semestru, 2, data);
    }
    
    public void modificaAbsenta(SituatieMaterieBaza smb, int semestru, int
            indiceAbsenta, int status)
    {
        smb.modificaAbsenta(semestru, indiceAbsenta, status);
    }
    
    public String toString()
    {
        String ret = super.toString() + "Materii: ";
        for (Iterator<Materie> i = materii.iterator(); i.hasNext(); )
            ret = ret + i.next().getNume() + " ";
        return ret + "\n";
    }
}
