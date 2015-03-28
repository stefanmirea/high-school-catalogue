package liceu;

public abstract class Utilizator
{
    private String numeUtilizator, parola, nume, prenume;
    
    public Utilizator(String numeUtilizator, String parola, String nume,
            String prenume)
    {
        this.numeUtilizator = numeUtilizator;
        this.parola = parola;
        this.nume = nume;
        this.prenume = prenume;
    }
    
    public String getNumeUtilizator()
    {
        return numeUtilizator;
    }
    
    public String getParola()
    {
        return parola;
    }
    
    public String getNume()
    {
        return nume;
    }
    
    public String getPrenume()
    {
        return prenume;
    }
    
    public void setDatePersonale(String nume, String prenume)
    {
        this.nume = nume;
        this.prenume = prenume;
    }
    
    public String toString()
    {
        return "Nume utilizator: " + numeUtilizator + "\nParola: " + parola +
                "\nNume: " + nume + "\nPrenume: " + prenume + "\n";
    }
}
