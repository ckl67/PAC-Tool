package pacp;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.KeyAdapter;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.UIManager;


public class PACwin {

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

	private JCheckBox checkoxBTU;
	private JCheckBox chckbxPound;

	private void fillScrollTexField(PACcop pac) {

		textFieldScrollName.setText(pac.getName());

		textFieldScrollEvap.setText(String.valueOf(pac.getEvap()));
		textFieldScrollRG.setText(String.valueOf(pac.getRG()));
		textFieldScrollCond.setText(String.valueOf(pac.getCond()));
		textFieldScrollLiq.setText(String.valueOf(pac.getLiq()));
		textFieldScrollCapacity.setText(String.valueOf(pac.getCapacity()));
		textFieldScrollPower.setText(String.valueOf(pac.getPower()));
		textFieldScrollCurrent.setText(String.valueOf(pac.getCurrent()));
		textFieldScrollSurchauffe.setText(String.valueOf(Math.round(pac.getRG() - pac.getEvap())));
		textFieldScrollSousRefroid.setText(String.valueOf(Math.round(pac.getCond() - pac.getLiq())));
		textFieldScrollEER.setText(String.valueOf(Math.round(pac.getCapacity()/pac.getPower()*10.0)/10.0));
		textFieldScrollMassFlow.setText(String.valueOf(pac.getMassFlow()));
		textFieldScrollDeltaH0.setText("-----");
		textFieldScrollVoltage.setText(String.valueOf(pac.getVoltage()));
		double tmp = Math.round(PACmain.cosphi(pac.getPower(), pac.getVoltage(), pac.getCurrent())*10000.0)/10000.0;
		textFieldScrollCosPhi.setText(String.valueOf(tmp));		

	}
	/**
	 * Create the application.
	 */
	public PACwin(PACcop pac) {
		initialize(pac);
		fillScrollTexField(pac);
	}

	/**
	 * 
	 * Initialize the contents of the frame.
	 */
	private void initialize(PACcop pac) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(PACwin.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frame.setTitle("PAC Tool (" + PACmain.getPacToolVersion()+ ")");
		frame.setBounds(100, 100, 443, 574);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		// ===============================================================================================================
		// 													MENU
		// ===============================================================================================================
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);

		JMenu file = new JMenu("File");
		menubar.add(file);

