package liceu;

import java.util.*;
import javax.swing.*;

public interface IProfesor
{
    public void listeazaElevi(Clasa clasa, JTable tabel);
    public void ordoneazaElevi();
    public void afiseazaSituatia(HashMap<Materie, SituatieMaterieBaza> situatie,
            JPanel panou);
    public void adaugaNota(SituatieMaterieBaza smb, int semestru, int nota);
    public void incheieMedie(SituatieMaterieBaza smb, int semestru);
    public void adaugaAbsenta(SituatieMaterieBaza smb, int semestru, Date data);
    public void modificaAbsenta(SituatieMaterieBaza smb, int semestru, int
            indiceAbsenta, int statusNou);
    public void adaugaMaterie(Materie materie);
    public void stergeMaterie(Materie materie);
    public boolean preda(Materie materie);
    public Iterator<Materie> iteratorMaterii();
}
