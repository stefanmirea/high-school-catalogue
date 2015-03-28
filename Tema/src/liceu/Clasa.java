package liceu;

import java.util.*;

public class Clasa
{
    private String idClasa;
    private ArrayList<Elev> elevi;
    private ArrayList<Materie> materii;
    private Catalog catalog;
    
    public Clasa(String id)
    {
        idClasa = id;
        elevi = new ArrayList<Elev>();
        materii = new ArrayList<Materie>();
        catalog = new Catalog();
    }
    
    // creeaza = creeaza si o situatie scolara in catalog
    public void adaugaElev(Elev elev, boolean creeaza)
    {
        elevi.add(elev);
        if (creeaza)
        {
            HashMap<Materie, SituatieMaterieBaza> hash;
            hash = new HashMap<Materie, SituatieMaterieBaza>();
            for (Iterator<Materie> i = materii.iterator(); i.hasNext(); )
            {
                Materie materie = i.next();
                if (materie.areTeza())
                    hash.put(materie, new SituatieMaterieCuTeza(materie));
                else
                    hash.put(materie, new SituatieMaterieBaza(materie));
            }
            catalog.getDictionar().put(elev, hash);
        }
    }
    
    public void stergeElev(Elev elev)
    {
        if (elevi.contains(elev))
        {
            elevi.remove(elev);
            catalog.getDictionar().remove(elev);
        }
    }
    
    public void adaugaMaterie(Materie materie)
    {
        materii.add(materie);
    }
    
    public String getId()
    {
        return idClasa;
    }
    
    public int getNumarElevi()
    {
        return elevi.size();
    }
    
    public int getNumarMaterii()
    {
        return materii.size();
    }
    
    public Catalog getCatalog()
    {
        return catalog;
    }
    
    public Elev getElev(int poz)
    {
        return elevi.get(poz);
    }
    
    public boolean contineElev(Elev elev)
    {
        return elevi.contains(elev);
    }
    
    public HashMap<Materie, SituatieMaterieBaza> getSituatie(Elev elev)
    {
        return catalog.getDictionar().get(elev);
    }
    
    public Iterator<Elev> iteratorElevi()
    {
        return elevi.iterator();
    }
    
    public Iterator<Materie> iteratorMaterii()
    {
        return materii.iterator();
    }
    
    public String toString()
    {
        String ret = idClasa + ":\n-----------------\n";
        ret = ret + "Numele de utilizator ale elevilor: ";
        for (Iterator<Elev> it = elevi.iterator(); it.hasNext(); )
            ret = ret + it.next().getNumeUtilizator() + " ";
        ret = ret + "\nNumele materiilor predate: ";
        for (Iterator<Materie> it = materii.iterator(); it.hasNext(); )
            ret = ret + it.next().getNume() + " ";
        ret = ret + "\nCatalog:\n\n" + catalog;
        return ret;
    }
}
