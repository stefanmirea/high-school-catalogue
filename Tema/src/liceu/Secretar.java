package liceu;

import java.util.*;

public class Secretar extends Utilizator implements ISecretar
{
    public Secretar(String numeUtilizator, String parola, String nume,
            String prenume)
    {
        super(numeUtilizator, parola, nume, prenume);
    }
    
    private class Triplet
    {
        private Materie materie;
        private Clasa clasa;
        private Profesor profesor;
        
        public Triplet(Materie materie, Clasa clasa, Profesor profesor)
        {
            this.materie = materie;
            this.clasa = clasa;
            this.profesor = profesor;
        }
        
        public Materie getMaterie()
        {
            return materie;
        }
        
        public Clasa getClasa()
        {
            return clasa;
        }
        
        public Profesor getProfesor()
        {
            return profesor;
        }
    }

    public Clasa adaugaClasa(String id)
    {
        Centralizator centralizator = Centralizator.getInstance();
        Clasa clasa = new Clasa(id);
        centralizator.getClase(null).add(clasa);
        return clasa;
    }
    
    public void stergeClasa(Clasa clasa)
    {
        Centralizator centralizator = Centralizator.getInstance();
        HashMap<Materie, HashMap<Clasa, Profesor>> hash;
        hash = centralizator.getDictionar();
        ArrayList<Triplet> deSters = new ArrayList<Triplet>();
        for (Map.Entry<Materie, HashMap<Clasa, Profesor>> i : hash.entrySet())
            for (Map.Entry<Clasa, Profesor> j : i.getValue().entrySet())
                if (j.getKey().equals(clasa))
                    deSters.add(new Triplet(i.getKey(), j.getKey(),
                            j.getValue()));
        for (Iterator<Triplet> i = deSters.iterator(); i.hasNext(); )
        {
            Triplet triplet = i.next();
            /*System.out.println(triplet.getProfesor().getNumeUtilizator());
            System.out.println(triplet.getMaterie().getNume());
            System.out.println(triplet.getClasa().getId());
            System.out.println("----");*/
            editeazaMaterieProfesor(triplet.getProfesor(), triplet.getMaterie(),
                    triplet.getClasa(), false);
        }
        centralizator.getClase(null).remove(clasa);
        /*for (Map.Entry<Materie, HashMap<Clasa, Profesor>> i : hash.entrySet())
            for (Map.Entry<Clasa, Profesor> j : i.getValue().entrySet())
                if (j.getValue() != null)
                    System.out.println(i.getKey().getNume() + " - " + j.getKey().getId() + " - " + j.getValue().getNumeUtilizator());
                else
                    System.out.println(i.getKey().getNume() + " - " + j.getKey().getId() + " - null");*/
    }

    public void editeazaClasa(Clasa clasa, Elev elev, int operatie)
    {
        // operatie = 1 => adaugare
        // operatie = 0 => stergere
        if (operatie == 1)
            adaugaElev(clasa, elev);
        else
            stergeElev(clasa, elev);
    }
    
    public void adaugaMaterieProfesor(Profesor profesor, Materie materie)
    {
        profesor.adaugaMaterie(materie);
    }
    
    public void stergeMaterieProfesor(Profesor profesor, Materie materie)
    {
        profesor.stergeMaterie(materie);
    }
    
    // seteaza daca profesorul preda materia la clasa
    public void editeazaMaterieProfesor(Profesor profesor, Materie materie,
            Clasa clasa, boolean preda)
    {
        HashMap<Clasa, Profesor> mapare = Centralizator.getInstance().
                    getDictionar().get(materie);
        if (preda)
        {
            // verific daca materia era deja predata de profesor
            boolean adaugaMaterie = true;
            for (Map.Entry<Clasa, Profesor> i : mapare.entrySet())
                if (i.getValue() == profesor)
                {
                    adaugaMaterie = false;
                    break;
                }
            if (adaugaMaterie)
                adaugaMaterieProfesor(profesor, materie);
            if (!mapare.containsKey(clasa))
            {
                // adaug materia in catalogul clasei
                clasa.adaugaMaterie(materie);
                HashMap<Elev, HashMap<Materie, SituatieMaterieBaza>> hash;
                hash = clasa.getCatalog().getDictionar();
                for (Map.Entry<Elev, HashMap<Materie, SituatieMaterieBaza>> i :
                        hash.entrySet())
                {
                    SituatieMaterieBaza smb;
                    if (materie.areTeza())
                        smb = new SituatieMaterieCuTeza(materie);
                    else
                        smb = new SituatieMaterieBaza(materie);
                    i.getValue().put(materie, smb);
                }
            }
            mapare.put(clasa, profesor);
        }
        else
        {
            // marchez ca materia nu mai este predata de nimeni la aceasta clasa
            // notele din catalog raman totusi
            mapare.put(clasa, null);

            // spune daca profesorul nu mai preda materia
            boolean stergeMaterie = true;

            for (Map.Entry<Clasa, Profesor> i : mapare.entrySet())
                if (i.getValue() == profesor)
                {
                    // profesorul inca mai preda materia
                    stergeMaterie = false;
                    break;
                }
            if (stergeMaterie)
                stergeMaterieProfesor(profesor, materie);
        }
    }
    
    public void adaugaElev(Clasa clasa, Elev elev)
    {
        // verific daca elevul era deja intr-o clasa
        Centralizator centralizator = Centralizator.getInstance();
        for (Iterator<Clasa> i = centralizator.iteratorClase(); i.hasNext(); )
        {
            Clasa clasaTemp = i.next();
            if (clasaTemp.contineElev(elev))
            {
                // elevul este sters din vechea clasa
                stergeElev(clasaTemp, elev);
                break;
            }
        }
        // in acest moment, elevul nu are o clasa
        clasa.adaugaElev(elev, true);
    }
    
    public void stergeElev(Clasa clasa, Elev elev)
    {
        clasa.stergeElev(elev);
    }
    
    public void editeazaElev(Elev elev, String nume, String prenume, String cnp)
    {
        elev.setDatePersonale(nume, prenume, cnp);
    }
    
    public void adaugaMaterieClasa(){}
    public void stergeMaterieClasa(){}
    public void editeazaMaterieClasa(){}
    public boolean calculeazaMediaGenerala(Elev elev)
    {
        return elev.calculeazaMedieGenerala();
    }
}
