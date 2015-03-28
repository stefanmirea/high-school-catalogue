package liceu;

public class Materie
{
    private String nume;
    private int numarOre;
    private boolean areTeza;
    
    public Materie(String nume, int numarOre, boolean areTeza)
    {
        this.nume = nume;
        this.numarOre = numarOre;
        this.areTeza = areTeza;
    }
    
    public String getNume()
    {
        return nume;
    }
    
    public int getNumarOre()
    {
        return numarOre;
    }
    
    public boolean areTeza()
    {
        return areTeza;
    }
    
    public String toString()
    {
        return nume + " (" + numarOre + " ore pe saptamana, " +
                (areTeza ? "cu" : "fara") + " teza)";
    }
}
