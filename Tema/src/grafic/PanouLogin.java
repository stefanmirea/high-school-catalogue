package grafic;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class PanouLogin extends JPanel
{
    private JTextField campNumeUtilizator;
    private JPasswordField campParola;
    private JLabel labelNumeUtilizator, labelParola;
    private JButton butonLogIn;
    private BufferedImage imagine;
    
    public PanouLogin(GridBagLayout layout)
    {
        super(layout);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;

        labelNumeUtilizator = new JLabel("Nume utilizator:");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        add(labelNumeUtilizator, c);

        campNumeUtilizator = new JTextField(15);
        c.gridy = 1;
        c.insets = new Insets(3, 0, 7, 0);
        add(campNumeUtilizator, c);

        labelParola = new JLabel("Parolă:");
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        add(labelParola, c);

        campParola = new JPasswordField(15);
        c.gridy = 3;
        c.insets = new Insets(3, 0, 7, 0);
        add(campParola, c);

        butonLogIn = new JButton("Autentificare");
        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 4;
        c.insets = new Insets(3, 0, 0, 0);
        add(butonLogIn, c);
        
        butonLogIn.addActionListener(new ListenerLogIn());
        
        try
        {                
            imagine = ImageIO.read(new File("img/fundal.bmp"));
        }
        catch (IOException e)
        {
            System.out.println("Eroare la citirea fisierului fundal.bmp.");
        }
    }
    
    public String getNumeUtilizator()
    {
        return campNumeUtilizator.getText();
    }
    
    public String getParola()
    {
        return new String(campParola.getPassword());
    }
    
    // face ca butonul Log in sa se "apese" la Enter, iar campul de nume sa
    // primeasca focusul
    public void butonImplicit(JRootPane radacina)
    {
        radacina.setDefaultButton(butonLogIn);
        campNumeUtilizator.requestFocus();
    }
    
    // deseneaza imaginea de fundal
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(imagine, 0, 0, null);
    }
    
    // goleste campurile text
    public void reseteaza()
    {
        campNumeUtilizator.setText("");
        campParola.setText("");
    }
}
