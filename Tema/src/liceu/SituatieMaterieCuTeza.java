package liceu;

import java.util.*;

public class SituatieMaterieCuTeza extends SituatieMaterieBaza
{
    private int noteTeza[];
    
    public SituatieMaterieCuTeza(Materie materie)
    {
        super(materie);
        noteTeza = new int[2];
        noteTeza[0] = noteTeza[1] = -1;
    }
    
    // incheie media
    public void calculeazaMedieSem(int semestru)
    {
        if (getNumarNote(semestru) < 2 || noteTeza[semestru - 1] == -1)
        {
            setMedieSem(semestru, -1);
            return;
        }
        int suma = 0;
        for (Iterator<Integer> i = iteratorNote(semestru); i.hasNext(); )
            suma += i.next();
        setMedieSem(semestru, Math.round(((float)suma / getNumarNote(semestru)
                * 3 + noteTeza[semestru - 1]) / 4));
    }
    
    public void setNotaTeza(int semestru, int notaTeza)
    {
        noteTeza[semestru - 1] = notaTeza;
    }
    
    public int getNotaTeza(int semestru)
    {
        return noteTeza[semestru - 1];
    }
    
    public String toString()
    {
        String ret = "";
        for (int sem = 1; sem <= 2; ++sem)
        {
            ret = ret + "Nota teza semestrul " + sem + ": ";
            if (noteTeza[sem - 1] == -1)
                ret = ret + "netrecuta\n";
            else
                ret = ret + noteTeza[sem - 1] + "\n";
        }
        return ret + super.toString();
    }
}
