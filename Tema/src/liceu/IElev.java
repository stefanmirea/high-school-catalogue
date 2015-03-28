package liceu;

import grafic.*;

public interface IElev
{
    public void afiseazaDatePersonale(Fereastra f);
    public void afiseazaSituatiaScolara(Fereastra f);
    public void calculeazaDataNasterii();
    public String getCNP();
    public String getDataNasterii();
    public float getMedieGenerala();
    public void setMedieGenerala(float medieGenerala);
    public boolean calculeazaMedieGenerala();
    public void setDatePersonale(String nume, String prenume, String cnp);
}
