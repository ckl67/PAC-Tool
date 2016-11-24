package pacp;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComboBox;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JFileChooser;

public class WinPrime {

	private JFrame frame;
	private JTextField textFieldH1;
	private JTextField textFieldH2;
	private JTextField textFieldH3;
	private JTextField textFieldTK;
	private JTextField textFieldT0;
	private JTextField textFieldCarnotFroid;
	private JTextField textFieldScrollEvap;
	private JTextField textFieldScrollRG;
	private JTextField textFieldScrollCond;
	private JTextField textFieldScrollLiq;
	private JTextField textFieldScrollCapacity;
	private JTextField textFieldScrollPower;
	private JTextField textFieldScrollCurrent;
	private JTextField textFieldScrollSurchauffe;
	private JTextField textFieldScrollSousRefroid;
	private JTextField textFieldScrollEER;
	private JTextField textFieldScrollMassFlow;
	private JTextField textFieldScrollDeltaH0;
	private JTextField textFieldScrollVoltage;
	private JTextField textFieldScrollCosPhi;
	private JTextField textFieldScrollName;
	private JCheckBox checkoxFaren;
	private JCheckBox checkoxBTU;
	private JCheckBox chckbxPound;
	private JComboBox<String> comboBoxScroll;

	private JLabel lblCapacity;
	private JLabel lblPower;
	private JLabel lblCurrent;
	private JLabel lblEer;
	private JLabel lblVoltage;
	private JLabel lblMassflow;
	private JLabel lblEvap;
	private JLabel lblRG;
	private JLabel lblCond;
	private JLabel lblLiq;

	
	// List of PAC 
	private List<Pac> pacl = new ArrayList<Pac>();
	// First pac is created !!
	Pac pac = new Pac();

	// COP measure
	Cop cop = new Cop();

	// ===================================================================================================================
	/**
	 * Create the application.
	 * 
	 */
	public WinPrime() {
		pacl.add(pac);	
		initialize(pac,cop);
		fillScrollTexField(pac.getScroll());
	}


