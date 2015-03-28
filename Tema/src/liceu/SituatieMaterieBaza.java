package liceu;

import java.util.*;

public class SituatieMaterieBaza
{
    private Materie materie;
    private ArrayList<Integer>[] listeNote;
    private int medieSem[];
    private ArrayList<Absenta>[] listeAbsente;
    private float medieAnuala;
    
    public class Absenta
    {
        private int status;
        private Date data;
        
        public Absenta(int status, Date data)
        {
            this.status = status;
            this.data = data;
        }
        
        public int getStatus()
        {
            return status;
        }
        
        public void setStatus(int status)
        {
            this.status = status;
        }
        
        public Date getData()
        {
            return data;
        }
        
        public String toString()
        {
            String ret = data.toString();
            switch (status)
            {
                case 0:
                    ret = ret + " (motivata)";
                    break;
                case 1:
                    ret = ret + " (nemotivata)";
                    break;
                case 2:
                    ret = ret + " (nedeterminat)";
            }
            return ret;
        }
    }
    
    public SituatieMaterieBaza(Materie materie)
    {
        this.materie = materie;
        listeNote = new ArrayList[2];
        for (int i = 0; i < 2; ++i)
            listeNote[i] = new ArrayList<Integer>();
        medieSem = new int[2];
        medieSem[0] = medieSem[1] = -1;
        listeAbsente = new ArrayList[2];
        for (int i = 0; i < 2; ++i)
            listeAbsente[i] = new ArrayList<Absenta>();
        medieAnuala = -1;
    }

    public void adaugaNota(int semestru, int nota)
    {
        listeNote[semestru - 1].add(nota);
    }
    
    public void adaugaAbsenta(int semestru, int status, Date data)
    {
        listeAbsente[semestru - 1].add(new Absenta(status, data));
    }
    
    public void modificaAbsenta(int semestru, int indiceAbsenta, int status)
    {
        listeAbsente[semestru - 1].get(indiceAbsenta).setStatus(status);
    }
    
    // incheie media
    public void calculeazaMedieSem(int semestru)
    {
        if (listeNote[semestru - 1].size() < 2)
        {
            medieSem[semestru - 1] = -1;
            return;
        }
        int suma = 0;
        for (Iterator<Integer> i = listeNote[semestru - 1].iterator();
                i.hasNext(); )
            suma += i.next();
        medieSem[semestru - 1] = Math.round((float)suma /
                listeNote[semestru - 1].size());
    }
    
    public void setMedieSem(int semestru, int medieSemestriala)
    {
        this.medieSem[semestru - 1] = medieSemestriala;
    }
    
    public int getMedieSem(int semestru)
    {
        return this.medieSem[semestru - 1];
    }
    
    public void setMedieAnuala(float medieAnuala)
    {
        this.medieAnuala = medieAnuala;
    }
    
    public float getMedieAnuala()
    {
        return medieAnuala;
    }
    
    // calculeaza mediile pe semestre si media anuala
    public void calculeazaMedieAnuala()
    {
        if (medieSem[0] == -1 || medieSem[1] == -1)
            medieAnuala = -1;
        else
            medieAnuala = (float)(medieSem[0] + medieSem[1]) / 2;
    }
    
    public int getNumarNote(int semestru)
    {
        return listeNote[semestru - 1].size();
    }
    
    public int getNumarAbsente(int semestru)
    {
        return listeAbsente[semestru - 1].size();
    }
    
    public Materie getMaterie()
    {
        return materie;
    }
    
    public Iterator<Integer> iteratorNote(int semestru)
    {
        return listeNote[semestru - 1].iterator();
    }
    
    public Iterator<Absenta> iteratorAbsente(int semestru)
    {
        return listeAbsente[semestru - 1].iterator();
    }
    
    public String toString()
    {
        String ret = "";
        for (int sem = 1; sem <= 2; ++sem)
        {
            ret = ret + "Note semestrul " + sem + ": ";
            for (Iterator<Integer> it = listeNote[sem - 1].iterator();
                    it.hasNext(); )
                ret = ret + it.next() + " ";
            ret = ret + "\nMedie semestrul " + sem + ": ";
            if (medieSem[sem - 1] == -1)
                ret = ret + "neincheiata\n";
            else
                ret = ret + medieSem[sem - 1] + "\n";
            ret = ret + "Absente semestrul " + sem + ":\n";
            for (Iterator<Absenta> it = listeAbsente[sem - 1].iterator();
                    it.hasNext(); )
                ret = ret + it.next() + "\n";
        }
        ret = ret + "Medie anuala: ";
        if (medieAnuala == -1)
            return ret + "necalculata\n\n";
        else
            return ret + medieAnuala + "\n\n";
    }
}
