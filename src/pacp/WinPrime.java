/*
 * - PAC-Tool - 
 * Tool for understanding basics and computation of PAC (Pompe à Chaleur)
 * Copyright (C) 2016 christian.klugesherz@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (version 2)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
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
import java.awt.Cursor;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.BevelBorder;

public class WinPrime {

	private JFrame frame;
	private JTextField textFieldH1;
	private JTextField textFieldH2;
	private JTextField textFieldH3;
	private JTextField textFieldTK;
	private JTextField textFieldT0;
	private JTextField textFieldCarnotFroid;
	private JTextField textFieldCompressorEvap;
	private JTextField textFieldCompressorRG;
	private JTextField textFieldCompressorCond;
	private JTextField textFieldCompressorLiq;
	private JTextField textFieldCompressorCapacity;
	private JTextField textFieldCompressorPower;
	private JTextField textFieldCompressorCurrent;
	private JTextField textFieldCompressorSurchauffe;
	private JTextField textFieldCompressorSousRefroid;
	private JTextField textFieldCompressorEER;
	private JTextField textFieldCompressorMassFlow;
	private JTextField textFieldCompressorDeltaH0;
	private JTextField textFieldCompressorVoltage;
	private JTextField textFieldCompressorCosPhi;
	private JTextField textFieldCompressorName;
	private JTextField textFieldCirculatorVoltage;

	private JCheckBox checkoxFaren;
	private JCheckBox checkoxBTU;
	private JCheckBox chckbxPound;
	private JComboBox<String> comboBoxCompressor;
	
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

	// List of PAC (First Pac can never been deleted!!)
	private List<Pac> pacl = new ArrayList<Pac>();
	private Enthalpy enthalpy;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Create the application.
	 * 
	 */
	public WinPrime(Pac paci, Ccop copi, Enthalpy enthalpyi) {
		enthalpy = enthalpyi;
		pacl.add(paci);	

		// Create Window
		initialize(paci,copi);
		fillCompressorTexField(paci.getCompressor());
		
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Get the frame visible
	 */
	public void WinPrimeVisible() {
		frame.setVisible(true);
	}
	
	/**
	 * Fill all the Compressor features field in the panel 
	 * The data are read from the variable, where the information is stored in British value
	 * @param pac
	 */
	private void fillCompressorTexField(Compressor compressor) {
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

		textFieldCompressorName.setText(compressor.getName());

		textFieldCompressorEvap.setText(String.valueOf(compressor.getEvap()));
		textFieldCompressorRG.setText(String.valueOf(compressor.getRG()));
		textFieldCompressorCond.setText(String.valueOf(compressor.getCond()));
		textFieldCompressorLiq.setText(String.valueOf(compressor.getLiq()));
		textFieldCompressorCapacity.setText(String.valueOf(compressor.getCapacity()));
		textFieldCompressorPower.setText(String.valueOf(compressor.getPower()));
		textFieldCompressorCurrent.setText(String.valueOf(compressor.getCurrent()));
		textFieldCompressorSurchauffe.setText(String.valueOf(Math.round(compressor.getRG() - compressor.getEvap())));
		textFieldCompressorSousRefroid.setText(String.valueOf(Math.round(compressor.getCond() - compressor.getLiq())));
		textFieldCompressorEER.setText(String.valueOf(Math.round(compressor.getCapacity()/compressor.getPower()*10.0)/10.0));
		textFieldCompressorMassFlow.setText(String.valueOf(compressor.getMassFlow()));
		textFieldCompressorDeltaH0.setText("-----");
		textFieldCompressorVoltage.setText(String.valueOf(compressor.getVoltage()));
		double tmp = Math.round(Misc.cosphi(compressor.getPower(), compressor.getVoltage(), compressor.getCurrent())*10000.0)/10000.0;
		textFieldCompressorCosPhi.setText(String.valueOf(tmp));

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

	/**
	 * Will save the information from Panel TextField to PAC variable
	 * Data will be stored in Anglo-Saxon Format
	 * @param paci
	 */
	private void UpdateTextField2Pac( Pac paci) {
		Compressor compressor = paci.getCompressor();

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

		compressor.setName(textFieldCompressorName.getText());

		compressor.setEvap(Double.valueOf(textFieldCompressorEvap.getText()));
		compressor.setRG(Double.valueOf(textFieldCompressorRG.getText()));
		compressor.setCond(Double.valueOf(textFieldCompressorCond.getText()));
		compressor.setLiq(Double.valueOf(textFieldCompressorLiq.getText()));
		compressor.setCapacity(Double.valueOf(textFieldCompressorCapacity.getText()));
		compressor.setPower(Double.valueOf(textFieldCompressorPower.getText()));
		compressor.setCurrent(Double.valueOf(textFieldCompressorCurrent.getText()));
		compressor.setMassFlow(Double.valueOf(textFieldCompressorMassFlow.getText()));
		compressor.setVoltage(Double.valueOf(textFieldCompressorVoltage.getText()));

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

	// -------------------------------------------------------
	// 				 GENERATED BY WIN BUILDER
	// -------------------------------------------------------
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Pac paci, Ccop copi) {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WinPrime.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frame.setTitle("PAC Tool (" + Run.PACTool_Version+ ")");
		frame.setBounds(100, 100, 443, 574);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//PacCommon.centreWindow(frame);

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

					System.out.println("READ JSON object to file");  
					System.out.println("-----------------------");  
					System.out.print(jsonObjectR);  

					JSONArray jsonObjectCompressorL = (JSONArray) jsonObjectR.get("Compressor");
					JSONObject jsonObjectCompressorn;
					JSONObject jsonObjectCfg = (JSONObject) jsonObjectR.get("Cfg");
					JSONObject jObjEBImg = (JSONObject) jsonObjectR.get("EnthalpyBkgdImg");

					
					EnthalpyBkgdImg vEnthalpyBkgdImg;
					vEnthalpyBkgdImg = enthalpy.getEnthalpyBkgImage();
					
					vEnthalpyBkgdImg.setJsonObject(jObjEBImg);
					
					System.out.println(vEnthalpyBkgdImg.getEnthalpyImageFile());
					System.out.println(vEnthalpyBkgdImg.getiBgH1x());

					System.exit(0);

					// Read Configuration & Preferences
					long nbCompressorList = (long) jsonObjectCfg.get("nbCompressorList");

					// Read Compressor List + Affect to pacl compressor
					for(int i=1;i<pacl.size();i++) {
						pacl.remove(i);
						comboBoxCompressor.removeItemAt(i);
					}
					//System.out.println("size="+pacl.size());
					//System.out.println("comboBoxCompressor size="+comboBoxCompressor.getItemCount());

					for(int i=0;i<nbCompressorList;i++) {
						jsonObjectCompressorn = (JSONObject) jsonObjectCompressorL.get(i);
						if(i==0) {
							//pacl.add(i, paci);
							pacl.get(i).getCompressor().setJsonObject(jsonObjectCompressorn);
						}
						else {
							pacl.add(i, new Pac());
							pacl.get(i).getCompressor().setJsonObject(jsonObjectCompressorn);
							comboBoxCompressor.insertItemAt(pacl.get(i).getCompressor().getName(),i);
						}
					}
					comboBoxCompressor.setSelectedIndex(0);
					fillCompressorTexField(pacl.get(0).getCompressor());

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

				// Creation of a JSON object which will contain all configuration to save
					// Squiggly brackets{} act as containers
					// Square brackets [] represents arrays.
					// Names and values are separated by a colon:
					// Array elements are separated by commas
					
				// Create PAC Array JSON Object
				JSONArray jsonObjPacL = new JSONArray();			
				for(int i=0;i<pacl.size();i++) {
					JSONObject jsonObj = new JSONObject();
					jsonObj = pacl.get(i).getJsonObject();
					jsonObjPacL.add(jsonObj);
				}

				// Create JSON data for the PAC-Tool : 
				// Configuration + preferences 
				JSONObject ObjCfg = new JSONObject();  
				ObjCfg.put("checkoxBTU", checkoxBTU.isSelected());
				ObjCfg.put("chckbxPound", chckbxPound.isSelected());
				ObjCfg.put("checkoxFaren", checkoxFaren.isSelected());


				EnthalpyBkgdImg vEnthalpyBkgdImg;
				vEnthalpyBkgdImg = enthalpy.getEnthalpyBkgImage();
				
				JSONObject jObjEBImg = new JSONObject();
				jObjEBImg = vEnthalpyBkgdImg.getJsonObject();
				
			
				// Compact in JSON Data: PacTool
				JSONObject ObjPacTool = new JSONObject();  
				ObjPacTool.put("PacList", jsonObjPacL);  
				ObjPacTool.put("Cfg", ObjCfg);  
				ObjPacTool.put("EnthalpyBkgdImg", jObjEBImg);  

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
		mexit.setIcon(new ImageIcon(WinPrime.class.getResource("/pacp/images/sortir-session16.png")));
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
		
		JMenuItem mImgEnthalpyCfg = new JMenuItem("Enthalpie Conf.");
		mImgEnthalpyCfg.setIcon(new ImageIcon(WinPrime.class.getResource("/pacp/images/configuration16.png")));
		mImgEnthalpyCfg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					WinConfEnthalpy window = new WinConfEnthalpy(enthalpy);
					window.WinConfEnthalpyVisible();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		mpreference.add(mImgEnthalpyCfg);

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
		about.setIcon(new ImageIcon(WinPrime.class.getResource("/pacp/images/About16.png")));
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					WinAbout window = new WinAbout();
					window.WinAboutVisible();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		help.add(about);;
		frame.getContentPane().setLayout(null);

		// ===============================================================================================================
		//													TABBED PANE
		// ===============================================================================================================

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 437, 525);
		frame.getContentPane().add(tabbedPane);

		// ===============================================================================================================
		//									                 PANEL PAC
		// ===============================================================================================================
		JPanel panelSroll = new JPanel();
		panelSroll.setForeground(Color.BLUE);
		tabbedPane.addTab("Compresseur", null, panelSroll, null);
		panelSroll.setLayout(null);

		// ================================================================
		// 					  	Performance Panel
		// ================================================================
		JPanel panel_pc1 = new JPanel();
		panel_pc1.setBorder(new TitledBorder(null, "Donn\u00E9es Performance Constructeur (Temp\u00E9rature)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pc1.setBounds(5, 73, 420, 161);
		panel_pc1.setLayout(null);
		panelSroll.add(panel_pc1);

		// ---------------------------------------------------------------
		// EVAP
		// ---------------------------------------------------------------
		lblEvap = new JLabel("Evap :");
		lblEvap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEvap.setBounds(5, 28, 93, 14);
		panel_pc1.add(lblEvap);

		textFieldCompressorEvap = new JTextField();
		textFieldCompressorEvap.setToolTipText("Temp\u00E9rature d'\u00E9vaporation (T0)");
		textFieldCompressorEvap.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tRG = Double.valueOf( textFieldCompressorRG.getText());
				double tEvap = Double.valueOf( textFieldCompressorEvap.getText());
				double tmp = Math.round(tRG - tEvap);
				textFieldCompressorSurchauffe.setText(String.valueOf(tmp));
			}
		});
		textFieldCompressorEvap.setBounds(99, 25, 67, 20);
		panel_pc1.add(textFieldCompressorEvap);

		textFieldCompressorEvap.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorEvap.setColumns(10);

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

		textFieldCompressorRG = new JTextField();
		textFieldCompressorRG.setToolTipText("Temp\u00E9rature d'aspiration du compresseur Point : (1)");
		textFieldCompressorRG.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tRG = Double.valueOf( textFieldCompressorRG.getText());
				double tEvap = Double.valueOf( textFieldCompressorEvap.getText());
				double tmp = Math.round(tRG - tEvap);
				textFieldCompressorSurchauffe.setText(String.valueOf(tmp));			
			}
		});
		textFieldCompressorRG.setBounds(99, 64, 67, 20);
		panel_pc1.add(textFieldCompressorRG);
		textFieldCompressorRG.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorRG.setColumns(10);

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

		textFieldCompressorSurchauffe = new JTextField();
		textFieldCompressorSurchauffe.setText("0.0");
		textFieldCompressorSurchauffe.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorSurchauffe.setBackground(Color.PINK);
		textFieldCompressorSurchauffe.setEditable(false);
		textFieldCompressorSurchauffe.setBounds(120, 98, 46, 20);
		panel_pc1.add(textFieldCompressorSurchauffe);
		textFieldCompressorSurchauffe.setColumns(10);

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

		textFieldCompressorCond = new JTextField();
		textFieldCompressorCond.setToolTipText("Temp\u00E9rature de condensation (TK) ");
		textFieldCompressorCond.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tcond = Double.valueOf( textFieldCompressorCond.getText());
				double tliq = Double.valueOf( textFieldCompressorLiq.getText());
				double tmp = Math.round(tcond - tliq);
				textFieldCompressorSousRefroid.setText(String.valueOf(tmp));
			}
		});
		textFieldCompressorCond.setBounds(302, 25, 75, 20);
		textFieldCompressorCond.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorCond.setColumns(10);
		panel_pc1.add(textFieldCompressorCond);

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

		textFieldCompressorLiq = new JTextField();
		textFieldCompressorLiq.setToolTipText("Temp\u00E9rature Entr\u00E9e D\u00E9tendeur : Point (3) ");
		textFieldCompressorLiq.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tcond = Double.valueOf( textFieldCompressorCond.getText());
				double tliq = Double.valueOf( textFieldCompressorLiq.getText());
				double tmp = Math.round(tcond - tliq);
				textFieldCompressorSousRefroid.setText(String.valueOf(tmp));
			}
		});

		textFieldCompressorLiq.setBounds(302, 64, 75, 20);
		textFieldCompressorLiq.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorLiq.setColumns(10);
		panel_pc1.add(textFieldCompressorLiq);

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

		textFieldCompressorSousRefroid = new JTextField();
		textFieldCompressorSousRefroid.setText("0.0");
		textFieldCompressorSousRefroid.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorSousRefroid.setEditable(false);
		textFieldCompressorSousRefroid.setColumns(10);
		textFieldCompressorSousRefroid.setBackground(Color.PINK);
		textFieldCompressorSousRefroid.setBounds(331, 98, 46, 20);
		panel_pc1.add(textFieldCompressorSousRefroid);

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

					double tcond = Misc.degre2farenheit(Double.valueOf( textFieldCompressorCond.getText()));
					double tliq = Misc.degre2farenheit(Double.valueOf( textFieldCompressorLiq.getText()));
					double tRG = Misc.degre2farenheit(Double.valueOf( textFieldCompressorRG.getText()));
					double tEvap = Misc.degre2farenheit(Double.valueOf( textFieldCompressorEvap.getText()));

					textFieldCompressorEvap.setText(String.valueOf(Math.round(tEvap*100.0)/100.0));
					textFieldCompressorRG.setText(String.valueOf(Math.round(tRG*100.0)/100.0));
					textFieldCompressorCond.setText(String.valueOf(Math.round(tcond*100.0)/100.0));
					textFieldCompressorLiq.setText(String.valueOf(Math.round(tliq*100.0)/100.0));

					double tmp = Math.round(tRG - tEvap);
					textFieldCompressorSurchauffe.setText(String.valueOf(tmp));			

					tmp = Math.round(tcond - tliq);
					textFieldCompressorSousRefroid.setText(String.valueOf(tmp));
				} else {
					lblTemp_unity1.setText("°C");
					lblTemp_unity2.setText("°C");
					lblTemp_unity3.setText("°C");
					lblTemp_unity4.setText("°C");
					lblTemp_unity5.setText("°C");
					lblTemp_unity6.setText("°C");

					double tcond = Misc.farenheit2degre(Double.valueOf( textFieldCompressorCond.getText()));
					double tliq = Misc.farenheit2degre(Double.valueOf( textFieldCompressorLiq.getText()));
					double tRG = Misc.farenheit2degre(Double.valueOf( textFieldCompressorRG.getText()));
					double tEvap = Misc.farenheit2degre(Double.valueOf( textFieldCompressorEvap.getText()));

					textFieldCompressorEvap.setText(String.valueOf(Math.round(tEvap*100.0)/100.0));
					textFieldCompressorRG.setText(String.valueOf(Math.round(tRG*100.0)/100.0));
					textFieldCompressorCond.setText(String.valueOf(Math.round(tcond*100.0)/100.0));
					textFieldCompressorLiq.setText(String.valueOf(Math.round(tliq*100.0)/100.0));

					double tmp = Math.round(tRG - tEvap);
					textFieldCompressorSurchauffe.setText(String.valueOf(tmp));			

					tmp = Math.round(tcond - tliq);
					textFieldCompressorSousRefroid.setText(String.valueOf(tmp));
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
		panelSroll.add(panel_pc2);

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

		textFieldCompressorCapacity = new JTextField();
		textFieldCompressorCapacity.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {

				double vCapacity = Double.valueOf( textFieldCompressorCapacity.getText());
				double vPower =  Double.valueOf( textFieldCompressorPower.getText());
				double vMassFlow = Double.valueOf(textFieldCompressorMassFlow.getText());
				double tmp = Math.round(vCapacity/vPower*10.0)/10.0;
				textFieldCompressorEER.setText(String.valueOf(tmp));

				if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
					textFieldCompressorDeltaH0.setText("-----");
				} else {
					tmp = Math.round(vCapacity/vMassFlow/1000.0);
					textFieldCompressorDeltaH0.setText(String.valueOf(tmp));
				}
			}
		});
		textFieldCompressorCapacity.setToolTipText("Puissance frigorifique: (H1-H3) x D\u00E9bit Massique");
		textFieldCompressorCapacity.setBounds(82, 25, 62, 20);
		textFieldCompressorCapacity.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorCapacity.setColumns(10);
		panel_pc2.add(textFieldCompressorCapacity);

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

		textFieldCompressorPower = new JTextField();
		textFieldCompressorPower.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {

				double vCapacity = Double.valueOf( textFieldCompressorCapacity.getText());
				double vPower =  Double.valueOf( textFieldCompressorPower.getText());
				double vVoltage = Double.valueOf(textFieldCompressorVoltage.getText());
				double vCurrent = Double.valueOf(textFieldCompressorCurrent.getText());
				double tmp = Math.round(vCapacity/vPower*10.0)/10.0;
				textFieldCompressorEER.setText(String.valueOf(tmp));	

				tmp = Math.round(Misc.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldCompressorCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldCompressorPower.setToolTipText("Puissance Absorb\u00E9e");
		textFieldCompressorPower.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorPower.setBounds(82, 56, 62, 20);
		textFieldCompressorPower.setColumns(10);
		panel_pc2.add(textFieldCompressorPower);

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

		textFieldCompressorCurrent = new JTextField();
		textFieldCompressorCurrent.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double vPower =  Double.valueOf( textFieldCompressorPower.getText());
				double vVoltage = Double.valueOf(textFieldCompressorVoltage.getText());
				double vCurrent = Double.valueOf(textFieldCompressorCurrent.getText());

				double tmp = Math.round(Misc.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldCompressorCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldCompressorCurrent.setToolTipText("Courant absorb\u00E9");
		textFieldCompressorCurrent.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorCurrent.setColumns(10);
		textFieldCompressorCurrent.setBounds(82, 87, 62, 20);
		panel_pc2.add(textFieldCompressorCurrent);

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

		textFieldCompressorEER = new JTextField();
		textFieldCompressorEER.setEditable(false);
		textFieldCompressorEER.setBackground(Color.PINK);
		textFieldCompressorEER.setToolTipText("EER (Energy Efficiency Ratio) : Coefficient d\u2019efficacit\u00E9 frigorifique");
		textFieldCompressorEER.setText("0.0");
		textFieldCompressorEER.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorEER.setColumns(10);
		textFieldCompressorEER.setBounds(82, 125, 62, 20);
		panel_pc2.add(textFieldCompressorEER);

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

		textFieldCompressorMassFlow = new JTextField();
		textFieldCompressorMassFlow.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double vCapacity = Double.valueOf( textFieldCompressorCapacity.getText());
				double vMassFlow = Double.valueOf(textFieldCompressorMassFlow.getText());

				if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
					textFieldCompressorDeltaH0.setText("-----");
				} else {
					double tmp = Math.round(vCapacity/vMassFlow/1000.0);
					textFieldCompressorDeltaH0.setText(String.valueOf(tmp));
				}
			}
		});
		textFieldCompressorMassFlow.setToolTipText("D\u00E9bit Massique");
		textFieldCompressorMassFlow.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorMassFlow.setColumns(10);
		textFieldCompressorMassFlow.setBounds(295, 25, 51, 20);
		panel_pc2.add(textFieldCompressorMassFlow);

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

					double vCapacity = Misc.watt2btuhr(Double.valueOf( textFieldCompressorCapacity.getText()));
					double vPower =  Double.valueOf( textFieldCompressorPower.getText());

					textFieldCompressorCapacity.setText(String.valueOf(Math.round(vCapacity*100.0)/100.0));
					textFieldCompressorEER.setText(String.valueOf(Math.round(vCapacity/vPower*10.0)/10.0));
					textFieldCompressorDeltaH0.setText("-----");

				} else {
					lblCapacity_unity.setText("Watt");
					lblEER_unity.setText("");

					double vCapacity = Misc.btuhr2watt(Double.valueOf( textFieldCompressorCapacity.getText()));
					double vPower =  Double.valueOf( textFieldCompressorPower.getText());
					double vMassFlow = Double.valueOf(textFieldCompressorMassFlow.getText());

					textFieldCompressorCapacity.setText(String.valueOf(Math.round(vCapacity*100.0)/100.0));
					textFieldCompressorEER.setText(String.valueOf(Math.round(vCapacity/vPower*10.0)/10.0));

					if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
						textFieldCompressorDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(vCapacity/vMassFlow/1000.0);
						textFieldCompressorDeltaH0.setText(String.valueOf(tmp));
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

					double vMassFlow = Misc.kg2pound(Double.valueOf(textFieldCompressorMassFlow.getText()));
					textFieldCompressorMassFlow.setText(String.valueOf(Math.round(vMassFlow*10.0)/10.0));

					textFieldCompressorDeltaH0.setText("-----");

				} else {
					lblMassFlow_unity.setText("Kg/s");

					double vCapacity = Double.valueOf( textFieldCompressorCapacity.getText());
					double vMassFlow = Misc.pound2kg(Double.valueOf(textFieldCompressorMassFlow.getText()));
					textFieldCompressorMassFlow.setText(String.valueOf(Math.round(vMassFlow*10000.0)/10000.0));		

					if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
						textFieldCompressorDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(vCapacity/vMassFlow/1000.0);
						textFieldCompressorDeltaH0.setText(String.valueOf(tmp));
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

		textFieldCompressorDeltaH0 = new JTextField();
		textFieldCompressorDeltaH0.setBackground(Color.PINK);
		textFieldCompressorDeltaH0.setEditable(false);
		textFieldCompressorDeltaH0.setToolTipText("Delta Enthalpie ");
		textFieldCompressorDeltaH0.setText("0.0");
		textFieldCompressorDeltaH0.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorDeltaH0.setColumns(10);
		textFieldCompressorDeltaH0.setBounds(295, 56, 51, 20);
		panel_pc2.add(textFieldCompressorDeltaH0);

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

		textFieldCompressorVoltage = new JTextField();
		textFieldCompressorVoltage.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {			
				double vPower =  Double.valueOf( textFieldCompressorPower.getText());
				double vVoltage = Double.valueOf(textFieldCompressorVoltage.getText());
				double vCurrent = Double.valueOf(textFieldCompressorCurrent.getText());

				double tmp = Math.round(Misc.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldCompressorCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldCompressorVoltage.setToolTipText("Tension");
		textFieldCompressorVoltage.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorVoltage.setColumns(10);
		textFieldCompressorVoltage.setBounds(82, 174, 62, 20);
		panel_pc2.add(textFieldCompressorVoltage);

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

		textFieldCompressorCosPhi = new JTextField();
		textFieldCompressorCosPhi.setToolTipText("Cosinus(Phi)");
		textFieldCompressorCosPhi.setText("0.0");
		textFieldCompressorCosPhi.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorCosPhi.setEditable(false);
		textFieldCompressorCosPhi.setColumns(10);
		textFieldCompressorCosPhi.setBackground(Color.PINK);
		textFieldCompressorCosPhi.setBounds(82, 205, 62, 20);
		panel_pc2.add(textFieldCompressorCosPhi);

		// ---------------------------------------------------------------
		// Compressor Name
		// ---------------------------------------------------------------
		textFieldCompressorName = new JTextField();
		textFieldCompressorName.setToolTipText("Name can be modified");
		textFieldCompressorName.setForeground(new Color(0, 0, 128));
		textFieldCompressorName.setBorder(null);
		textFieldCompressorName.setBackground(UIManager.getColor("Button.background"));
		textFieldCompressorName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCompressorName.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldCompressorName.setBounds(10, 10, 167, 52);
		textFieldCompressorName.setColumns(10);
		panelSroll.add(textFieldCompressorName);

		// ---------------------------------------------------------------
		// Combo box Compressor
		// ---------------------------------------------------------------
		comboBoxCompressor = new JComboBox<String>();
		comboBoxCompressor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCompressor.getSelectedIndex();
				fillCompressorTexField(pacl.get(ComboId).getCompressor());
			}
		});
		comboBoxCompressor.setBounds(243, 10, 131, 20);
		comboBoxCompressor.addItem(pacl.get(0).getCompressor().getName());
		panelSroll.add(comboBoxCompressor);


		// ---------------------------------------------------------------
		// Compressor Save
		// ---------------------------------------------------------------
		JButton btnSaveCompressor = new JButton("Sauv.");
		btnSaveCompressor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCompressor.getSelectedIndex();
				if ( ComboId > 0 ) {
					String tmp = textFieldCompressorName.getText();
					UpdateTextField2Pac(pacl.get(ComboId));
					comboBoxCompressor.removeItemAt(ComboId);
					comboBoxCompressor.insertItemAt(tmp, ComboId);
					comboBoxCompressor.setSelectedIndex(ComboId);
					pacl.get(ComboId).getCompressor().setName(tmp);
					textFieldCompressorName.setText(tmp);
				}
			}
		});
		btnSaveCompressor.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnSaveCompressor.setBounds(277, 39, 68, 23);
		panelSroll.add(btnSaveCompressor);

		// ---------------------------------------------------------------
		// Delete Compressor 
		// ---------------------------------------------------------------
		JButton btnDeleteCompressor = new JButton("Suppr.");
		btnDeleteCompressor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCompressor.getSelectedIndex();
				if ( ComboId > 0 ) {
					pacl.remove(ComboId);
					comboBoxCompressor.removeItemAt(ComboId);
					comboBoxCompressor.setSelectedIndex(ComboId-1);
				} else {
					JOptionPane.showMessageDialog(frame, "This entry cannot be deleted");
				}
			}
		});
		btnDeleteCompressor.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnDeleteCompressor.setBounds(355, 39, 68, 23);
		panelSroll.add(btnDeleteCompressor);

		// ---------------------------------------------------------------
		// New Compressor
		// ---------------------------------------------------------------
		JButton btnNewCompressor = new JButton("Nouv.");
		btnNewCompressor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCompressor.getSelectedIndex();
				ComboId++;
				pacl.add(ComboId, new Pac());
				comboBoxCompressor.insertItemAt("Empty", ComboId);
				comboBoxCompressor.setSelectedIndex(ComboId);
				textFieldCompressorName.setText("Empty");
				pacl.get(ComboId).getCompressor().setName("Empty");
			}
		});
		btnNewCompressor.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnNewCompressor.setBounds(199, 39, 68, 23);
		panelSroll.add(btnNewCompressor);

		JPanel panelCirculator = new JPanel();
		tabbedPane.addTab("Circulateur", null, panelCirculator, null);
		panelCirculator.setLayout(null);

		JLabel lblCirculVolt = new JLabel("Tension");
		lblCirculVolt.setBounds(40, 52, 46, 14);
		panelCirculator.add(lblCirculVolt);

		textFieldCirculatorVoltage = new JTextField();
		textFieldCirculatorVoltage.setBounds(122, 49, 86, 20);
		panelCirculator.add(textFieldCirculatorVoltage);
		textFieldCirculatorVoltage.setColumns(10);


		// ===============================================================================================================
		//									        PANEL MEASURE 
		// ===============================================================================================================

		JPanel panelCompCOP = new JPanel();
		panelCompCOP.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tabbedPane.addTab("Mesure COP", null, panelCompCOP, null);
		panelCompCOP.setLayout(null);

		// ---------------------------------------------------------------
		// Button Compute
		// ---------------------------------------------------------------
		JButton btnComp = new JButton("Calcul");
		btnComp.setBounds(294, 327, 89, 23);
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
		panelCompCOP.add(btnComp);


		// ---------------------------------------------------------------
		// H1
		// ---------------------------------------------------------------
		JLabel lblH = new JLabel("H1 :");
		lblH.setBounds(25, 76, 30, 14);
		panelCompCOP.add(lblH);

		JLabel lblH1unity = new JLabel("kJ/kg");
		lblH1unity.setBounds(158, 76, 36, 14);
		panelCompCOP.add(lblH1unity);

		textFieldH1 = new JTextField();
		textFieldH1.setText("416");
		textFieldH1.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldH1.setBounds(69, 73, 86, 20);
		textFieldH1.setColumns(10);
		textFieldH1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if ( ( (Character.isDigit(vChar)) || (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == '.') || (vChar == KeyEvent.VK_DELETE)) ) {
					if ( (vChar == '.') && Misc.nbCharInString(textFieldH1.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					e.consume();					
				}
			}
		});
		panelCompCOP.add(textFieldH1);

		// ---------------------------------------------------------------
		// H2
		// ---------------------------------------------------------------
		JLabel lblH2 = new JLabel("H2 :");
		lblH2.setBounds(25, 107, 30, 14);
		panelCompCOP.add(lblH2);

		JLabel lblH2unity = new JLabel("kJ/kg");
		lblH2unity.setBounds(158, 107, 36, 14);
		panelCompCOP.add(lblH2unity);

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
					if ( (vChar == '.') && Misc.nbCharInString(textFieldH2.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelCompCOP.add(textFieldH2);

		// ---------------------------------------------------------------
		// H3 & H4
		// ---------------------------------------------------------------
		JLabel lblH3 = new JLabel("H3=H4 :");
		lblH3.setBounds(25, 138, 46, 14);
		panelCompCOP.add(lblH3);

		JLabel lblH3unity = new JLabel("kJ/kg");
		lblH3unity.setBounds(158, 138, 36, 14);
		panelCompCOP.add(lblH3unity);

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
					if ( (vChar == '.') && Misc.nbCharInString(textFieldH3.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelCompCOP.add(textFieldH3);

		// ---------------------------------------------------------------
		// T0
		// ---------------------------------------------------------------
		JLabel lblT0 = new JLabel("T0 :");
		lblT0.setBounds(25, 14, 30, 14);
		panelCompCOP.add(lblT0);

		JLabel lblT0unity = new JLabel("\u00B0C");
		lblT0unity.setBounds(158, 14, 36, 14);
		panelCompCOP.add(lblT0unity);

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
					if ( (vChar == '.') && Misc.nbCharInString(textFieldT0.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelCompCOP.add(textFieldT0);

		// ---------------------------------------------------------------
		// TK
		// ---------------------------------------------------------------
		JLabel lblTk = new JLabel("TK :");
		lblTk.setBounds(25, 45, 30, 14);
		panelCompCOP.add(lblTk);

		JLabel lblTkunity = new JLabel("\u00B0C");
		lblTkunity.setBounds(158, 42, 36, 14);
		panelCompCOP.add(lblTkunity);

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
					if ( (vChar == '.') && Misc.nbCharInString(textFieldTK.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelCompCOP.add(textFieldTK);

		// ---------------------------------------------------------------
		// Image
		// ---------------------------------------------------------------

		JLabel lblEnthalpyView = new JLabel("New label");
		lblEnthalpyView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblEnthalpyView.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblEnthalpyView.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
					WinEnthalpy window = new WinEnthalpy(enthalpy);
					window.WinEnthalpieVisible();
			}
		});
		lblEnthalpyView.setIcon(new ImageIcon(WinPrime.class.getResource("/pacp/images/enthalpie_150.jpg")));
		lblEnthalpyView.setBounds(233, 11, 150, 100);
		panelCompCOP.add(lblEnthalpyView);

		// ---------------------------------------------------------------
		// Carnot Froid
		// ---------------------------------------------------------------
		JLabel labelCarnotFroid = new JLabel("Carnot Froid :");
		labelCarnotFroid.setBounds(219, 299, 75, 14);
		panelCompCOP.add(labelCarnotFroid);

		textFieldCarnotFroid = new JTextField();
		textFieldCarnotFroid.setBackground(Color.PINK);
		textFieldCarnotFroid.setEditable(false);
		textFieldCarnotFroid.setText("0");
		textFieldCarnotFroid.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCarnotFroid.setColumns(10);
		textFieldCarnotFroid.setBounds(304, 296, 86, 20);
		panelCompCOP.add(textFieldCarnotFroid);
		
		JLabel lblMeasurePointView = new JLabel("New label");
		lblMeasurePointView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				WinMeasurePoints window = new WinMeasurePoints();
				window.WinMeasurePointsVisible();
			}
		});
		lblMeasurePointView.setIcon(new ImageIcon(WinPrime.class.getResource("/pacp/images/Cycle_150.PNG")));
		lblMeasurePointView.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblMeasurePointView.setBounds(233, 141, 150, 93);
		panelCompCOP.add(lblMeasurePointView);

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

	}
}

