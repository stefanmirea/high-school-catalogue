package liceu;

import java.util.*;
import java.io.*;

public class Centralizator
{
    private static Centralizator centralizator = new Centralizator();

    private ArrayList<Elev> elevi;
    private ArrayList<Profesor> profesori;
    private ArrayList<Secretar> secretari;

    private ArrayList<Clasa> clase;
    private HashMap<Materie, HashMap<Clasa, Profesor>> dictionar;

    private Centralizator()
    {
        elevi = new ArrayList<Elev>();
        profesori = new ArrayList<Profesor>();
        secretari = new ArrayList<Secretar>();
        clase = new ArrayList<Clasa>();
        dictionar = new HashMap<Materie, HashMap<Clasa, Profesor>>();
        citeste();
    }

    public static Centralizator getInstance()
    {
        return centralizator;
    }
    
    public Utilizator getUtilizatorByNumeUtilizator(String numeUtilizator)
    {
        Utilizator utilizatorTemporar;
        for (Iterator<Elev> it = elevi.iterator(); it.hasNext(); )
        {
            utilizatorTemporar = it.next();
            if (utilizatorTemporar.getNumeUtilizator().equals(numeUtilizator))
                return utilizatorTemporar;
        }
        for (Iterator<Profesor> it = profesori.iterator(); it.hasNext(); )
        {
            utilizatorTemporar = it.next();
            if (utilizatorTemporar.getNumeUtilizator().equals(numeUtilizator))
                return utilizatorTemporar;
        }
        for (Iterator<Secretar> it = secretari.iterator(); it.hasNext(); )
        {
            utilizatorTemporar = it.next();
            if (utilizatorTemporar.getNumeUtilizator().equals(numeUtilizator))
                return utilizatorTemporar;
        }
        return null;
    }
    
    public Materie getMaterieByNume(String nume, ArrayList<Materie> materii)
    {
        Materie materieTemporara;
        for (Iterator<Materie> it = materii.iterator(); it.hasNext(); )
        {
            materieTemporara = it.next();
            if (materieTemporara.getNume().equals(nume))
                return materieTemporara;
        }
        return null;
    }
    
    public Clasa getClasaById(String idClasa)
    {
        Clasa clasaTemporara;
        for (Iterator<Clasa> it = clase.iterator(); it.hasNext(); )
        {
            clasaTemporara = it.next();
            if (clasaTemporara.getId().equals(idClasa))
                return clasaTemporara;
        }
        return null;
    }
    
    public Iterator<Elev> iteratorElevi()
    {
        return elevi.iterator();
    }
    
    // clasele unui profesor
    public ArrayList<Clasa> getClase(Profesor prof)
    {
        if (prof == null) // toate clasele
            return clase;
        
        ArrayList<Clasa> ret = new ArrayList<Clasa>();
        for (Map.Entry<Materie, HashMap<Clasa, Profesor>> i :
                dictionar.entrySet())
            for (Map.Entry<Clasa, Profesor> j : i.getValue().entrySet())
                if (j.getValue() != null)
                    if (j.getValue().equals(prof) && !ret.contains(j.getKey()))
                        ret.add(j.getKey());
        return ret;
    }
    
    public HashMap<Materie, HashMap<Clasa, Profesor>> getDictionar()
    {
        return dictionar;
    }
    
    public Iterator<Profesor> iteratorProfesori()
    {
        return profesori.iterator();
    }
    
    public Iterator<Clasa> iteratorClase()
    {
        return clase.iterator();
    }
    
    public HashMap<Materie, SituatieMaterieBaza> getSituatie(Elev elev)
    {
        boolean found = false;
        Elev elevTemporar;
        Clasa clasa = null;

        for (Iterator<Clasa> i = clase.iterator(); i.hasNext(); )
        {
            clasa = i.next();
            for (Iterator<Elev> j = clasa.iteratorElevi(); j.hasNext(); )
            {
                elevTemporar = j.next();
                if (elevTemporar == elev)
                {
                    found = true;
                    break;
                }
            }
            if (found == true)
                break;
        }
        if (found == false)
            return null;
        return clasa.getSituatie(elev);
    }
    
