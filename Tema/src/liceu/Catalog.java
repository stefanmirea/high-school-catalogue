package liceu;

import java.util.*;

public class Catalog
{
    private HashMap<Elev, HashMap<Materie, SituatieMaterieBaza>> dictionar;
    
    public Catalog()
    {
        dictionar = new HashMap<Elev, HashMap<Materie, SituatieMaterieBaza>>();
    }
    
    public HashMap<Elev, HashMap<Materie, SituatieMaterieBaza>> getDictionar()
    {
        return dictionar;
    }
    
    public String toString()
    {
        String ret = "";
        for (Map.Entry<Elev, HashMap<Materie, SituatieMaterieBaza>> i :
                dictionar.entrySet())
        {
            String temp = "Situatia lui " + i.getKey().getNumeUtilizator() +
                    " la ";
            for (Map.Entry<Materie, SituatieMaterieBaza> j :
                    i.getValue().entrySet())
                ret = ret + temp + j.getKey().getNume() + ":\n" + j.getValue();
        }
        return ret;
    }
}
