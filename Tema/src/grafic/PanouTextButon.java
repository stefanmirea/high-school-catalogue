package grafic;

import java.awt.*;
import javax.swing.*;

public class PanouTextButon extends JPanel
{
    public String getText()
    {
        Component[] componente = getComponents();
        for (int i = 0; i < componente.length; ++i)
            if (componente[i].getClass().getName().toString().
                    equals("javax.swing.JTextField"))
                return ((JTextField)componente[i]).getText();
        return "";
    }
}