    public void citesteUtilizatori()
    {
        try
        {
            int numarUtilizatori, i, tip;
            float medieGenerala;
            String numeUtilizator, parola, nume, prenume;
            Scanner scanner = new Scanner(new File("utilizatori.txt"));
            scanner.useLocale(Locale.US);

            numarUtilizatori = scanner.nextInt();
            for (i = 0; i < numarUtilizatori; ++i)
            {
                tip = scanner.nextInt();
                scanner.nextLine(); // trec pe linia urmatoare
                numeUtilizator = scanner.nextLine();
                parola = scanner.nextLine();
                nume = scanner.nextLine();
                prenume = scanner.nextLine();
                switch (tip)
                {
                    case 1: // elev
                        String cnp = scanner.nextLine();
                        medieGenerala = scanner.nextFloat();
                        Elev elev = new Elev(numeUtilizator, parola, nume,
                                prenume, cnp);
                        elev.setMedieGenerala(medieGenerala);
                        elevi.add(elev);
                        break;
                    case 2: // profesor
                        Profesor prof = new Profesor(numeUtilizator, parola,
                                nume, prenume);
                        profesori.add(prof);
                        break;
                    default: // secretar
                        Secretar secretar = new Secretar(numeUtilizator, parola,
                                nume, prenume);
                        secretari.add(secretar);
                        break;
                }
            }
            scanner.close();
        }
        catch (IOException e)
        {
            System.out.println("Eroare la citirea fisierului utilizatori.txt.");
        }
    }
    
