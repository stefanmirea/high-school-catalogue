package grafic;

import javax.swing.*;
import java.awt.event.*;

public class ListenerLogOut implements ActionListener
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
        
        // delogheaza
        f.logheaza(null);
    }
}
