package gameframe;


import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.border.LineBorder;


/**
 * @author Jens Fießelmann (s0539077), Aleksej Skepu (s0538539)
 * @version 1.0
 * @date 02.01.2013
 * 
 * Die Klasse GameFrame erstellt das Spielfenster für Viergewinnt mit variabler
 * Feldgroesse, die der Benutzer über eine ComboBox für Breite und Hoehe 
 * einstellen kann. Das Spielfeld wird ueber ein Gridlayout hergesetellt. Die
 * Anordnung der Elemente erfolgt ueber ein BorderLayout.
 */
public class GameFrame extends JFrame implements ActionListener{
 
    int max_zuege;
    int count;
	
    public static final int SPIELER_1 = 1;
    public static final int SPIELER_2 = 2;
        
    int [][] status;
    
    JComboBox comboBreite;
    JComboBox comboHoehe;
    final static int maxGap = 20;
    
    JButton applyButton;
    JButton newGame;
    
    JPanel comps; // Spielfeld
    JPanel controls; // Steuerelemente
    
    JButton buttons[][]; 
    
    int breite_min, breite_max;
    int hoehe_min, hoehe_max;
    
    int combo_breite;
    int combo_hoehe;
    
    private String hoehe, breite; // zuweisen der Werte aus Combobox
    private String  listeH []= {"5","6","7","8"};
    private String  listeB []= {"6","7","8","9","10"};

    
    /** Im Konstruktor wird der Name des Spiels angegeben, sowie die Startwerte
     *  fuer die Hoehe und Breite des Spielfeldes 
     */
    public GameFrame() {
        super("VierGewinnt");
        hoehe_min = 5;
        breite_min = 6;
    }
    
    /** In dieser Methode werden den beiden ComboBoxen erstellt
     */
    public void felder(){
        comboBreite = new JComboBox(listeB);
        comboHoehe = new JComboBox(listeH);
    }
    