	// ===================================================================================================================
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Pac paci, Cop copi) {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WinPrime.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frame.setTitle("PAC Tool (" + PACmain.getPacToolVersion()+ ")");
		frame.setBounds(100, 100, 443, 574);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// ===============================================================================================================
		// 													MENU
		// ===============================================================================================================
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);

		JMenu mfile = new JMenu("Fichier");
		menubar.add(mfile);

		// ---------------------------------------------------------------
		// Load Config
		// ---------------------------------------------------------------
		JMenuItem mloadcfg = new JMenuItem(" Ouvrir Config.");
		mloadcfg.setIcon(new ImageIcon(WinPrime.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		mloadcfg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Conf. files", "cfg");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());

					JSONParser parser = new JSONParser(); 
					JSONObject jsonObjectR = null;
					try {
						jsonObjectR = (JSONObject) parser.parse(new FileReader(chooser.getSelectedFile().getAbsolutePath()));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  

					//System.out.println("READ JSON object to file");  
					//System.out.println("-----------------------");  
					//System.out.print(jsonObjectR);  

					JSONArray jsonObjectScrollL = (JSONArray) jsonObjectR.get("Scroll");
					JSONObject jsonObjectScrolln;
					JSONObject jsonObjectCfg = (JSONObject) jsonObjectR.get("Cfg");

					// Read Configuration & Preferences
					long nbScrollList = (long) jsonObjectCfg.get("nbScrollList");

					// Read Scroll List + Affect to pacl scroll
					for(int i=1;i<pacl.size();i++) {
						pacl.remove(i);
						comboBoxScroll.removeItemAt(i);
					}
					//System.out.println("size="+pacl.size());
					//System.out.println("comboBoxScroll size="+comboBoxScroll.getItemCount());

					for(int i=0;i<nbScrollList;i++) {
						jsonObjectScrolln = (JSONObject) jsonObjectScrollL.get(i);
						if(i==0) {
							//pacl.add(i, paci);
							pacl.get(i).getScroll().setJsonObject(jsonObjectScrolln);
						}
						else {
							pacl.add(i, new Pac());
							pacl.get(i).getScroll().setJsonObject(jsonObjectScrolln);
							comboBoxScroll.insertItemAt(pacl.get(i).getScroll().getName(),i);
						}
					}
					comboBoxScroll.setSelectedIndex(0);
					fillScrollTexField(pacl.get(0).getScroll());

					// Read Configuration & Preferences WITH ACTION TO PERFORM --> MUST BE THE LAST ACTION !!
					if (!(boolean)(jsonObjectCfg.get("checkoxFaren")))
						checkoxFaren.doClick(); 		
					if (!(boolean)(jsonObjectCfg.get("checkoxBTU")))
						checkoxBTU.doClick(); 		
					if (!(boolean)(jsonObjectCfg.get("chckbxPound")))
						chckbxPound.doClick(); 		
				} 
			}
		});
		mfile.add(mloadcfg);

		// ---------------------------------------------------------------
		// Save Config
		// ---------------------------------------------------------------
		JMenuItem msavecfg = new JMenuItem("Sauver Config.");
		msavecfg.setIcon(new ImageIcon(WinPrime.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		msavecfg.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {

				// Create JSON Scroll list
				JSONArray listOfScroll = new JSONArray();
				JSONObject ObjScroll = new JSONObject();  
				for(int i=0;i<pacl.size();i++) {
					ObjScroll = pacl.get(i).getScroll().getJsonObject();  
					listOfScroll.add(ObjScroll);
				}

				// Create JSON data for the PAC-Tool : Configuration + preferences 
				JSONObject ObjCfg = new JSONObject();  
				ObjCfg.put("nbScrollList", pacl.size());
				ObjCfg.put("checkoxBTU", checkoxBTU.isSelected());
				ObjCfg.put("chckbxPound", chckbxPound.isSelected());
				ObjCfg.put("checkoxFaren", checkoxFaren.isSelected());

				// Compact in JSON Data: PacTool
				JSONObject ObjPacTool = new JSONObject();  
				ObjPacTool.put("Scroll", listOfScroll);  
				ObjPacTool.put("Cfg", ObjCfg);  

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Conf. files", "cfg");
				chooser.setApproveButtonText("Sauvegarder");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());

					// Writing to a file  
					//file.createNewFile();  
					FileWriter fileWriter;
					try {
						fileWriter = new FileWriter(chooser.getSelectedFile().getAbsolutePath());
						fileWriter.write(ObjPacTool.toJSONString());  
						fileWriter.flush();  
						fileWriter.close();  
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
					//System.out.println("Writing JSON object to file");  
					//System.out.println("-----------------------");  
					//System.out.print(ObjPacTool);  


				} 
			}
		});
		mfile.add(msavecfg);

		// ---------------------------------------------------------------
		// Separator
		// ---------------------------------------------------------------
		JSeparator mseparator = new JSeparator();
		mfile.add(mseparator);

		// ---------------------------------------------------------------
		// Exit
		// ---------------------------------------------------------------
		JMenuItem mexit = new JMenuItem("Quitter");
		mexit.setIcon(new ImageIcon(WinPrime.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		mexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mfile.add(mexit);

		// ---------------------------------------------------------------
		// Menu Config
		// ---------------------------------------------------------------
		JMenu mpreference = new JMenu("Pr\u00E9f\u00E9rences");
		menubar.add(mpreference);

		JMenu mlangue = new JMenu("Langues");
		mlangue.setIcon(new ImageIcon(WinPrime.class.getResource("/pacp/images/flag-frgb16x16.png")));
		mpreference.add(mlangue);

		ButtonGroup buttonGroup = new ButtonGroup();

		JRadioButtonMenuItem mRationItemFrench = new JRadioButtonMenuItem("Francais");
		mRationItemFrench.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblCapacity.setText("Puis. Frigo.:");
				lblPower.setText("Puis. Absorb.:");
				lblCurrent.setText("Courant:");
				lblEer.setText("COP Froid:");
				lblVoltage.setText("Tension:");
				lblMassflow.setText("Débit Massique:");
				lblEvap.setText("T. Evap. (T0):");
				lblRG.setText("T. Asp. Comp.(P1)");
				lblCond.setText("T. Cond. (TK):");
				lblLiq.setText("T. Détend.(P3):");
			}
		});
		buttonGroup.add(mRationItemFrench);

		mlangue.add(mRationItemFrench);

		JRadioButtonMenuItem mRationItemEnglisch = new JRadioButtonMenuItem("Anglais");
		mRationItemEnglisch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblCapacity.setText("Capacity:");
				lblPower.setText("Power:");
				lblCurrent.setText("Current:");
				lblEer.setText("EER:");
				lblVoltage.setText("Voltage:");
				lblMassflow.setText("Mass flow:");
				lblEvap.setText("Evap:");
				lblRG.setText("RG:");
				lblCond.setText("Cond:");
				lblLiq.setText("Liq:");
			}
		});
		mRationItemEnglisch.setSelected(true);
		buttonGroup.add(mRationItemEnglisch);

		mlangue.add(mRationItemEnglisch);

		// ---------------------------------------------------------------
		// Help
		// ---------------------------------------------------------------

		JMenu help = new JMenu("Aide");
		menubar.add(help);
		JMenuItem about = new JMenuItem("A propos de ?");
		about.setIcon(new ImageIcon(WinPrime.class.getResource("/pacp/images/Apropos.png")));
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinAbout nabw = new WinAbout();
				nabw.NewAboutWin();
			}
		});
		help.add(about);

		// ===============================================================================================================
		//													TABBED PANE
		// ===============================================================================================================

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// ===============================================================================================================
		//									                 PANEL PAC
		// ===============================================================================================================
		JPanel panelPAC = new JPanel();
		panelPAC.setForeground(Color.BLUE);
		tabbedPane.addTab("PAC", null, panelPAC, null);
		panelPAC.setLayout(null);

		// ================================================================
		// 					  	Performance Panel
		// ================================================================
		JPanel panel_pc1 = new JPanel();
		panel_pc1.setBorder(new TitledBorder(null, "Donn\u00E9es Performance Constructeur (Temp\u00E9rature)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pc1.setBounds(5, 73, 420, 161);
		panel_pc1.setLayout(null);
		panelPAC.add(panel_pc1);

		// ---------------------------------------------------------------
		// EVAP
		// ---------------------------------------------------------------
		lblEvap = new JLabel("Evap :");
		lblEvap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEvap.setBounds(5, 28, 93, 14);
		panel_pc1.add(lblEvap);

		textFieldScrollEvap = new JTextField();
		textFieldScrollEvap.setToolTipText("Temp\u00E9rature d'\u00E9vaporation (T0)");
		textFieldScrollEvap.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tRG = Double.valueOf( textFieldScrollRG.getText());
				double tEvap = Double.valueOf( textFieldScrollEvap.getText());
				double tmp = Math.round(tRG - tEvap);
				textFieldScrollSurchauffe.setText(String.valueOf(tmp));
			}
		});
		textFieldScrollEvap.setBounds(99, 25, 67, 20);
		panel_pc1.add(textFieldScrollEvap);

		textFieldScrollEvap.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollEvap.setColumns(10);

		JLabel lblTemp_unity1 = new JLabel("\u00B0F");
		lblTemp_unity1.setBounds(176, 28, 25, 14);
		panel_pc1.add(lblTemp_unity1);

		// ---------------------------------------------------------------
		// RG
		// ---------------------------------------------------------------
		lblRG = new JLabel("RG :");
		lblRG.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRG.setBounds(5, 67, 93, 14);
		panel_pc1.add(lblRG);

		textFieldScrollRG = new JTextField();
		textFieldScrollRG.setToolTipText("Temp\u00E9rature d'aspiration du compresseur Point : (1)");
		textFieldScrollRG.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tRG = Double.valueOf( textFieldScrollRG.getText());
				double tEvap = Double.valueOf( textFieldScrollEvap.getText());
				double tmp = Math.round(tRG - tEvap);
				textFieldScrollSurchauffe.setText(String.valueOf(tmp));			
			}
		});
		textFieldScrollRG.setBounds(99, 64, 67, 20);
		panel_pc1.add(textFieldScrollRG);
		textFieldScrollRG.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollRG.setColumns(10);

		JLabel lblTemp_unity2 = new JLabel("\u00B0F");
		lblTemp_unity2.setBounds(176, 67, 25, 14);
		panel_pc1.add(lblTemp_unity2);

		// ---------------------------------------------------------------
		// SURCHAUFFE
		// ---------------------------------------------------------------
		JLabel lblSurchauffe = new JLabel("Surchauffe :");
		lblSurchauffe.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSurchauffe.setBounds(38, 101, 81, 14);
		panel_pc1.add(lblSurchauffe);

		textFieldScrollSurchauffe = new JTextField();
		textFieldScrollSurchauffe.setText("0.0");
		textFieldScrollSurchauffe.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollSurchauffe.setBackground(Color.PINK);
		textFieldScrollSurchauffe.setEditable(false);
		textFieldScrollSurchauffe.setBounds(120, 98, 46, 20);
		panel_pc1.add(textFieldScrollSurchauffe);
		textFieldScrollSurchauffe.setColumns(10);

		JLabel lblTemp_unity5 = new JLabel("\u00B0F");
		lblTemp_unity5.setBounds(176, 101, 25, 14);
		panel_pc1.add(lblTemp_unity5);

		// ---------------------------------------------------------------
		// COND
		// ---------------------------------------------------------------
		lblCond = new JLabel("Cond :");
		lblCond.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCond.setBounds(203, 28, 99, 14);
		panel_pc1.add(lblCond);

		textFieldScrollCond = new JTextField();
		textFieldScrollCond.setToolTipText("Temp\u00E9rature de condensation (TK) ");
		textFieldScrollCond.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tcond = Double.valueOf( textFieldScrollCond.getText());
				double tliq = Double.valueOf( textFieldScrollLiq.getText());
				double tmp = Math.round(tcond - tliq);
				textFieldScrollSousRefroid.setText(String.valueOf(tmp));
			}
		});
		textFieldScrollCond.setBounds(302, 25, 75, 20);
		textFieldScrollCond.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollCond.setColumns(10);
		panel_pc1.add(textFieldScrollCond);

		JLabel lblTemp_unity3 = new JLabel("\u00B0F");
		lblTemp_unity3.setBounds(380, 28, 23, 14);
		panel_pc1.add(lblTemp_unity3);

		// ---------------------------------------------------------------
		// LIQ
		// ---------------------------------------------------------------
		lblLiq = new JLabel("Liq :");
		lblLiq.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLiq.setBounds(203, 67, 99, 14);
		panel_pc1.add(lblLiq);

		textFieldScrollLiq = new JTextField();
		textFieldScrollLiq.setToolTipText("Temp\u00E9rature Entr\u00E9e D\u00E9tendeur : Point (3) ");
		textFieldScrollLiq.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tcond = Double.valueOf( textFieldScrollCond.getText());
				double tliq = Double.valueOf( textFieldScrollLiq.getText());
				double tmp = Math.round(tcond - tliq);
				textFieldScrollSousRefroid.setText(String.valueOf(tmp));
			}
		});

		textFieldScrollLiq.setBounds(302, 64, 75, 20);
		textFieldScrollLiq.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollLiq.setColumns(10);
		panel_pc1.add(textFieldScrollLiq);

		JLabel lblTemp_unity4 = new JLabel("\u00B0F");
		lblTemp_unity4.setBounds(380, 64, 23, 14);
		panel_pc1.add(lblTemp_unity4);

		// ---------------------------------------------------------------
		// SOUS REFROIDISSEMENT
		// ---------------------------------------------------------------

		JLabel lblSousRefroid = new JLabel("S-Refroidissement :");
		lblSousRefroid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSousRefroid.setBounds(226, 101, 103, 14);
		panel_pc1.add(lblSousRefroid);

		textFieldScrollSousRefroid = new JTextField();
		textFieldScrollSousRefroid.setText("0.0");
		textFieldScrollSousRefroid.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollSousRefroid.setEditable(false);
		textFieldScrollSousRefroid.setColumns(10);
		textFieldScrollSousRefroid.setBackground(Color.PINK);
		textFieldScrollSousRefroid.setBounds(331, 98, 46, 20);
		panel_pc1.add(textFieldScrollSousRefroid);

		JLabel lblTemp_unity6 = new JLabel("\u00B0F");
		lblTemp_unity6.setBounds(380, 101, 23, 14);
		panel_pc1.add(lblTemp_unity6);

		// ---------------------------------------------------------------
		// Check Box Farenheit / Celcius
		// ---------------------------------------------------------------
		checkoxFaren = new JCheckBox("Farenheit");
		checkoxFaren.setBounds(302, 131, 95, 23);
		panel_pc1.add(checkoxFaren);
		checkoxFaren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (checkoxFaren.isSelected()) {
					lblTemp_unity1.setText("°F");
					lblTemp_unity2.setText("°F");
					lblTemp_unity3.setText("°F");
					lblTemp_unity4.setText("°F");
					lblTemp_unity5.setText("°F");
					lblTemp_unity6.setText("°F");

					double tcond = PACmain.degre2farenheit(Double.valueOf( textFieldScrollCond.getText()));
					double tliq = PACmain.degre2farenheit(Double.valueOf( textFieldScrollLiq.getText()));
					double tRG = PACmain.degre2farenheit(Double.valueOf( textFieldScrollRG.getText()));
					double tEvap = PACmain.degre2farenheit(Double.valueOf( textFieldScrollEvap.getText()));

					textFieldScrollEvap.setText(String.valueOf(Math.round(tEvap*100.0)/100.0));
					textFieldScrollRG.setText(String.valueOf(Math.round(tRG*100.0)/100.0));
					textFieldScrollCond.setText(String.valueOf(Math.round(tcond*100.0)/100.0));
					textFieldScrollLiq.setText(String.valueOf(Math.round(tliq*100.0)/100.0));

					double tmp = Math.round(tRG - tEvap);
					textFieldScrollSurchauffe.setText(String.valueOf(tmp));			

					tmp = Math.round(tcond - tliq);
					textFieldScrollSousRefroid.setText(String.valueOf(tmp));
				} else {
					lblTemp_unity1.setText("°C");
					lblTemp_unity2.setText("°C");
					lblTemp_unity3.setText("°C");
					lblTemp_unity4.setText("°C");
					lblTemp_unity5.setText("°C");
					lblTemp_unity6.setText("°C");

					double tcond = PACmain.farenheit2degre(Double.valueOf( textFieldScrollCond.getText()));
					double tliq = PACmain.farenheit2degre(Double.valueOf( textFieldScrollLiq.getText()));
					double tRG = PACmain.farenheit2degre(Double.valueOf( textFieldScrollRG.getText()));
					double tEvap = PACmain.farenheit2degre(Double.valueOf( textFieldScrollEvap.getText()));

					textFieldScrollEvap.setText(String.valueOf(Math.round(tEvap*100.0)/100.0));
					textFieldScrollRG.setText(String.valueOf(Math.round(tRG*100.0)/100.0));
					textFieldScrollCond.setText(String.valueOf(Math.round(tcond*100.0)/100.0));
					textFieldScrollLiq.setText(String.valueOf(Math.round(tliq*100.0)/100.0));

					double tmp = Math.round(tRG - tEvap);
					textFieldScrollSurchauffe.setText(String.valueOf(tmp));			

					tmp = Math.round(tcond - tliq);
					textFieldScrollSousRefroid.setText(String.valueOf(tmp));
				}
			}
		});
		checkoxFaren.setSelected(true);

		// ================================================================
		// 					   	Performance 2 Panel
		// ================================================================
		JPanel panel_pc2 = new JPanel();
		panel_pc2.setBounds(5, 245, 420, 241);
		panel_pc2.setBorder(new TitledBorder(null, "Donn\u00E9es Performance Constructeur (Autres)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pc2.setLayout(null);
		panelPAC.add(panel_pc2);

		checkoxBTU = new JCheckBox("BTU/hr");
		checkoxBTU.setToolTipText("British Thermal Unit / hour");
		chckbxPound = new JCheckBox("lbs/h");

		// ---------------------------------------------------------------
		// Capacity
		// ---------------------------------------------------------------

		lblCapacity = new JLabel("Capacity :");
		lblCapacity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCapacity.setToolTipText("");
		lblCapacity.setBounds(5, 28, 73, 14);
		panel_pc2.add(lblCapacity);

		textFieldScrollCapacity = new JTextField();
		textFieldScrollCapacity.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {

				double vCapacity = Double.valueOf( textFieldScrollCapacity.getText());
				double vPower =  Double.valueOf( textFieldScrollPower.getText());
				double vMassFlow = Double.valueOf(textFieldScrollMassFlow.getText());
				double tmp = Math.round(vCapacity/vPower*10.0)/10.0;
				textFieldScrollEER.setText(String.valueOf(tmp));

				if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
					textFieldScrollDeltaH0.setText("-----");
				} else {
					tmp = Math.round(vCapacity/vMassFlow/1000.0);
					textFieldScrollDeltaH0.setText(String.valueOf(tmp));
				}
			}
		});
		textFieldScrollCapacity.setToolTipText("Puissance frigorifique: (H1-H3) x D\u00E9bit Massique");
		textFieldScrollCapacity.setBounds(82, 25, 62, 20);
		textFieldScrollCapacity.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollCapacity.setColumns(10);
		panel_pc2.add(textFieldScrollCapacity);

		JLabel lblCapacity_unity = new JLabel("Btu/hr");
		lblCapacity_unity.setToolTipText("(BUT/hr) British Thermal Unit / hour = Unit\u00E9 de mesure d'\u00E9nergie thermique / Heure. L'unit\u00E9 de puissance du SI est le watt (symbole : W), qui correspond \u00E0  un joule fourni par seconde.");
		lblCapacity_unity.setBounds(154, 28, 46, 14);
		panel_pc2.add(lblCapacity_unity);

		// ---------------------------------------------------------------
		// Power
		// ---------------------------------------------------------------
		lblPower = new JLabel("Power :");
		lblPower.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPower.setBounds(5, 59, 73, 14);
		panel_pc2.add(lblPower);

		textFieldScrollPower = new JTextField();
		textFieldScrollPower.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {

				double vCapacity = Double.valueOf( textFieldScrollCapacity.getText());
				double vPower =  Double.valueOf( textFieldScrollPower.getText());
				double vVoltage = Double.valueOf(textFieldScrollVoltage.getText());
				double vCurrent = Double.valueOf(textFieldScrollCurrent.getText());
				double tmp = Math.round(vCapacity/vPower*10.0)/10.0;
				textFieldScrollEER.setText(String.valueOf(tmp));	

				tmp = Math.round(PACmain.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldScrollCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldScrollPower.setToolTipText("Puissance Absorb\u00E9e");
		textFieldScrollPower.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollPower.setBounds(82, 56, 62, 20);
		textFieldScrollPower.setColumns(10);
		panel_pc2.add(textFieldScrollPower);

		JLabel lblPower_Unity = new JLabel("Watt");
		lblPower_Unity.setBounds(154, 62, 46, 14);
		panel_pc2.add(lblPower_Unity);

		// ---------------------------------------------------------------
		// Courant
		// ---------------------------------------------------------------
		lblCurrent = new JLabel("Current :");
		lblCurrent.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrent.setBounds(5, 90, 73, 14);
		panel_pc2.add(lblCurrent);

		textFieldScrollCurrent = new JTextField();
		textFieldScrollCurrent.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double vPower =  Double.valueOf( textFieldScrollPower.getText());
				double vVoltage = Double.valueOf(textFieldScrollVoltage.getText());
				double vCurrent = Double.valueOf(textFieldScrollCurrent.getText());

				double tmp = Math.round(PACmain.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldScrollCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldScrollCurrent.setToolTipText("Courant absorb\u00E9");
		textFieldScrollCurrent.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollCurrent.setColumns(10);
		textFieldScrollCurrent.setBounds(82, 87, 62, 20);
		panel_pc2.add(textFieldScrollCurrent);

		JLabel lblCurrent_unity = new JLabel("A");
		lblCurrent_unity.setBounds(154, 90, 46, 14);
		panel_pc2.add(lblCurrent_unity);

		// ---------------------------------------------------------------
		// EER
		// ---------------------------------------------------------------
		lblEer = new JLabel("EER :");
		lblEer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEer.setBounds(5, 128, 73, 14);
		panel_pc2.add(lblEer);

		textFieldScrollEER = new JTextField();
		textFieldScrollEER.setEditable(false);
		textFieldScrollEER.setBackground(Color.PINK);
		textFieldScrollEER.setToolTipText("EER (Energy Efficiency Ratio) : Coefficient d\u2019efficacit\u00E9 frigorifique");
		textFieldScrollEER.setText("0.0");
		textFieldScrollEER.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollEER.setColumns(10);
		textFieldScrollEER.setBounds(82, 125, 62, 20);
		panel_pc2.add(textFieldScrollEER);

		JLabel lblEER_unity = new JLabel("BTU/(hr.W)");
		lblEER_unity.setBounds(154, 125, 73, 22);
		panel_pc2.add(lblEER_unity);

		// ---------------------------------------------------------------
		// Mass Flow
		// ---------------------------------------------------------------

		lblMassflow = new JLabel("Mass Flow :");
		lblMassflow.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMassflow.setBounds(205, 28, 86, 14);
		panel_pc2.add(lblMassflow);

		textFieldScrollMassFlow = new JTextField();
		textFieldScrollMassFlow.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double vCapacity = Double.valueOf( textFieldScrollCapacity.getText());
				double vMassFlow = Double.valueOf(textFieldScrollMassFlow.getText());

				if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
					textFieldScrollDeltaH0.setText("-----");
				} else {
					double tmp = Math.round(vCapacity/vMassFlow/1000.0);
					textFieldScrollDeltaH0.setText(String.valueOf(tmp));
				}
			}
		});
		textFieldScrollMassFlow.setToolTipText("D\u00E9bit Massique");
		textFieldScrollMassFlow.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollMassFlow.setColumns(10);
		textFieldScrollMassFlow.setBounds(295, 25, 51, 20);
		panel_pc2.add(textFieldScrollMassFlow);

		JLabel lblMassFlow_unity = new JLabel("lbs/hr");
		lblMassFlow_unity.setBounds(356, 28, 36, 14);
		panel_pc2.add(lblMassFlow_unity);

		// ---------------------------------------------------------------
		// Check Box BTU/HR or Watt
		// ---------------------------------------------------------------

		checkoxBTU.setBounds(324, 171, 68, 23);
		checkoxBTU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (checkoxBTU.isSelected()) {
					lblCapacity_unity.setText("Btu/hr");
					lblEER_unity.setText("BTU/(hr.W)");

					double vCapacity = PACmain.watt2btuhr(Double.valueOf( textFieldScrollCapacity.getText()));
					double vPower =  Double.valueOf( textFieldScrollPower.getText());

					textFieldScrollCapacity.setText(String.valueOf(Math.round(vCapacity*100.0)/100.0));
					textFieldScrollEER.setText(String.valueOf(Math.round(vCapacity/vPower*10.0)/10.0));
					textFieldScrollDeltaH0.setText("-----");

				} else {
					lblCapacity_unity.setText("Watt");
					lblEER_unity.setText("");

					double vCapacity = PACmain.buthr2watt(Double.valueOf( textFieldScrollCapacity.getText()));
					double vPower =  Double.valueOf( textFieldScrollPower.getText());
					double vMassFlow = Double.valueOf(textFieldScrollMassFlow.getText());

					textFieldScrollCapacity.setText(String.valueOf(Math.round(vCapacity*100.0)/100.0));
					textFieldScrollEER.setText(String.valueOf(Math.round(vCapacity/vPower*10.0)/10.0));

					if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
						textFieldScrollDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(vCapacity/vMassFlow/1000.0);
						textFieldScrollDeltaH0.setText(String.valueOf(tmp));
					}
				}
			}
		});
		checkoxBTU.setSelected(true);
		panel_pc2.add(checkoxBTU);

		// ---------------------------------------------------------------
		// Pound
		// ---------------------------------------------------------------

		chckbxPound.setToolTipText("Pounds/hour");
		chckbxPound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxPound.isSelected()) {
					lblMassFlow_unity.setText("lbs/hr");

					double vMassFlow = PACmain.kg2pound(Double.valueOf(textFieldScrollMassFlow.getText()));
					textFieldScrollMassFlow.setText(String.valueOf(Math.round(vMassFlow*10.0)/10.0));

					textFieldScrollDeltaH0.setText("-----");

				} else {
					lblMassFlow_unity.setText("Kg/s");

					double vCapacity = Double.valueOf( textFieldScrollCapacity.getText());
					double vMassFlow = PACmain.pound2kg(Double.valueOf(textFieldScrollMassFlow.getText()));
					textFieldScrollMassFlow.setText(String.valueOf(Math.round(vMassFlow*10000.0)/10000.0));		

					if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
						textFieldScrollDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(vCapacity/vMassFlow/1000.0);
						textFieldScrollDeltaH0.setText(String.valueOf(tmp));
					}

				}
			}
		});
		chckbxPound.setSelected(true);
		chckbxPound.setBounds(324, 201, 68, 23);
		panel_pc2.add(chckbxPound);

		// ---------------------------------------------------------------
		// H1-H3
		// ---------------------------------------------------------------

		JLabel lblDeltaH0 = new JLabel("H1-H3 :");
		lblDeltaH0.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDeltaH0.setBounds(220, 59, 62, 14);
		panel_pc2.add(lblDeltaH0);

		textFieldScrollDeltaH0 = new JTextField();
		textFieldScrollDeltaH0.setBackground(Color.PINK);
		textFieldScrollDeltaH0.setEditable(false);
		textFieldScrollDeltaH0.setToolTipText("Delta Enthalpie ");
		textFieldScrollDeltaH0.setText("0.0");
		textFieldScrollDeltaH0.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollDeltaH0.setColumns(10);
		textFieldScrollDeltaH0.setBounds(295, 56, 51, 20);
		panel_pc2.add(textFieldScrollDeltaH0);

		JLabel lblDeltaH0_unity = new JLabel("KJ/Kg");
		lblDeltaH0_unity.setBounds(356, 59, 36, 14);
		panel_pc2.add(lblDeltaH0_unity);

		// ---------------------------------------------------------------
		// Voltage
		// ---------------------------------------------------------------
		lblVoltage = new JLabel("Voltage :");
		lblVoltage.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVoltage.setBounds(5, 177, 73, 14);
		panel_pc2.add(lblVoltage);

		textFieldScrollVoltage = new JTextField();
		textFieldScrollVoltage.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {			
				double vPower =  Double.valueOf( textFieldScrollPower.getText());
				double vVoltage = Double.valueOf(textFieldScrollVoltage.getText());
				double vCurrent = Double.valueOf(textFieldScrollCurrent.getText());

				double tmp = Math.round(PACmain.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldScrollCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldScrollVoltage.setToolTipText("Tension");
		textFieldScrollVoltage.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollVoltage.setColumns(10);
		textFieldScrollVoltage.setBounds(82, 174, 62, 20);
		panel_pc2.add(textFieldScrollVoltage);

		JLabel lblVoltage_unity = new JLabel("V");
		lblVoltage_unity.setBounds(154, 177, 46, 14);
		panel_pc2.add(lblVoltage_unity);

		// ---------------------------------------------------------------
		// Cos Phi
		// ---------------------------------------------------------------
		JLabel lblCosphi = new JLabel("Cos (\u03C6)");
		lblCosphi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCosphi.setBounds(5, 208, 73, 14);
		panel_pc2.add(lblCosphi);

		textFieldScrollCosPhi = new JTextField();
		textFieldScrollCosPhi.setToolTipText("Cosinus(Phi)");
		textFieldScrollCosPhi.setText("0.0");
		textFieldScrollCosPhi.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollCosPhi.setEditable(false);
		textFieldScrollCosPhi.setColumns(10);
		textFieldScrollCosPhi.setBackground(Color.PINK);
		textFieldScrollCosPhi.setBounds(82, 205, 62, 20);
		panel_pc2.add(textFieldScrollCosPhi);

		// ---------------------------------------------------------------
		// Compressor Name
		// ---------------------------------------------------------------
		textFieldScrollName = new JTextField();
		textFieldScrollName.setToolTipText("Name can be modified");
		textFieldScrollName.setForeground(new Color(0, 0, 128));
		textFieldScrollName.setBorder(null);
		textFieldScrollName.setBackground(UIManager.getColor("Button.background"));
		textFieldScrollName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldScrollName.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldScrollName.setBounds(10, 10, 167, 52);
		textFieldScrollName.setColumns(10);
		panelPAC.add(textFieldScrollName);

		// ---------------------------------------------------------------
		// Combo box Scroll
		// ---------------------------------------------------------------
		comboBoxScroll = new JComboBox<String>();
		comboBoxScroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxScroll.getSelectedIndex();
				fillScrollTexField(pacl.get(ComboId).getScroll());
			}
		});
		comboBoxScroll.setBounds(243, 10, 131, 20);
		comboBoxScroll.addItem(pacl.get(0).getScroll().getName());
		panelPAC.add(comboBoxScroll);


		// ---------------------------------------------------------------
		// Scroll Save
		// ---------------------------------------------------------------
		JButton btnSaveScroll = new JButton("Sauv.");
		btnSaveScroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxScroll.getSelectedIndex();
				if ( ComboId > 0 ) {
					String tmp = textFieldScrollName.getText();
					UpdateTextField2Pac(pacl.get(ComboId));
					comboBoxScroll.removeItemAt(ComboId);
					comboBoxScroll.insertItemAt(tmp, ComboId);
					comboBoxScroll.setSelectedIndex(ComboId);
					pacl.get(ComboId).getScroll().setName(tmp);
					textFieldScrollName.setText(tmp);
				}
			}
		});
		btnSaveScroll.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnSaveScroll.setBounds(277, 39, 68, 23);
		panelPAC.add(btnSaveScroll);

		// ---------------------------------------------------------------
		// Delete Scroll 
		// ---------------------------------------------------------------
		JButton btnDeleteScroll = new JButton("Suppr.");
		btnDeleteScroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxScroll.getSelectedIndex();
				if ( ComboId > 0 ) {
					pacl.remove(ComboId);
					comboBoxScroll.removeItemAt(ComboId);
					comboBoxScroll.setSelectedIndex(ComboId-1);
				} else {
					JOptionPane.showMessageDialog(frame, "This entry cannot be deleted");
				}
			}
		});
		btnDeleteScroll.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnDeleteScroll.setBounds(355, 39, 68, 23);
		panelPAC.add(btnDeleteScroll);

		// ---------------------------------------------------------------
		// New Scroll
		// ---------------------------------------------------------------
		JButton btnNewScroll = new JButton("Nouv.");
		btnNewScroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxScroll.getSelectedIndex();
				ComboId++;
				pacl.add(ComboId, new Pac());
				comboBoxScroll.insertItemAt("Empty", ComboId);
				comboBoxScroll.setSelectedIndex(ComboId);
				textFieldScrollName.setText("Empty");
				pacl.get(ComboId).getScroll().setName("Empty");
			}
		});
		btnNewScroll.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnNewScroll.setBounds(199, 39, 68, 23);
		panelPAC.add(btnNewScroll);


		// ===============================================================================================================
		//									        PANEL MEASURE 
		// ===============================================================================================================

		JPanel panelComp = new JPanel();
		tabbedPane.addTab("Mesures", null, panelComp, null);
		panelComp.setLayout(null);

		// ---------------------------------------------------------------
		// Button Compute
		// ---------------------------------------------------------------
		JButton btnComp = new JButton("Calcul");
		btnComp.setBounds(66, 271, 89, 23);
		btnComp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copi.setH1(Double.parseDouble(textFieldH1.getText()));
				copi.setH2(Double.parseDouble(textFieldH2.getText()));
				copi.setH3(Double.parseDouble(textFieldH3.getText()));
				copi.setT0(Double.parseDouble(textFieldT0.getText()));
				copi.setTK(Double.parseDouble(textFieldTK.getText()));

				textFieldCarnotFroid.setText(String.valueOf(
						Math.round(copi.cop_carnot_froid()*100.0) /100.0
						));

				/*
						System.out.format("TO = %.2f°C\n",copi.getT0());
						System.out.format("TK = %.2f°C\n",copi.getTK());
						System.out.format("H1 = %.2fkJ/kg\n",copi.getH1());
						System.out.format("H2 = %.2fkJ/kg\n",copi.getH2());
						System.out.format("H3=H4 = %.2fkJ/kg\n",copi.getH3());
				 */
			}
		});
		panelComp.add(btnComp);


		// ---------------------------------------------------------------
		// H1
		// ---------------------------------------------------------------
		JLabel lblH = new JLabel("H1 :");
		lblH.setBounds(25, 76, 30, 14);
		panelComp.add(lblH);

		JLabel lblH1unity = new JLabel("kJ/kg");
		lblH1unity.setBounds(158, 76, 36, 14);
		panelComp.add(lblH1unity);

		textFieldH1 = new JTextField();
		textFieldH1.setText("416");
		textFieldH1.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldH1.setBounds(69, 73, 86, 20);
		textFieldH1.setColumns(10);
		textFieldH1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if ( ( (Character.isDigit(vChar)) || (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == '.') || (vChar == KeyEvent.VK_DELETE)) ) {
					if ( (vChar == '.') && numberOf(textFieldH1.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					e.consume();					
				}
			}
		});
		panelComp.add(textFieldH1);

		// ---------------------------------------------------------------
		// H2
		// ---------------------------------------------------------------
		JLabel lblH2 = new JLabel("H2 :");
		lblH2.setBounds(25, 107, 30, 14);
		panelComp.add(lblH2);

		JLabel lblH2unity = new JLabel("kJ/kg");
		lblH2unity.setBounds(158, 107, 36, 14);
		panelComp.add(lblH2unity);

		textFieldH2 = new JTextField();
		textFieldH2.setText("448");
		textFieldH2.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldH2.setBounds(69, 104, 86, 20);
		textFieldH2.setColumns(10);
		textFieldH2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if ( ( (Character.isDigit(vChar)) || (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == '.') || (vChar == KeyEvent.VK_DELETE)) ) {
					// OK
					if ( (vChar == '.') && numberOf(textFieldH2.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelComp.add(textFieldH2);

		// ---------------------------------------------------------------
		// H3 & H4
		// ---------------------------------------------------------------
		JLabel lblH3 = new JLabel("H3=H4 :");
		lblH3.setBounds(25, 138, 46, 14);
		panelComp.add(lblH3);

		JLabel lblH3unity = new JLabel("kJ/kg");
		lblH3unity.setBounds(158, 138, 36, 14);
		panelComp.add(lblH3unity);

		textFieldH3 = new JTextField();
		textFieldH3.setText("250");
		textFieldH3.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldH3.setColumns(10);
		textFieldH3.setBounds(69, 135, 86, 20);
		textFieldH3.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if ( ( (Character.isDigit(vChar)) || (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == '.') || (vChar == KeyEvent.VK_DELETE)) ) {
					// OK
					if ( (vChar == '.') && numberOf(textFieldH3.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelComp.add(textFieldH3);

		// ---------------------------------------------------------------
		// T0
		// ---------------------------------------------------------------
		JLabel lblT0 = new JLabel("T0 :");
		lblT0.setBounds(25, 14, 30, 14);
		panelComp.add(lblT0);

		JLabel lblT0unity = new JLabel("\u00B0C");
		lblT0unity.setBounds(158, 14, 36, 14);
		panelComp.add(lblT0unity);

		textFieldT0 = new JTextField();
		textFieldT0.setText("5");
		textFieldT0.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldT0.setColumns(10);
		textFieldT0.setBounds(69, 11, 86, 20);
		textFieldT0.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if ( ( (Character.isDigit(vChar)) || (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == '.') || (vChar == KeyEvent.VK_DELETE)) ) {
					// OK
					if ( (vChar == '.') && numberOf(textFieldT0.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelComp.add(textFieldT0);

		// ---------------------------------------------------------------
		// TK
		// ---------------------------------------------------------------
		JLabel lblTk = new JLabel("TK :");
		lblTk.setBounds(25, 45, 30, 14);
		panelComp.add(lblTk);

		JLabel lblTkunity = new JLabel("\u00B0C");
		lblTkunity.setBounds(158, 42, 36, 14);
		panelComp.add(lblTkunity);

		textFieldTK = new JTextField();
		textFieldTK.setText("48");
		textFieldTK.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldTK.setColumns(10);
		textFieldTK.setBounds(69, 42, 86, 20);
		textFieldTK.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if ( ( (Character.isDigit(vChar)) || (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == '.') || (vChar == KeyEvent.VK_DELETE)) ) {
					// OK
					if ( (vChar == '.') && numberOf(textFieldTK.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelComp.add(textFieldTK);

		// ---------------------------------------------------------------
		// Image
		// ---------------------------------------------------------------

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(WinPrime.class.getResource("/pacp/images/enthalpie.jpg")));
		lblNewLabel.setBounds(233, 11, 150, 100);
		panelComp.add(lblNewLabel);

		// ---------------------------------------------------------------
		// Carnot Froid
		// ---------------------------------------------------------------
		JLabel labelCarnotFroid = new JLabel("Carnot Froid :");
		labelCarnotFroid.setBounds(212, 138, 75, 14);
		panelComp.add(labelCarnotFroid);

		textFieldCarnotFroid = new JTextField();
		textFieldCarnotFroid.setBackground(Color.PINK);
		textFieldCarnotFroid.setEditable(false);
		textFieldCarnotFroid.setText("0");
		textFieldCarnotFroid.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCarnotFroid.setColumns(10);
		textFieldCarnotFroid.setBounds(297, 135, 86, 20);
		panelComp.add(textFieldCarnotFroid);

		// ===============================================================================================================
		//									        PANEL DEFINITION 
		// ===============================================================================================================

		JPanel panelDef = new JPanel();
		tabbedPane.addTab("D\u00E9finitions", null, panelDef, null);
		panelDef.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 432, 467);
		panelDef.add(scrollPane);

		JEditorPane txtdef = new JEditorPane();
		txtdef.setEditable(false);
		txtdef.setContentType("text/html");
		txtdef.setText("<b>Capacity : </b>Puissance Frigorifique<b><br>\r\n&nbsp;&nbsp;&nbsp; </b>(H1-H3) x D\u00E9bit Massique<br>\r\n<b>COP:</b> COefficient de Performance <br>\r\n&nbsp;&nbsp;&nbsp; COP = Puissance <u>Restitu\u00E9e</u>\r\n(Chaleur) / Puissance consomm\u00E9e <br>\r\n&nbsp;&nbsp;&nbsp; Attention sur les catalogues le COP\r\n(constructeur) <br>\r\n&nbsp;&nbsp;&nbsp; est calcul\u00E9 \u00E0 partir d'une temp\u00E9rature\r\nd'eau de nappe <br>\r\n&nbsp;&nbsp;&nbsp; phr\u00E9atique de 10\u00B0C <br>\r\n<b>COP constructeur :</b> <br>\r\n&nbsp;&nbsp;&nbsp; Performance d'une PAC d\u00E9termin\u00E9e en\r\nlaboratoire ,<br>\r\n&nbsp;&nbsp;&nbsp; donc assez loin des r\u00E9alit\u00E9s.<br>\r\n<b>COP global de la PAC :</b> <br>\r\n&nbsp;&nbsp;&nbsp; Performance qui tient compte des\r\nauxiliaires, <br>\r\n&nbsp;&nbsp;&nbsp; ventilateurs, pompes,etc..<br>\r\n<b>COP annuel (COPPA) : </b><br>\r\n&nbsp;&nbsp;&nbsp; Performance r\u00E9elle calcul\u00E9e pendant une\r\np\u00E9riode compl\u00E8te <br>\r\n&nbsp;&nbsp;&nbsp; de chauffage qui tient compte des\r\nsp\u00E9cificit\u00E9s de l'installation.<br>\r\n<b>EER (Energy Efficiency Ratio) : </b><br>\r\n&nbsp;&nbsp;&nbsp;&nbsp;Coefficient d'Efficacit\u00E9\r\nFrigorifique (ou) COP froid <br>\r\n&nbsp;&nbsp;&nbsp; EER = Puissance&nbsp;<u>Absorb\u00E9e</u>\r\n(Froid) / Puissance consomm\u00E9e<br>\r\n<b>Mass Flow :</b><br>\r\n&nbsp;&nbsp;&nbsp; D\u00E9bit Massique (Kg/s)<br>\r\n<span style=\"font-weight: bold;\">BTU/h :</span>\r\nBritish Thermal Unit per hour<span style=\"font-weight: bold;\"></span><br\r\n style=\"font-weight: bold;\">\r\n&nbsp;&nbsp;&nbsp; Unit\u00E9 anglo-saxonne de puissance: <br>\r\n&nbsp;&nbsp;&nbsp; 1 000 BTU/h valent approximativement\r\n293,071 W &nbsp;<br>\r\n");
		scrollPane.setViewportView(txtdef);


		// ---------------------------------------------------------------
		// Display 
		// ---------------------------------------------------------------
		frame.setVisible(true);

	}

	/**
	 * 
	 * @param str
	 * @param c
	 * @return
	 */
	public static int numberOf(String str,char c) {
		int res=0;
		if(str==null)
			return res;
		for(int i=0;i<str.length();i++)
			if(c==str.charAt(i))
				res++;
		return res;
	}


	/**
	 * Fill all the field For Scroll 
	 * with class Scroll infos !!
	 * The data are read from the variable, where the information is stored in British value
	 * @param pac
	 */
	private void fillScrollTexField(Scroll scroll) {
		boolean weclickf = false;
		boolean weclickb = false;
		boolean weclickp = false;

		if (!checkoxFaren.isSelected()) {
			checkoxFaren.doClick();
			weclickf = true;
		}
		if (!checkoxBTU.isSelected()) {
			checkoxBTU.doClick();
			weclickb = true;
		}
		if (!chckbxPound.isSelected()) {
			chckbxPound.doClick();
			weclickp = true;
		}

		textFieldScrollName.setText(scroll.getName());

		textFieldScrollEvap.setText(String.valueOf(scroll.getEvap()));
		textFieldScrollRG.setText(String.valueOf(scroll.getRG()));
		textFieldScrollCond.setText(String.valueOf(scroll.getCond()));
		textFieldScrollLiq.setText(String.valueOf(scroll.getLiq()));
		textFieldScrollCapacity.setText(String.valueOf(scroll.getCapacity()));
		textFieldScrollPower.setText(String.valueOf(scroll.getPower()));
		textFieldScrollCurrent.setText(String.valueOf(scroll.getCurrent()));
		textFieldScrollSurchauffe.setText(String.valueOf(Math.round(scroll.getRG() - scroll.getEvap())));
		textFieldScrollSousRefroid.setText(String.valueOf(Math.round(scroll.getCond() - scroll.getLiq())));
		textFieldScrollEER.setText(String.valueOf(Math.round(scroll.getCapacity()/scroll.getPower()*10.0)/10.0));
		textFieldScrollMassFlow.setText(String.valueOf(scroll.getMassFlow()));
		textFieldScrollDeltaH0.setText("-----");
		textFieldScrollVoltage.setText(String.valueOf(scroll.getVoltage()));
		double tmp = Math.round(PACmain.cosphi(scroll.getPower(), scroll.getVoltage(), scroll.getCurrent())*10000.0)/10000.0;
		textFieldScrollCosPhi.setText(String.valueOf(tmp));

		if (weclickf) {
			checkoxFaren.doClick();
		}
		if (weclickb) {
			checkoxBTU.doClick();
		}
		if (weclickp) {
			chckbxPound.doClick();
		}

	}

	// Will save the information from TextField to PAC variable
	// Data will be stored in Anglo-Saxon Format
	private void UpdateTextField2Pac( Pac paci) {
		Scroll scroll = paci.getScroll();

		boolean weclickf = false;
		boolean weclickb = false;
		boolean weclickp = false;

		if (!checkoxFaren.isSelected()) {
			checkoxFaren.doClick();
			weclickf = true;
		}
		if (!checkoxBTU.isSelected()) {
			checkoxBTU.doClick();
			weclickb = true;
		}
		if (!chckbxPound.isSelected()) {
			chckbxPound.doClick();
			weclickp = true;
		}

		scroll.setName(textFieldScrollName.getText());

		scroll.setEvap(Double.valueOf(textFieldScrollEvap.getText()));
		scroll.setRG(Double.valueOf(textFieldScrollRG.getText()));
		scroll.setCond(Double.valueOf(textFieldScrollCond.getText()));
		scroll.setLiq(Double.valueOf(textFieldScrollLiq.getText()));
		scroll.setCapacity(Double.valueOf(textFieldScrollCapacity.getText()));
		scroll.setPower(Double.valueOf(textFieldScrollPower.getText()));
		scroll.setCurrent(Double.valueOf(textFieldScrollCurrent.getText()));
		scroll.setMassFlow(Double.valueOf(textFieldScrollMassFlow.getText()));
		scroll.setVoltage(Double.valueOf(textFieldScrollVoltage.getText()));

		if (weclickf) {
			checkoxFaren.doClick();
		}
		if (weclickb) {
			checkoxBTU.doClick();
		}
		if (weclickp) {
			chckbxPound.doClick();
		}

	}
}

