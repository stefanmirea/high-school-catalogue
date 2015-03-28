package liceu;

public interface ISecretar
{
    public Clasa adaugaClasa(String id);
    public void stergeClasa(Clasa clasa);
    public void editeazaClasa(Clasa clasa, Elev elev, int op);
    public void adaugaMaterieProfesor(Profesor profesor, Materie materie);
    public void stergeMaterieProfesor(Profesor profesor, Materie materie);
    public void editeazaMaterieProfesor(Profesor profesor, Materie materie,
            Clasa clasa, boolean preda);
    public void adaugaElev(Clasa clasa, Elev elev);
    public void stergeElev(Clasa clasa, Elev elev);
    public void editeazaElev(Elev elev, String nume, String prenume, String
            cnp);
    public void adaugaMaterieClasa();
    public void stergeMaterieClasa();
    public void editeazaMaterieClasa();
    public boolean calculeazaMediaGenerala(Elev elev);
}
