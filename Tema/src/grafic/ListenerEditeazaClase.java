package grafic;

import java.awt.event.*;
import javax.swing.*;

public class ListenerEditeazaClase implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        Fereastra f = null;
        if (e.getSource() instanceof JMenuItem)
        {
            JMenuItem itemMeniu = (JMenuItem)e.getSource();  
            JPopupMenu meniuPopup = (JPopupMenu)itemMeniu.getParent();  
            JComponent inv = (JComponent)meniuPopup.getInvoker();
            f = (Fereastra)inv.getTopLevelAncestor();
        }
        PanouLogat panouLogat = f.getPanouLogat();
        panouLogat.golesteWorkSpace();
        JPanel workspace = panouLogat.getWorkSpace();
        workspace.setLayout(new BoxLayout(workspace, BoxLayout.PAGE_AXIS));
        PanouClaseElevi panouClaseElevi = new PanouClaseElevi();
        workspace.add(panouClaseElevi);

        f.revalidate();
        f.repaint();
    }
}
