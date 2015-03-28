package grafic;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import liceu.*;

public class Fereastra extends JFrame
{
    private JMenuBar baraMeniu;
    private JMenu meniu, meniuAjutor;
    private JMenuItem itemMeniu;
    private PanouLogin panouLogin;
    private Utilizator utilizatorLogat;
    private PanouLogat panouLogat;

    public Fereastra(String titlu)
    {
        super(titlu);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                Centralizator.getInstance().scrie();
                System.exit(0);
            }
        });
        
        // adaugare meniu
        meniuAjutor = new JMenu("Ajutor");
        itemMeniu = new JMenuItem("Despre...");
        itemMeniu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(SwingUtilities.
                        getWindowAncestor((Component)e.getSource()),
                        "Creat de: Ştefan-Gabriel Mirea",
                        "Despre Catalog", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        meniuAjutor.add(itemMeniu);
        creeazaMeniuri();
        
        // creez panoul de logare
        panouLogin = new PanouLogin(new GridBagLayout());

        // afisez panoul de logare
        creeazaLayout();
        
        //centrare
        Dimension ws = getSize();
        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        int newX = (ss.width - ws.width) / 2;
        int newY = (ss.height - ws.height) / 2;
        setLocation(newX, newY);
        
        setVisible(true);
    }
    
    public PanouLogin getPanouLogin()
    {
        return panouLogin;
    }
    
    public PanouLogat getPanouLogat()
    {
        return panouLogat;
    }
    
    public Utilizator getUtilizatorLogat()
    {
        return utilizatorLogat;
    }
    
    public void logheaza(Utilizator utilizator)
    {
        utilizatorLogat = utilizator;
        creeazaMeniuri();
        creeazaLayout();
    }
    
    // creeaza meniurile in functie de cine e logat
    public void creeazaMeniuri()
    {
        baraMeniu = new JMenuBar();
        if (utilizatorLogat == null)
        {
            meniu = new JMenu("Opţiuni");
            baraMeniu.add(meniu);
            itemMeniu = new JMenuItem("Autentificare");
            itemMeniu.addActionListener(new ListenerLogIn());
            meniu.add(itemMeniu);
            meniu.addSeparator();
            itemMeniu = new JMenuItem("Ieşire");
            itemMeniu.addActionListener(new ListenerIesire());
            meniu.add(itemMeniu);
        }
        else
        {
            meniu = new JMenu("Opţiuni");
            baraMeniu.add(meniu);
            
            if (utilizatorLogat instanceof Elev)
            {
                itemMeniu = new JMenuItem("Vizualizare date personale");
                Elev elevLogat = (Elev)utilizatorLogat;
                // 0 de la constructor inseamna date personale
                itemMeniu.addActionListener(new ListenerDateElev(elevLogat, 0));
                meniu.add(itemMeniu);
                itemMeniu = new JMenuItem("Vizualizare situaţie şcolară");
                // 1 de la constructor inseamna situatia scolara
                itemMeniu.addActionListener(new ListenerDateElev(elevLogat, 1));
                meniu.add(itemMeniu);
            }
            else if (utilizatorLogat instanceof Secretar)
            {
                itemMeniu = new JMenuItem("Editare clase şi elevi");
                itemMeniu.addActionListener(new ListenerEditeazaClase());
                meniu.add(itemMeniu);
                itemMeniu = new JMenuItem("Editare materii profesor");
                itemMeniu.addActionListener(new ListenerEditeazaMaterii());
                meniu.add(itemMeniu);
                itemMeniu = new JMenuItem("Calculaţi mediile generale");
                itemMeniu.addActionListener(new ListenerCalculeazaMedii());
                meniu.add(itemMeniu);
            }
            
            itemMeniu = new JMenuItem("Log out");
            itemMeniu.addActionListener(new ListenerLogOut());
            meniu.add(itemMeniu);
            meniu.addSeparator();
            itemMeniu = new JMenuItem("Ieşire");
            itemMeniu.addActionListener(new ListenerIesire());
            meniu.add(itemMeniu);
        }
        baraMeniu.add(meniuAjutor);
        setJMenuBar(baraMeniu);
        revalidate();
        repaint();
    }
    
    // creeaza layout-ul in functie de cine e logat
    public void creeazaLayout()
    {
        if (utilizatorLogat == null)
        {
            setSize(540, 400);
            setResizable(false);
            panouLogin.reseteaza();
            setContentPane(panouLogin);
            panouLogin.butonImplicit(getRootPane());
        }
        else
        {
            getRootPane().setDefaultButton(null);
            setResizable(true);
        
            panouLogat = new PanouLogat(utilizatorLogat);
            setContentPane(panouLogat);
            
            if (utilizatorLogat instanceof Profesor)
            {
                panouLogat.golesteWorkSpace();
                JPanel workspace = panouLogat.getWorkSpace();
                workspace.setLayout(new BoxLayout(workspace,
                        BoxLayout.PAGE_AXIS));
                PanouProf panouProf = new PanouProf((Profesor)utilizatorLogat);
                panouProf.setAlignmentX(Component.LEFT_ALIGNMENT);
                workspace.add(panouProf);
            }
        }
        revalidate();
        repaint();
    }
}
