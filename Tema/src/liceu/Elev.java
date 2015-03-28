package liceu;

import grafic.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import java.awt.*;

public class Elev extends Utilizator implements IElev
{
    private String cnp;
    private Date dataNasterii;
    private float medieGenerala;
    
    public Elev(String numeUtilizator, String parola, String nume,
        String prenume, String cnp)
    {
        super(numeUtilizator, parola, nume, prenume);
        this.cnp = cnp;
        calculeazaDataNasterii();
        medieGenerala = -1;
    }
    
    public void calculeazaDataNasterii()
    {
        // preiau sirul cu data nasterii
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String sirData = cnp.substring(1, 7);
        if (sirData.charAt(0) == '9')
            sirData = "19" + sirData;
        else
            sirData = "20" + sirData;

        try
        {
            dataNasterii = sdf.parse(sirData);
        }
        catch (ParseException e)
        {
            System.out.println("CNP incorect.");
        }
    }
    
    public String getCNP()
    {
        return cnp;
    }
    
    public String getDataNasterii()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataNasterii);
        int zi = calendar.get(Calendar.DAY_OF_MONTH);
        int luna = calendar.get(Calendar.MONTH) + 1;
        int an = calendar.get(Calendar.YEAR);
        return (zi < 10 ? "0" : "") + zi + "." + (luna < 10 ? "0" : "") + luna
                + "." + an;
    }
    
    public float getMedieGenerala()
    {
        return medieGenerala;
    }
    
    public void setMedieGenerala(float medieGenerala)
    {
        this.medieGenerala = medieGenerala;
    }
    
    public boolean calculeazaMedieGenerala()
    {
        Centralizator centralizator = Centralizator.getInstance();
        boolean gasit = false;
        HashMap<Materie, SituatieMaterieBaza> hash = null;
        for (Iterator<Clasa> i = centralizator.iteratorClase(); i.hasNext(); )
        {
            Clasa clasa = i.next();
            if (clasa.contineElev(this))
            {
                gasit = true;
                hash = clasa.getCatalog().getDictionar().get(this);
                break;
            }
        }
        if (gasit == false)
            return false;
        float suma = 0;
        int nr = 0;
        for (Map.Entry<Materie, SituatieMaterieBaza> i : hash.entrySet())
        {
            if (i.getValue().getMedieAnuala() == -1)
                return false;
            suma += i.getValue().getMedieAnuala();
            ++nr;
        }
        medieGenerala = suma / nr;
        return true;
    }
    
    public void setDatePersonale(String nume, String prenume, String cnp)
    {
        setDatePersonale(nume, prenume);
        this.cnp = cnp;
        calculeazaDataNasterii();
    }
    
    public void afiseazaDatePersonale(Fereastra f)
    {
        PanouLogat panouLogat = f.getPanouLogat();
        panouLogat.golesteWorkSpace();
        JPanel workspace = panouLogat.getWorkSpace();
        workspace.setLayout(new BoxLayout(workspace, BoxLayout.PAGE_AXIS));
        PanouDate panouDate = new PanouDate(this);
        panouDate.setAlignmentX(Component.LEFT_ALIGNMENT);
        workspace.add(panouDate);

        f.revalidate();
        f.repaint();
    }
    
    public void afiseazaSituatiaScolara(Fereastra f)
    {
        PanouLogat panouLogat = f.getPanouLogat();
        panouLogat.golesteWorkSpace();
        JPanel workspace = panouLogat.getWorkSpace();
        workspace.setLayout(new BoxLayout(workspace, BoxLayout.PAGE_AXIS));

        HashMap<Materie, SituatieMaterieBaza> hash;
        hash = Centralizator.getInstance().getSituatie(this);
        PanouSituatieScolara panouSituatie = new PanouSituatieScolara(hash,
                this);

        workspace.add(panouSituatie);
        panouSituatie.setAlignmentX(Component.LEFT_ALIGNMENT);

        f.revalidate();
        f.repaint();
    }
    
    public String toString()
    {
        String temp = super.toString();
        return temp + "CNP: " + cnp + "\nData nasterii: " + dataNasterii + "\n";
    }
}