    /** Die Methode createWindow erstellt das Spielfeld fuer 4Gewinnt.
     *  Die Anordnung erfolgt ueber die JPanels comps und controls. Comps 
     *  enthaelt die Buttons fuer das Spielfeld und controls die Steuerelemnte
     *  ComboBreite, ComboHoehe, den Start Button sowie den Neues Spiel Button
     *
     * @param breite Die Breite des Spielfeldes
     * @param hoehe Die Hoehe des Spielfeldes
     */
    public  void createWindow(int breite, int hoehe)
    {
        count = 0;
        combo_breite = breite;
        combo_hoehe = hoehe;
         
        max_zuege = combo_breite * combo_hoehe;	 
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        setResizable(false);
        setVisible(true);

        comps = new JPanel();
        GridLayout grid = new GridLayout(combo_hoehe,combo_breite);
        comps.setLayout(grid);
        felder();
        
        status = new int [combo_hoehe][combo_breite];
        
        for (int i = 0;  i < combo_hoehe; i++) {
            for (int j = 0; j < combo_breite; j++)
            {
                    status [i][j] = 0;
            }
        }

        buttons = new JButton[combo_hoehe][combo_breite];
        for(int i=0; i< combo_hoehe; i++)
        {
            for (int j = 0; j< combo_breite; j++)
            {
                comps.add(buttons[i][j] = new JButton());
                buttons[i][j].addActionListener(this);
                buttons[i][j].setBorder(new LineBorder(Color.lightGray
                        ,6));
                Dimension buttonSize = buttons[i][j].getPreferredSize();
                comps.setPreferredSize(new Dimension((int)(buttonSize.getWidth()
                        * 40.5)+maxGap,
                (int)(buttonSize.getHeight() * 7.5)+maxGap * 18));
            }
            add(comps);
        }

        controls = new JPanel();
        controls.setLayout(new GridLayout(2,4));
        controls.add(new Label ("Breite"));
        controls.add(new Label ("Höhe"));
        controls.add(new Label (""));
        controls.add(new Label (""));
        controls.setBackground(Color.gray);
        controls.add(comboBreite);
        controls.add(comboHoehe);
        
        applyButton = new JButton("ändern");
        applyButton.addActionListener(this);
        controls.add(applyButton);
        
        newGame = new JButton("Neues Spiel");
        newGame.addActionListener(this);
        controls.add(newGame);
        
        add(comps,BorderLayout.NORTH);
        add(new JSeparator(), BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        }
    
    /** Diese Methode faerbt die Buttons, wenn ein Spieler darauf geklickt hat
     *
     * @param hoehe ist der Wert des Feldes, auf das der Spieler geklickt hat
     * @param breite ist der Wert des Feldes, auf das der Spieler geklickt hat
     */
    public void colorButton (int hoehe, int breite)
	{
		Connect4 c4 = new Connect4(status, combo_hoehe, combo_breite);
		status = c4.player(hoehe, breite, (count % 2)+1);
		
		for (int i = 0; i<combo_hoehe; i++ ) {
                for(int j = 0; j<combo_breite; j++)
                {
                        switch(status[i][j])
                        {
                        case 1:	buttons[i][j].setBackground(Color.ORANGE);        			
                                            buttons[i][j].setOpaque(true);
                                            break;
                        case 2:	buttons[i][j].setBackground(Color.red);        			
                                            buttons[i][j].setOpaque(true);
                                            break;
                        }
                }
            }
		if(c4.has_won()==true)
		{
                    try {
			TimeUnit.MILLISECONDS.sleep(125L);
		} catch (InterruptedException e) {
		}
			JOptionPane.showMessageDialog(this,
                        "Spieler "
                         +((count%2)+1)+" hat gewonnen !", "Gewonnen!",
                         JOptionPane.PLAIN_MESSAGE);
			comps.removeAll();
                        controls.removeAll();
			comps.setVisible(false);
                        controls.setVisible(false);
			createWindow(combo_breite,combo_hoehe);
			comps.updateUI();
                        controls.updateUI();
			pack();
		}
	}
        
    /** das Hauptprogramm main startet die Funktion createWindow mit den 
     *  kleinsten Werten fuer Breite und Hoehe
     *
     * @param args
     */
    public  static void main (String[] args)
    {/* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | 
                InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
         
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        GameFrame g = new GameFrame();
        g.createWindow(g.breite_min, g.hoehe_min);
        }
    });
        }

    /** Hier werden alle Eingaben des Benutzers abgefangen
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        /* Wenn der Start Button betaetigt wird, werden die Werte aus den Combo
         * Boxen ausgelesen und das Spielfeld wird mit diesen Werten gestartet.
         */
        if(ae.getSource()== applyButton ) {
            
                breite = (String)comboBreite.getSelectedItem();
                hoehe = (String)comboHoehe.getSelectedItem();
              
                combo_breite = Integer.parseInt(breite);
                combo_hoehe = Integer.parseInt(hoehe);
                
                /* Bevor ein neues Spielfeld erstellt wird, werden die alten
                 * Elemente entfernt
                 */ 
                
                comps.removeAll();
                controls.removeAll();
                controls.setVisible(false);
                comps.setVisible(false);
                
                createWindow(combo_breite,combo_hoehe);
                comps.updateUI();
                controls.updateUI();
                applyButton.setEnabled(false);
        }
                
               
		for (int i = 0; i<combo_hoehe; i++ ) {
                for(int j = 0; j<combo_breite; j++)
                {        		
                        if(ae.getSource() == buttons [i][j] )
                        {
                            applyButton.setEnabled(false);
                              
                                if(status[0][j] == 0)
                                {
                                        colorButton(i,j);
                                        count++;
                                }
                                                      
                                if(count == combo_breite*combo_hoehe)
                                {
                                    try {
			TimeUnit.MILLISECONDS.sleep(125L);
		} catch (InterruptedException e) {
		}
                                        JOptionPane.showMessageDialog(this,
                                "Unentschieden, Spiel ist zu Ende",
                                "Untentschieden",JOptionPane.PLAIN_MESSAGE);
                                        comps.removeAll();
                                        controls.removeAll();
                                        comps.setVisible(false);
                                        controls.setVisible(false);
                                        createWindow(combo_breite,combo_hoehe);        				
                                        comps.updateUI();
                                        controls.updateUI();
                                }
                        }        			
                }
            }
                
       /* Hier kann das Spiel neu gestartet werden. Es wird eine Warnung aus-
        * gegeben, ob der Spieler dies auch tun moechte
        */         
                
       if(ae.getSource()== newGame )
        {       
            int result = JOptionPane.showConfirmDialog(null,
				"Neues Spiel?", "Frage", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION) {
                comps.removeAll();
                controls.removeAll();
                comps.setVisible(false);
                controls.setVisible(false);
                controls.updateUI();
                comps.updateUI();
                combo_breite = 6;
                combo_hoehe = 5;  
                createWindow(combo_breite,combo_hoehe);
		}
		else {
		}
        }
}
}

    
    

    