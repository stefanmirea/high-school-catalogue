package grafic;

import java.awt.event.*;
import liceu.*;

public class ListenerIesire implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        Centralizator.getInstance().scrie();
        System.exit(0);
    }
}