    public void citesteClase()
    {
        int numarMaterii, i, numarOre, numarMapari, j, numarClase, numarElevi;
        int k, numarNote, nota, semestru, numarAbsente, status, zi, luna, an;
        String nume, idClasaCurenta, numeElev, idClasaMapata;
        boolean areTeza;
        Elev elevTemporar;
        Materie materieTemporara;

        // creez o lista temporara a materiilor si listele materiilor pentru
        // fiecare profesor
        ArrayList<Materie> materii = new ArrayList<Materie>();

        try
        {
            Scanner scanner = new Scanner(new File("materii.txt"));
            scanner.useLocale(Locale.US);
            
            numarMaterii = scanner.nextInt();
            scanner.nextLine();
            for (i = 0; i < numarMaterii; ++i)
            {
                nume = scanner.nextLine();
                numarOre = scanner.nextInt();
                areTeza = scanner.nextBoolean();
                Materie materie = new Materie(nume, numarOre, areTeza);
                materii.add(materie);
                
                numarMapari = scanner.nextInt();
                scanner.nextLine();
                for (j = 0; j < numarMapari; ++j)
                {
                    // skip id clasa (inca nu am lista claselor)
                    scanner.nextLine();
                    
                    String numeProfesor = scanner.nextLine();
                    // putem avea (temporar) situatia in care o materie exista
                    // in catalogul unei clase, dar niciun profesor nu o preda
                    if (!numeProfesor.equals("-1"))
                    {
                        Profesor prof = (Profesor)getUtilizatorByNumeUtilizator
                            (numeProfesor);
                        if (prof.preda(materie) == false)
                            prof.adaugaMaterie(materie);
                    }
                }
            }
            scanner.close();
        }
        catch (IOException e)
        {
            System.out.println("Eroare la citirea fisierului materii.txt.");
        }
        try
        {
            Scanner scannerClase = new Scanner(new File("clase.txt"));
            scannerClase.useLocale(Locale.US);
            
            numarClase = scannerClase.nextInt();
            scannerClase.nextLine(); // trec pe linia urmatoare
            for (i = 0; i < numarClase; ++i)
            {
                idClasaCurenta = scannerClase.nextLine();
                clase.add(new Clasa(idClasaCurenta));

                // creez lista cu elevi a clasei
                numarElevi = scannerClase.nextInt();
                scannerClase.nextLine(); // trec pe linia urmatoare
                for (j = 0; j < numarElevi; ++j)
                {
                    numeElev = scannerClase.nextLine();
                    clase.get(i).adaugaElev((Elev)
                            getUtilizatorByNumeUtilizator(numeElev), false);
                }
                
                // creez lista cu materii a clasei
                Scanner scannerMaterii = new Scanner(new File("materii.txt"));
                scannerMaterii.useLocale(Locale.US);
                numarMaterii = scannerMaterii.nextInt();
                scannerMaterii.nextLine();
                for (j = 0; j < numarMaterii; ++j)
                {
                    nume = scannerMaterii.nextLine();
                    scannerMaterii.nextInt();
                    scannerMaterii.nextBoolean();

                    // verific daca materia se preda la clasa curenta
                    numarMapari = scannerMaterii.nextInt();
                    scannerMaterii.nextLine();
                    for (k = 0; k < numarMapari; ++k)
                    {
                        idClasaMapata = scannerMaterii.nextLine();
                        if (idClasaMapata.equals(idClasaCurenta))
                            clase.get(i).adaugaMaterie(getMaterieByNume(nume,
                                    materii));
                        // trec peste profesorul care preda materia
                        scannerMaterii.nextLine();
                    }
                }
                scannerMaterii.close();
                
                // creez catalogul clasei curente
                for (Iterator<Elev> itElev = clase.get(i).iteratorElevi();
                        itElev.hasNext(); )
                {
                    elevTemporar = itElev.next();
                    
                    // creez un dictionar Materie - SMB asociat elevului
                    HashMap<Materie, SituatieMaterieBaza> hash = new
                            HashMap<Materie, SituatieMaterieBaza>();

                    for (j = 0; j < clase.get(i).getNumarMaterii(); ++j)
                    {
                        nume = scannerClase.nextLine();
                        materieTemporara = getMaterieByNume(nume, materii);
                        
                        // materieTemporara e o referinta catre materia pentru
                        // care voi citi situatia
                        SituatieMaterieBaza sm;
                        if (materieTemporara.areTeza())
                             sm = new SituatieMaterieCuTeza(materieTemporara);
                        else
                             sm = new SituatieMaterieBaza(materieTemporara);
                        for (semestru = 1; semestru <= 2; ++semestru)
                        {
                            numarNote = scannerClase.nextInt();
                            for (k = 0; k < numarNote; ++k)
                            {
                                nota = scannerClase.nextInt();
                                sm.adaugaNota(semestru, nota);
                            }
                            if (materieTemporara.areTeza())
                            {
                                int notaTeza = scannerClase.nextInt();
                                ((SituatieMaterieCuTeza)sm).setNotaTeza(
                                        semestru, notaTeza);
                            }
                            int medieSemestriala = scannerClase.nextInt();
                            sm.setMedieSem(semestru, medieSemestriala);
                            numarAbsente = scannerClase.nextInt();
                            for (k = 0; k < numarAbsente; ++k)
                            {
                                status = scannerClase.nextInt();
                                zi = scannerClase.nextInt();
                                luna = scannerClase.nextInt();
                                an = scannerClase.nextInt();
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(an, luna - 1, zi);
                                sm.adaugaAbsenta(semestru, status,
                                        calendar.getTime());
                            }
                        }
                        float medieAnuala = scannerClase.nextFloat();
                        sm.setMedieAnuala(medieAnuala);

                        hash.put(materieTemporara, sm);
                        scannerClase.nextLine();
                    }
                    clase.get(i).getCatalog().getDictionar().put(elevTemporar,
                            hash);
                }
            }
            scannerClase.close();
        }
        catch (IOException e)
        {
            System.out.println("Eroare la citirea fisierului clase.txt.");
        }
        
        // creez dictionarul Materie - (Clasa - Profesor)
        try
        {
            Scanner scanner = new Scanner(new File("materii.txt"));
            scanner.useLocale(Locale.US);
            
            numarMaterii = scanner.nextInt();
            scanner.nextLine();
            for (i = 0; i < numarMaterii; ++i)
            {
                // creez dictionarul Clasa - Profesor asociat materiei
                HashMap<Clasa, Profesor> hash = new HashMap<Clasa, Profesor>();
                
                nume = scanner.nextLine();
                scanner.nextInt(); // skip numarOre
                scanner.nextBoolean(); // skip areTeza

                numarMapari = scanner.nextInt();
                scanner.nextLine();
                for (j = 0; j < numarMapari; ++j)
                {
                    idClasaMapata = scanner.nextLine();
                    String numeProfesor = scanner.nextLine();
                    Profesor profesor;
                    if (numeProfesor == "-1")
                        profesor = null;
                    else
                        profesor = (Profesor)getUtilizatorByNumeUtilizator(
                                numeProfesor);
                    hash.put(getClasaById(idClasaMapata), profesor);
                }
                
                dictionar.put(getMaterieByNume(nume, materii), hash);
            }
            scanner.close();
        }
        catch (IOException e)
        {
            System.out.println("Eroare la citirea fisierului materii.txt.");
        }
    }
    
