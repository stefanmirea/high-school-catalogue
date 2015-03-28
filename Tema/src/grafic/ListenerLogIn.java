package grafic;

import javax.swing.*;
import java.awt.event.*;
import liceu.*;

public class ListenerLogIn implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        Fereastra f;
        if (e.getSource() instanceof JMenuItem)
        {
            JMenuItem itemMeniu = (JMenuItem)e.getSource();  
            JPopupMenu meniuPopup = (JPopupMenu)itemMeniu.getParent();  
            JComponent inv = (JComponent)meniuPopup.getInvoker();
            f = (Fereastra)inv.getTopLevelAncestor();
        }
        else
        {
            JButton butonLogin = (JButton)(e.getSource());
            f = (Fereastra)SwingUtilities.getWindowAncestor(butonLogin);
        }
        PanouLogin panouLogin = f.getPanouLogin();

        // validare nume utilizator
        String numeUtilizator = panouLogin.getNumeUtilizator();
        if (numeUtilizator.equals(""))
        {
            JOptionPane.showMessageDialog(panouLogin, "Introduceţi un nume de "
                    + "utilizator.", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // validare parola
        String parola = panouLogin.getParola();
        if (parola.length() < 6)
        {
            JOptionPane.showMessageDialog(panouLogin, "Parola trebuie să aibă "
                    + "minimum 6 caractere.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // validare credentiale
        Centralizator centralizator = Centralizator.getInstance();
        Utilizator ut = centralizator.autentificare(numeUtilizator, parola);
        if (ut == null)
        {
            JOptionPane.showMessageDialog(panouLogin, "Utilizatorul nu există "
                    + "sau parola este incorectă.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        f.logheaza(ut);
    }
}
