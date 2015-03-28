package grafic;

import liceu.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class PanouSituatieScolara extends javax.swing.JPanel
{
    private final Color gri = new Color(220, 220, 220);
    private Elev elev;
    
    public PanouSituatieScolara()
    {
        initComponents();
    }
    
    public JPanel generarePanou(String text, int orientare)
    {
        JPanel panou;
        if (orientare == 0) // sus
            panou = new JPanel(new FlowLayout());
        else
            panou = new JPanel(new GridBagLayout());
        panou.setBorder(BorderFactory.createLineBorder(gri));
        panou.add(new JLabel(text));
        panou.setBackground(Color.white);
        return panou;
    }
    
    public String numeMaterieTabel(String numeMaterieMemorie)
    {
        StringBuilder sb = new StringBuilder(numeMaterieMemorie);

        // in catalog, materia apare cu majuscula...
        if (sb.charAt(0) != Character.toUpperCase(sb.charAt(0)))
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    
        // ... si cu diacritice (cel putin 'a' de la sfarsit)
        if (numeMaterieMemorie.endsWith("a"))
            sb.setCharAt(numeMaterieMemorie.length() - 1, 'ă');

        return sb.toString();
    }
    
    public PanouSituatieScolara(HashMap<Materie, SituatieMaterieBaza> hash,
            Elev elev)
    {
        initComponents();
        jPanel1.setBackground(gri);
        jPanel1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        jPanel1.add(generarePanou("", 1), c);

        c.gridy = 1;
        c.gridheight = 2;
        jPanel1.add(generarePanou("Semestrul 1", 1), c);

        c.gridy = 3;
        c.gridheight = 1;
        jPanel1.add(generarePanou("Teză", 1), c);
        
        c.gridy = 4;
        c.gridheight = 1;
        jPanel1.add(generarePanou("Media semestrială", 1), c);

        c.gridy = 5;
        c.gridheight = 2;
        jPanel1.add(generarePanou("Semestrul 2", 1), c);

        c.gridy = 7;
        c.gridheight = 1;
        jPanel1.add(generarePanou("Teză", 1), c);
        
        c.gridy = 8;
        c.gridheight = 1;
        jPanel1.add(generarePanou("Media semestrială", 1), c);
        
        c.gridy = 9;
        c.gridheight = 1;
        jPanel1.add(generarePanou("Media anuală", 1), c);
        
        c.gridy = 10;
        c.gridheight = 1;
        jPanel1.add(generarePanou("Media generală", 1), c);

        c.gridx = 1;
        c.gridwidth = 1 + 2 * hash.size();
        if (elev.getMedieGenerala() == -1)
            jPanel1.add(generarePanou("Necalculată", 1), c);
        else
            jPanel1.add(generarePanou("" + elev.getMedieGenerala(), 1), c);
        String s;
        int coloana = 1;
        for (Map.Entry<Materie, SituatieMaterieBaza> i : hash.entrySet())
        {
            c.gridx = coloana;
            
            c.gridy = 0;
            c.gridheight = 1;
            c.gridwidth = 2;
            String numeMaterie = i.getKey().getNume();
            
            jPanel1.add(generarePanou(numeMaterieTabel(numeMaterie), 1), c);
            c.gridwidth = 1;
            
            for (int sem = 1; sem <= 2; ++sem)
            {
                // absente
                c.gridx = coloana;
                c.gridy = (sem - 1) * 4 + 1;
                c.gridwidth = 1;
                c.gridheight = 1;
                jPanel1.add(generarePanou("Absenţe", 1), c);
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
                jPanel1.add(generarePanou(s + "</html>", 0), c);
                
                // note
                ++c.gridx;
                c.gridy = (sem - 1) * 4 + 1;
                c.gridheight = 1;
                jPanel1.add(generarePanou("Note", 1), c);
            
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
                jPanel1.add(generarePanou(s + "</html>", 0), c);
                
                // teza
                ++c.gridy;
                c.gridheight = 1;
                if (i.getKey().areTeza())
                {
                    SituatieMaterieCuTeza smt =
                            (SituatieMaterieCuTeza)i.getValue();
                    int notaTeza = smt.getNotaTeza(sem);
                    if (notaTeza == -1)
                        jPanel1.add(generarePanou("Netrecută", 1), c);
                    else
                        jPanel1.add(generarePanou("" + smt.getNotaTeza(sem), 1),
                                c);
                }
                else
                    jPanel1.add(generarePanou("N/A", 1), c);
                
                ++c.gridy;
                --c.gridx;
                c.gridheight = 1;
                c.gridwidth = 2;
                int medieSemestriala = i.getValue().getMedieSem(sem);
                if (medieSemestriala == -1)
                    jPanel1.add(generarePanou("Neîncheiată", 1), c);
                else
                    jPanel1.add(generarePanou("" + medieSemestriala, 1), c);
            }

            ++c.gridy;
            c.gridheight = 1;
            c.gridwidth = 2;
            if (i.getValue().getMedieAnuala() == -1)
                jPanel1.add(generarePanou("Necalculată", 1), c);
            else
                jPanel1.add(generarePanou("" +
                        i.getValue().getMedieAnuala(), 1), c);
            
            coloana += 2;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        jLabel1.setText("Situaţia dumneavoastră şcolară:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 265, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