    public void scrieUtilizatori()
    {
        try
        {
            FileWriter writer = new FileWriter("utilizatori.txt");
            int numarUtilizatori = elevi.size() + profesori.size()
                    + secretari.size();

            writer.write("" + numarUtilizatori + "\r\n");

            // afisare elevi
            for (Iterator<Elev> it = elevi.iterator(); it.hasNext(); )
            {
                Elev elevTemporar = it.next();
                writer.write("1\r\n"); // 1 = tipul elev
                writer.write(elevTemporar.getNumeUtilizator() + "\r\n");
                writer.write(elevTemporar.getParola() + "\r\n");
                writer.write(elevTemporar.getNume() + "\r\n");
                writer.write(elevTemporar.getPrenume() + "\r\n");
                writer.write(elevTemporar.getCNP() + "\r\n");
                writer.write(elevTemporar.getMedieGenerala() + "\r\n");
            }
            
            // afisare profesori
            for (Iterator<Profesor> it = profesori.iterator(); it.hasNext(); )
            {
                Profesor profesorTemporar = it.next();
                writer.write("2\r\n"); // 2 = tipul profesor
                writer.write(profesorTemporar.getNumeUtilizator() + "\r\n");
                writer.write(profesorTemporar.getParola() + "\r\n");
                writer.write(profesorTemporar.getNume() + "\r\n");
                writer.write(profesorTemporar.getPrenume() + "\r\n");
            }
            
            // afisare secretari
            for (Iterator<Secretar> it = secretari.iterator(); it.hasNext(); )
            {
                Secretar secretarTemporar = it.next();
                writer.write("3\r\n"); // 3 = tipul secretar
                writer.write(secretarTemporar.getNumeUtilizator() + "\r\n");
                writer.write(secretarTemporar.getParola() + "\r\n");
                writer.write(secretarTemporar.getNume() + "\r\n");
                writer.write(secretarTemporar.getPrenume() + "\r\n");
            }
            
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Eroare la scrierea in utilizatori.txt.");
        }
    }
    
    public void scrieClase()
    {
        try
        {
            FileWriter writer = new FileWriter("clase.txt");
            
            writer.write("" + clase.size() + "\r\n");
            for (Iterator<Clasa> itCls = clase.iterator(); itCls.hasNext(); )
            {
                Clasa clasa = itCls.next();
                writer.write(clasa.getId() + "\r\n");
                writer.write("" + clasa.getNumarElevi() + "\r\n");
                for (Iterator<Elev> itElev = clasa.iteratorElevi();
                        itElev.hasNext(); )
                    writer.write(itElev.next().getNumeUtilizator() + "\r\n");
                for (Iterator<Elev> itElev = clasa.iteratorElevi();
                        itElev.hasNext(); )
                {
                    Elev elev = itElev.next();
                    for (Iterator<Materie> itMat = clasa.iteratorMaterii();
                            itMat.hasNext(); )
                    {
                        Materie materie = itMat.next();
                        writer.write(materie.getNume() + "\r\n");
                        SituatieMaterieBaza sm = clasa.getCatalog().
                                getDictionar().get(elev).get(materie);
                        for (int semestru = 1; semestru <= 2; ++semestru)
                        {
                            writer.write("" + sm.getNumarNote(semestru) +
                                    "\r\n");
                            for (Iterator<Integer> itNote = sm.iteratorNote(
                                    semestru); itNote.hasNext(); )
                                writer.write("" + itNote.next() + " ");
                            if (materie.areTeza())
                            {
                                SituatieMaterieCuTeza smt;
                                smt = (SituatieMaterieCuTeza)sm;
                                writer.write("" + smt.getNotaTeza(semestru));
                            }
                            writer.write("\r\n" + sm.getMedieSem(semestru) +
                                "\r\n" + sm.getNumarAbsente(semestru) + "\r\n");
                            for (Iterator<SituatieMaterieBaza.Absenta> it =
                                   sm.iteratorAbsente(semestru); it.hasNext(); )
                            {
                                SituatieMaterieBaza.Absenta absenta = it.next();
                                writer.write("" + absenta.getStatus() + " ");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(absenta.getData());
                                int zi = calendar.get(Calendar.DAY_OF_MONTH);
                                int luna = calendar.get(Calendar.MONTH) + 1;
                                int an = calendar.get(Calendar.YEAR);
                                writer.write("" + zi + " " + luna + " " + an +
                                        "\r\n");
                            }
                        }
                        writer.write(sm.getMedieAnuala() + "\r\n");
                    }
                }
            }
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Eroare la scrierea in clase.txt.");
        }
    }
    
