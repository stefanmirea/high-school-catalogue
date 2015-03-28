package grafic;

import java.awt.event.*;
import javax.swing.*;
import liceu.*;
import java.awt.*;

public class ListenerAdaugaClasa implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        JPanel panou = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        panou.add(new JLabel("ID-ul clasei:"), c);
        JTextField text = new JTextField(20);
        c.gridy = 1;
        panou.add(text, c);
        int rasp = JOptionPane.showConfirmDialog(null, panou, "Adăugare clasă",
                JOptionPane.OK_CANCEL_OPTION);
        if (rasp == JOptionPane.OK_OPTION)
        {
            JButton adauga = (JButton)e.getSource();
            Fereastra f = (Fereastra)SwingUtilities.getWindowAncestor(adauga);
            Clasa clasa = ((Secretar)f.getUtilizatorLogat()).
                    adaugaClasa(text.getText());
            PanouListaClase panouClase = (PanouListaClase)(adauga.getParent().
                    getParent());
            PanouClaseElevi panouClaseElevi;
            panouClaseElevi = (PanouClaseElevi)panouClase.getParent();
            panouClaseElevi.getListaClase().add(clasa);
            DefaultListModel model = (DefaultListModel)panouClase.getLista().
                    getModel();
            model.add(model.size(), text.getText());
        }
    }
}