		// ---------------------------------------------------------------
		// Load Config
		// ---------------------------------------------------------------
		JMenuItem mloadcfg = new JMenuItem("Load Config.");
		mloadcfg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JSONParser parser = new JSONParser();  
				try {  

					Object obj = parser.parse(new FileReader("D:/Users/kluges1/workspace/pac-tool/test/PAC-Tool.cfg"));  
					JSONObject jsonObjectL1 = (JSONObject) obj;  

					// Read + Set Scroll
					JSONObject jsonObjectL2 = (JSONObject) jsonObjectL1.get("Scroll");  
					pac.setJsonObject(jsonObjectL2);

					System.out.println("READ JSON object to file");  
					System.out.println("-----------------------");  
					System.out.print(jsonObjectL2);  

					fillScrollTexField(pac);

				} catch (FileNotFoundException e) {  
					e.printStackTrace();  
				} catch (IOException e) {  
					e.printStackTrace();  
				} catch (ParseException e) {  
					e.printStackTrace();  
				}  
			}
		});
		file.add(mloadcfg);

		// ---------------------------------------------------------------
		// Save Config
		// ---------------------------------------------------------------
		JMenuItem msavecfg = new JMenuItem("Save Config.");
		msavecfg.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {

				// Create JSON Data for Class Scroll
				JSONObject ObjPacTool = new JSONObject();  
				ObjPacTool.put("Scroll", pac.getJsonObject());  

				// Create JSON data for the configuration
				JSONObject ObjCfg = new JSONObject();  
				ObjCfg.put("checkoxBTU", checkoxBTU.isSelected());
				ObjCfg.put("chckbxPound", chckbxPound.isSelected());
				ObjPacTool.put("Cfg", ObjCfg);  

				try {  
					// Writing to a file  
					File file=new File("D:/Users/kluges1/workspace/pac-tool/test/PAC-Tool.cfg");  
					file.createNewFile();  
					FileWriter fileWriter = new FileWriter(file);  
					System.out.println("Writing JSON object to file");  
					System.out.println("-----------------------");  
					System.out.print(ObjPacTool);  

					fileWriter.write(ObjPacTool.toJSONString());  
					fileWriter.flush();  
					fileWriter.close();  

				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}
		});
		file.add(msavecfg);

		// ---------------------------------------------------------------
		// Seperator
		// ---------------------------------------------------------------
		JSeparator separator = new JSeparator();
		file.add(separator);

		// ---------------------------------------------------------------
		// Exit
		// ---------------------------------------------------------------
		JMenuItem mexit = new JMenuItem("Exit");
		mexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		file.add(mexit);

		// ---------------------------------------------------------------
		// Help
		// ---------------------------------------------------------------

		JMenu help = new JMenu("Help");
		menubar.add(help);
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PACwabout nabw = new PACwabout();
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
		panel_pc1.setBorder(new TitledBorder(null, "Performance Constructeur 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pc1.setBounds(10, 73, 413, 161);
		panel_pc1.setLayout(null);
		panelPAC.add(panel_pc1);

		// ---------------------------------------------------------------
		// EVAP
		// ---------------------------------------------------------------
		JLabel lblEvap = new JLabel("Evap :");
		lblEvap.setBounds(10, 28, 51, 14);
		panel_pc1.add(lblEvap);

		textFieldScrollEvap = new JTextField();
		textFieldScrollEvap.setToolTipText("Temp\u00E9rature d'\u00E9vaporation (T0)");
		textFieldScrollEvap.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				pac.setEvap(Double.valueOf( textFieldScrollEvap.getText()));

				double tmp = Math.round(pac.getRG() - pac.getEvap());
				textFieldScrollSurchauffe.setText(String.valueOf(tmp));

			}
		});
		textFieldScrollEvap.setBounds(59, 25, 75, 20);
		panel_pc1.add(textFieldScrollEvap);

		textFieldScrollEvap.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollEvap.setColumns(10);

		JLabel lblTemp_unity1 = new JLabel("\u00B0F");
		lblTemp_unity1.setBounds(144, 28, 46, 14);
		panel_pc1.add(lblTemp_unity1);

		// ---------------------------------------------------------------
		// RG
		// ---------------------------------------------------------------
		JLabel lblRG = new JLabel("RG :");
		lblRG.setBounds(10, 67, 51, 14);
		panel_pc1.add(lblRG);

		textFieldScrollRG = new JTextField();
		textFieldScrollRG.setToolTipText("Temp\u00E9rature d'aspiration du compresseur Point : (1)");
		textFieldScrollRG.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {

				pac.setRG(Double.valueOf( textFieldScrollRG.getText()));

				double tmp = Math.round(pac.getRG() - pac.getEvap());
				textFieldScrollSurchauffe.setText(String.valueOf(tmp));			
			}
		});
		textFieldScrollRG.setBounds(59, 64, 75, 20);
		panel_pc1.add(textFieldScrollRG);
		textFieldScrollRG.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollRG.setColumns(10);

		JLabel lblTemp_unity2 = new JLabel("\u00B0F");
		lblTemp_unity2.setBounds(144, 67, 46, 14);
		panel_pc1.add(lblTemp_unity2);

		// ---------------------------------------------------------------
		// SURCHAUFFE
		// ---------------------------------------------------------------
		JLabel lblSurchauffe = new JLabel("Surchauffe :");
		lblSurchauffe.setBounds(10, 101, 81, 14);
		panel_pc1.add(lblSurchauffe);

		textFieldScrollSurchauffe = new JTextField();
		textFieldScrollSurchauffe.setText("0.0");
		textFieldScrollSurchauffe.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollSurchauffe.setBackground(Color.PINK);
		textFieldScrollSurchauffe.setEditable(false);
		textFieldScrollSurchauffe.setBounds(88, 98, 46, 20);
		panel_pc1.add(textFieldScrollSurchauffe);
		textFieldScrollSurchauffe.setColumns(10);

		JLabel lblTemp_unity5 = new JLabel("\u00B0F");
		lblTemp_unity5.setBounds(144, 101, 46, 14);
		panel_pc1.add(lblTemp_unity5);

		// ---------------------------------------------------------------
		// COND
		// ---------------------------------------------------------------
		JLabel lblCond = new JLabel("Cond :");
		lblCond.setBounds(233, 28, 46, 14);
		panel_pc1.add(lblCond);

		textFieldScrollCond = new JTextField();
		textFieldScrollCond.setToolTipText("Temp\u00E9rature de condensation (TK) ");
		textFieldScrollCond.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {

				pac.setCond(Double.valueOf( textFieldScrollCond.getText()));

				double tmp = Math.round(pac.getCond() - pac.getLiq());
				textFieldScrollSousRefroid.setText(String.valueOf(tmp));
			}
		});
		textFieldScrollCond.setBounds(289, 25, 75, 20);
		textFieldScrollCond.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollCond.setColumns(10);
		panel_pc1.add(textFieldScrollCond);

		JLabel lblTemp_unity3 = new JLabel("\u00B0F");
		lblTemp_unity3.setBounds(374, 28, 29, 14);
		panel_pc1.add(lblTemp_unity3);

		// ---------------------------------------------------------------
		// LIQ
		// ---------------------------------------------------------------
		JLabel lblLiq = new JLabel("Liq :");
		lblLiq.setBounds(233, 67, 46, 14);
		panel_pc1.add(lblLiq);

		textFieldScrollLiq = new JTextField();
		textFieldScrollLiq.setToolTipText("Temp\u00E9rature Entr\u00E9e D\u00E9tendeur : Point (3) ");
		textFieldScrollLiq.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {

				pac.setLiq(Double.valueOf( textFieldScrollLiq.getText()));

				double tmp = Math.round(pac.getCond() - pac.getLiq());
				textFieldScrollSousRefroid.setText(String.valueOf(tmp));
			}
		});

		textFieldScrollLiq.setBounds(289, 64, 75, 20);
		textFieldScrollLiq.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollLiq.setColumns(10);
		panel_pc1.add(textFieldScrollLiq);

		JLabel lblTemp_unity4 = new JLabel("\u00B0F");
		lblTemp_unity4.setBounds(374, 64, 29, 14);
		panel_pc1.add(lblTemp_unity4);

		// ---------------------------------------------------------------
		// SOUS REFROIDISSEMENT
		// ---------------------------------------------------------------

		JLabel lblSousRefroid = new JLabel("S-Refroidissement :");
		lblSousRefroid.setBounds(205, 101, 113, 14);
		panel_pc1.add(lblSousRefroid);

		textFieldScrollSousRefroid = new JTextField();
		textFieldScrollSousRefroid.setText("0.0");
		textFieldScrollSousRefroid.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldScrollSousRefroid.setEditable(false);
		textFieldScrollSousRefroid.setColumns(10);
		textFieldScrollSousRefroid.setBackground(Color.PINK);
		textFieldScrollSousRefroid.setBounds(318, 98, 46, 20);
		panel_pc1.add(textFieldScrollSousRefroid);

		JLabel lblTemp_unity6 = new JLabel("\u00B0F");
		lblTemp_unity6.setBounds(374, 101, 29, 14);
		panel_pc1.add(lblTemp_unity6);

		// ---------------------------------------------------------------
		// Check Box Farenheit / Celcius
		// ---------------------------------------------------------------
		JCheckBox checkoxFaren = new JCheckBox("Farenheit");
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

					pac.setEvap(PACmain.degre2farenheit(pac.getEvap()));
					textFieldScrollEvap.setText(String.valueOf(Math.round(pac.getEvap()*100.0)/100.0));

					pac.setRG(PACmain.degre2farenheit(pac.getRG()));
					textFieldScrollRG.setText(String.valueOf(Math.round(pac.getRG()*100.0)/100.0));

					pac.setCond(PACmain.degre2farenheit(pac.getCond()));
					textFieldScrollCond.setText(String.valueOf(Math.round(pac.getCond()*100.0)/100.0));

					pac.setLiq(PACmain.degre2farenheit(pac.getLiq()));
					textFieldScrollLiq.setText(String.valueOf(Math.round(pac.getLiq()*100.0)/100.0));

					textFieldScrollSurchauffe.setText(String.valueOf(Math.round(pac.getRG() - pac.getEvap())));
					textFieldScrollSousRefroid.setText(String.valueOf(Math.round(pac.getCond() - pac.getLiq())));
				} else {
					lblTemp_unity1.setText("°C");
					lblTemp_unity2.setText("°C");
					lblTemp_unity3.setText("°C");
					lblTemp_unity4.setText("°C");
					lblTemp_unity5.setText("°C");
					lblTemp_unity6.setText("°C");

					pac.setEvap(PACmain.farenheit2degre(pac.getEvap()));
					textFieldScrollEvap.setText(String.valueOf(Math.round(pac.getEvap()*100.0)/100.0));

					pac.setRG(PACmain.farenheit2degre(pac.getRG()));
					textFieldScrollRG.setText(String.valueOf(Math.round(pac.getRG()*100.0)/100.0));

					pac.setCond(PACmain.farenheit2degre(pac.getCond()));
					textFieldScrollCond.setText(String.valueOf(Math.round(pac.getCond()*100.0)/100.0));

					pac.setLiq(PACmain.farenheit2degre(pac.getLiq()));
					textFieldScrollLiq.setText(String.valueOf(Math.round(pac.getLiq()*100.0)/100.0));

					textFieldScrollSurchauffe.setText(String.valueOf(Math.round(pac.getRG() - pac.getEvap())));
					textFieldScrollSousRefroid.setText(String.valueOf(Math.round(pac.getCond() - pac.getLiq())));
				}
			}
		});
		checkoxFaren.setSelected(true);

		// ================================================================
		// 					   	Performance 2 Panel
		// ================================================================
		JPanel panel_pc2 = new JPanel();
		panel_pc2.setBounds(10, 245, 413, 241);
		panel_pc2.setBorder(new TitledBorder(null, "Performance Constructeur 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pc2.setLayout(null);
		panelPAC.add(panel_pc2);

		checkoxBTU = new JCheckBox("BTU/hr");
		checkoxBTU.setToolTipText("British Thermal Unit / hour");
		chckbxPound = new JCheckBox("lbs/h");

		// ---------------------------------------------------------------
		// Capacity
		// ---------------------------------------------------------------

		JLabel lblCapacity = new JLabel("Capacity :");
		lblCapacity.setToolTipText("");
		lblCapacity.setBounds(10, 28, 73, 14);
		panel_pc2.add(lblCapacity);

		textFieldScrollCapacity = new JTextField();
		textFieldScrollCapacity.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {

				pac.setCapacity(Double.valueOf( textFieldScrollCapacity.getText()));

				double tmp = Math.round(pac.getCapacity()/pac.getPower()*10.0)/10.0;
				textFieldScrollEER.setText(String.valueOf(tmp));

				if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
					textFieldScrollDeltaH0.setText("-----");
				} else {
					tmp = Math.round(pac.getCapacity()/pac.getMassFlow()/1000.0);
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
		JLabel lblPower = new JLabel("Power :");
		lblPower.setBounds(10, 59, 73, 14);
		panel_pc2.add(lblPower);

		textFieldScrollPower = new JTextField();
		textFieldScrollPower.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {

				pac.setPower(Double.valueOf( textFieldScrollPower.getText()));

				double tmp = Math.round(pac.getCapacity()/pac.getPower()*10.0)/10.0;
				textFieldScrollEER.setText(String.valueOf(tmp));

				tmp = Math.round(PACmain.cosphi(pac.getPower(), pac.getVoltage(), pac.getCurrent())*10000.0)/10000.0;
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
		JLabel lblCourant = new JLabel("Current :");
		lblCourant.setBounds(10, 90, 73, 14);
		panel_pc2.add(lblCourant);

		textFieldScrollCurrent = new JTextField();
		textFieldScrollCurrent.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				pac.setCurrent(Double.valueOf( textFieldScrollCurrent.getText()));

				double tmp = Math.round(PACmain.cosphi(pac.getPower(), pac.getVoltage(), pac.getCurrent())*10000.0)/10000.0;
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
		JLabel lblEer = new JLabel("EER :");
		lblEer.setBounds(10, 128, 73, 14);
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

		JLabel lblMassflow = new JLabel("MassFlow :");
		lblMassflow.setBounds(220, 28, 73, 14);
		panel_pc2.add(lblMassflow);

		textFieldScrollMassFlow = new JTextField();
		textFieldScrollMassFlow.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				pac.setMassFlow(Double.valueOf( textFieldScrollMassFlow.getText()));

				if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
					textFieldScrollDeltaH0.setText("-----");
				} else {
					double tmp = Math.round(pac.getCapacity()/pac.getMassFlow()/1000.0);
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

					pac.setCapacity(PACmain.watt2btuhr(pac.getCapacity()));
					textFieldScrollCapacity.setText(String.valueOf(Math.round(pac.getCapacity()*100.0)/100.0));

					textFieldScrollEER.setText(String.valueOf(Math.round(pac.getCapacity()/pac.getPower()*10.0)/10.0));

					textFieldScrollDeltaH0.setText("-----");

				} else {
					lblCapacity_unity.setText("Watt");
					lblEER_unity.setText("");

					pac.setCapacity(PACmain.buthr2watt(pac.getCapacity()));
					textFieldScrollCapacity.setText(String.valueOf(Math.round(pac.getCapacity()*100.0)/100.0));

					textFieldScrollEER.setText(String.valueOf(Math.round(pac.getCapacity()/pac.getPower()*10.0)/10.0));

					if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
						textFieldScrollDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(pac.getCapacity()/pac.getMassFlow()/1000.0);
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

					pac.setMassFlow(PACmain.kg2pound(pac.getMassFlow()) );
					textFieldScrollMassFlow.setText(String.valueOf(Math.round(pac.getMassFlow()*10.0)/10.0));

					textFieldScrollDeltaH0.setText("-----");

				} else {
					lblMassFlow_unity.setText("Kg/s");

					pac.setMassFlow(PACmain.pound2kg(pac.getMassFlow()) );
					textFieldScrollMassFlow.setText(String.valueOf(Math.round(pac.getMassFlow()*10000.0)/10000.0));		

					if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
						textFieldScrollDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(pac.getCapacity()/pac.getMassFlow()/1000.0);
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

		JLabel lblDeltaH0 = new JLabel("H1-H3");
		lblDeltaH0.setBounds(223, 59, 62, 14);
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
		JLabel lblVoltage = new JLabel("Voltage :");
		lblVoltage.setBounds(10, 177, 73, 14);
		panel_pc2.add(lblVoltage);

		textFieldScrollVoltage = new JTextField();
		textFieldScrollVoltage.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {			

				pac.setVoltage(Double.valueOf( textFieldScrollVoltage.getText()));

				double tmp = Math.round(PACmain.cosphi(pac.getPower(), pac.getVoltage(), pac.getCurrent())*10000.0)/10000.0;
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
		JLabel lblCosphi = new JLabel("Cos (Phi)");
		lblCosphi.setBounds(10, 208, 73, 14);
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
		// Combo Box Scroll compressor
		// ---------------------------------------------------------------
		JComboBox comboBoxScroll = new JComboBox();
		comboBoxScroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(comboBoxScroll.getSelectedItem());
				System.out.println(comboBoxScroll.getComponentCount());
			}
		});
		comboBoxScroll.setModel(new DefaultComboBoxModel(new String[] {"one", "two", "three"}));
		comboBoxScroll.setBounds(277, 10, 145, 20);
		panelPAC.add(comboBoxScroll);

		// ---------------------------------------------------------------
		// Compressor Name
		// ---------------------------------------------------------------
		textFieldScrollName = new JTextField();
		textFieldScrollName.setForeground(new Color(0, 0, 128));
		textFieldScrollName.setBorder(null);
		textFieldScrollName.setBackground(UIManager.getColor("Button.background"));
		textFieldScrollName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldScrollName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {

				pac.setName( textFieldScrollName.getText());
			}
		});
		textFieldScrollName.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldScrollName.setBounds(25, 10, 167, 52);
		panelPAC.add(textFieldScrollName);
		textFieldScrollName.setColumns(10);

		JButton btnSaveNewScroll = new JButton("Save");
		btnSaveNewScroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSaveNewScroll.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnSaveNewScroll.setBounds(277, 39, 68, 23);
		panelPAC.add(btnSaveNewScroll);

		JButton btnSaveDeleteScroll = new JButton("Delete");
		btnSaveDeleteScroll.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnSaveDeleteScroll.setBounds(355, 39, 68, 23);
		panelPAC.add(btnSaveDeleteScroll);

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
				pac.setH1(Double.parseDouble(textFieldH1.getText()));
				pac.setH2(Double.parseDouble(textFieldH2.getText()));
				pac.setH3(Double.parseDouble(textFieldH3.getText()));
				pac.setT0(Double.parseDouble(textFieldT0.getText()));
				pac.setTK(Double.parseDouble(textFieldTK.getText()));

				textFieldCarnotFroid.setText(String.valueOf(
						Math.round(pac.cop_carnot_froid()*100.0) /100.0
						));

				/*
						System.out.format("TO = %.2f°C\n",pac.getT0());
						System.out.format("TK = %.2f°C\n",pac.getTK());
						System.out.format("H1 = %.2fkJ/kg\n",pac.getH1());
						System.out.format("H2 = %.2fkJ/kg\n",pac.getH2());
						System.out.format("H3=H4 = %.2fkJ/kg\n",pac.getH3());
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
		lblNewLabel.setIcon(new ImageIcon(PACwin.class.getResource("/pacp/images/enthalpie.jpg")));
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
}