    public void scrieMaterii()
    {
        try
        {
            FileWriter writer = new FileWriter("materii.txt");
            
            writer.write("" + dictionar.size() + "\r\n");
            for (Map.Entry<Materie, HashMap<Clasa, Profesor>> i :
                    dictionar.entrySet())
            {
                writer.write("" + i.getKey().getNume() + "\r\n");
                writer.write("" + i.getKey().getNumarOre() + "\r\n");
                writer.write("" + i.getKey().areTeza() + "\r\n");
                
                // afisez maparile Clasa - Profesor corespunzatoare materiei
                writer.write("" + i.getValue().size() + "\r\n");
                for (Map.Entry<Clasa, Profesor> j : i.getValue().entrySet())
                {
                    writer.write(j.getKey().getId() + "\r\n");
                    if (j.getValue() == null)
                        writer.write("-1\r\n");
                    else
                        writer.write(j.getValue().getNumeUtilizator() + "\r\n");
                }
            }
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Eroare la scrierea in materii.txt");
        }
    }
    
    public void citeste()
    {
        citesteUtilizatori();
        citesteClase();
    }
    
    public void scrie()
    {
        scrieUtilizatori();
        scrieClase();
        scrieMaterii();
    }
    
    // returneaza referinta utilizatorului daca credentialele sunt corecte sau
    // null in caz contrar
    public Utilizator autentificare(String numeUtilizator, String parola)
    {
        for (Iterator<Elev> it = elevi.iterator(); it.hasNext(); )
        {
            Elev elev = it.next();
            if (elev.getNumeUtilizator().equals(numeUtilizator))
                if (elev.getParola().equals(parola))
                    return elev;
                else
                    return null;
        }
        for (Iterator<Profesor> it = profesori.iterator(); it.hasNext(); )
        {
            Profesor profesor = it.next();
            if (profesor.getNumeUtilizator().equals(numeUtilizator))
                if (profesor.getParola().equals(parola))
                    return profesor;
                else
                    return null;
        }
        for (Iterator<Secretar> it = secretari.iterator(); it.hasNext(); )
        {
            Secretar secretar = it.next();
            if (secretar.getNumeUtilizator().equals(numeUtilizator))
                if (secretar.getParola().equals(parola))
                    return secretar;
                else
                    return null;
        }
        return null;
    }
    
    public String toString()
    {
        String ret = "Elevi:\n\n";
        for (Iterator<Elev> it = elevi.iterator(); it.hasNext(); )
            ret = ret + it.next() + "\n";

        ret = ret + "Profesori:\n\n";
        for (Iterator<Profesor> it = profesori.iterator(); it.hasNext(); )
            ret = ret + it.next() + "\n";
        
        ret = ret + "Secretari:\n\n";
        for (Iterator<Secretar> it = secretari.iterator(); it.hasNext(); )
            ret = ret + it.next() + "\n";
        
        ret = ret + "Clase:\n\n";
        for (Iterator<Clasa> it = clase.iterator(); it.hasNext(); )
            ret = ret + it.next();
        
        ret = ret + "Materii:\n\n";
        for (Map.Entry<Materie, HashMap<Clasa, Profesor>> i :
                dictionar.entrySet())
        {
            if (i.getValue().size() == 0)
            {
                ret = ret + "Materia " + i.getKey() + " nu este " +
                        "predata in liceu (un secretar trebuie sa adauge " +
                        "profesori si clase pentru aceasta materie!).\n";
                continue;
            }
            ret = ret + "Materia " + i.getKey() + " este predata:\n";
            for (Map.Entry<Clasa, Profesor> j : i.getValue().entrySet())
            {
                ret = ret + "   - la clasa " + j.getKey().getId() + " de catre";
                if (j.getValue() != null)
                    ret = ret + " " + j.getValue().getNumeUtilizator() + ";\n";
                else
                    ret = ret + " <nimeni> (Un secretar trebuie sa atribuie " +
                            "un profesor la aceasta materie!);\n";
            }
        }
        return ret;
    }
}
